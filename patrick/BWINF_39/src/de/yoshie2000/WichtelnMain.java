package de.yoshie2000;

import java.util.ArrayList;
import java.util.List;

public class WichtelnMain {

	public static void main(String[] args) {
		List<Wunsch> alleW�nsche = new ArrayList<Wunsch>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Wunsch(0, "Xiaomi E-Scooter"));
				add(new Wunsch(1, "Prusa I3 Mk3s"));
				add(new Wunsch(2, "5� Schein"));
				add(new Wunsch(3, "Raspberry Pi 4B+"));
			}
		};
		
		List<Person> personen = new ArrayList<Person>() {
			private static final long serialVersionUID = 1L;
			{
				add(new Person(0, "Jonas", alleW�nsche.get(1), alleW�nsche.get(3), alleW�nsche.get(2)));
				add(new Person(1, "Logistikmann", alleW�nsche.get(1), alleW�nsche.get(3), alleW�nsche.get(0)));
				add(new Person(2, "Schwalbenoperator", alleW�nsche.get(1), alleW�nsche.get(3), alleW�nsche.get(0)));
				add(new Person(3, "K�hn", alleW�nsche.get(1), alleW�nsche.get(3), alleW�nsche.get(2)));
			}
		};
		
		WunschHandler wunschHandler = new WunschHandler(personen, alleW�nsche);
		
		wunschHandler = new WunschHandler("D:\\OneDrive\\Stuff\\Bundeswettbewerb Informatik\\BWINF 39\\Beispiele\\wichteln3.txt");
		
		System.out.println(wunschHandler.getOptimaleWunschverteilung());
	}

}
