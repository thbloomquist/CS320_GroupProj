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


public class RoomControllerTest {
	Map m = new Map();
	RoomController r1 = new RoomController();
	RoomController r2 = new RoomController();
	
	
	// 
	@Before
	public void setUp() {
		r1.setModel(m.getRoom(8, 3));
		r2.setModel(m.getRoom(7, 3));
	}
	
	@Test 
	public void testAssignKey() {
		r1.assignKey();
		assertFalse(m.getRoom(8, 3).checkEmpty());
	}
	
	
}
