package de.yoshie2000;

import java.util.ArrayList;
import java.util.List;

public class WichtelnMain {

	public static void main(String[] args) {
		List<Wunsch> alleWünsche = new ArrayList<Wunsch>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Wunsch(0, "Xiaomi E-Scooter"));
				add(new Wunsch(1, "Prusa I3 Mk3s"));
				add(new Wunsch(2, "5€ Schein"));
				add(new Wunsch(3, "Raspberry Pi 4B+"));
			}
		};
		
		List<Person> personen = new ArrayList<Person>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Person(0, "Jonas", alleWünsche.get(1), alleWünsche.get(3), alleWünsche.get(2)));
				add(new Person(1, "Logistikmann", alleWünsche.get(1), alleWünsche.get(3), alleWünsche.get(0)));
				add(new Person(2, "Schwalbenoperator", alleWünsche.get(1), alleWünsche.get(3), alleWünsche.get(0)));
				add(new Person(3, "Kühn", alleWünsche.get(1), alleWünsche.get(3), alleWünsche.get(2)));
			}
		};
		
		WunschHandler wunschHandler = new WunschHandler(personen, alleWünsche);
		
		wunschHandler = new WunschHandler("D:\\OneDrive\\Stuff\\Bundeswettbewerb Informatik\\BWINF 39\\Beispiele\\wichteln3.txt");
		
		System.out.println(wunschHandler.getOptimaleWunschverteilung());
	}

}
