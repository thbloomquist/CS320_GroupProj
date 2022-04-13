package edu.ycp.cs320.groupProj.modelTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.NameTag;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.controller.ObjectController;
import edu.ycp.cs320.groupProj.model.Room;


public class PlayerModelTest {
	private PlayerModel model;
	private ObjectModel banana;
	private ObjectController oc;
	private Room room;
	private NameTag tag;
	
	@Before
	public void setup() {
		model = new PlayerModel();
		banana = new ObjectModel();
		oc = new ObjectController();
		tag = new NameTag ("test", "test");
		room = new Room(true, tag, model.inventory);
		
		banana = oc.banana();
		model.addInventory(banana);
		
	}
	@Test
	public void testGetUp() {
		assertEquals(model.getUp(), 2);
	}
	@Test
	public void testGetSide() {
		assertEquals(model.getSide(), 2);
	}
	@Test
	public void testGetInventory() {
		assertEquals(model.getInventory(0), banana);
	}
	@Test
	public void testAddInventory() {
		model.addInventory(banana);
		assertEquals(model.getInventory(0), banana);
		assertEquals(model.getInventory(1), banana);
	}
	@Test
	public void testGetInvenFULL() {
		assertEquals(model.getInvenFULL(), model.inventory);
	}
	@Test
	public void testRemoveItem() {
		model.removeItem(1);
		assertEquals(model.getInventory(1), null);
	}
	@Test
	public void testSetLocation(){
		model.setLocation(5, 5);
		assertEquals(model.getUp(), 5);
		assertEquals(model.getSide(), 5);
	}
	@Test
	public void testSetSideLoc() {
		model.setSideLoc(3);
		assertEquals(model.getSide(), 3);
	}
	@Test
	public void testSetUpLoc() {
		model.setUpLoc(3);
		assertEquals(model.getUp(), 3);
	}
	@Test
	public void testGetHP() {
		assertEquals(model.getHP(), 100);
	}
	@Test
	public void testSetHP() {
		model.setHP(50);
		assertEquals(model.getHP(), 50);
	}
	@Test
	public void testGetRoom() {
		assertEquals(model.getRoom(), model.currentRoom);
	}
	@Test
	public void testSetRoom() {
		model.setRoom(room);
		assertEquals(model.getRoom(), room);
	}
}
