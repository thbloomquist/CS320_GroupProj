package edu.ycp.cs320.groupProj.modelTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.groupProj.model.NameTag;

public class TagTest {
	NameTag tag1;
	NameTag tag2;
	
	@Before
	public void setUp() {
		tag1 = new NameTag();
		tag2 = new NameTag("Banana", "It's a yellow banana with a bit of brown on the top");
	}
	
	@Test
	public void testgetDesc() {
		assertTrue(tag1.getDesc().toLowerCase().equals("default tag desc"));
		assertTrue(tag2.getDesc().toLowerCase().equals("it's a yellow banana with a bit of brown on the top"));
	}
	@Test
	public void testGetName() {
		assertTrue(tag1.getName().toLowerCase().equals("default tag name"));
		assertTrue(tag2.getName().toLowerCase().equals("banana"));		
	}
	@Test
	public void testSetDesc() {
		String n = "New Description!";
		tag1.setDesc(n);
		assertTrue(tag1.getDesc().equals(n));
	}
	@Test
	public void testSetName() {
		String n = "New Name!";
		tag1.setName(n);
		assertTrue(tag1.getName().equals(n));
	}
}
