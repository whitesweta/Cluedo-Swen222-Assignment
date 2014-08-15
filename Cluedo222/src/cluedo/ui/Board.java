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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cluedo.other.Card;
import cluedo.other.Character;
import cluedo.other.Character.CharaType;
import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.other.Room;
import cluedo.other.Room.RoomType;
import cluedo.other.Type;
import cluedo.other.Weapon;
import cluedo.tile.BoardTile;
import cluedo.tile.DoorTile;
import cluedo.tile.EmptyTile;
import cluedo.tile.HallwayTile;
import cluedo.tile.RoomTile;
import cluedo.tile.SecretTile;

public class Board {
	private BoardTile[][] tiles;
	private List<Player> players;
	private Set<Card> solution;
	private Map<String,Room> rooms;
	private Set<Weapon> weapons;
	private static final int ROW = 25;
	private static final int COL = 24;
	private int numEliminated = 0; //number of players that have been eliminated
	
	int state; // this is used to tell us what state we're in. 
	private int currentPlayer = 0; //the index of which player's turn it is
	private boolean hasRolledDice = false; //whether or not the current player has 
	private boolean hasMoved = false;	   //done any of these things already
	private boolean hasSuggested = false;
	private int toMove = 0; //amount of spaces the player can move. The result from dice roll
	private CluedoCanvas canvas;
	
	public static final int WAITING = 0;
	public static final int READY = 1;
	public static final int PLAYING = 2;
	public static final int GAMEOVER = 3;
	public static final int GAMEWON = 4;
	
	//Constructor. sets up board with all the tiles from files. creates player and solution collections
	public Board(CluedoCanvas canvas) {
		this.canvas=canvas;
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
	
	//state changing methods

	public void readyToStart() {
		if (state == WAITING) {
			state = READY;
			dealCards();
		}
		startGame();
	}

	public void startGame(){
		if (state == READY) {
			state = PLAYING;
			getCanvas().getFrame().createCardPanel();
			getCanvas().getFrame().revalidate();
			String filename=players.get(currentPlayer).getCharacter().getType()+"oval.png";
				
			
			Image image = canvas.loadImage(filename);

			picLabel = new ImageIcon(image);
			JOptionPane.showMessageDialog(null,"It is "+players.get(currentPlayer).getName()+"'s turn, as "+players.get(currentPlayer).getCharacter().getType(),"First Turn", JOptionPane.PLAIN_MESSAGE,  picLabel);
			
		}
		
	}
	
	//in game methods
	
	/**Moves the current player to where they have clicked on the screen, if it is a valid move.
	 * A valid move are as follows:
	 * -player has not moved in this turn and has not rolled the dice. They are in a room with a secret passage. They click on that secret passage tile to be transported to the room the passage connects to
	 * -player has not moved in this turn and has rolled the dice. The tile they want to go to is not an empty tile. They have rolled a sufficient amount to get to that tile from their old tile
	 * If it was not a valid move, a popup window will inform the player that it is invalid
	 * @param Position newPos*/
	public void move (Position newPos){
		if(hasMoved){
			JOptionPane.showMessageDialog(null, "You have already moved.");
			return;
		}
		Player player = players.get(currentPlayer);
		Position oldPos = player.getPosition();
		BoardTile before = tiles[oldPos.getY()][oldPos.getX()];
		BoardTile after = tiles[newPos.getY()][newPos.getX()];

		if(after instanceof SecretTile){//player clicked on a secret tile
			if(!(before instanceof RoomTile)){
				JOptionPane.showMessageDialog(null, "Must be in the room before using secret passage");
			}
			RoomTile roomOrigin = (RoomTile) before;
			SecretTile passage = (SecretTile) after;
			if(roomOrigin.equals(passage.getOrigin())){//player was in the room the secret passage is in
				RoomTile destination = passage.getPassageTo().getUnoccupiedTile(); //get some tile in the room the passage leads to
				moveToTile(before, destination, player);
			}
			else{//player was not in room the secret passage is in
				JOptionPane.showMessageDialog(null, "Must be in the room before using secret passage");
			}
		}else if(!hasRolledDice){
			JOptionPane.showMessageDialog(null,"Please roll dice first or click on a secret passage");
			return;
		}else{
			oldPos = before.posWhenMovedOut(newPos);
			if(after.canMoveToTile(before,oldPos, toMove)){
				moveToTile(before, after, player);
			}
			else{
				JOptionPane.showMessageDialog(null, "Invalid move");
			}
		}
	}

	private void moveToTile(BoardTile before,BoardTile after,Player player){
		player.move(after.getPosition());
		before.movePlayerOut();
		after.movePlayerIn(player);
		hasMoved = true;
		canvas.repaint();
	}
	
	public void endTurn(){
		toMove = 0;
		hasRolledDice = false;
		hasMoved = false;
		hasSuggested = false;
		canvas.getFrame().getCardViewer().removeAll();
		while(true){
			int next = nextPlayer(currentPlayer);
			if(!(players.get(next).isEliminated())){
				currentPlayer = next;
				canvas.repaint();
				
				String filename=players.get(currentPlayer).getCharacter().getType()+"oval.png";
				
				
				Image image = canvas.loadImage(filename);

				picLabel = new ImageIcon(image);
				JOptionPane.showMessageDialog(null,"It is "+players.get(currentPlayer).getName()+"'s turn, as "+players.get(currentPlayer).getCharacter().getType(),"Next Turn", JOptionPane.PLAIN_MESSAGE,  picLabel);
				
				
				
				return;
			}
			
			
			
		}
	}

	int first =0;
	int second=0;
	Icon picLabel;
	public void diceRolled(){
		if(!hasRolledDice){
		first = (int )(Math.random() * 6 + 1);
		second = (int )(Math.random() * 6 + 1);
		toMove = first + second;
		hasRolledDice=true;
		Image one = canvas.loadImage("d"+first+".png");
		Image two = canvas.loadImage("d"+second+".png");
		Image combined = attachImages((BufferedImage) one,(BufferedImage) two);
		
		picLabel = new ImageIcon(combined);
		JOptionPane.showMessageDialog(null,"You have rolled a "+first+" and a "+second, "Rolled Dice", JOptionPane.PLAIN_MESSAGE,  picLabel);
		}

		else if(hasRolledDice) {
			JOptionPane.showMessageDialog(null,"You already rolled a "+first+" and a "+second, "Already Rolled This Turn", JOptionPane.PLAIN_MESSAGE,  picLabel);
		}
		
	}
	
	public void makeSuggestion(){
		if(hasSuggested){
			JOptionPane.showMessageDialog(null,"You can only suggest once in a turn");
			return;
		}
		Player p = players.get(currentPlayer);
		Position pos = p.getPosition();
		if(!(tiles[pos.getX()][pos.getY()] instanceof RoomTile)){
			JOptionPane.showMessageDialog(null, "You cannot make a suggestion outside of a room");
			return;
		}
		hasSuggested = true;
		Type currentRoom = ((RoomTile)tiles[pos.getX()][pos.getY()]).getRoom().getType();
		Set<Type> chosenItems = new HashSet<Type>(popupOptions(false));
		chosenItems.add(currentRoom);
		Type refutedItem = null;
		for(int i = 0; i < players.size()-1;i++){
			Player nextPlayer = players.get(nextPlayer(currentPlayer));
			refutedItem = nextPlayer.refuteSuggestion(chosenItems);
			if(refutedItem !=null){
				break;
			}
		}		
		String message = null;
		if(refutedItem == null){
			message = "The other players cannot refute your suggestion";
		} else{
			message = "A player has "+ refutedItem + " in their cards";
		}
		JOptionPane.showMessageDialog(null, message);
	}
	
	private void moveForSuggestion(List<Type> chosenItems,Room currentRoom){
		Weapon.WeaponType w = (Weapon.WeaponType) chosenItems.get(0);
		for(Weapon weapon:weapons){
			if(weapon.getType() == w){
				Room oldRoom = weapon.getRoom();
				currentRoom.setWeapon(weapon);
				oldRoom.setWeapon(currentRoom.getWeapon());
				break;
			}
		}
		Character.CharaType c = (CharaType) chosenItems.get(1);
		for(Player p : players){
			if(p.getCharacter().getType() == c){
				RoomTile toTile = currentRoom.getUnoccupiedTile();
				BoardTile before = tiles[p.getPosition().getX()][p.getPosition().getX()];
				moveToTile(before, toTile, p);
			}
		}
		
		
	}
	
	public void makeAccusation(){
		Player p = players.get(currentPlayer);
		Set<Type> chosenItems = new HashSet<Type>(popupOptions(true));
		boolean lost = false;
		for(Card c : solution){
			if(!(chosenItems.contains(c.cardType()))){
				lost = true;
				p.setEliminated(true);
				break;
			}
		}
		String message = "";
		if(lost){
			message = "You have made a wrong accusation. The solution is: ";
			for(Card c : solution){
				message += c.cardType()+" ";
			}
			if(++numEliminated == players.size()){
				endGame(false);
			}
		}
		else{
			message = "You have solved the murder! You win";
			endGame(true);
		}
		JOptionPane.showMessageDialog(null, message);
	}
	
	private void endGame(boolean gameWon){
		//if game lost, show the solution
	}
	
	private List<Type> popupOptions(boolean forAccusation){
		List<Type> chosenItems = new ArrayList<Type>();
		String label = "Make Suggestion";
		if(forAccusation){
			label = "Make Accusation";
			Object[] roomOptions = Room.RoomType.values();
			Type room = (Type) JOptionPane.showInputDialog(null,"Which room?", label,JOptionPane.PLAIN_MESSAGE,null,roomOptions,roomOptions[0]);
			chosenItems.add(room);
		}
		Object[] weaponOptions = Weapon.WeaponType.values();
		Type weapon = (Type) JOptionPane.showInputDialog(null,"Which weapon?", label ,JOptionPane.PLAIN_MESSAGE,null,weaponOptions,weaponOptions[0]);
		chosenItems.add(weapon);
		Object[] characterOptions = Character.CharaType.values();
		Type character = (Type) JOptionPane.showInputDialog(null,"Which character?",
				label,JOptionPane.PLAIN_MESSAGE,null,characterOptions,characterOptions[0]);
		chosenItems.add(character);
		return chosenItems;
	}

	

	// assumes all the players have been created
	//fix later to use the sets instead
	private void dealCards() {
		List<Card> weaponCards = new ArrayList<Card>();
		List<Card> characterCards = new ArrayList<Card>();
		List<Card> roomCards = new ArrayList<Card>();
		List<Card> allCards = new ArrayList<Card>();
		for (Weapon w: weapons) {
			Image image = CluedoCanvas.loadImage(w.getType().toString() + ".jpg");
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


	private int nextPlayer(int currentPlayer) {
		int next = currentPlayer + 1;
		if (next == players.size()) {
			return 0;
		}
		return next;
	}
	
	
	
	
	
	//setup methods

	private void createBoardFromFile() throws IOException {
		String filename = "cluedo.txt";
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> lines = new ArrayList<String>();
		int width = -1;
		String line;
		while ((line = br.readLine()) != null) {
			lines.add(line);
			
			// now sanity check

			if (width == -1) {
				width = line.length();
			} else if (width != line.length()) {
				throw new IllegalArgumentException("Input file \"" + filename
						+ "\" is malformed; line " + lines.size()
						+ " incorrect width.");
			}
		}

		for (int y = 0; y < lines.size(); y++) {
			line = lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				char c = line.charAt(x);
				Position p = new Position(x,y);
				switch (c) {
				case '!':
					tiles[y][x] = new SecretTile(p,rooms.get("K"),rooms.get("T"));
					break;
				case '?':
					tiles[y][x] = new SecretTile(p,rooms.get("T"),rooms.get("K"));
					break;
				case '@':
					tiles[y][x] = new SecretTile(p,rooms.get("G"),rooms.get("C"));
					break;
				case '$':
					tiles[y][x] = new SecretTile(p,rooms.get("C"),rooms.get("G"));
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
					RoomTile r = new RoomTile(p,rooms.get(s));
					tiles[y][x] = r;
					rooms.get(s).addRoomTile(r);
					break;
				default:
					if(java.lang.Character.isDigit(c)){
						DoorTile d = new DoorTile(p);
						tiles[y][x] = d;
						switch(c){
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
	
	private void setupRooms(){
		rooms = new HashMap<String,Room>();
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

	private void setupWeapons(){
		weapons = new HashSet<Weapon>();
		List<Room> room = new ArrayList<Room>(rooms.values());
		int i=0;
		for(Weapon.WeaponType w : Weapon.WeaponType.values()){
			Weapon weapon = new Weapon(w);
			weapons.add(weapon);
			room.get(i).setWeapon(weapon);
			i++;
		}
	}
	
	private void addToSolution(List<Card> cards) {
		int i = new Random().nextInt(cards.size());
		solution.add(cards.remove(i));	
	}

	private void dealToPlayers(Collection<Card> cards) {
		
		int currentPlayer = 0;
		for (Card c : cards) {
			;
			players.get(currentPlayer).addCard(c);
			currentPlayer = nextPlayer(currentPlayer);
		}

	}
	
	
	

	//getters and setters

		public BoardTile[][] getTiles() {
			return tiles;
		}

		public Collection<Room> getRooms(){
			return rooms.values();
		}
		
		public List<Player> getPlayers() {
			return players;
		}
		
		public Player getCurrentPlayer(){
			if(state!=WAITING){
			return players.get(currentPlayer);
			}
			else return null;
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
		
		
		
		public BufferedImage attachImages(BufferedImage img1, BufferedImage img2)
		{
		        BufferedImage resultImage = new BufferedImage(img1.getWidth() +
		                img2.getWidth(), img1.getHeight(),
		                BufferedImage.TYPE_INT_RGB);
		        Graphics g = resultImage.getGraphics();
		        g.drawImage(img1, 0, 0, null);
		        g.drawImage(img2, img1.getWidth(), 0, null);
		        return resultImage;
		         
		}
		
		
		
}
