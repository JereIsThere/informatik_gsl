package de.yoshie2000;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private int id;
	private String name;
	private List<Wunsch> w�nsche;
	
	@Override
	public String toString() {
		return "Person " + id;// + " {" + name + "}";
	}
	
	public Person(int id, String name, List<Wunsch> w�nsche) {
		this.id = id;
		this.name = name;
		this.w�nsche = w�nsche;
	}
	
	public Person(int id, String name, Wunsch... w�nsche) {
		this.id = id;
		this.name = name;
		this.w�nsche = new ArrayList<Wunsch>();
		for (Wunsch wunsch : w�nsche) {
			this.w�nsche.add(wunsch);
		}
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Wunsch> getW�nsche() {
		return w�nsche;
	}
	
}
