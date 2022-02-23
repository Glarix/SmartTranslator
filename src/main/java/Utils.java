import java.util.ArrayList;

public abstract class Utils {

    /**
     * Method to create a non-completed Word for testing purposes
     * @param word the word you want to create
     * @return a Word reference
     */
    public static Word createWordForTest(String word) {
        Word testWord = new Word(word);

        testWord.setWord_en("test");
        testWord.setType("irrelevant");
        testWord.setSingular(null);
        testWord.setPlural(null);
        testWord.setDefinitions(null);
        return testWord;
    }

    /**
     * Method to create a non-complete Definition for testing purposes
     * @param dict the name of the dict parameter
     * @param text the text of the definition
     * @return a Definition reference
     */
    public static Definition createDefinitionForTest(String dict, String text) {
        Definition testDef = new Definition(dict);
        testDef.setDictType("irrelevant");
        testDef.setYear(2009);
        ArrayList<String> texts = new ArrayList<>();
        texts.add(text);
        return testDef;
    }

}
