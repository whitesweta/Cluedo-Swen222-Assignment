package cluedo.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.items.Room;
import cluedo.items.Weapon;
import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.tile.BoardTile;

/**
 * Represents the canvas that does the drawing of the tiles, the players and the
 * weapons that are on the board
 * 
 * @author Shweta Barapatre and Maria Libunao
 *
 */
@SuppressWarnings("serial")
public class CluedoCanvas extends Canvas implements ImageObserver {
	private Board board;
	private static final int sizeOfTile = 19;
	private CluedoFrame frame;

	/**
	 * Constructs the canvas
	 * 
	 * @param frame
	 *            the frame that called the construction of the canvas
	 */

	public CluedoCanvas(CluedoFrame frame) {
		this.board = new Board(this);
		this.frame = frame;
	}

	/**
	 * 
	 * @return returns the frame that called the canvas
	 */
	public CluedoFrame getFrame() {
		return frame;
	}

	/**
	 * @return the width or height of an indivual tile
	 */
	public int getSizeOfTile() {
		return sizeOfTile;
	}

	/**
	 * draws the tiles, the weapons and the players on the canvas at their given
	 * position tiles are drawn as squares, the size indicated by the size of
	 * tile field players are one tile large and weapons four tiles
	 */
	public void paint(Graphics g) {
		for (int i = 0; i < board.getTiles().length; i++) {
			for (int j = 0; j < board.getTiles()[i].length; j++) {
				BoardTile tile = board.getTiles()[i][j];
				g.setColor(tile.getColour());
				g.fillRect(j * sizeOfTile, i * sizeOfTile, sizeOfTile,
						sizeOfTile);
				g.setColor(Color.BLACK);
				g.drawRect(j * sizeOfTile, i * sizeOfTile, sizeOfTile,
						sizeOfTile);
			}
	

		}
		
		for(Room r: board.getRooms()){
			
			Position pos = r.getFirstPosition();
			String filename = r.getType() + "text.png";
			g.drawImage(loadImage(filename), pos.getX() * sizeOfTile,pos.getY() * sizeOfTile, this);
		}
		
		for (Weapon w : board.getWeapons()) {
			Position p = w.getPosition();
			String filename = w.getType() + ".png";
			g.drawImage(loadImage(filename), p.getX() * sizeOfTile, p.getY()
					* sizeOfTile, this);
		}
		for (Player p : board.getPlayers()) {
			Position pos = p.getPosition();
			String filename = p.getCharacter().getType() + ".png";
			if (p.equals(board.getCurrentPlayer())) {
				g.setColor(new Color(0, 191, 255));
				g.fillRect(pos.getX() * sizeOfTile, pos.getY() * sizeOfTile,
						sizeOfTile, sizeOfTile);
			}

			g.drawImage(loadImage(filename), pos.getX() * sizeOfTile,
					pos.getY() * sizeOfTile, this);

		}

	}

	/**
	 * helper method to load and image from the filename
	 * 
	 * @param filename
	 *            name of the file
	 * @return an Image object of the file
	 */
	public static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.

		java.net.URL imageURL = CluedoCanvas.class.getResource("images"
				+ File.separator + filename);

		try {

			Image img = ImageIO.read(imageURL);
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}

	/**
	 * returns the board that is called by this canvas class
	 * 
	 * @return
	 */
	public Board getBoard() {
		return board;
	}

}
