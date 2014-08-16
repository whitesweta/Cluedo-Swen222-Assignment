package cluedo.other;

/**
 * Represents a position on the canvas and board
 * @author Shweta Barapatre and Maria Libunao
 *
 */
public class Position {
	private int x;
	private int y;
	
	/**
	 * Constructs a position with a given x and y location
	 * @param x x co-ordinate
	 * @param y y co-ordinate
	 */
	public Position(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return returns the x co-ordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * sets the x co-ordinate field
	 * @param x the new x co-ordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * @return Returns the y-co-ordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the y co-ordinate field
	 * @param y the new y co-ordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
}
