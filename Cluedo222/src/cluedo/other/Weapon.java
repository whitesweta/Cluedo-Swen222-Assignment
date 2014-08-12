package cluedo.other;

public class Weapon implements Item {

	public enum WeaponType implements Type {
		CANDLESTICK, DAGGER, LEAD_PIPE, REVOLVER, ROPE, SPANNER
	};

	private WeaponType weapon;
	private Room room;

	public Weapon(WeaponType w) {
		weapon = w;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((weapon == null) ? 0 : weapon.hashCode());
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
		Weapon other = (Weapon) obj;
		if (weapon != other.weapon)
			return false;
		return true;
	}

	@Override
	public Type getType(){
		return weapon;
	}

}
