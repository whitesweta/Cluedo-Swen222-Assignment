package cluedo.other;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cluedo.tile.DoorTile;
import cluedo.tile.RoomTile;

/**
 * Represents a room in the game from a given roomType enum. Implements type
 * 
 * @author Shweta Barapatre and Maria Libunao
 *
 */
public class Room implements Item {
	public enum RoomType implements Type {
		KITCHEN, BALLROOM, CONSERVATORY, DINING_ROOM, LOUNGE, HALL, STUDY, LIBRARY, BILLIARD_ROOM
	};

	private RoomType room;
	private Weapon weapon;
	private List<RoomTile> roomtiles = new ArrayList<RoomTile>();
	private Set<DoorTile> doortiles = new HashSet<DoorTile>();

	/**
	 * Constructs a room with a given Type
	 * 
	 * @param r
	 *            enum of room type
	 */
	public Room(RoomType r) {
		room = r;
	}

	/**
	 * gets the weapon in the room
	 * 
	 * @return returns weapon
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * sets the weapon in the room
	 * 
	 * @param weapon
	 *            the weapon to be placed in the room
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
		if (this.weapon != null) {
			this.weapon.setPosition(getFirstPosition());
		}
	}

	/**
	 * adds a doortile to the doortiles collection in the room
	 * 
	 * @param d
	 *            the doortile to be added
	 */

	public void addDoorTile(DoorTile d) {
		doortiles.add(d);
	}
	/**
	 * 
	 * @return Returns list of roomtiles in room
	 */
	public List<RoomTile> getRoomTiles(){
		return roomtiles;
	}

	/**
	 * returns set of door tiles
	 * 
	 * @return returns the set of doortiles
	 */
	public Set<DoorTile> getDoortiles() {
		return doortiles;
	}

	/**
	 * add a room tile to the room tiles set of the room
	 * 
	 * @param r
	 *            the roomtile to be added
	 */
	public void addRoomTile(RoomTile r) {
		roomtiles.add(r);
	}

	/**
	 * get the top left room tile
	 * 
	 * @return the first roomtile in roomtiles set
	 */
	public Position getFirstPosition() {
		return roomtiles.get(0).getPosition();
	}

	/**
	 * get an unoccupied room tile in the room
	 * 
	 * @return an roomtile that is not occupied or if they are all occupied null
	 */
	public RoomTile getUnoccupiedTile() {
		for (RoomTile r : roomtiles) {
			if (!r.isOccupied()) {
				return r;
			}
		}
		return null;
	}

	/**
	 * get the type of the room
	 * 
	 * @return returns the room
	 */
	public Type getType() {
		return room;
	}

	
}