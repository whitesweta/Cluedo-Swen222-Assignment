package cluedo.other;


public class Room implements Item {
	public enum RoomType{KITCHEN,BALLROOM,CONSERVATORY,DINING_ROOM,LOUNGE,HALL,STUDY,LIBRARY,BILLIARD_ROOM};
	private RoomType room;
	private Weapon weapon;
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Room(RoomType r){
		room = r;
	}

	
}
