package cluedo.tile;

import java.awt.Color;

import cluedo.other.Position;
import cluedo.other.Room;

public class RoomTile extends BoardTile{
	private Room room;

	public RoomTile(Position p,Room r){
		super(p);
		room = r;
	}

	@Override
	public Color getColour() {
		return Color.ORANGE;
	}
}
