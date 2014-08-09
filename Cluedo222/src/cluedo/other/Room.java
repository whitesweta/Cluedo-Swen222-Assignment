package cluedo.other;


public class Room implements Item {
	public enum RoomType{KITCHEN,BALLROOM,CONSERVATORY,DINING_ROOM,LOUNGE,HALL,STUDY,LIBRARY,BILLIARD_ROOM};
	private RoomType room;
	
	public Room(RoomType r){
		room = r;
	}

}
