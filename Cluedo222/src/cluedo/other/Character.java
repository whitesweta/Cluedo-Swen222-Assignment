package cluedo.other;

public class Character implements Item {
	public enum Name{MISS_SCARLET,COLONEL_MUSTARD,MRS_WHITE,REVEREND_GREEN,MRS_PEACOCK,PROFESSOR_PLUM };
	private Name type;
	
	public Character(Name n){
		type = n;
	}	

}
