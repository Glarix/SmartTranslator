import java.util.ArrayList;

public abstract class TranslatorTool {

    /**
     * Method to translate a word from english to another language
     * @param langList list of words of the wanted language
     * @param word the word in english
     * @param toLanguage the language to be translated into
     * @param translateType the type of translation: 0 Singular form,
     *                      1 Plural form, 2 Normal form
     * @return the transalation of the word, null if translation is not possible
     */
    public static String directEnglishTranslate(ArrayList<Word> langList,
                                         Word word,
                                         String toLanguage, int translateType) {
        if (toLanguage.equals("en"))
            return word.getWord();

        if (langList == null) { // Here maybe an Exception!!!!!!!!
            System.out.println("directEnglishTranslate : Error no language " + toLanguage + " found!");
            return null;
        }
        if (langList.contains(word)) {
            Word translated = langList.get(langList.indexOf(word));
            switch (translateType) {
                case 0:
                    ArrayList<String> singulars = translated.getSingular();
                    if (singulars.size() > 0)
                        return singulars.get(0);
                    return translated.getWord();
                case 1:
                    ArrayList<String> plurals = translated.getPlural();
                    if (plurals.size() > 0)
                        return plurals.get(0);
                    return translated.getWord();
                case 2:
                    return translated.getWord();
                default:
                    return null;
            }
        } else {
            return null;
        }
    }
}
