package cluedo.tile;

import java.awt.Color;

import cluedo.other.Player;
import cluedo.other.Position;

public class HallwayTile extends BoardTile{
	private Player playerOnTile = null;
	public HallwayTile(Position p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Color getColour() {
		return new Color(130,201,164);

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
		return differenceX + differenceY <= rolledAmt;
	}

}
