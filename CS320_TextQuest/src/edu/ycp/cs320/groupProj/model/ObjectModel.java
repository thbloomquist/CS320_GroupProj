package edu.ycp.cs320.groupProj.model;

// model class for TextQuest
// keeps track of the moves, totaling North-South and East-West


public class ObjectModel {
	public NameTag tag;
	public int thing; // this will be the way it affects the player unless its a key
	public Boolean key; // this flag will be true if its the key false otherwise

	public ObjectModel() {
		
	}
	public ObjectModel(NameTag n, int num, Boolean f) {
		tag = n;
		thing = num;
		key = f;
	}
	public void setAttributes(NameTag n, int num, Boolean tf) {
		tag = n;
		thing = num;
		key = tf;
	}
	public void setDesc(String d) {
		tag.setDesc(d);
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
	public NameTag getTag() {
		return tag;
	}
	//Returns name of object
	public String getName()
	{
		return this.tag.getName();
	}
	//Returns description of object
	public String getDesc()
	{
		return this.tag.getDesc();
	}
	public ObjectModel getObject() {
		return this;
	}
	
	
	
	
}
