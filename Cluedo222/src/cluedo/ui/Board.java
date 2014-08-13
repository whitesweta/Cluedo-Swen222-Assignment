package cluedo.ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
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

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

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
	
	private int state; // this is used to tell us what state we're in.
	private int currentPlayer = 0;
	
	public static final int WAITING = 0;
	public static final int READY = 1;
	public static final int PLAYING = 2;
	public static final int GAMEOVER = 3;
	public static final int GAMEWON = 4;

	public Board() {
		tiles = new BoardTile[ROW][COL];
		setupRooms();
		setupWeapons();
		try {
			createBoardFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		players = new ArrayList<Player>();
		solution = new HashSet<Card>();
		state = WAITING;
	}

	public BoardTile[][] getTiles() {
		return tiles;
	}

	public void addPlayer(Player p) {
		if (state == WAITING) {
			players.add(p);
		}
	}

	public void readyToStart() {
		if (state == WAITING) {
			state = READY;
			dealCards();
		}
	}

	public Collection<Room> getRooms(){
		return rooms.values();
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void endTurn(){
		while(true){
			int next = nextPlayer(currentPlayer);
			if(!(players.get(next).isEliminated())){
				currentPlayer = next;
				return;
			}
		}
	}

	public int diceRolled(){
		int first = (int )(Math.random() * 6 + 1);
		int second = (int )(Math.random() * 6 + 1);
		return first + second;
	}
	
	public void makeSuggestion(){
		Player p = players.get(currentPlayer);
		Position pos = p.getPosition();
		if(!(tiles[pos.getX()][pos.getY()] instanceof RoomTile)){
			JOptionPane.showMessageDialog(null,
				    "You cannot make a suggestion outside of a room",
				    "Invalid move",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		Type currentRoom = ((RoomTile)tiles[pos.getX()][pos.getY()]).getRoom().getType();
		Set<Type> chosenItems = popupOptions(false);
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
	
	public void makeAccusation(){
		Player p = players.get(currentPlayer);
		Set<Type> chosenItems = popupOptions(true);
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
	
	private Set<Type> popupOptions(boolean forAccusation){
		Set<Type> chosenItems = new HashSet<Type>();
		String label = "Make Suggestion";
		if(forAccusation){
			label = "Make Accusation";
			Object[] roomOptions = Weapon.WeaponType.values();
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
			room.get(i).setWeapon(weapon);
			i++;
		}
	}

	// assumes all the players have been created
	//fix later to use the sets instead
	private void dealCards() {
		List<Card> weaponCards = new ArrayList<Card>();
		List<Card> characterCards = new ArrayList<Card>();
		List<Card> roomCards = new ArrayList<Card>();
		List<Card> allCards = new ArrayList<Card>();
		for (Weapon.WeaponType w : Weapon.WeaponType.values()) {
			Image image = CluedoCanvas.loadImage(w.toString() + ".jpg");
			weaponCards.add(new Card(new Weapon(w), image));
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


	private void addToSolution(List<Card> cards) {
		int i = new Random().nextInt(cards.size());
		solution.add(cards.remove(i));
		for(Room r: rooms.values()){

		}
	}

	private void dealToPlayers(Collection<Card> cards) {
		int currentPlayer = 0;
		for (Card c : cards) {
			System.out.println(currentPlayer+" " + c.toString());
			players.get(currentPlayer).addCard(c);
			currentPlayer = nextPlayer(currentPlayer);
		}

	}

	private int nextPlayer(int currentPlayer) {
		int next = currentPlayer + 1;
		if (next == players.size()) {
			return 0;
		}
		return next;
	}


	private void createBoardFromFile() throws IOException {
		String filename = "cluedo.txt";
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> lines = new ArrayList<String>();
		int width = -1;
		String line;
		while ((line = br.readLine()) != null) {
			lines.add(line);
			// System.out.println(line);

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
				case 'D':
					tiles[y][x] = new DoorTile(p);
					break;
				case 'S':
					tiles[y][x] = new SecretTile(p);
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
					rooms.get(s).addTile(r);
					break;
				}
			}
		}

	}




}
