package cluedo.ui;

import cluedo.tile.BoardTile;

public class Board {
	private BoardTile[][] tiles;
	
	public Board(){
		initialiseTiles();
	}
	
	private void initialiseTiles(){
		tiles = new BoardTile[23][24];
		
	}
}
