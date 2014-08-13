package cluedo.other;

public class Weapon implements Item {
	public enum WeaponType implements Type{CANDLESTICK,DAGGER,LEAD_PIPE,REVOLVER,ROPE,SPANNER};
	private WeaponType weapon;
	private Room room;

	public Weapon(WeaponType w){
		weapon = w;
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
