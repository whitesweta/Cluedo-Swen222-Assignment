package cluedo.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import cluedo.other.Card;
import cluedo.other.Player;
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

	public Board(){
		tiles = new BoardTile[ROW][COL];
		try {
			createBoardFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new SelectCharFrame();
	}

	public BoardTile[][] getTiles(){
		return tiles;
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
