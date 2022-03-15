package edu.ycp.cs320.groupProj.model;

// model class for TextQuest
// keeps track of the moves, totaling North-South and East-West


public class ObjectModel {
	public String desc;
	public int thing; // this will be the way it affects the player unless its a key
	public Boolean key; // this flag will be true if its the key false otherwise
	
	
	public ObjectModel() {
	}
	public ObjectModel(String d, int num, Boolean f) {
		desc = d;
		thing = num;
		key = f;
	}
	public void setAttributes(String d, int num, Boolean tf) {
		desc = d;
		thing = num;
		key = tf;
	}
	public void setDesc(String d) {
		desc = d;
	}
	public void setThing(int num) {
		thing = num;
	}
	public void setKey(Boolean t) {
		key = t;
	}
	public int getThing() {
		return thing;
	}
	public Boolean getKey() {
		return key;
	}
	public String getDesc() {
		return desc;
	}
	public ObjectModel getObject() {
		return this;
	}
	
	
	
	
}
