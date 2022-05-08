package edu.ycp.cs320.groupProj.model;

// model class for TextQuest
// keeps track of the moves, totaling North-South and East-West


public class ObjectModel {
	public NameTag tag;
//	public int thing; // this will be the way it affects the player unless its a key
	public Boolean key; // this flag will be true if its the key false otherwise
	
	
	public ObjectModel() {
		
	}
	
	/**
	 * Typcially used for inventory objects 
	 * @param n		Tag (nametag)
	 * @param num	The way it affects the player
	 * @param f		key
	 */
	public ObjectModel(NameTag n, Boolean f) {
		tag = n;
//		thing = num;
		key = f;
	}
	
	
	public void setAttributes(NameTag n, Boolean tf) {
		tag = n;
//		thing = num;
		key = tf;
	}
	public void setDesc(String d) {
		tag.setDesc(d);
	}
//	public void setThing(int num) {
//		thing = num;
//	}
	public void setKey(Boolean t) {
		key = t;
	}
//	public int getThing() {
//		return thing;
//	}
	public Boolean getKey() {
		return key;
	}
	public NameTag getTag() {
		return tag;
	}
	public ObjectModel getObject() {
		return this;
	}
	public String getName()
	{
		return this.getTag().getName();
	}
	public String getDesc()
	{
		return this.getTag().getName();
	}
}
