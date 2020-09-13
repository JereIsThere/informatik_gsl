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
	private List<Wunsch> alleW�nsche;

	public WunschHandler(List<Person> personen, List<Wunsch> alleW�nsche) {
		this.personen = personen;
		this.alleW�nsche = alleW�nsche;
	}
	
	public WunschHandler(String filePath) {
		this.personen = new ArrayList<Person>();
		this.alleW�nsche = new ArrayList<Wunsch>();
		try {
			BufferedReader leser = new BufferedReader(new FileReader(filePath));
			
			String zeile = leser.readLine();
			int anzahlSch�ler = Integer.valueOf(zeile);
			for (int i = 0; i < anzahlSch�ler; i++) {
				alleW�nsche.add(new Wunsch(i + 1, ""));
			}
			
			int sch�lerIndex = 1;
			while ((zeile = leser.readLine()) != null) {
				
				List<Wunsch> sch�lerW�nsche = new ArrayList<Wunsch>();
				
				String[] wunschIndizes = zeile.split("\\s+");
				for (String wunschIndex : wunschIndizes) {
					if (wunschIndex.equals("")) continue;
					
					int wunschIndexZahl = Integer.valueOf(wunschIndex);
					sch�lerW�nsche.add(alleW�nsche.get(wunschIndexZahl - 1));
				}
				
				Person sch�ler = new Person(sch�lerIndex, "", sch�lerW�nsche);
				personen.add(sch�ler);
				
				sch�lerIndex++;
			}
			
			leser.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<Wunsch, Person> getOptimaleWunschverteilung() {

		int[] wunschVerteilungsIndices = new int[personen.size()];

		List<Map<Wunsch, Person>> alleM�glichkeiten = new ArrayList<Map<Wunsch, Person>>();

		while (true) {
			
			//System.out.println(Arrays.toString(wunschVerteilungsIndices));
			
			Map<Wunsch, Person> wunschVerteilung = new HashMap<Wunsch, Person>();

			boolean valideKonfiguration = true;
			
			// Anhand der Wunschverteilung die W�nsche zuordnen und die Konfiguration testen
			for (int i = 0; i < wunschVerteilungsIndices.length; i++) {
				Wunsch personenWunsch = personen.get(i).getW�nsche().get(wunschVerteilungsIndices[i]);

				if (wunschVerteilung.containsKey(personenWunsch)) {
					valideKonfiguration = false;
					break;
				}
				
				wunschVerteilung.put(personenWunsch, personen.get(i));
			}

			if (valideKonfiguration) {
				alleM�glichkeiten.add(wunschVerteilung);
			}
			
			// Increase index
			int indexToIncrement = 0;

			int counter = 0;
			while (indexToIncrement < wunschVerteilungsIndices.length
					&& wunschVerteilungsIndices[indexToIncrement] >= personen.get(indexToIncrement).getW�nsche().size()
							- 1) {
				wunschVerteilungsIndices[indexToIncrement] = 0;
				indexToIncrement++;
				counter++;
			}

			if (counter == wunschVerteilungsIndices.length) {
				// Alle M�glichkeiten sind durchgegangen worden
				break;
			}

			if (indexToIncrement < wunschVerteilungsIndices.length) {
				wunschVerteilungsIndices[indexToIncrement]++;
			}
		}

		Map<Wunsch, Person> besteM�glichkeit = new HashMap<Wunsch, Person>();
		int[] erf�llteW�nscheNachIndex = new int[alleW�nsche.size()];

		for (Map<Wunsch, Person> m�glichkeit : alleM�glichkeiten) {

			int[] erf�llteW�nscheNachIndexVonDieserM�glichkeit = new int[alleW�nsche.size()];

			for (Wunsch wunsch : m�glichkeit.keySet()) {
				Person personDesWunsches = m�glichkeit.get(wunsch);
				int wunschIndex = personDesWunsches.getW�nsche().indexOf(wunsch);
				erf�llteW�nscheNachIndexVonDieserM�glichkeit[wunschIndex]++;
			}

			for (int i = 0; i < erf�llteW�nscheNachIndex.length; i++) {
				if (erf�llteW�nscheNachIndexVonDieserM�glichkeit[i] > erf�llteW�nscheNachIndex[i]) {
					// Bessere Konfiguration gefunden
					besteM�glichkeit = m�glichkeit;
					erf�llteW�nscheNachIndex = erf�llteW�nscheNachIndexVonDieserM�glichkeit;
					break;
				} else if (erf�llteW�nscheNachIndexVonDieserM�glichkeit[i] < erf�llteW�nscheNachIndex[i]) {
					break;
				}
			}

		}

		System.out.println("--------");
		System.out.println(Arrays.toString(erf�llteW�nscheNachIndex));
		return besteM�glichkeit;
	}

	public List<Person> getPersonen() {
		return personen;
	}

	public List<Wunsch> getAlleW�nsche() {
		return alleW�nsche;
	}

}
