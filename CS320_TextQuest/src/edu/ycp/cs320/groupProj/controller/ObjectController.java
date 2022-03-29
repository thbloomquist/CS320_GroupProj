package edu.ycp.cs320.groupProj.controller;

import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.model.NameTag;

public class ObjectController {
	private ObjectModel model;
	
	public void setModel(ObjectModel nModel) {
		this.model = nModel;
	}
	public void setAtt(String d, int num, Boolean f) {
		model.setDesc(d);
		model.setThing(num);
		model.setKey(f);
	}
	public ObjectModel banana() {
		NameTag n = new NameTag("Banana", "It's a yellow banana");
		int num = 5;
		boolean f = false;
		ObjectModel thing = new ObjectModel(n, num, f);
		return thing;
	}
}
