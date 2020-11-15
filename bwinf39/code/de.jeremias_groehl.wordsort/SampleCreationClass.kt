import java.util.regex.Matcher
import java.util.regex.Pattern

public object SampleCreationClass {
    private val criteriaForWord = Pattern.compile("[\\wüöäÜÄÖ]+")
    private var extractedWords: ArrayList<String> = ArrayList()
    private var textWithGaps: String = ""

    fun printSample(text: String) {
        val word: Matcher = criteriaForWord.matcher(text)

        while (word.find()) {
            val word_str: String = text.substring(word.start(), word.end())
            for(i in word.start()..word.end()){

            }
            extractedWords.add(word_str)
        }
    }
}