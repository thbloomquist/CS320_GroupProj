package edu.ycp.cs320.groupProj.controller;

import edu.ycp.cs320.groupProj.model.Room;
import edu.ycp.cs320.groupProj.model.ObjectModel;

public class RoomController {
	private Room currentRoom;

	public void setModel(Room cRoom) {
		currentRoom = cRoom;
	}

	public Boolean contains(String item) {
		Boolean thing = false;
		for (int i = 0; i < currentRoom.getInven().length; i++) {
			if (currentRoom.getInven()[i] != null) {
				if (currentRoom.getInven()[i].getTag().getName().equals(item)) {
					thing = true;
				}
			}
		}
		return thing;
	}
	public void setRoomType(int rNum) {
		switch(rNum) {
		case 0:
			// 1 is slime type
			currentRoom.setCel("Write a description about a slimy ceiling here.");
			currentRoom.setFloor("Write a description about a slimy floor here.");
			break;
		case 1:
			//2 is fire type
			currentRoom.setCel("Write a description about a firey ceiling here.");
			currentRoom.setFloor("Write a description about a firey floor here.");
			break;
		case 2:
			currentRoom.setCel("Write a description about an icey ceiling here.");
			currentRoom.setFloor("Write a description about an icey floor here.");
			//ice type
			break;
			
		case 3:
			currentRoom.setCel("Write a description about a candy ceiling here.");
			currentRoom.setFloor("Write a description about a candy floor here.");
			//candy type
			break;
		}
	}

}
