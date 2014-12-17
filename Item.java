public class Item{
	String name;
	String description;
	int location;  //this is where the item is. == roomID

	Item(String name, String descr, int location) {
		this.name = name;
		description = descr;
		this.location = location;
	}
}
