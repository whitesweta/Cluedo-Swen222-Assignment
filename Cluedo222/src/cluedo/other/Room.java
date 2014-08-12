package cluedo.other;

import java.util.ArrayList;
import java.util.List;

import cluedo.tile.RoomTile;

public class Room implements Item {

	public enum RoomType implements Type {
		KITCHEN, BALLROOM, CONSERVATORY, DINING_ROOM, LOUNGE, HALL, STUDY, LIBRARY, BILLIARD_ROOM
	};

	private RoomType room;
	private Weapon weapon;
	private List<RoomTile> roomtiles = new ArrayList<RoomTile>();

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Room(RoomType r) {
		room = r;
	}

	public void addTile(RoomTile r) {
		roomtiles.add(r);
	}

	public Position getFirstPosition() {
		return roomtiles.get(0).getPosition();
	}

	public RoomType getRoom() {
		return room;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (room != other.room)
			return false;
		return true;
	}

	@Override
	public Type getType() {
		return room;
	}

}
