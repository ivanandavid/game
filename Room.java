//package AdventureGame;

import java.util.*;
public class Room{
	
	private int roomID;
	private String title;
	private String description;
	private int N,E,S,W; //add U, D if needed

	//constructos
	Room(int id, String title, String description){
		roomID = id;
		this.title = title;
		this.description = description;
	}
	
	void setExits(int N, int E, int S, int W) {
		this.N = N;
		this.E = E;
		this.S = S;
		this.W = W;		
	}
	
	int getExit(char c) {
		switch (c) {
		case 'N': return this.N;
		case 'E': return this.E;
		case 'S': return this.S;
		case 'W': return this.W;
		default: return 0;
		}
	}
	
	int getRoomID()	  { return roomID; }
	String getTitle() { return title; }
	
	void setDesc(String s)	{ this.description = s; }
	String getDesc()  		{ return description; }
	
	
	//most classes need to have this
	public String toString(){
		String s = String.format("Title=%-25s\tDescription=%s",title,description);		
		return s;
	}

}
