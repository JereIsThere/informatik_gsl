package de.yoshie2000;

public class Wunsch {
	
	private int id;
	private String name;
	
	@Override
	public String toString() {
		return "Wunsch " + id;// + " {" + name + "}";
	}
	
	public Wunsch(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
}
