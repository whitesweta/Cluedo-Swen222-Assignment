package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;

public class DoorTile extends BoardTile{
	private Player playerOnTile = null;
	public DoorTile(Position p) {
		super(p);
	}

	@Override
	public Color getColour() {
		return Color.GRAY;

	}

	@Override
	public Position posWhenMovedOut(Position newPos) {
		return this.getPosition();
	}

	@Override
	public void movePlayerOut() {
		playerOnTile = null;
	}
	
	@Override
	public void movePlayerIn(Player p) {
		playerOnTile = p;
	}

	@Override
	public boolean canMoveToTile(BoardTile oldTile,Position oldPos, int rolledAmt) {
		if(playerOnTile != null){
			return false;
		}
		int differenceX = Math.abs(oldPos.getX()-this.getPosition().getX());
		int differenceY = Math.abs(oldPos.getY()-this.getPosition().getY());
		return differenceX + differenceY == rolledAmt;
	}

	

}
