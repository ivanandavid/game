//package AdventureGame;
import java.util.*;

public class AdventureMain {

	//global (instance) variables
	ArrayList<Room> roomList = new ArrayList<Room>();
	int currRoomID = 1;
	Room currentRoom = new Room(0,"","");;
	
	public static void main(String[]args){
		new AdventureMain();
	}
	
	AdventureMain() {
		
		boolean playing = true;
		String command = "";
		setupRooms();
		updateCurrentRoom(currRoomID);
		//boolean dead = false; 
		String introText = 
		"You awake to the deafening cries of your own thoughts, all pleading for escape from  \n" + 
		"this hell. Your cell is small and dark, and yet, a bright hope suddenly sparks within you.\n" +
		"This is the night to escape if you are to retain your sanity.\n" +
		"WELCOME TO: PRISONBREAK";
		
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
			if (word2.equals("AT")) {
				//	lookAtObject(words[]);
				break;
			}
			System.out.println("You are in the "+ currentRoom.getTitle() + ". " + currentRoom.getDesc());
			break;
		default: 
			System.out.println("Sorry, I don't understand that command");
		}
		System.out.println("\n" + currentRoom.getTitle());			
		return true;
		
		
	}			
	
	void move(char dir) {
		int newRoom = currentRoom.getExit(dir);
		if (newRoom == 0) {
			System.out.println("You can't go that way");
			return;
		}
		currRoomID = newRoom;
		updateCurrentRoom(currRoomID);
		System.out.println("You walk to the "+ currentRoom.getTitle() + ". " + currentRoom.getDesc());
	}
	
	
	void setupRooms() {

		Room r = new Room(1, "Jail Cell", "");
		//		   N E S W
		r.setExits(0,2,0,0);
		r.setDesc(
		"A small and dark jail cell. Your sanity feels unstable in this room.");
		roomList.add(r);
		
		r = new Room(2, "Jailblock", "It's dark.");
		//		   N E S W
		r.setExits(0,3,0,1); //fix these exits
		r.setDesc(
		"A massive gloomy room. You feel uneasy on the outside of your cell");// Description goes here ivan
		roomList.add(r);
		
		r = new Room(3, "Hub", "");
		//		   N E S W
		r.setExits(4,7,6,2); //fix these exits
		r.setDesc(
		"This appears to have entrances to many other rooms. Dont get lost, or its game over man!");// Description goes here ivan
		roomList.add(r);
		
		// *******************************Requires flash light because it is so dark************************************8
		r = new Room(4, "cafe", "");
		//		   N E S W
		r.setExits(0,9,3,5); //fix these exits
		r.setDesc(
		"Its the cafateria, but its way too dark to see anything");// needs object for progression
		roomList.add(r);
		
		r = new Room(5, "kitchen", "");
		//		   N E S W
		r.setExits(0,4,0,0); //fix these exits
		r.setDesc(
		"It is surprisingly clean inside the kitchen");// after cafe
		roomList.add(r);
		
		r = new Room(6, "reception", "");
		//		   N E S W
		r.setExits(3,0,0,0); //fix these exits
		r.setDesc(
		"You feel jittery being this close to the exit. There is a guard with his back turned to you");// Description goes here ivan
		roomList.add(r);
		
		r = new Room(7, "barracks", "");
		//		   N E S W
		r.setExits(9,8,0,3); //fix these exits
		r.setDesc(
		"Where are all the guards? There is a large mound of weapons on the floor");// Description goes here ivan
		roomList.add(r);
		
		r = new Room(8, "filing room", "");
		//		   N E S W
		r.setExits(0,0,0,7); //fix these exits
		r.setDesc(
		"Filing cabinets take up most of the space in this small room");// Description goes here ivan
		roomList.add(r);
		
		r = new Room(9, "warden's office", "");
		//		   N E S W
		r.setExits(0,0,7,0); //fix these exits
		r.setDesc(
		"You are so close to freedom. this place emanates with evil");// Description goes here ivan
		roomList.add(r);
		
		// *******************************needs an object to enter***********************************************
		r = new Room(9, "vault", "");
		//		   N E S W
		r.setExits(0,0,7,0); //fix these exits
		r.setDesc(
		"There is no treasure! Nothing but a small device");// Description goes here ivan
		roomList.add(r);
		
		//JAILBLOCK: You are in disbelief at your luck, as the iron door of your cell silently swings open.
		//Stepping into the gloom, it encapulates you. Stumbling over a lone chair,  You are able to
		// make out a door at the far end of the massive room.
		//HUB This room is refreshingly well lit. You actually have choices here. On the north side, a 
		//gleaming neon sign states CAFATERIA. The east, the dreaded barracks waits, with gaping double doors. 
		//finally, to the left is reception, the entrance, and freedom!	
		
		
		//	for (Room m : roomList){
		//		System.out.println(m.toString());
		//	}
	}
	
}
