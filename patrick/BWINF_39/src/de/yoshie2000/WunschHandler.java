package de.yoshie2000;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WunschHandler {

	private List<Person> personen;
	private List<Wunsch> alleWünsche;

	public WunschHandler(List<Person> personen, List<Wunsch> alleWünsche) {
		this.personen = personen;
		this.alleWünsche = alleWünsche;
	}
	
	public WunschHandler(String filePath) {
		this.personen = new ArrayList<Person>();
		this.alleWünsche = new ArrayList<Wunsch>();
		try {
			BufferedReader leser = new BufferedReader(new FileReader(filePath));
			
			String zeile = leser.readLine();
			int anzahlSchüler = Integer.valueOf(zeile);
			for (int i = 0; i < anzahlSchüler; i++) {
				alleWünsche.add(new Wunsch(i + 1, ""));
			}
			
			int schülerIndex = 1;
			while ((zeile = leser.readLine()) != null) {
				
				List<Wunsch> schülerWünsche = new ArrayList<Wunsch>();
				
				String[] wunschIndizes = zeile.split("\\s+");
				for (String wunschIndex : wunschIndizes) {
					if (wunschIndex.equals("")) continue;
					
					int wunschIndexZahl = Integer.valueOf(wunschIndex);
					schülerWünsche.add(alleWünsche.get(wunschIndexZahl - 1));
				}
				
				Person schüler = new Person(schülerIndex, "", schülerWünsche);
				personen.add(schüler);
				
				schülerIndex++;
			}
			
			leser.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<Wunsch, Person> getOptimaleWunschverteilung() {

		int[] wunschVerteilungsIndices = new int[personen.size()];

		List<Map<Wunsch, Person>> alleMöglichkeiten = new ArrayList<Map<Wunsch, Person>>();

		while (true) {
			
			//System.out.println(Arrays.toString(wunschVerteilungsIndices));
			
			Map<Wunsch, Person> wunschVerteilung = new HashMap<Wunsch, Person>();

			boolean valideKonfiguration = true;
			
			// Anhand der Wunschverteilung die Wünsche zuordnen und die Konfiguration testen
			for (int i = 0; i < wunschVerteilungsIndices.length; i++) {
				Wunsch personenWunsch = personen.get(i).getWünsche().get(wunschVerteilungsIndices[i]);

				if (wunschVerteilung.containsKey(personenWunsch)) {
					valideKonfiguration = false;
					break;
				}
				
				wunschVerteilung.put(personenWunsch, personen.get(i));
			}

			if (valideKonfiguration) {
				alleMöglichkeiten.add(wunschVerteilung);
			}
			
			// Increase index
			int indexToIncrement = 0;

			int counter = 0;
			while (indexToIncrement < wunschVerteilungsIndices.length
					&& wunschVerteilungsIndices[indexToIncrement] >= personen.get(indexToIncrement).getWünsche().size()
							- 1) {
				wunschVerteilungsIndices[indexToIncrement] = 0;
				indexToIncrement++;
				counter++;
			}

			if (counter == wunschVerteilungsIndices.length) {
				// Alle Möglichkeiten sind durchgegangen worden
				break;
			}

			if (indexToIncrement < wunschVerteilungsIndices.length) {
				wunschVerteilungsIndices[indexToIncrement]++;
			}
		}

		Map<Wunsch, Person> besteMöglichkeit = new HashMap<Wunsch, Person>();
		int[] erfüllteWünscheNachIndex = new int[alleWünsche.size()];

		for (Map<Wunsch, Person> möglichkeit : alleMöglichkeiten) {

			int[] erfüllteWünscheNachIndexVonDieserMöglichkeit = new int[alleWünsche.size()];

			for (Wunsch wunsch : möglichkeit.keySet()) {
				Person personDesWunsches = möglichkeit.get(wunsch);
				int wunschIndex = personDesWunsches.getWünsche().indexOf(wunsch);
				erfüllteWünscheNachIndexVonDieserMöglichkeit[wunschIndex]++;
			}

			for (int i = 0; i < erfüllteWünscheNachIndex.length; i++) {
				if (erfüllteWünscheNachIndexVonDieserMöglichkeit[i] > erfüllteWünscheNachIndex[i]) {
					// Bessere Konfiguration gefunden
					besteMöglichkeit = möglichkeit;
					erfüllteWünscheNachIndex = erfüllteWünscheNachIndexVonDieserMöglichkeit;
					break;
				} else if (erfüllteWünscheNachIndexVonDieserMöglichkeit[i] < erfüllteWünscheNachIndex[i]) {
					break;
				}
			}

		}

		System.out.println("--------");
		System.out.println(Arrays.toString(erfüllteWünscheNachIndex));
		return besteMöglichkeit;
	}

	public List<Person> getPersonen() {
		return personen;
	}

	public List<Wunsch> getAlleWünsche() {
		return alleWünsche;
	}

}
