package cluedo.other;

public class Weapon implements Item {
	public enum WeaponType implements Type{CANDLESTICK,DAGGER,LEAD_PIPE,REVOLVER,ROPE,SPANNER};
	private WeaponType weapon;
	private Room room;
	private Position position;

	public Weapon(WeaponType w, Position p){
		weapon = w;
		position = p;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public Type getType() {
		return weapon;
	}


}
