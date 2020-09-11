import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
	
	//------------------------------------------------vars------------------------------------------------------------//
	
	private File mainFile;
	
	private ArrayList<String> wordsAndGaps = new ArrayList<>();
	
	private JeresHashMap gaps = new JeresHashMap();
	
	private List<List<String>> wordsFromLetters = new ArrayList<>();
	
	private JeresHashMap sortedWords = new JeresHashMap();
	
	private Pattern criteriaForWord = Pattern.compile("[\\wüöäÜÄÖ]+");
	
	private String pathToBackupFile = "backups/raetsel0.txt";
	
	//instantiates list of solved gaps that need to be deleted
	private List<Integer> gapsToDelete = new ArrayList<>();
	
	
	//----------------------------------------------main--------------------------------------------------------------//
	
	public static void main(String[] args) {
		
		MainClass mainClass = new MainClass();
		
		//mainClass.selectFile();
		
		mainClass.extractWords();
		
		mainClass.extractGapsFromWords();
		
		mainClass.sortFromLetterCount();
		
		mainClass.sortWords();
		
		mainClass.printWords();
	}
	
	//-----------------------------------------------logic------------------------------------------------------------//
	
	private void printWords() {
		try {
			
			BufferedReader reader;
			
			if (mainFile != null) {
				reader = new BufferedReader(new FileReader(mainFile));
			} else {
				reader = new BufferedReader(new FileReader(pathToBackupFile));
			}
			
			String text = "";
			
			String nextLine = reader.readLine();
			
			while (nextLine != null) {
				
				Matcher m = criteriaForWord.matcher(nextLine);
				
				int matcherEndBuffer = 0;
				int wordIndex = 0;
				while (m.find()) {
					String wordToPrint = "";
					String readWord = nextLine.substring(m.start(), m.end());
					//checks if word is a gap
					if (readWord.contains("_")) {
						wordToPrint = sortedWords.get(wordIndex);
						wordIndex++;
					}else{
						break;
					}
					
					String wordAddendum = nextLine.substring(matcherEndBuffer, m.start());
					matcherEndBuffer = m.end();
					
					text += wordAddendum + wordToPrint;
				}
				
				//TODO implement last punctuation mark
				
				nextLine = reader.readLine();
			}
			
			System.out.println(text);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sortWords() {
		gapsToDelete = new ArrayList<>();
		
		//iterates through all gaps and tries to solve them
		for (Integer gapKey : gaps.keySet()) {
			
			//gathers information about gap: String value, length and what letters
			// are contained in it at which position
			String gap = gaps.get(gapKey);
			int length = gap.length();
			HashMap<Integer, String> gapLetters = detectLetters(gap);
			
			//creates list of possible words that fit the length and the position of the given letters
			List<String> possibleWords = wordsFromLetters.get(length);
			
			//if there are no letters in the gap:
			if (gapLetters.keySet().size() == 0) {
				
				// and the options for a possible word are just one,
				// add the first option to the list of sorted words
				if (possibleWords.size() == 1) {
					String word = possibleWords.get(0);
					wordFound(word, gapKey);
					
					//and there is more than one possible word, this is unsolvable and remains to be sorted
				} else {
					sortedWords.put(gapKey, gap);
				}
				
			}
			//if there are letters in the gap:
			else {
				
				//creates list for words that match the letters and the lettercount of the gap
				List<String> fittingWords = new ArrayList<>();
				
				//iterates through every word that has the required number of letters
				possibleWords.forEach(word -> {
					//detects all letters in the current possible word
					HashMap<Integer, String> wordLetters = detectLetters(word);
					
					//sets up boolean for the case that all of the letters match
					boolean allMatch = true;
					//iterates through every letter in the gap
					for (Integer gapLettersKey : gapLetters.keySet()) {
						//buffers the letter of the current possible word and the gap
						String wordLetter = wordLetters.get(gapLettersKey);
						String gapLetter = gapLetters.get(gapLettersKey);
						
						//checks if the current letter of the word is identical with the equivalent letter
						//of the gap, if not then it sets allMatch to false and breaks
						if (! wordLetter.equals(gapLetter)) {
							allMatch = false;
							break;
						}
					}
					
					//
					if (allMatch) {
						fittingWords.add(word);
					}
				});
				
				if (fittingWords.size() == 1) {
					String word = fittingWords.get(0);
					wordFound(word, gapKey);
					
				} else {
					boolean areIdentical = false;
					for (int i = 0; i < fittingWords.size(); i++) {
						for (int j = i + 1; j < fittingWords.size(); j++) {
							String word1 = fittingWords.get(i);
							String word2 = fittingWords.get(j);
							
							if (! word1.equals(word2)) {
								areIdentical = false;
								break;
							} else {
								areIdentical = true;
							}
							
						}
						if (! areIdentical)
							break;
					}
					
					if (areIdentical) {
						String word = fittingWords.get(0);
						wordFound(word, gapKey);
					} else {
						sortedWords.put(gapKey, gap);
					}
				}
				
				
			}
		}
		
		for (int deleteKey : gapsToDelete) {
			gaps.remove(deleteKey);
		}
		
		int sizeOfGaps = gaps.keySet().size();
		while (sizeOfGaps > 0) {
			
			sortWords();
			
			sizeOfGaps = gaps.keySet().size();
		}
	}
	
	//-----------------------------------------------------done-------------------------------------------------------//
	
	private void wordFound(String word, Integer gapKey) {
		//buffers the word length
		int length = word.length();
		
		//adds word to sortedWords, removes word from wordsFromLetters, flags gap for deletion
		sortedWords.put(gapKey, word);
		wordsFromLetters.get(length).remove(word);
		gapsToDelete.add(gapKey);
	}
	
	private HashMap<Integer, String> detectLetters(String str) {
		//creates local buffer HashMap for return value
		HashMap<Integer, String> detectedLetters = new HashMap<>();
		
		//splits input String into characters
		String[] splitStr = str.split("");
		
		//iterates through characters, if it detects any other character than an underscore,
		// it saves that to the HashMap
		for (int i = 0; i < splitStr.length; i++) {
			if (! splitStr[i].equals("_")) {
				detectedLetters.put(i, splitStr[i]);
			}
		}
		
		//returns local buffer of HashMap
		//Example: str = __i_e__
		//detectedLetters = [3=i, 5=e]
		return detectedLetters;
	}
	
	public void sortFromLetterCount() {
		//iterates through every detected word (there are no gaps present anymore!)
		for (String word : wordsAndGaps) {
			
			//saves the length of the word in local variable
			int length = word.length();
			
			//if the ArrayList isn't big enough, it's made to the required size
			while (wordsFromLetters.size() <= length) {
				wordsFromLetters.add(wordsFromLetters.size(), new ArrayList<>());
			}
			
			//adds the word to the list at the appropriate place
			wordsFromLetters.get(length).add(word);
		}
	}
	
	private void extractGapsFromWords() {
		//iterates through all words and gaps collected
		for (int i = 0; i < wordsAndGaps.size(); i++) {
			String word = wordsAndGaps.get(i);
			
			//adds every detected gap to list of gaps, then deletes gap from wordsAndGaps
			if (word.contains("_")) {
				gaps.add(word);
				wordsAndGaps.remove(i);
				i--;
			} else {
				//if there is no gap detected anymore, the method returns
				return;
			}
		}
	}
	
	private void extractWords() {
		try {
			//creates new BufferedReader
			BufferedReader reader;
			
			//checks if the mainFile the reader is trying to read == null, if it is then use a backup file
			if (mainFile != null) {
				reader = new BufferedReader(new FileReader(mainFile));
			} else {
				reader = new BufferedReader(new FileReader(pathToBackupFile));
			}
			
			//iterates through the file line by line
			String nextLine = "";
			while ((nextLine = reader.readLine()) != null) {
				
				//creates a matcher for the pattern which defines what a word is
				Matcher m = criteriaForWord.matcher(nextLine);
				
				//while there is another word, it adds it to the list of words and gaps
				while (m.find()) {
					String word = nextLine.substring(m.start(), m.end());
					wordsAndGaps.add(word);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void selectFile() {
		//instantiates local buffer for new main file
		File file;
		
		//set LookAndFeel of UIManager to the system-wide default
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		//instantiates a JFileChooser with a filter for text files and opens it
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"text files", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.showOpenDialog(null);
		
		//sets the value of the local buffer file to the selected file and updates mainFile
		file = fileChooser.getSelectedFile();
		mainFile = file;
	}
}
