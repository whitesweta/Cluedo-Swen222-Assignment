package cluedo.other;

import java.util.ArrayList;
import java.util.List;

import cluedo.tile.RoomTile;


public class Room implements Item {
	public enum RoomType{KITCHEN,BALLROOM,CONSERVATORY,DINING_ROOM,LOUNGE,HALL,STUDY,LIBRARY,BILLIARD_ROOM};
	private RoomType room;
	private Weapon weapon;
	private List<RoomTile> roomtiles = new ArrayList<RoomTile>();

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Room(RoomType r){
		room = r;
	}

	public void addTile(RoomTile r){
		roomtiles.add(r);
	}

	public Position getFirstPosition(){
		return roomtiles.get(0).getPosition();
	}

	public RoomType getRoom() {
		return room;
	}
}