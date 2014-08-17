package cluedo.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import cluedo.items.Character;
import cluedo.items.Room;
import cluedo.items.Type;
import cluedo.items.Weapon;
import cluedo.items.Character.CharaType;
import cluedo.items.Room.RoomType;
import cluedo.other.Card;
import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.tile.BoardTile;
import cluedo.tile.DoorTile;
import cluedo.tile.EmptyTile;
import cluedo.tile.HallwayTile;
import cluedo.tile.RoomTile;
import cluedo.tile.SecretTile;

/**
 * The board class represents the cluedo game board. This class is used for all
 * the in game functionalities such as moving the players, making an accusation
 * or suggestion
 * 
 * @author Shweta Barapatre and Maria Libunao
 *
 */
public class Board {
	private BoardTile[][] tiles;
	private List<Player> players;
	private Set<Card> solution;
	private Map<String, Room> rooms;
	private Set<Weapon> weapons;
	private Icon picLabel; // picture that will appear on popup windows
	private CluedoCanvas canvas;// the canvas that called this board

	private int firstDice = 0; // result of first die
	private int secondDice = 0;// result of second die
	private int toMove = 0; // amount of spaces the player can move. The result
							// from dice roll
	private int numEliminated = 0; // number of players that have been
									// eliminated
	private int state; // this is used to tell us what state we're in.
	private int currentPlayer = 0; // the index of which player's turn it is

	private boolean hasRolledDice = false; // whether or not the current player
											// has
	private boolean hasMoved = false; // done any of these things already
	private boolean hasSuggested = false;

	public static final int WAITING = 0;
	public static final int READY = 1;
	public static final int PLAYING = 2;
	public static final int GAMEOVER = 3;
	private static final int ROW = 25;
	private static final int COL = 24;

	/**
	 * Constructs a board from the file, creating the appropriate tiles as
	 * specified in the 'board.txt' file. Sets up the rooms and weapons that will
	 * be on the board. Sets the state to waiting, indicating it is waiting for
	 * all of the players to be added to the board first.
	 * 
	 * @param canvas
	 *            the canvas that is used to call the board
	 */
	//
	public Board(CluedoCanvas canvas) {
		this.canvas = canvas;
		tiles = new BoardTile[ROW][COL];
		setupRooms();

		try {
			createBoardFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setupWeapons();
		players = new ArrayList<Player>();
		solution = new HashSet<Card>();
		state = WAITING;
	}

	// state changing methods
	/**
	 * changes the state from waiting to ready. deals the cards to the players
	 * ensures that board is set up and players are created before attempting to
	 * allocate cards to them starts the game going.
	 */
	public void readyToStart() {
		if (state == WAITING) {
			state = READY;
			dealCards();
			startGame();
		}
	}

	/**
	 * Sets the game going. only runs if the state is ready. Changes the state
	 * to playing pop up window informs the player who has the first turn
	 */
	public void startGame() {
		if (state == READY) {
			state = PLAYING;

			getCanvas().getFrame().createCardPanel();
			getCanvas().getFrame().revalidate();
			popupWithPlayerIcon("It is " + players.get(currentPlayer).getName()
					+ "'s turn, as "
					+ players.get(currentPlayer).getCharacter().getType());

		}

	}

	// IN GAME METHODS
	/**
	 * Moves the current player to where they have clicked on the screen, if it
	 * is a valid move and the state of the game is playing. A valid move are as
	 * follows: -player has not moved in this turn and has not rolled the dice.
	 * They are in a room with a secret passage. They click on that secret
	 * passage tile to be transported to the room the passage connects to
	 * -player has not moved in this turn and has rolled the dice. The tile they
	 * want to go to is not an empty tile. They have rolled a sufficient amount
	 * to get to that tile from their old tile.
	 * If it was not a valid move, a pop
	 * up window will inform the player that it is invalid
	 * 
	 * @param Position
	 *            newPos the new position of the player
	 * */
	public void move(Position newPos) {
		if (state != PLAYING) {
			return;
		}
		if (hasMoved) {
			JOptionPane.showMessageDialog(canvas.getFrame(), "You have already moved.");
			return;
		}
		Player player = players.get(currentPlayer);
		Position oldPos = player.getPosition();
		BoardTile before = tiles[oldPos.getY()][oldPos.getX()];
		BoardTile after = tiles[newPos.getY()][newPos.getX()];

		if (after instanceof SecretTile) {// player clicked on a secret tile
			if (!(before instanceof RoomTile)) {
				JOptionPane.showMessageDialog(canvas.getFrame(),
						"Must be in the room before using secret passage");
				return;
			}
			RoomTile roomOrigin = (RoomTile) before;
			SecretTile passage = (SecretTile) after;
			if (roomOrigin.getRoom().getType() == passage.getOrigin().getType()) {// player was in the
															// room the secret
															// passage is in
				RoomTile destination = passage.getPassageTo()
						.getUnoccupiedTile(); // get some tile in the room the
												// passage leads to
				moveToTile(before, destination, player);
			} else {// player was not in room the secret passage is in
				JOptionPane.showMessageDialog(canvas.getFrame(),
						"Must be in the same room as secret passage");
			}
		} else if (!hasRolledDice) {
			JOptionPane.showMessageDialog(canvas.getFrame(),
					"Please roll dice first or click on a secret passage");
		} else {
			oldPos = before.posWhenMovedOut(newPos);
			if (after.canMoveToTile(before, oldPos, toMove)) {
				moveToTile(before, after, player);
			} else {
				JOptionPane.showMessageDialog(canvas.getFrame(), "Invalid move");
			}
		}
	}

	/**
	 * this method occurs if the player clicks on the end turn button,
	 * indicating they want their turn to be over intialises all the booleans
	 * back to false changes the current player to the next player so long as
	 * they are not eliminated by making a wrong accusation, otherwise skips
	 * them makes a new pop up indicating whose turn it is now
	 */
	public void endTurn() {
		if(state != PLAYING){return;}
		toMove = 0;
		firstDice = 0;
		secondDice = 0;
		hasRolledDice = false;
		hasMoved = false;
		hasSuggested = false;
		int next = nextPlayer(currentPlayer);

		while (true) {
			if (!(players.get(next).isEliminated())) {
				currentPlayer = next;
				popupWithPlayerIcon("It is "
						+ players.get(currentPlayer).getName() + "'s turn, as "
						+ players.get(currentPlayer).getCharacter().getType());

				return;
			}
			next = nextPlayer(next);
		}
	}

	/**
	 * This method is executed when the player has clicked roll the dice button
	 * picks two random numbers from 1-6 and displays what they have rolled in a
	 * pop up. If they have already pressed the roll button before in their turn,
	 * the pop up displays the same values they rolled the first time
	 */
	public void diceRolled() {
		if (state == GAMEOVER) {
			return;
		}
		if (!hasRolledDice) {
			firstDice = (int) (Math.random() * 6 + 1);
			secondDice = (int) (Math.random() * 6 + 1);
			toMove = firstDice + secondDice;
			hasRolledDice = true;
			Image one = CluedoCanvas.loadImage("d" + firstDice + ".png");
			Image two = CluedoCanvas.loadImage("d" + secondDice + ".png");
			Image combined = attachImages((BufferedImage) one,
					(BufferedImage) two);

			picLabel = new ImageIcon(combined);
			JOptionPane.showMessageDialog(canvas.getFrame(), "You have rolled a "
					+ firstDice + " and a " + secondDice, "Rolled Dice",
					JOptionPane.PLAIN_MESSAGE, picLabel);
		}

		else if (hasRolledDice) {
			JOptionPane.showMessageDialog(canvas.getFrame(), "You already rolled a "
					+ firstDice + " and a " + secondDice,
					"Already Rolled This Turn", JOptionPane.PLAIN_MESSAGE,
					picLabel);
		}

	}

	/**
	 * This method executes if the player wants to make a suggestion. Does
	 * nothing if state of game is game over. Checks suggested types and displays
	 * the card if it is it amongst the other players hand tells the player if
	 * it does not find a card of one of the things they suggested
	 */
	public void makeSuggestion() {
		if (state != PLAYING) {
			return;
		}
		if (hasSuggested) {
			JOptionPane.showMessageDialog(canvas.getFrame(),
					"You can only suggest once in a turn");
			return;
		}
		Player p = players.get(currentPlayer);
		Position pos = p.getPosition();
		if (!(tiles[pos.getY()][pos.getX()] instanceof RoomTile)) {
			JOptionPane.showMessageDialog(canvas.getFrame(),
					"You cannot make a suggestion outside of a room");
			return;
		}

		hasSuggested = true;
		Room currentRoom = ((RoomTile) tiles[pos.getY()][pos.getX()]).getRoom();
		Type currentRoomType = currentRoom.getType();
		List<Type> items = popupOptions(false); //get user suggestions
		if (items == null) {// user pressed cancel
			return;
		} 
		hasSuggested = true;
		Set<Type> chosenItems = new HashSet<Type>(items);
		chosenItems.add(currentRoomType);
		moveForSuggestion(items, currentRoom);//move suggested items to current room
		Type refutedItem = null;
		for (int i = 0; i < players.size() - 1; i++) {//finding a card in other player's decks that matches one of the suggested items
			Player nextPlayer = players.get(nextPlayer(currentPlayer));
			refutedItem = nextPlayer.refuteSuggestion(chosenItems);
			if (refutedItem != null) {
				break;
			}
		}
		String message = null;
		if (refutedItem == null) {
			message = "The other players cannot refute your suggestion";
			int random = (int) (Math.random() * 1 + 1);
			String filename = "unknown"+random+".jpg";
			Image image = CluedoCanvas.loadImage(filename);
			picLabel = new ImageIcon(image);
			JOptionPane.showMessageDialog(canvas.getFrame(), message, "Suggestion",JOptionPane.PLAIN_MESSAGE, picLabel);
		} else {
			message = "A player has " + refutedItem + " in their cards";
			String filename = refutedItem+".jpg";
			Image image = CluedoCanvas.loadImage(filename);
			picLabel = new ImageIcon(image);
			JOptionPane.showMessageDialog(canvas.getFrame(), message, "Suggestion",JOptionPane.PLAIN_MESSAGE, picLabel);
		}
	}

	/**
	 * This method is executed if a player clicks the accusation button. does
	 * nothing if game state is game over checks against solution - if right,
	 * ends the game, if wrong a pop up shows indicating the real solution, the
	 * player is eliminated they no longer have a turn
	 */
	public void makeAccusation() {
		if (state == GAMEOVER) {
			return;
		}
		Player p = players.get(currentPlayer);
		List<Type> items = popupOptions(true);
		if (items == null) {// user pressed cancel
			return;
		}
		Set<Type> chosenItems = new HashSet<Type>(items);
		boolean lost = false;
		for (Card c : solution) {
			if (!(chosenItems.contains(c.cardType()))) {//one of the items they have selected is not in the solution
				lost = true;
				p.setEliminated(true);
				break;
			}
		}
		if (lost) {
			String message = "You have made a wrong accusation. The solution is: ";
			for (Card c : solution) {
				message += c.cardType() + " ";
			}
			JOptionPane.showMessageDialog(canvas.getFrame(), message);
			if (++numEliminated == players.size()) {
				endGame(false);
			} else {
				endTurn();
			}
		} else {
			endGame(true);
		}

	}

	/**
	 * changes the current player to the next player
	 * Assumes all players have been added
	 * 
	 * @param currentPlayer
	 *            the current player
	 * @return next player
	 */
	private int nextPlayer(int currentPlayer) {
		int next = currentPlayer + 1;
		if (next == players.size()) {
			return 0;
		}
		return next;
	}

	// SETUP METHODS
	/**
	 * this is the method that is used to set up the board from the text file
	 * 
	 * @throws IOException
	 */
	private void createBoardFromFile() throws IOException {
		String filename = "cluedo.txt";
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> lines = new ArrayList<String>();
		int width = -1;
		String line;
		while ((line = br.readLine()) != null) {
			lines.add(line);
			if (width == -1) {
				width = line.length();
			} else if (width != line.length()) {
				throw new IllegalArgumentException("Input file \"" + filename
						+ "\" is malformed; line " + lines.size()
						+ " incorrect width.");
			}
		}
		br.close();
		for (int y = 0; y < lines.size(); y++) {
			line = lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				char c = line.charAt(x);
				Position p = new Position(x, y);
				switch (c) {
				case '!':
					tiles[y][x] = new SecretTile(p, rooms.get("K"),
							rooms.get("T"));
					break;
				case '?':
					tiles[y][x] = new SecretTile(p, rooms.get("T"),
							rooms.get("K"));
					break;
				case '@':
					tiles[y][x] = new SecretTile(p, rooms.get("G"),
							rooms.get("C"));
					break;
				case '$':
					tiles[y][x] = new SecretTile(p, rooms.get("C"),
							rooms.get("G"));
					break;
				case 'X':
					tiles[y][x] = new EmptyTile(p);
					break;
				case 'H':
					tiles[y][x] = new HallwayTile(p);
					break;
				case 'K':
				case 'C':
				case 'B':
				case 'N':
				case 'R':
				case 'L':
				case 'A':
				case 'G':
				case 'T':
					String s = String.valueOf(c);
					RoomTile r = new RoomTile(p, rooms.get(s));
					tiles[y][x] = r;
					rooms.get(s).addRoomTile(r);
					break;
				default:
					if (java.lang.Character.isDigit(c)) {
						DoorTile d = new DoorTile(p);
						tiles[y][x] = d;
						switch (c) {
						case '1':
							rooms.get("K").addDoorTile(d);
							break;
						case '2':
							rooms.get("B").addDoorTile(d);
							break;
						case '3':
							rooms.get("C").addDoorTile(d);
							break;
						case '4':
							rooms.get("R").addDoorTile(d);
							break;
						case '5':
							rooms.get("L").addDoorTile(d);
							break;
						case '6':
							rooms.get("T").addDoorTile(d);
							break;
						case '7':
							rooms.get("A").addDoorTile(d);
							break;
						case '8':
							rooms.get("G").addDoorTile(d);
							break;
						case '9':
							rooms.get("N").addDoorTile(d);
							break;
						}
					}
				}
			}
		}

	}

	/**
	 * sets up the rooms collections
	 */
	private void setupRooms() {
		rooms = new HashMap<String, Room>();
		rooms.put("K", new Room(RoomType.KITCHEN));
		rooms.put("C", new Room(RoomType.CONSERVATORY));
		rooms.put("B", new Room(RoomType.BALLROOM));
		rooms.put("N", new Room(RoomType.DINING_ROOM));
		rooms.put("R", new Room(RoomType.BILLIARD_ROOM));
		rooms.put("L", new Room(RoomType.LIBRARY));
		rooms.put("A", new Room(RoomType.HALL));
		rooms.put("G", new Room(RoomType.LOUNGE));
		rooms.put("T", new Room(RoomType.STUDY));
	}

	/**
	 * sets up the weapons collection
	 */
	private void setupWeapons() {
		weapons = new HashSet<Weapon>();
		List<Room> room = new ArrayList<Room>(rooms.values());
		Collections.shuffle(room);
		int i = 0;
		for (Weapon.WeaponType w : Weapon.WeaponType.values()) {
			Weapon weapon = new Weapon(w);
			weapon.setRoom(room.get(i));
			weapons.add(weapon);
			room.get(i).setWeapon(weapon);
			i++;
		}
	}

	// HELPER METHODS
	/**
	 * helper method for moving a player onto the given tile
	 * 
	 * @param before
	 *            the boardtile they are on before the move
	 * @param after
	 *            board tile they are now on after the move
	 * @param player
	 *            player that is being moved
	 */
	private void moveToTile(BoardTile before, BoardTile after, Player player) {
		player.move(after.getPosition());
		before.movePlayerOut();
		after.movePlayerIn(player);
		hasMoved = true;
		canvas.repaint();
	}

	/**
	 * Helper method that moves suggested items into the room they have been
	 * suggested in
	 * 
	 * @param chosenItems
	 *            the items that have been suggested by player
	 * @param currentRoom
	 *            the current room of the current player
	 */
	private void moveForSuggestion(List<Type> chosenItems, Room currentRoom) {
		Weapon.WeaponType w = (Weapon.WeaponType) chosenItems.get(0);
		for (Weapon weapon : weapons) {
			if (weapon.getType() == w) {
				Room oldRoom = weapon.getRoom();
				Weapon currentWeapon = currentRoom.getWeapon();
				currentRoom.setWeapon(weapon);
				oldRoom.setWeapon(currentWeapon);
				break;
			}
		}
		Character.CharaType c = (CharaType) chosenItems.get(1);
		for (Player p : players) {
			if (p.getCharacter().getType() == c) {
				RoomTile toTile = currentRoom.getUnoccupiedTile();
				BoardTile before = tiles[p.getPosition().getY()][p
						.getPosition().getX()];
				moveToTile(before, toTile, p);
				break;
			}
		}

	}

	/**
	 * helper method for checking if an accusation is correct. pop up comes up
	 * indicating the current player has won, or if all players have been
	 * eliminated pop up indicates the players have all lost and shows correct
	 * solution
	 * 
	 * @param gameWon
	 *            true if a player has selected the right solution, false if all
	 *            players have been eliminated
	 */

	private void endGame(boolean gameWon) {
		if (gameWon) {
			String name = players.get(currentPlayer).getName();
			popupWithPlayerIcon(name + " has solved the murder!. " + name
					+ " has won!");

		} else {
			String message = "The murder has not been solved. You lose. The solution was ";
			for (Card c : solution) {
				message += c.cardType() + " ";
			}
			JOptionPane.showMessageDialog(canvas.getFrame(), message);
		}
		state = GAMEOVER;
	}

	/**
	 * helper method for making the pop up for showing the options to the player
	 * if they want to make a suggestion or accusation
	 * 
	 * @param forAccusation
	 *            true if player has selected make accusation, false for
	 *            suggestion
	 * @return
	 */

	private List<Type> popupOptions(boolean forAccusation) {
		List<Type> chosenItems = new ArrayList<Type>();
		String label = "Make Suggestion";
		if (forAccusation) {
			label = "Make Accusation";
			Object[] roomOptions = Room.RoomType.values();
			Type room = (Type) JOptionPane.showInputDialog(canvas.getFrame(), "Which room?",
					label, JOptionPane.PLAIN_MESSAGE, null, roomOptions,
					roomOptions[0]);
			if (room == null) {
				return null;
			}// user pressed cancel
			chosenItems.add(room);
		}
		Object[] weaponOptions = Weapon.WeaponType.values();
		Type weapon = (Type) JOptionPane.showInputDialog(canvas.getFrame(), "Which weapon?",
				label, JOptionPane.PLAIN_MESSAGE, null, weaponOptions,
				weaponOptions[0]);
		if (weapon == null) {
			return null;
		}// user pressed cancel
		chosenItems.add(weapon);
		Object[] characterOptions = Character.CharaType.values();
		Type character = (Type) JOptionPane.showInputDialog(canvas.getFrame(),
				"Which character?", label, JOptionPane.PLAIN_MESSAGE, null,
				characterOptions, characterOptions[0]);
		if (character == null) {
			return null;
		}// user pressed cancel
		chosenItems.add(character);
		return chosenItems;
	}

	/**
	 * helper method for starting up the game, deals the cards that are not the
	 * solution to the players that have been created
	 */
	private void dealCards() {
		List<Card> weaponCards = new ArrayList<Card>();
		List<Card> characterCards = new ArrayList<Card>();
		List<Card> roomCards = new ArrayList<Card>();
		List<Card> allCards = new ArrayList<Card>();
		for (Weapon w : weapons) {
			Image image = CluedoCanvas.loadImage(w.getType().toString()
					+ ".jpg");
			weaponCards.add(new Card(w, image));
		}
		addToSolution(weaponCards);
		for (Character.CharaType c : Character.CharaType.values()) {
			Image image = CluedoCanvas.loadImage(c.toString() + ".jpg");
			characterCards.add(new Card(new Character(c), image));
		}
		addToSolution(characterCards);
		for (Room.RoomType r : Room.RoomType.values()) {
			Image image = CluedoCanvas.loadImage(r.toString() + ".jpg");
			roomCards.add(new Card(new Room(r), image));
		}
		addToSolution(roomCards);
		allCards.addAll(weaponCards);
		allCards.addAll(characterCards);
		allCards.addAll(roomCards);
		Collections.shuffle(allCards);
		dealToPlayers(allCards);
	}

	/**
	 * helper method for adding a card to the solutions collection
	 * 
	 * @param cards
	 */
	private void addToSolution(List<Card> cards) {
		int i = new Random().nextInt(cards.size());
		solution.add(cards.remove(i));
	}

	/**
	 * helper method for dealing a set of cards equally to the set of players
	 * 
	 * @param cards
	 */
	private void dealToPlayers(Collection<Card> cards) {

		int currentPlayer = 0;
		for (Card c : cards) {
			;
			players.get(currentPlayer).addCard(c);
			currentPlayer = nextPlayer(currentPlayer);
		}

	}
	
	/**
	 * method that creates the pop up with a given message, with the current
	 * player's picture
	 * 
	 * @param message
	 *            the message that will be in the pop up
	 */
	private void popupWithPlayerIcon(String message) {
		String filename = players.get(currentPlayer).getCharacter().getType()
				+ "oval.png";
		Image image = CluedoCanvas.loadImage(filename);
		picLabel = new ImageIcon(image);
		JOptionPane.showMessageDialog(canvas.getFrame(), message, "Cluedo",
				JOptionPane.PLAIN_MESSAGE, picLabel);
	}
	
	/**Merges two images together and returns the resulting image
	 * @param img1
	 * @param img2
	 * @return
	 * */
	private BufferedImage attachImages(BufferedImage img1, BufferedImage img2) {
		BufferedImage resultImage = new BufferedImage(img1.getWidth() + img2.getWidth(),img1.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = resultImage.getGraphics();
		g.drawImage(img1, 0, 0, null);
		g.drawImage(img2, img1.getWidth(), 0, null);
		return resultImage;

	}

	// GETTERS AND SETTERS

	public BoardTile[][] getTiles() {
		return tiles;
	}

	public Collection<Room> getRooms() {
		return rooms.values();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getCurrentPlayer() {
		if (state != WAITING) {
			return players.get(currentPlayer);
		} else
			return null;
	}

	public int getState() {
		return state;
	}

	public Set<Weapon> getWeapons() {
		return weapons;
	}

	public CluedoCanvas getCanvas() {
		return canvas;
	}

	

}
