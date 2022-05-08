package edu.ycp.cs320.groupProj.modelTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.NameTag;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.controller.ObjectController;
import edu.ycp.cs320.groupProj.model.Room;

public class PlayerModelTest {
	private PlayerModel model;
	private PlayerModel model2;
	private Room testRoom;
	@Before
	public void setUp() {
		model = new PlayerModel();
		model.reset();
//		testRoom = new Room(true, new NameTag("Test", "Test"), new ObjectModel[10], false, false);
	}
	
	@Test
	public void testInitial() {
		assertTrue(model.getUp() == 7);
		
		assertTrue(model.getSide() == 3);
		
//		assertTrue(model.getiNum() == 1);
	}
	@Test
	public void testTorch() {
		String torchRef = model.getItemFromInventory(0).getTag().getName();
		assertTrue(torchRef.equals("torch"));
	}
	@Test
	public void testHP() {
		assertTrue(model.getHP() == 100);
		model.setHP(model.getHP()-10);
		assertTrue(model.getHP()==90);
		model.reset();
		assertTrue(model.getHP()==100);
	}
	@Test
	public void testSetLoc() {
		model.setLocation(5, 5);
		assertTrue(model.getUp() + model.getSide() == 10);
	}
	@Test
	public void testGetMatches() {
		assertTrue(model.getMatches() == 10);
	}
	@Test
	public void testSetMatches() {
		model.setMatches(40);
		model.setMatches(model.getMatches()-5); // should be the previously set 40 - 5 = 35
		assertTrue(model.getMatches() == 35);
	}
	@Test
	public void testInitLit() {
		assertFalse(model.isLit());
	}
	@Test
	public void testSetLit() {
		model.setLit(true);
		assertTrue(model.isLit());
	}
	@Test
	public void testSetRoom() {
		model.setRoom(testRoom);
		assertTrue(model.getRoom() != null);
		// the TestRoom contains no monsters or items
		// if the models.GetRoom has no monsters or items, it is LIKELY the right room
		assertFalse(model.getRoom().hasMonster()); 
		assertTrue(model.getRoom().checkEmpty());
	}
//	@Test
//	public void testAddItem() {
//		ObjectModel testTemp = new ObjectModel();
//		ObjectModel temp = new ObjectModel(); // this test uses ObjectModel & ObjectModel controller to create a new item
//		ObjectController cTemp = new ObjectController();
//		cTemp.setModel(temp);
//		cTemp.banana();
//		testTemp = cTemp.getThis();
//		model.addItemToInventory(cTemp.getThis());
////		assertTrue(testTemp.equals(model.getItemFromInventory(model.getiNum()-1))); // this also test the iNum feature
//		// as it's always one ahead of the most recently added item (to avoid errors when adding the nxt one).	
//	}
//	@Test
//	public void testINum() {
//		// originally it's 0 then the torch is created and it's at 1 for the nxt item
//		assertTrue(model.getiNum() == 1);
//		model.addItemToInventory(new ObjectModel());
//		model.addItemToInventory(new ObjectModel());
//		model.addItemToInventory(new ObjectModel()); // SHOULD BE 1 + 3 = 4
//		assertTrue(model.getiNum() == 4);
//	}
//	@Test
//	public void testsetINum() {
//		assertTrue(model.getiNum() == 1);
//		model.setiNum(7); // this is pretty self-explanatory
//		assertTrue(model.getiNum() == 7);
//	}
	@Test
	public void testGetScore() {
		assertTrue(model.getScore() == 0); // score starts as zero always
	}
	@Test
	public void testSetScore() {
		model.setScore(30);
		assertTrue(model.getScore() == 30);
	}
	@Test
	public void testIncrementScore() {
		model.incrementScore(10);
		assertTrue(model.getScore()==10);
		model.incrementScore(10);
		assertFalse(model.getScore()==10);
		assertTrue(model.getScore()==20);
		model.incrementScore(20);
		assertFalse(model.getScore()==20);
		assertTrue(model.getScore()==40);
	}
	@Test
	public void testReset() {
		// reset should be equal to the init values of a playermodel
		PlayerModel temp = new PlayerModel();
		PlayerModel temp2 = new PlayerModel();
		temp2.reset();
		assertTrue(temp2.equals(temp)); // very weird how this doesn't work actually
		
	}
	
}
