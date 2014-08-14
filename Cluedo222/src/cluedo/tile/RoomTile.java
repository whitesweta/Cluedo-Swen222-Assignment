package cluedo.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

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

	public Room getRoom() {
		return room;
	}
	
	public boolean canReachDoor(Position oldPos, int amountRolled){
		for(DoorTile d : room.getDoortiles()){
			int differenceX = Math.abs(oldPos.getX()-d.getPosition().getX());
			int differenceY = Math.abs(oldPos.getX()-d.getPosition().getX());
			if(differenceX+differenceY<=amountRolled){
				return true;
			}
		}
		return false;
	}
}
