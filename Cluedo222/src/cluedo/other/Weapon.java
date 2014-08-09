package cluedo.other;

public class Weapon implements Item {
	public enum WeaponType{CANDLESTICK,DAGGER,LEAD_PIPE,REVOLVER,ROPE,SPANNER};
	private WeaponType weapon;
	
	public Weapon(WeaponType w){
		weapon = w;
	}
}
