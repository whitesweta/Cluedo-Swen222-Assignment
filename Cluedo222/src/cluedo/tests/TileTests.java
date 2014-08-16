package cluedo.tests;

import static org.junit.Assert.*;
import cluedo.other.Position;
import cluedo.tile.*;

import org.junit.*;

public class TileTests {
	@Test
	public void secretTileTest1(){
		SecretTile st = new SecretTile(new Position(0,0),null,null);
		try{
			st.movePlayerOut();
			fail("Should not be able to move a player out of tile");
		}
		catch(RuntimeException e){	
		}
	}
	
	
}
