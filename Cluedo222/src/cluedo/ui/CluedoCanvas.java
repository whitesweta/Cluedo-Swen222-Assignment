package cluedo.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.other.Room;
import cluedo.other.Weapon;
import cluedo.tile.BoardTile;

@SuppressWarnings("serial")
public class CluedoCanvas extends Canvas implements ImageObserver{
	Board board;
	private int sizeOfTile = 19;
	

	public CluedoCanvas(CluedoFrame frame) {
		this.board = new Board(this);
	}
	
	
	public int getSizeOfTile() {
		return sizeOfTile;
	}

	public void paint(Graphics g) {
		for (int i = 0; i < board.getTiles().length; i++) {
			for (int j = 0; j < board.getTiles()[i].length; j++) {
				BoardTile tile = board.getTiles()[i][j];
				// System.out.println(i+" x " + j+" y "+ tile.getColour());
				g.setColor(tile.getColour());
				g.fillRect(j * sizeOfTile, i * sizeOfTile, sizeOfTile,sizeOfTile);
				g.setColor(Color.BLACK);
				g.drawRect(j * sizeOfTile, i * sizeOfTile, sizeOfTile,sizeOfTile);
			}

		}
		g.setColor(Color.white);
		for(Weapon w:board.getWeapons()){
			Position p = w.getPosition();
			String filename = w.getType()+".png";
			System.out.println(filename);
			g.drawImage(loadImage(filename), p.getX()*sizeOfTile, p.getY()*sizeOfTile, this);
		}
		for(Player p: board.getPlayers()){
			g.setColor(Color.black);
			Position pos = p.getPosition();
			g.fillOval(pos.getX()*sizeOfTile, pos.getY()*sizeOfTile, sizeOfTile, sizeOfTile);
			
		}
	}


	public static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		System.out.println(File.separator);
		java.net.URL imageURL = CluedoCanvas.class.getResource("images" + File.separator + filename);

		try {
			System.out.println(imageURL);
			Image img = ImageIO.read(imageURL);
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}


	public Board getBoard() {
		return board;
	}
	
	

}
