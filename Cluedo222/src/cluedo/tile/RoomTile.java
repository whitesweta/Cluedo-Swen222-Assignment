package cluedo.tile;

import java.awt.Color;
import java.awt.image.BufferedImage;

import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.other.Room;

public class RoomTile extends BoardTile{
	private Room room;
	private Player playerOnTile = null;

	public RoomTile(Position p,Room r){
		super(p);
		room = r;
	}

	@Override
	public Color getColour() {
		return new Color(240,230,140);
	}

	public Room getRoom() {
		return room;
	}

	/**chooses the doortile which is closest to the new position*/
	@Override
	public Position posWhenMovedOut(Position newPos) {
		int minDifference = Integer.MAX_VALUE;
		Position outPos = null;
		for(DoorTile d : room.getDoortiles()){
			int differenceX = Math.abs(newPos.getX()-d.getPosition().getX());
			int differenceY = Math.abs(newPos.getY()-d.getPosition().getY());
			if(differenceX + differenceY < minDifference){
				outPos = d.getPosition();
				minDifference = differenceX + differenceY;
			}
		}
		return outPos;
	}

	@Override
	public void movePlayerOut() {
		playerOnTile = null;
	}
	
	@Override
	public void movePlayerIn(Player p) {
		playerOnTile = p;		
	}
	
	public boolean isOccupied(){
		return playerOnTile != null;
	}

	@Override
	public boolean canMoveToTile(BoardTile oldTile,Position oldPos, int rolledAmt) {
		if(isOccupied()){
			return false;
		}
		if(oldTile instanceof RoomTile){
			if(((RoomTile)oldTile).room.equals(this.room)){
				return false;
			}
		}
		for(DoorTile d : room.getDoortiles()){
			int differenceX = Math.abs(oldPos.getX()-d.getPosition().getX());
			int differenceY = Math.abs(oldPos.getY()-d.getPosition().getY());
			if(differenceX+differenceY <= rolledAmt){
				return true;
			}
		}
		return false;
	}
}
