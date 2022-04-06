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


}
