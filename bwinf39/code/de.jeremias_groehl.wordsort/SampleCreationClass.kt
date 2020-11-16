import java.util.regex.Matcher
import java.util.regex.Pattern

class SampleCreationClass {
    companion object {
        private val criteriaForWord = Pattern.compile("[\\wüöäÜÄÖ]+")
        private var extractedWords: ArrayList<String> = ArrayList()
        private var textWithGaps: String = ""

        @JvmStatic
        fun main(args: Array<String>) {
            printSample(textToConvert)
        }

        fun printSample(text: String) {
            val word: Matcher = criteriaForWord.matcher(text)
            textWithGaps = text

            //while there is another word in the text
            while (word.find()) {
                //extracts words
                val word_str: String = text.substring(word.start(), word.end())
                extractedWords.add(word_str)

                //converts words in the text to gaps
                for ((index, char) in word_str.toCharArray().withIndex()) {
                    val charIsWord = char.toString().matches(criteriaForWord.toRegex())

                    //converts the current char to a _ when either:
                    //-a 60% chance is triggered
                    //-when the char isn't at the 1st position to keep those as letters (makes solving longer samples more probable)
                    //when the word doesn't contain a _
                    if (Math.random() > 0.6 && charIsWord && index != 0 || (index == word_str.length - 1 && !textWithGaps.substring(word.start(), word.end()).contains("_"))) {
                        textWithGaps = textWithGaps.substring(0, index + word.start()) + "_" + textWithGaps.substring(index + word.start() + 1)
                    }
                }
            }

            //first prints the whole source file
            println(textWithGaps)
            for (word in extractedWords) {
                //prints all the words
                print("$word ")
            }
        }

        //hard coded string instead of file, because i don't think it'll be necessary to make stuff overly complicated
        private val textToConvert: String = ""
    }
}
