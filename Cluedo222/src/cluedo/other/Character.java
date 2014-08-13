package cluedo.other;

public class Character implements Item {
	public enum CharaType implements Type{MISS_SCARLET,COLONEL_MUSTARD,MRS_WHITE,REVEREND_GREEN,MRS_PEACOCK,PROFESSOR_PLUM };
	private CharaType type;
	
	public Character(CharaType n){
		type = n;
	}	

	public CharaType getType() {
		return type;
	}
}
