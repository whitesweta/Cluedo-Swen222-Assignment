package cluedo.tests;
import static org.junit.Assert.*;

import org.junit.*;

import cluedo.other.Character.CharaType;
import cluedo.other.Player;
import cluedo.ui.Board;
import cluedo.ui.CluedoCanvas;
import cluedo.ui.CluedoFrame;
import cluedo.other.Character;


public class BoardTests { 
	
	@Test
	public void dealCardsTest(){
		for(int i = 3; i<=6;i++){
			Board b = new Board(new CluedoCanvas(null));
			addToBoard(i,b);
			b.readyToStart(false);
			int numOfCards = 18/i;
			for(Player p : b.getPlayers()){
				int handSize = p.getCards().size();
				if(!(handSize==numOfCards||handSize == numOfCards+1)){
					fail("Number of cards should be evenly distributed");
				}
			}
		}
	}
	
	@Test
	public void moveTest1(){
		
	}
	
	private void addToBoard(int amtOfPlayers,Board b){
		for(int i = 0; i<amtOfPlayers;i++){
			b.addPlayer(new Player(new Character(CharaType.COLONEL_MUSTARD),"testing"));
		}
	}
}
