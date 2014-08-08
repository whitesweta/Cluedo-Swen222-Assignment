package cluedo.other;

public abstract class Item {
	protected String name;

	public Item(String n){
		name = n;
	}

	public String toString(){
		return name;
	}
}
