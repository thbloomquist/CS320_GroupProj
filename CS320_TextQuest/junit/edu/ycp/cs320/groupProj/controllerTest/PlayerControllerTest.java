package edu.ycp.cs320.groupProj.controllerTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;


import edu.ycp.cs320.groupProj.model.Map;
import edu.ycp.cs320.groupProj.controller.RoomController;
import edu.ycp.cs320.groupProj.model.Room;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.controller.PlayerController;
import edu.ycp.cs320.groupProj.controller.ObjectController;


public class PlayerControllerTest {
	PlayerModel model = new PlayerModel();
	PlayerController controller = new PlayerController();
	
	
	// 
	@Before
	public void setUp() {
		controller.setModel(model);
	}
	
	@Test
	public void testSortInven() {
		ObjectModel temp = new ObjectModel();
		ObjectController cTemp = new ObjectController();
		cTemp.setModel(temp);
		cTemp.banana();
		temp = cTemp.getThis();
		// need item to add to players inventory so ^^^
		
//		model.addItemToInventory(temp);
//		assertTrue(controller.sortInven(model.getInventory()) == 2); 
//		// iNum should be set to 2 as 0 index is the torch and 1 index is now the banana
//		
//		assertTrue(model.getItemFromInventory(model.getiNum()-1).equals(temp));
		// the banana should also have been sorted into the first index (one less than the current iNum)
		
	}
	
	
	
}
