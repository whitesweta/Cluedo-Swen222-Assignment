package cluedo.other;
/**
 * Represents a character in the game. Implements item
 * 
 * @author Shweta Barapatre and Maria Libunao 
 * 
 */
public class Character implements Item {
	public enum CharaType implements Type{MISS_SCARLET,COLONEL_MUSTARD,MRS_WHITE,REVEREND_GREEN,MRS_PEACOCK,PROFESSOR_PLUM };
	private CharaType type;
	
	/**
	 * Constructs a Character with a given type
	 * @param n
	 * 			Which character is being created
	 */
	
	public Character(CharaType n){
		type = n;
	}	
	
	/**
	 * @return returns which Character Type this object is
	 */

	public CharaType getType() {
		return type;
	}
}
