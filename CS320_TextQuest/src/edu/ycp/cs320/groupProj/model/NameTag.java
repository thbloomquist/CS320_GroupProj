package edu.ycp.cs320.groupProj.model;

public class NameTag {
	private String name, desc;
	
	//NameTags are used to create Items and Rooms
	//They are used to identify specific game objects through checking their name or description
	public NameTag(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}	
	public String getName() {
		return name;
	}
	public String getDesc() {
		return desc;
	}
	public void setName(String n) {
		name = n;
	}
	public void setDesc(String n) {
		desc = n;
	}
}