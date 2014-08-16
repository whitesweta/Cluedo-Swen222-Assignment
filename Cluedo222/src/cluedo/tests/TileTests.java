package cluedo.tests;

import static org.junit.Assert.*;
import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.tile.*;
import cluedo.other.Character;

import org.junit.*;

public class TileTests {
	@Test
	public void secretTileTest1(){
		SecretTile st = new SecretTile(new Position(0,0),null,null);
		try{
			st.movePlayerOut();
			fail("Should not be able to move a player out of secret tile");
		}
		catch(RuntimeException e){	
		}
	}
	
	@Test
	public void secretTileTest2(){
		SecretTile st = new SecretTile(new Position(0,0),null,null);
		try{
			st.movePlayerIn(null);
			fail("Should not be able to move a player into secret tile");
		}
		catch(RuntimeException e){	
		}
	} 
	
	@Test
	public void secretTileTest3(){
		SecretTile st = new SecretTile(new Position(0,0),null,null);
		try{
			st.posWhenMovedOut(new Position(0,1));
			fail("player should not be in secret tile");
		}
		catch(RuntimeException e){	
		}
	} 
	
	@Test
	public void secretTileTest4(){
		SecretTile st = new SecretTile(new Position(0,0),null,null);
		assertFalse("Should not be able to go into secret tile",st.canMoveToTile(new HallwayTile(new Position(0,0)), new Position(0,1), 1));
	} 
	
	@Test
	public void emptyTileTest1(){
		EmptyTile et = new EmptyTile(new Position(0,0));
		try{
			et.movePlayerOut();
			fail("Should not be able to move a player out of empty tile");
		}
		catch(RuntimeException e){	
		}
	}
	
	@Test
	public void emptyTileTest2(){
		EmptyTile et = new EmptyTile(new Position(0,0));
		try{
			et.movePlayerIn(null);
			fail("Should not be able to move a player into empty tile");
		}
		catch(RuntimeException e){	
		}
	} 
	
	@Test
	public void emptyTileTest3(){
		EmptyTile et = new EmptyTile(new Position(0,0));
		try{
			et.posWhenMovedOut(new Position(0,1));
			fail("player should not be in empty tile");
		}
		catch(RuntimeException e){	
		}
	} 
	
	@Test
	public void emptyTileTest4(){
		EmptyTile et = new EmptyTile(new Position(0,0));
		assertFalse("Should not be able to go into empty tile",et.canMoveToTile(new HallwayTile(new Position(0,0)), new Position(0,1), 1));
	} 
	
	@Test
	public void doorTileTest1(){
		Position p = new Position(0,5);
		DoorTile dt = new DoorTile(p);
		Position newPos = dt.posWhenMovedOut(new Position(0,0));
		assertEquals("Door tile should return same position",p.getX(),newPos.getX());
		assertEquals("Door tile should return same position",p.getY(),newPos.getY());
	}
	
	@Test
	public void doorTileTest(){
		
	}
	
	private Player makePlayer(int x,int y){
		Position p = new Position (x,y);
		Character c = new Character(Character.CharaType.COLONEL_MUSTARD);
		Player player = new Player(c,"Name");
		player.move(p);
		return player;
	}
	
	
}
