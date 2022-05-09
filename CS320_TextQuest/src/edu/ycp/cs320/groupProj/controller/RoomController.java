package edu.ycp.cs320.groupProj.controller;

import edu.ycp.cs320.groupProj.model.Room;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.controller.ObjectController;

public class RoomController {
	private Room currentRoom;

	public void setModel(Room cRoom) {
		currentRoom = cRoom;
	}

	public Boolean contains(String item) {
		Boolean thing = false;
		for(int i = 0; i < currentRoom.getInven().size(); i++) {
			if(currentRoom.getInven().get(i).getName().toLowerCase().equals(item)) {
				thing = true;
			}
		}
		return thing;
	}
	public void setRoomType(int rNum) {
		switch(rNum) {
		case 0:
			// 1 is slime type
			currentRoom.setCel("A strange green slime is leaking through a crack in the ceiling");
			currentRoom.setFloor("The floor has patches of a weird green gooey substance, it reminds you of toxic waste");
			break;
		case 1:
			//2 is fire type
			currentRoom.setCel("The ceiling is composed of entirely igneous rock from hardened magma");
			currentRoom.setFloor("Below you is a floor comprised entirely of lava rock- you break into sweat as you feel the heat rising");
			break;
		case 2:
			currentRoom.setCel("The ceiling is encased in ice, a few icicles hang directly above you");
			currentRoom.setFloor("The floor is comprised entirely of ice, better watch your step!");
			//ice type
			break;
			
		case 3:
			currentRoom.setCel("The ceiling has a big red and white spiral extending from the middle of it, it looks like a big candy cane");
			currentRoom.setFloor("Below you is a neon bright floor containing almost every color of the rainbow, it's suspiciously pretty");
			//candy type
			break;
		}
	}
	public void assignKey() {
		ObjectController obj = new ObjectController();
		ObjectModel temp = new ObjectModel();
		
		obj.setModel(temp);
		obj.Key();
		int te = 0;
		Boolean first1 = true;
		for(int i = 0; i < currentRoom.getInven().size(); i++) {
			if(currentRoom.getInven().get(i) == null && first1) {
				te = i;
				first1 = false;
			}
		}
		currentRoom.getInven().set(te, temp);
	}

}
