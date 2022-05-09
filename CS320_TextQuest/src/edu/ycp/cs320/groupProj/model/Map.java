package edu.ycp.cs320.groupProj.model;

import java.util.ArrayList;
import java.util.Random;
import edu.ycp.cs320.groupProj.controller.RoomController;

public class Map {
	public Room[][] map;
	public int currentUp;
	public int currentSide;

	// need to add setDark(true) to the parts that are dark
	// and setChest(true) to the chest room
	// and setKey(true) to the key room
	// This part of the code initializes the map
	public Map() {
		this.reset();
	}

	public Room[][] getMap() {
		return map;
	}

	public Room getRoom(int up, int side) {
		return map[up][side];
	}
	
	public void setRoomInventory(int up, int side, ArrayList<ObjectModel> room) {
		map[up][side].setInventory(room);
	}

	public void reset() {
		map = new Room[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				map[i][j] = new Room(false, null, null);
			}
		}

		this.map[1][1] = new Room(true, new NameTag("Marble Corner", "It's a decadant room with marble flooring and polished wood panel walls. There's two doors leading south and east. This is probably the best corner room you've ever seen."), new ArrayList<ObjectModel>(), false, false);
		this.map[1][2] = new Room(true, new NameTag("Empty", "It's a clean room with more marble flooring and more polished wood panel walls. There's a dark wooden door with a golden door knob leading south, east, and west. If you weren't in a hurry, you would probably find a way to take the door knobs."), new ArrayList<ObjectModel>(), false, false);
		this.map[1][3] = new Room(true, new NameTag("Hallway", "It's a fancy hallway leading east and west. The walls are made from polished wood, the floors are marble, but you do notice a couple scratches on the eastern side."), new ArrayList<ObjectModel>(), false, false);
		this.map[1][4] = new Room(true, new NameTag("Hallway", "It's a terrible awful room with scratches all over the formerly polished wood panel walls and blood stains covering the tarnished marble flooring. There's an inviting door to the west and a door that looks like a huge animal slashed it to the east."), new ArrayList<ObjectModel>(), false, false);
		this.map[1][5] = new Room(true, new NameTag("Hallway", "It's a hideous room misisng some of the wood panels, revealing stone underneath, and marble floors covered in so many stains you don't think it could ever be saved. There's a broken door leading west and an unscathed golden door with fancy arches leading east."), new ArrayList<ObjectModel>(), false, false);
		this.map[1][6] = new Room(true, new NameTag("Chest Room!", "It's an extravagent room with marble flooring, polished wood panel walls, and a gilded chest sitting in the middle."), new ArrayList<ObjectModel>(), false, false);
		// [1][7] is a wall
		this.map[1][8] = new Room(true, new NameTag("Monster/Red Gem", "It's a large steel chamber covered in an inconvievable amount of blood and with so many scratch marks your at risk of cutting yourself on the walls."), new ArrayList<ObjectModel>(), true, true);
		// [1][9] is outside the dungeon

		// [2][0] is outside of dungeon
		this.map[2][1] = new Room(true, new NameTag("Blue Gem", "It's a gorgeous room with fancy wooden panel walls and checkerboard pattern marble flooring. There's three doors leading north, south, and east. If this place wasn't flooding you would probably try to live here."), new ArrayList<ObjectModel>(), false, false);
		this.map[2][2] = new Room(true, new NameTag("Empty", "It's a vacant, yet marvelous room with wooden panel walls darkened by polish and spottless marble flooring that you almost feel bad for walking on. There's three doors leading north, south, and west."), new ArrayList<ObjectModel>(), false, false);
		// [2][3] is a wall
		// [2][4] is a wall
		// [2][5] is a wall
		// [2][6] is a wall
		// [2][7] is a wall
		this.map[2][8] = new Room(true, new NameTag("Hallway", "It's a dark steel hallway covered in bloody scratch marks. There's two doors leading north and south."), new ArrayList<ObjectModel>(), false, false);
		// [2][9] is outside of dungeon

		// [3][0] is outside of dungeon
		this.map[3][1] = new Room(true, new NameTag("Open", "It's a sparklingly clean room with marble flooring and polished wooden walls. There's three doors leading north, south, and east."), new ArrayList<ObjectModel>(), false, false);
		this.map[3][2] = new Room(true, new NameTag("Open", "It's a glamourous room with marble tile flooring and polished wooden walls with doors leading in every direction. The floor actually seems to be at a slight incline to the south."), new ArrayList<ObjectModel>(), false, false);
		this.map[3][3] = new Room(true, new NameTag("Hallway", "It's a grungy steel hallway with a couple doors leading east and west. The rest of the room is comparatively dirty to the eastern door."), new ArrayList<ObjectModel>(), false, false);
		this.map[3][4] = new Room(true, new NameTag("Open", "It's an open steel room with doors leading south, east, and west. The walls are a bit rusty."), new ArrayList<ObjectModel>(), false, false);
		this.map[3][5] = new Room(true, new NameTag("Open", "It's a gloomy steel corridor with doors leading south, east, and west."), new ArrayList<ObjectModel>(), false, false);
		this.map[3][6] = new Room(true, new NameTag("Hallway", "It's a dimly lit steel hall leading east and west, it's not so dark that you need a torch but having a lit torch would make you feel better."), new ArrayList<ObjectModel>(), false, false);
		this.map[3][7] = new Room(true, new NameTag("Hallway", "It's a dimly lit steel hall leading east and west, there's a slight odor in the air."), new ArrayList<ObjectModel>(), false, false);
		this.map[3][8] = new Room(true, new NameTag("Turn in Hallway", "It's a dimly lit steel hall that smells awful. The door out is to the west and the hallway continues north."), new ArrayList<ObjectModel>(), false, false);
		// [3][9] is outside of dungeon

		this.map[4][1] = new Room(true, new NameTag("Open", "Its an oppulent room with wooden walls and marble flooring. There's three doors leading north, south, and east, but the arrangement of the marble tiles seem to be pointing you east."), new ArrayList<ObjectModel>(), false, false);
		this.map[4][2] = new Room(true, new NameTag("Key Room", "It's a magnificent chamber with the usual marble floors, wooden walls, but now there are steps in the middle of the room leading up to a raised portion. There's also three doors leading north, south, and west."), new ArrayList<ObjectModel>(), false, false);
		// [4][3] is a wall
		this.map[4][4] = new Room(true, new NameTag("Yellow Gem", "It's a dirty old steel corridor with three doors leading north, south, and east."), new ArrayList<ObjectModel>(), false, false);
		this.map[4][5] = new Room(true, new NameTag("Open", "It's a foul smelling steel chamber with three doors leading north, south, and west. The odor is stronger to the south."), new ArrayList<ObjectModel>(), false, false);
		// [4][6] is a wall
		// [4][7] is a wall
		// [4][8] is a wall
		// [4][9] is outside of dungeon

		// [5][0] is outside of dungeon
		this.map[5][1] = new Room(true, new NameTag("Open", "It's a brilliant chamber with polished wooden panel walls and marble flooring. There a three doors leading north, south, and east. You think to yourself that this area looks a lot better than where you started."), new ArrayList<ObjectModel>(), false, false);
		this.map[5][2] = new Room(true, new NameTag("Open", "It's a pristine room with marble flooring and wooden walls. There are two doors leading north and west. The pattern of the floor in this room seems to be leading you north."), new ArrayList<ObjectModel>(), false, false);
		// [5][3] is a wall
		this.map[5][4] = new Room(true, new NameTag("Open", "It's a murky steel room with two doors leading north and east. There's a large scratch mark on the door to the east."), new ArrayList<ObjectModel>(), false, false);
		this.map[5][5] = new Room(true, new NameTag("Open", "It's a horrific steel room covered in scratch marks. There are three doors leading north, south, and west. The room smells awful."), new ArrayList<ObjectModel>(), false, false);
		// [5][6] is a wall
		this.map[5][7] = new Room(true, new NameTag("Dark/Black Gem", "It's a stone room enveloped in so much fog the torch is just barley able to help. Atleast you think it's stone, your really just going off of how it feels."), new ArrayList<ObjectModel>(), false, false);
		this.map[5][8] = new Room(true, new NameTag("Dark", "It's a stone room completely enveloped in a thick layer of dark fog. You think you can only go south but you can't really tell. What you can tell is that the floor is scratched to shit."), new ArrayList<ObjectModel>(), true, true);
		// [5][9] is outside of dungeon

		this.map[6][1] = new Room(true, new NameTag("Stone Tunnel", "It's a narrow stone tunnel leading north and south. The south path leads to more stone but the northern path seems to lead to something else."), new ArrayList<ObjectModel>(), false, false);
		// [6][2] is a wall
		// [6][3] is a wall
		// [6][4] is a wall
		this.map[6][5] = new Room(true, new NameTag("Metal hall", "It's a large steel hallway leading north and south. There are scratch marks on the walls to the north."), new ArrayList<ObjectModel>(), false, true);
		// [6][6] is a wall
		this.map[6][7] = new Room(true, new NameTag("Dark Monster", "It's a stone room completely enveloped in a thick layer of dark fog. You think there's two paths to the south, and east but you honestly aren't sure. You also aren't sure if the liquid your standing in is more blood or something worse."), new ArrayList<ObjectModel>(), true, true);
		this.map[6][8] = new Room(true, new NameTag("Dark", "It's a stone room completely enveloped in a thick layer of dark fog. You think there's three paths to the north, south, and west but you honestly aren't sure."), new ArrayList<ObjectModel>(), false, true);

		// [7][0] is outside of dungeon
		this.map[7][1] = new Room(true, new NameTag("Threeway Stone Tunnel", "It's a narrow stone tunnel that splits into three paths, leading north, south , and west. You notice a blood stain on the wall of the southern path."), new ArrayList<ObjectModel>(), false, false);
		this.map[7][2] = new Room(true, new NameTag("Green Gem", "It's a stone corridor with an opening to the east, west, and south."), new ArrayList<ObjectModel>(), false, true);
		this.map[7][3] = new Room(true, new NameTag("Starting Area", "It's a great stone chamber with an opening to the west and the south."), new ArrayList<ObjectModel>(), false, false);
		// [7][4] is a wall
		this.map[7][5] = new Room(true, new NameTag("Misty/Stone/Metal crossroad", "It's a stone corridor with an opening to the north, south, and east. The northern path looks metallic. The eastern side of the room has been enveloped in a layer of mist."), new ArrayList<ObjectModel>(), true, true);
		this.map[7][6] = new Room(true, new NameTag("Misty", "It's a stone corridor enveloped in fog. You manage to make out three openings to the south, east, and west."), new ArrayList<ObjectModel>(), false, true);
		this.map[7][7] = new Room(true, new NameTag("Foggy", "It's a stone corridor completely enveloped in fog. You barley manage to make out four openings in all direcitons. There's a trail of blood leading from the south to the north."), new ArrayList<ObjectModel>(), false, true);
		this.map[7][8] = new Room(true, new NameTag("Foggy", "It's a stone corridor completely enveloped in fog. You barley manage to make out three openings to the north, south, and west."), new ArrayList<ObjectModel>(), false, true);
		// [7][9] is outside the dungeon

		// [8][1] is outside the dungeon
		this.map[8][1] = new Room(true, new NameTag("Monster", "It's a stone chamber soaked in blood with an exit to the north and to the east."), new ArrayList<ObjectModel>(), true, true);
		this.map[8][2] = new Room(true, new NameTag("Bottom Stone", "It's a stone corridor with three openings to the north, east, and west. The opening to the west has a trail of blood leading into it."), new ArrayList<ObjectModel>(), false, true);
		this.map[8][3] = new Room(true, new NameTag("Bottom Stone", "It's a stone corridor with three openings, leading north, east, and west. The path to the east looks narrow."), new ArrayList<ObjectModel>(), false, true);
		this.map[8][4] = new Room(true, new NameTag("Bottom Stone Tunnel", "It's a tight stone tunnel leading east and west. The light seems to be getting darker to the east."), new ArrayList<ObjectModel>(), false, false);
		this.map[8][5] = new Room(true, new NameTag("Misty/Stone crossroad", "It's a dimly lit stone chamber with a path to the north, east, and west. The eastern side of the room has been enveloped in a layer of mist."), new ArrayList<ObjectModel>(), false, true);
		this.map[8][6] = new Room(true, new NameTag("Foggy", "It's a stone corridor enveloped in fog. You manage to make out three openings to the north, east, and west."), new ArrayList<ObjectModel>(), false, true);
		this.map[8][7] = new Room(true, new NameTag("Foggy", "It's a stone corridor enveloped in fog. You manage to make out three openings to the north, east, and west. There's a trail of blood leading north."), new ArrayList<ObjectModel>(), false, true);
		this.map[8][8] = new Room(true, new NameTag("Foggy", "It's a stone corridor enveloped in fog. You manage to make out two openings to the north and the west."), new ArrayList<ObjectModel>(), false, true);
		// [8][9] is outside the dungeon

		// top dark section ==
		this.map[1][8].setDark(true);
		this.map[2][8].setDark(true);
		this.map[3][8].setDark(true);
		this.map[3][7].setDark(true);
		this.map[3][6].setDark(true);

		// bottom dark section ==
		this.map[5][7].setDark(true);
		this.map[5][8].setDark(true);
		this.map[6][7].setDark(true);
		this.map[6][8].setDark(true);
		this.map[7][6].setDark(true);
		this.map[7][7].setDark(true);
		this.map[7][8].setDark(true);
		this.map[8][6].setDark(true);
		this.map[8][7].setDark(true);
		this.map[8][8].setDark(true);

		// chest :_)
		this.map[1][6].setChest(true);
		this.map[4][2].createKey();
		
		//FOR TESTING CHEST AND KEY, COMMENT OUT WHEN FINISHED
		//this.map[8][3].setChest(true);
		//this.map[8][3].createKey();
		
		//Gems
		//When you grab a gem for the first time you get 500 points and the number updates so that you can't get more
		//You also get more points at the end if you pick up every gem
		this.map[2][1].getInven().add(new ObjectModel(new NameTag(("Sapphire"), ("as blue as the ocean over by the beach that I work at over the summer.")), false));
		this.map[1][8].getInven().add(new ObjectModel(new NameTag(("Ruby"), ("as red as a box of candy on valentines day.")), false));
		this.map[7][2].getInven().add(new ObjectModel(new NameTag(("Emerald"), ("as green as a well maintained suburban lawn.")), false));
		this.map[4][4].getInven().add(new ObjectModel(new NameTag(("Diamond"), ("as yellow as one of those cool ass smiley face cookies they got down by this local bakery near where I work over the summer.")), false));
		this.map[5][7].getInven().add(new ObjectModel(new NameTag(("Onyx"), ("as dark as the room you found it in.")), false));

		RoomController rC = new RoomController();
		Random rand = new Random();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				rC.setModel(this.map[i][j]);
				int num = rand.nextInt(3);
				rC.setRoomType(num);
			}
		}
	}

}

