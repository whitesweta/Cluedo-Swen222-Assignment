package cluedo.ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import cluedo.other.Card;
import cluedo.other.Character;
import cluedo.other.Player;
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
	private static final int ROW = 25;
	private static final int COL = 24;
	public static final int WAITING = 0;
	public static final int READY = 1;
	public static final int PLAYING = 2;
	public static final int GAMEOVER = 3;
	public static final int GAMEWON = 4;
	
	private int state; // this is used to tell us what state we're in. 

	public Board(){
		tiles = new BoardTile[ROW][COL];
		try {
			createBoardFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		players = new ArrayList<Player>();
		state = WAITING;
	}

	public BoardTile[][] getTiles(){
		return tiles;
	}
	
	public void addPlayer(Player p){
		if(state == WAITING){
			players.add(p);
		}		
	}
	
	//assumes all the players have been created
	private void dealCards(){ 
		List<Card> weaponCards = new ArrayList<Card>();
		List<Card> characterCards = new ArrayList<Card>();
		List<Card> allCards = new ArrayList<Card>();
		for(Weapon.WeaponType w : Weapon.WeaponType.values()){
			Image image = loadImage(w.toString()+".png");
			weaponCards.add(new Card(new Weapon(w),image));
		}
		Collections.shuffle(weaponCards);
		solution.add(weaponCards.remove(0));
		
		for(Character.Name c: Character.Name.values()){
			
		}
		
	}
	
	
	
	
	private Image loadImage(String filename){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("images"+File.separator+filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	private void createBoardFromFile() throws IOException {
		String filename = "cluedo.txt";
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> lines = new ArrayList<String>();
		int width = -1;
		String line;
		while((line = br.readLine()) != null) {
			lines.add(line);
			//System.out.println(line);

			// now sanity check

			if(width == -1) {
				width = line.length();
			} else if(width != line.length()) {
				throw new IllegalArgumentException("Input file \"" + filename + "\" is malformed; line " + lines.size() + " incorrect width.");
			}
		}

		for(int y=0;y<lines.size();y++) {
			// y is the number of rows. does not start at zero so will show 25.
			line = lines.get(y);
			for(int x=0;x<line.length();x++) {
				//x is length of each line. will show 24
				char c = line.charAt(x);
				switch (c) {
					case 'K' :
						tiles[y][x] = new RoomTile("Kitchen");
						break;
					case 'S' :
						tiles [y][x] = new SecretTile();
						break;
					case 'X':
						tiles [y][x] = new EmptyTile();
						break;
					case 'H':
						tiles [y][x] = new HallwayTile();
						break;
					case 'C':
						tiles [y][x] = new RoomTile("Conservatory");
						break;
					case 'B':
						tiles [y][x] = new RoomTile("Ballroom");
						break;
					case 'D':
						tiles [y][x] = new DoorTile();
						break;
					case 'N':
						tiles [y][x] = new RoomTile("Dining Room");
						break;
					case 'R':
						tiles [y][x] = new RoomTile("Billard Room");
						break;
					case 'L':
						tiles [y][x] = new RoomTile("Library");
						break;
					case 'A':
						tiles [y][x] = new RoomTile("Hall");
						break;
					case 'G':
						tiles [y][x] = new RoomTile("Lounge");
						break;
					case 'T':
						tiles [y][x] = new RoomTile("Study");
						break;
				}
			}
		}

	}
}
