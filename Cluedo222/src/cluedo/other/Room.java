package cluedo.other;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cluedo.tile.DoorTile;
import cluedo.tile.RoomTile;


public class Room implements Item {
	public enum RoomType implements Type{KITCHEN,BALLROOM,CONSERVATORY,DINING_ROOM,LOUNGE,HALL,STUDY,LIBRARY,BILLIARD_ROOM};
	private RoomType room;
	private Weapon weapon;
	private List<RoomTile> roomtiles = new ArrayList<RoomTile>();
	private Set<DoorTile> doortiles = new HashSet<DoorTile>();

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Room(RoomType r){
		room = r;
	}

	public void addDoorTile(DoorTile d){
		doortiles.add(d);
	}
	public Set<DoorTile> getDoortiles() {
		return doortiles;
	}

	public void addRoomTile(RoomTile r){
		roomtiles.add(r);
	}

	public Position getFirstPosition(){
		return roomtiles.get(0).getPosition();
	}

	public Type getType() {
		return room;
	}
}