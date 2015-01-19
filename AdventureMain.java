import java.util.*;

public class AdventureMain {

	
	
	//room constants -- none must be the same as other rooms.
	static final int INVENTORY = -99;  //for item location
	static final int WARDENS_OFFICE = 5;  
	static final int RECEPTION = 6;  
	
	
	//global (instance) variables
	ArrayList<Room> roomList = new ArrayList<Room>();
	ArrayList<Item> itemList = new ArrayList<Item>();
	//ArrayList<String> inventory = new ArrayList<String>();
	int currRoomID = 1;
	Room currentRoom = new Room(0,"","");;
	
	public static void main(String[]args){
		new AdventureMain();
	}
	
	AdventureMain() {
		
		boolean playing = true;
		String command = "";
		setupRooms();
		makeItems();
		updateCurrentRoom(currRoomID);
		//boolean dead = false; 
		String introText = 
		"You awake to the deafening cries of your own thoughts, all pleading for escape from  \n" + 
		"this hell. Your cell is small and dark, and yet, a bright hope suddenly sparks within you.\n" +
		"This is the night to escape if you are to retain your sanity.\n" +
		"WELCOME TO: PRISONBREAK\n"+
		"\t Type help for a list of commands";
		
		System.out.println(introText);
		
		//main game loop
		//set to false if the user quits
		while (playing) {
			command = getCommand();
			
			playing = parseCommand(command);
			
			
		}		
		
	}
	
	void updateCurrentRoom(int n) {
		//check each room for current room id
		for (Room r : roomList){
			if (r.getRoomID() == n) {
				currentRoom = r; //set currentRoom to this room
				break;
			}
		}
	}
	
	String getCommand() {
		Scanner sc = new Scanner(System.in);
		//System.out.println("\nCurrent location: " + currentRoom.getTitle());
		String text = sc.nextLine();
		if (text.length() == 0) text = "qwerty";
		return text;
	}
	
	boolean parseCommand(String text) {
		
		
		text = text.toUpperCase().trim();
		
		//split text into words
		String words[] = text.split(" ");
		String word1 = words[0];
		String word2 = "";
		int numWords = words.length;
		if (numWords > 1) word2 = words[1];
		//preparsing: remove GO (also remove "THE")
		if (word1.equals("GO")) word1 = words[1];	//ie. GO NORTH --> NORTH
		
		//directions:
		switch(word1) {
		case "QUIT":
		case "EXIT":
		case "DIE":
		case "END":
		case "PERISH":
		case "EXPIRE":
		case "TERMINATE":
		case "CRUCIFY":
		case "WASTE":
		case "EXPLODE":
			System.out.print("Do you really want to quit the game?\n");
			String ans = getCommand().toUpperCase();
			if (ans.equals("Y") || ans.equals("YES")) return false;	
			System.out.println("\n" + currentRoom.getTitle()); 			
			return true;
		case "N": case "E": case "S": case "W":
		case "NORTH": case "SOUTH": case "EAST": case "WEST":
			move(word1.charAt(0));
			break;
		case "LOOK":
			System.out.println("You are in the "+ currentRoom.getTitle() + ". " + currentRoom.getDesc());
			break;
		case "?": case "HELP":
			System.out.println("Commands: \n" + 
			"\"North\"-sends you in the north direction \n" +
			"\"East\"- Sends you in the East direction \n"+
			"\"South\"- Sends you in the South direction \n" +
			"\"West\"- Sends you in the West direction \n" +
			"\"Look\"- Gives you a description of your surroundings \n"+
			"\"Quit\" or \"die\"- ends your game (lol) \n"+
			"\"Help\"- gives you a list of commands (duh) \n"+
			"\"Take (Item name)\"- picks up specified item \n"+
			"\"Use (Item name)\"- uses specified item \n");
			break;
		case "TAKE":
		case "PICK" :
			takeObject(words);
			break;
		case "INVENTORY":
		case "I":
			displayInventory();
			break;
		case "USE": 
			useObject(words);
			break;
			
		}	
		return true;
	} //end of parseCommand()
	
	void move(char dir) {
		int newRoom = currentRoom.getExit(dir);
		if (newRoom == 0) {
			System.out.println("You can't go that way");
			return;
		}
		currRoomID = newRoom;
		updateCurrentRoom(currRoomID);
		System.out.println("You walk to the "+ currentRoom.getTitle() + ". " + currentRoom.getDesc());
		//now see if there are any items in the room. 
		//go through all of the itmes, 
		for (Item item : itemList){
			//Any that have location == currRoomID --> print out.
			if(item.location == currRoomID) {
				System.out.println("There is a " + item.name + " here.");
			}
		}
		
	}

	void useObject(String[] words) {
		//see if object <words[1]> is in inventory
		boolean found = false;	
		for (Item q : itemList) {
			if (q.name.toUpperCase().equals(words[1]) && q.location == INVENTORY) { 				
				found = true;				
				break;
			}
		}	
		if (! found) {
			System.out.println("The thingy is not in your inventory");
			return;
		}	
		//now we know that the item is in the inventory so ...
		
		//special situations
		if (words[1].equals ("SPOON") && currRoomID == RECEPTION){			
			System.out.println("The wall starts to crumble and falls over.");
			currRoomID = 10;
			System.out.println("You are in the vault. There is no treasure, only a small device!");
			return;
		}
		
		if (words[1].equals ("DEVICE") && currRoomID == 10){			
			System.out.println("You press a button and are emersed in light, you magicly appear in the warden's office");
			currRoomID = 9;
			System.out.println("You are in the Warden's office. You are so close to freedom, you find a keycard and a security door.");
			return;
		}
		if (words[1].equals ("KEYCARD") && currRoomID == 9){			
			System.out.println("You press a button and are emersed in light, you magicly appear in the warden's office");
			currRoomID = 11;
			System.out.println("You are outside now. You are free!!");
			System.out.println("Then you hear the shots and it's too late");
			System.out.println("GAME OVER KID GG");
			System.out.println("you played the system and it bit you in the butt!");
			System.exit(0);
			return;
		}
		if (words[1].equals ("VIAL") && currRoomID == 18){			
			System.out.println("You consume the vial of liquid.");
			System.out.println("Your vision becomes blurry,");
			System.out.println("you fall to the ground,");
			System.out.println("and your eyes close");
			System.out.println("Good job finding the only way to die in this game.");
			System.exit(0);
		}
			
		System.out.println("this thingy won't work here");
	}
	
	void takeObject(String[] words) {
		String itemName = "";
		if (words[0].equals ("PICK") && words[1].equals ("UP") )
		itemName = words[2];
		else {
			itemName = words[1];
		}	 
		//is itemName in the room?
		boolean found = false;		
		for (Item q : itemList) {
			if (q.location == currRoomID) { // it is in the room
				q.location = INVENTORY;			
				found = true;
				System.out.println("You take the " + itemName);
				break;
			}
		}
		if (! found) {
			System.out.println("There is no " + itemName + " here.");
			return;
		}	
	}
	


	void displayInventory() {
		System.out.println("******************************************************************");
		System.out.println("Your Inventory:\n");
		//go thourgh each item in itemList and print out the names of all items that have location=-99
		for (Item qq: itemList){  
			//Any that have location == currRoomID --> print out.
			if(qq.location == -99) {
				System.out.println(qq.name);
			}
		}
		System.out.println("******************************************************************");
	}
	
	/////////////////////////////////////////////////////////////////////////
	void makeItems() {
		Item q = new Item("Device", "What does it do?", 10); // teleports you to the warden's office from the vault.
		itemList.add(q);
		
		Item s = new Item("Spoon", "good for eating..... and other stuff", 17);	// Allows you to break the reception wall into the vault
		itemList.add(s);
		
		Item r = new Item("Keycard", "What will it let the user access?", 9);	//allows you to escape through a door in the warden's office.
		itemList.add(r);
		
		
		Item r = new Item("Vial", "What will happen if you use it?", 18);	// kills you
		itemList.add(r);
		
		
	}
	
	
	void setupRooms() {

		Room r = new Room(1, "Jail Cell","");
		//		   N E S W
		r.setExits(0,2,0,0);
		r.setDesc("A small and dark jail cell. Your sanity feels unstable in this room.");
		roomList.add(r);
		
		r = new Room(2, "Jailblock", "It's dark.");
		//		   N E S W
		r.setExits(0,3,0,1); 
		r.setDesc("A massive gloomy room. You feel uneasy on the outside of your cell");
		roomList.add(r);
		
		r = new Room(3, "Hub", "");
		//		   N E S W
		r.setExits(4,7,RECEPTION,2); 
		r.setDesc("This appears to have entrances to many other rooms. Dont get lost, or its game over man!");
		roomList.add(r);
		
		
		r = new Room(4, "cafe", "");
		//		   N E S W
		r.setExits(0,0,3,5); 
		r.setDesc("Its the cafateria, it's pretty gross and hasn't been cleaned in awhile");
		roomList.add(r);
		
		r = new Room(5, "kitchen", "");
		//		   N E S W
		r.setExits(0,4,0,0); 
		r.setDesc("It is surprisingly clean inside the kitchen. There is a note here that says: \n I took the diggers spoon, so you will never escape! ~warden~");// after cafe
		roomList.add(r);
		
		r = new Room(RECEPTION, "reception", "");
		//		   N E S W
		r.setExits(3,0,0,0); //** you can only go east if you have an object ***
		r.setDesc("You feel jittery being this close to the exit. One of the walls looks very brittle");
		roomList.add(r);
		
		r = new Room(7, "barracks", "");
		//		   N E S W
		r.setExits(0,8,0,3); 
		r.setDesc("Where are all the guards? All the guns are missing");
		roomList.add(r);
		
		r = new Room(8, "filing room", "");
		//		   N E S W
		r.setExits(0,12,0,7); 
		r.setDesc("Filing cabinets take up most of the space in this small room. \n There appears to be a tunnel dug into the wall");
		roomList.add(r);
	
		
		r = new Room(9, "warden's office", "");
		//		   N E S W
		r.setExits(0,0,0,0); 
		r.setDesc("You are so close to freedom, you find a keycard and a security door.");
		roomList.add(r);
		
		
		r = new Room(10, "vault", "");
		//		   N E S W
		r.setExits(0,0,0,0); 
		r.setDesc("There is no treasure! Nothing but a small device");
		roomList.add(r);
		
		r = new Room(11, "outside", "");
		//         N E S W
		r.setExits(0,0,0,0);
		r.setDesc("You are free1111");
		roomList.add(r);
		
		r = new Room(12, "Dark tunnel", "");
		//         N E S W
		r.setExits(0,13,0,8);
		r.setDesc("Why did you go in here? its so scary!");
		roomList.add(r);
		
		
		r = new Room(13, "Cave Entrance", "");
		//         N E S W
		r.setExits(15,0,14,12);
		r.setDesc("You find yourself in a massive cave. Is this natural, or man-made?");
		roomList.add(r);
		
		r = new Room(14, "Abandoned village", "");
		//         N E S W
		r.setExits(13,16,0,0);
		r.setDesc("Did people live here? To the right, The largest house's door is open");
		roomList.add(r);
		
		r = new Room(15, "Underground Stream", "");
		//         N E S W
		r.setExits(0,0,13,0);
		r.setDesc("Fresh water runs along the cavern edge. Pretty pointless");
		roomList.add(r);
		
		r = new Room(16, "Front Hallway", "");
		//         N E S W
		r.setExits(17,0,18,14);
		r.setDesc("What a lovley hall");
		roomList.add(r);
		
		r = new Room(17, "Dining Room", "");
		//         N E S W
		r.setExits(0,0,16,0);
		r.setDesc("A fancy dining hall that appears untouched.");
		roomList.add(r);
		
		r = new Room(17, "Lounge", "");
		//         N E S W
		r.setExits(0,0,16,0);
		r.setDesc("A spacious room with lots of places to sit, you find a vial of something on the ground.");
		roomList.add(r);
		
		
		// for (Room m : roomList){
		// System.out.println(m.getTitle());
		// }
	}
	
}   


