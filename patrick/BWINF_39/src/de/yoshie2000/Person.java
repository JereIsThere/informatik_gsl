package de.yoshie2000;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private int id;
	private String name;
	private List<Wunsch> wünsche;
	
	@Override
	public String toString() {
		return "Person " + id;// + " {" + name + "}";
	}
	
	public Person(int id, String name, List<Wunsch> wünsche) {
		this.id = id;
		this.name = name;
		this.wünsche = wünsche;
	}
	
	public Person(int id, String name, Wunsch... wünsche) {
		this.id = id;
		this.name = name;
		this.wünsche = new ArrayList<Wunsch>();
		for (Wunsch wunsch : wünsche) {
			this.wünsche.add(wunsch);
		}
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Wunsch> getWünsche() {
		return wünsche;
	}
	
}
