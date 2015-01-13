
import java.util.*;

public class AdventureMain {

	static final int INVENTORY = -99;  //for item location
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
	
	/* Commands that work so far:
	GO direction, direction,
	LOOK, QUIT
	?, HELP  <--- not working yet
	*/
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
			// if (word2.equals("AT")) { // FOR LOOKING AT AN ITEM OK
			//	lookAtObject(words[]);
			// }
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
		//case "USE": // needs to be done
			//useObject(words);
			//break;
			
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
		Item q = new Item("device", "What does it do?", 10);	
		itemList.add(q);
		
		Item s = new Item("Spoon", "good for eating..... and other stuff", 5);	
		itemList.add(s);
		
		Item r = new Item("Keycard", "What will it let the user access?", 6);	
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
		r.setExits(4,7,6,2); 
		r.setDesc("This appears to have entrances to many other rooms. Dont get lost, or its game over man!");
		roomList.add(r);
		
		// *******************************Requires flash light because it is so dark************************************8
		r = new Room(4, "cafe", "");
		//		   N E S W
		r.setExits(0,9,3,5); 
		r.setDesc("Its the cafateria, but its way too dark to see anything");// needs object for progression
		roomList.add(r);
		
		r = new Room(5, "kitchen", "");
		//		   N E S W
		r.setExits(0,4,0,0); 
		r.setDesc("It is surprisingly clean inside the kitchen");// after cafe
		roomList.add(r);
		
		r = new Room(6, "reception", "");
		//		   N E S W
		r.setExits(3,10,0,0); //** you can only go east if you have an object ***
		r.setDesc("You feel jittery being this close to the exit. There is a dead guard with a keycard lying next to him");
		roomList.add(r);
		
		r = new Room(7, "barracks", "");
		//		   N E S W
		r.setExits(9,8,0,3); 
		r.setDesc("Where are all the guards? There is a large mound of weapons on the floor");
		roomList.add(r);
		
		r = new Room(8, "filing room", "");
		//		   N E S W
		r.setExits(0,0,0,7); 
		r.setDesc("Filing cabinets take up most of the space in this small room");
		roomList.add(r);
		
		r = new Room(9, "warden's office", "");
		//		   N E S W
		r.setExits(0,0,7,0); 
		r.setDesc("You are so close to freedom. this place emanates with evil");
		roomList.add(r);
		
		// *******************************needs an object to enter***********************************************
		r = new Room(10, "vault", "");
		//		   N E S W
		r.setExits(0,0,0,0); 
		r.setDesc("There is no treasure! Nothing but a small device");
		roomList.add(r);
		
		
		// for (Room m : roomList){
		// System.out.println(m.getTitle());
		// }
	}
	
}   


	
