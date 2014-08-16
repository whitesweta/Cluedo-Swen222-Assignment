package cluedo.tests;

import static org.junit.Assert.*;
import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.other.Room;
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
	public void doorTileTest2(){
		DoorTile dt = new DoorTile(new Position(0,5));
		Position p2 = new Position(0,0);
		DoorTile dt2 = new DoorTile(p2);
		assertFalse("Should not be able to move into door tile without sufficient roll of dice",dt.canMoveToTile(dt2, p2, 1));
		assertTrue("Should be able to move into door tile",dt.canMoveToTile(dt2, p2, 5));
	}
	
	@Test
	public void doorTileTest3(){
		Player player = makePlayer(0, 5);
		DoorTile dt = new DoorTile(new Position(0,5));
		dt.movePlayerIn(player);
		Position p2 = new Position(0,0);
		DoorTile dt2 = new DoorTile(p2);
		assertFalse("Should not be able to move into room when there is a player occupying it",dt.canMoveToTile(dt2, p2, 5));
	}
	
	@Test
	public void hallwayTileTest1(){
		Position p = new Position(0,5);
		HallwayTile ht = new HallwayTile(p);
		Position newPos = ht.posWhenMovedOut(new Position(0,0));
		assertEquals("Door tile should return same position",p.getX(),newPos.getX());
		assertEquals("Door tile should return same position",p.getY(),newPos.getY());
	}
	
	@Test
	public void hallwayTileTest2(){
		HallwayTile ht = new HallwayTile(new Position(0,5));
		Position p2 = new Position(0,0);
		HallwayTile ht2 = new HallwayTile(p2);
		assertFalse("Should not be able to move into door tile without sufficient roll of dice",ht.canMoveToTile(ht2, p2, 1));
		assertTrue("Should be able to move into door tile",ht.canMoveToTile(ht2, p2, 5));
	}
	
	@Test
	public void hallwayTileTest3(){
		Player player = makePlayer(0, 5);
		HallwayTile ht = new HallwayTile(new Position(0,5));
		ht.movePlayerIn(player);
		Position p2 = new Position(0,0);
		HallwayTile ht2 = new HallwayTile(p2);
		assertFalse("Should not be able to move into room when there is a player occupying it",ht.canMoveToTile(ht2, p2, 5));
	}
	
	@Test
	public void roomTileTest1(){
		Room r = new Room(Room.RoomType.STUDY);
		RoomTile rt = new RoomTile(new Position(1,2),r);
		Position p = new Position(3,1);
		DoorTile dt = new DoorTile(p);
		r.addDoorTile(dt);
		r.addRoomTile(rt);
		Position p2 = rt.posWhenMovedOut(new Position(4,1));
		assertEquals("Postion should be the same as door tile position",p.getX(),p2.getX());
		assertEquals("Postion should be the same as door tile position",p.getY(),p2.getY());
	}
	
	@Test
	public void roomTileTest2(){
		Room r = new Room(Room.RoomType.STUDY);
		RoomTile rt = new RoomTile(new Position(1,2),r);
		Position p = new Position(3,1);
		DoorTile dt = new DoorTile(p);
		DoorTile dt2 = new DoorTile(new Position(5,6));
		r.addDoorTile(dt);
		r.addDoorTile(dt2);
		r.addRoomTile(rt);
		Position p2 = rt.posWhenMovedOut(new Position(4,1));
		assertEquals("Postion should be the same as closest door tile position",p.getX(),p2.getX());
		assertEquals("Postion should be the same as closest door tile position",p.getY(),p2.getY());
	}
	
	@Test
	public void roomTileTest3(){
		Room r = new Room(Room.RoomType.STUDY);
		RoomTile rt = new RoomTile(new Position(1,2),r);
		Position p = new Position(3,1);
		RoomTile rt2 = new RoomTile(p,r);
		assertFalse("Cannot move to another tile in the same room",rt.canMoveToTile(rt2, p, 1));
	}
	
	@Test 
	public void roomTileTest4(){
		Room r = new Room(Room.RoomType.STUDY);
		RoomTile rt = new RoomTile(new Position(1,2),r);
		DoorTile dt = new DoorTile(new Position(3,1));
		r.addDoorTile(dt);
		r.addRoomTile(rt);
		Position oldPos = new Position(0,0);
		HallwayTile ht = new HallwayTile(oldPos);
		assertFalse("Should not be able to move to room",rt.canMoveToTile(ht, oldPos, 1));
	}
	
	@Test
	public void roomTileTest5(){
		Room r = new Room(Room.RoomType.STUDY);
		RoomTile rt = new RoomTile(new Position(1,2),r);
		DoorTile dt = new DoorTile(new Position(3,1));
		DoorTile dt2 = new DoorTile(new Position(5,6));
		r.addDoorTile(dt);
		r.addDoorTile(dt2);
		r.addRoomTile(rt);
		Position oldPos = new Position(0,0);
		HallwayTile ht = new HallwayTile(oldPos);
		assertTrue("Should be able to move into room", rt.canMoveToTile(ht, oldPos, 12));		
	}
	
	private Player makePlayer(int x,int y){
		Position p = new Position (x,y);
		Character c = new Character(Character.CharaType.COLONEL_MUSTARD);
		Player player = new Player(c,"Name");
		player.move(p);
		return player;
	}
	
	
}
