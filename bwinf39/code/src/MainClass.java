import src.JeresHashMap;

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

    private File mainFile;

    private JeresHashMap words = new JeresHashMap();

    private JeresHashMap gaps = new JeresHashMap();

    private List<List<String>> wordsFromLetters = new ArrayList<>();

    private JeresHashMap sortedWords = new JeresHashMap();

    //----------------------------------------------------------------------------------------------------------------//

    public static void main(String[] args) {

        MainClass mainClass = new MainClass();

        //TODO hardcode
        //mainClass.selectFile();

        mainClass.extractWords();

        mainClass.extractGapsFromWords();

        mainClass.sortFromLetterCount();

        mainClass.sortWords();

        mainClass.cleanUpGaps();
    }

    //----------------------------------------------------------------------------------------------------------------//

    private void cleanUpGaps() {
        int sizeOfGaps = gaps.keySet().size();
        while (sizeOfGaps > 0) {

            sortWords();

            sizeOfGaps = gaps.keySet().size();
        }
    }

    private void sortWords() {
        JeresHashMap gapsToDelete = new JeresHashMap();

        for (Integer gapKey : gaps.keySet()) {

            String gap = gaps.get(gapKey);
            int length = gap.length();

            HashMap<Integer, String> gapLetters = detectLetters(gap);

            List<String> possibleWords = wordsFromLetters.get(length);

            if (gapLetters.keySet().size() == 0) {
                if (possibleWords.size() == 1) {
                    String word = possibleWords.get(0);
                    sortedWords.put(gapKey, word);
                    wordsFromLetters.get(length).remove(word);
                    gapsToDelete.put(gapKey, gap);
                } else {
                    sortedWords.put(gapKey, gap);
                    System.out.println("to be sorted" + length);
                }
            } else {

                List<String> fittingWords = new ArrayList<>();
                possibleWords.forEach(word -> {
                    HashMap<Integer, String> wordLetters = detectLetters(word);

                    boolean allMatch = true;
                    for (Integer gapLettersKey : gapLetters.keySet()) {

                        String wordLetter = wordLetters.get(gapLettersKey);
                        String gapLetter = gapLetters.get(gapLettersKey);
                        if (!wordLetter.equals(gapLetter)) {
                            allMatch = false;
                            break;
                        }
                    }

                    if (allMatch) {
                        fittingWords.add(word);
                    }
                });

                if (fittingWords.size() == 1) {
                    String word = fittingWords.get(0);
                    sortedWords.put(gapKey, word);
                    wordsFromLetters.get(length).remove(word);
                    gapsToDelete.put(gapKey, gap);

                } else {
                    boolean areIdentical = false;
                    for (int i = 0; i < fittingWords.size(); i++) {
                        for (int j = i + 1; j < fittingWords.size(); j++) {
                            String word1 = fittingWords.get(i);
                            String word2 = fittingWords.get(j);

                            if (!word1.equals(word2)) {
                                areIdentical = false;
                                break;
                            } else {
                                areIdentical = true;
                            }

                        }
                        if (!areIdentical)
                            break;
                    }

                    if (areIdentical) {
                        String word = fittingWords.get(0);
                        sortedWords.put(gapKey, word);
                        wordsFromLetters.get(length).remove(word);
                        gapsToDelete.put(gapKey, gap);
                    } else {
                        sortedWords.add(gap);
                    }
                }


            }
        }

        for (Integer deleteKey : gapsToDelete.keySet()) {
            gaps.remove(deleteKey);
        }

        System.out.println(sortedWords);
        System.out.println(gaps);

    }

    private HashMap<Integer, String> detectLetters(String str) {

        HashMap<Integer, String> detectedLetters = new HashMap<>();
        String[] splitStr = str.split("");
        for (int i = 0; i < splitStr.length; i++) {
            if (!splitStr[i].equals("_")) {
                detectedLetters.put(i, splitStr[i]);
            }
        }
        return detectedLetters;
    }

    public void sortFromLetterCount() {
        for (Integer key : words.keySet()) {
            String word = words.get(key);

            int length = word.length();
            while (wordsFromLetters.size() <= length) {
                wordsFromLetters.add(wordsFromLetters.size(), new ArrayList<>());
            }
            wordsFromLetters.get(length).add(word);
        }
    }

    private void extractGapsFromWords() {
        for (int i = 0; i < words.size(); i++) {


            String word = words.get(i);

            if (word.contains("_")) {
                gaps.add(word);
                words.remove(i);
            } else {
                break;
            }
        }
    }

    private void extractWords() {
        String text = "";

        try {

            //BufferedReader reader = new BufferedReader(new FileReader(mainFile));
            //TODO hardcode
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\fuuma\\OneDrive\\Desktop\\informatik_gsl\\bwinf39\\samples\\raetsel4.txt"));

            String nextLine = reader.readLine();
            while (nextLine != null) {

                //Pattern p = Pattern.compile("[\\w']+");
                Pattern p = Pattern.compile("[\\wüöäÜÄÖ]+");
                Matcher m = p.matcher(nextLine);

                while (m.find()) {
                    String word = nextLine.substring(m.start(), m.end());
                    words.add(word);
                }

                nextLine = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void selectFile() {
        File file;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "text files", "txt");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(null);
        file = fileChooser.getSelectedFile();

        mainFile = file;
    }
}
