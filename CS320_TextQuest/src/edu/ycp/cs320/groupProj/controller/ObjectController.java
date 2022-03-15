package edu.ycp.cs320.groupProj.controller;

import edu.ycp.cs320.groupProj.model.ObjectModel;

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
		String t = "Banana";
		int num = 5;
		boolean f = false;
		ObjectModel thing = new ObjectModel(t, num, f);
		return thing;
	}
}
