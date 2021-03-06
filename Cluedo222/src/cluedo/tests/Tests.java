package cluedo.tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import cluedo.items.Character;
import cluedo.items.Room;
import cluedo.items.Type;
import cluedo.items.Weapon;
import cluedo.other.Card;
import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.tile.*;

import org.junit.*;

public class Tests {
	
	/**Tests that these cannot be done to a secret passage tile*/
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
	
	/**Tests that these cannot be done to an empty tile*/
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
	
	/**Tests that door tile returns the same position as itself when posWhenMovedOut is called*/
	@Test
	public void doorTileTest1(){
		Position p = new Position(0,5);
		DoorTile dt = new DoorTile(p);
		Position newPos = dt.posWhenMovedOut(new Position(0,0));
		assertEquals("Door tile should return same position",p.getX(),newPos.getX());
		assertEquals("Door tile should return same position",p.getY(),newPos.getY());
	}
	
	/**Tests that it cannot move without a high enough roll of the dice*/
	@Test
	public void doorTileTest2(){
		DoorTile dt = new DoorTile(new Position(0,5));
		Position p2 = new Position(0,0);
		DoorTile dt2 = new DoorTile(p2);
		assertFalse("Should not be able to move into door tile without sufficient roll of dice",dt.canMoveToTile(dt2, p2, 1));
		assertTrue("Should be able to move into door tile",dt.canMoveToTile(dt2, p2, 5));
	}
	
	/**Cannot move to a tile which has already been occupied*/
	@Test
	public void doorTileTest3(){
		Player player = makePlayer(0, 5);
		DoorTile dt = new DoorTile(new Position(0,5));
		dt.movePlayerIn(player);
		Position p2 = new Position(0,0);
		DoorTile dt2 = new DoorTile(p2);
		assertFalse("Should not be able to move into door tile when there is a player occupying it",dt.canMoveToTile(dt2, p2, 5));
	}
	
	/**Tests that hallway tile returns the same position as itself when posWhenMovedOut is called*/
	@Test
	public void hallwayTileTest1(){
		Position p = new Position(0,5);
		HallwayTile ht = new HallwayTile(p);
		Position newPos = ht.posWhenMovedOut(new Position(0,0));
		assertEquals("Hallway tile should return same position",p.getX(),newPos.getX());
		assertEquals("Hallway tile should return same position",p.getY(),newPos.getY());
	}
	
	/**Tests that it cannot move without a high enough roll of the dice*/
	@Test
	public void hallwayTileTest2(){
		HallwayTile ht = new HallwayTile(new Position(0,5));
		Position p2 = new Position(0,0);
		HallwayTile ht2 = new HallwayTile(p2);
		assertFalse("Should not be able to move into hallway tile without sufficient roll of dice",ht.canMoveToTile(ht2, p2, 1));
		assertTrue("Should be able to move into hallway tile",ht.canMoveToTile(ht2, p2, 5));
	}
	
	/**Cannot move to a tile which has already been occupied*/
	@Test
	public void hallwayTileTest3(){
		Player player = makePlayer(0, 5);
		HallwayTile ht = new HallwayTile(new Position(0,5));
		ht.movePlayerIn(player);
		Position p2 = new Position(0,0);
		HallwayTile ht2 = new HallwayTile(p2);
		assertFalse("Should not be able to move into hallway tile when there is a player occupying it",ht.canMoveToTile(ht2, p2, 5));
	}
	
	/**Tests whether or not room tile returns the 
	 * position which is the room's closest door tile to the new position*/
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
	
	/**Tests whether or not room tile returns the 
	 * position which is the room's closest door tile to the new position*/
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
	
	/**Makes sure that cannot move from one room tile to another if they are the same rooms*/
	@Test
	public void roomTileTest3(){
		Room r = new Room(Room.RoomType.STUDY);
		RoomTile rt = new RoomTile(new Position(1,2),r);
		Position p = new Position(3,1);
		RoomTile rt2 = new RoomTile(p,r);
		assertFalse("Cannot move to another tile in the same room",rt.canMoveToTile(rt2, p, 100));
	}
	
	/**Makes sure that can only move to room if a sufficient amount has been rolled*/
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
	
	/**tests a valid move into room tile*/
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
	
	/**Tests the refuting of cards by a player*/
	@Test
	public void playerRefute(){
		Player player = new Player(new Character(Character.CharaType.MISS_SCARLET),"testing");
		player.addCard(new Card(new Weapon(Weapon.WeaponType.CANDLESTICK),null));
		Set<Type> chosenItem = new HashSet<Type>();
		chosenItem.add(Weapon.WeaponType.DAGGER);
		if(player.refuteSuggestion(chosenItem)!=null){
			fail("Should not be able to refute the suggestion");
		}
		chosenItem.add(Weapon.WeaponType.CANDLESTICK);
		if(player.refuteSuggestion(chosenItem)!= Weapon.WeaponType.CANDLESTICK){
			fail("Should be able to refute the suggestion");
		}
	}
	
	/**Helper method to make some player with the position (x,y)*/
	private Player makePlayer(int x,int y){
		Position p = new Position (x,y);
		Character c = new Character(Character.CharaType.MRS_PEACOCK);
		Player player = new Player(c,"Name");
		player.move(p);
		return player;
	}
	
}
