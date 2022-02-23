import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Administration {

    private HashMap<String, ArrayList<Word>> generalStorage;

    public Administration(HashMap<String, ArrayList<Word>> generalStorage) {
        this.generalStorage = generalStorage;
    }

    public HashMap<String, ArrayList<Word>> getGeneralStorage() {
        return generalStorage;
    }

    /**
     * Method used to get a list of Words based on a key from HashMap
     * @param language the key
     * @return the list of Words
     */
    public ArrayList<Word> getLanguageList(String language) {
        ArrayList<Word> langList = this.generalStorage.get(language);

        return langList;
    }

    /**
     * Method to add or update a word from the storage after it was first added
     * If the word exists but the new one has some new info, it gets added
     * @param word the word to be added in collection
     * @param language the language of the word
     * @return true if a new word was added or if an existing one was updated,
     * false if no changes made
     */
    public boolean addWord(Word word, String language) {

        ArrayList<Word> langList = getLanguageList(language);

        if (langList == null) {
            langList = new ArrayList<>();
            langList.add(word);
            this.generalStorage.put(language, langList);
            return true;
        }

        if (!langList.contains(word)){
            langList.add(word);
            this.generalStorage.put(language, langList);
            return true;
        } else {
            Word toModify = langList.get(langList.indexOf(word));
            return toModify.updateWord(word);
        }

    }

    /**
     * Method to remove a word from dictionary
     * @param word the word to be removed
     * @param language the language of the specific word
     * @return true if the word was removed, false if the word does not exist in dictionary
     * @throws LanguageNotFoundException exception thrown if the language is not found and displays a message
     */
    public boolean removeWord(String word, String language) throws LanguageNotFoundException{
        ArrayList<Word> langList = getLanguageList(language);

        if (langList == null) {
            throw new LanguageNotFoundException("removeWord : Error no language " + language + " found!");
        }

        if (langList.contains(new Word(word))) {
            langList.remove(langList.indexOf(new Word(word)));
            return true;
        }
        return false;
    }

    /**
     * Method to add a new definition for a specified word
     * @param word the word to add the definition to
     * @param language the language of the word
     * @param definition the definition object to be added
     * @return true if the definition was added, false if a definition with the same "dict" parameter
     * already exists
     * @throws LanguageNotFoundException exception thrown if the language is not found and displays a message
     * @throws WordNotFoundException exception thrown if the word is not found and displays a message
     */
    public boolean addDefinitionForWord(String word, String language, Definition definition)
            throws LanguageNotFoundException, WordNotFoundException{
        ArrayList<Word> langList = getLanguageList(language);

        if (langList == null) {
            throw new LanguageNotFoundException("addDefinitionForWord : Error no language " + language + " found!");
        }

        if (langList.contains(new Word(word))) {
            Word foundWord = langList.get(langList.indexOf(new Word(word)));
            if (!foundWord.getDefinitions().contains(definition)) {
                foundWord.getDefinitions().add(definition);
                return true;
            } else return false;
        } else
            throw new WordNotFoundException("addDefinitionForWord : Error no word " + word + " found!");

    }

    /**
     * Method to remove a definition from a specified word
     * @param word the word to have a definition removed
     * @param language the language of the word
     * @param dictionary the "dict" parameter of the definition to be removed
     * @return true if definition found and removed, false if no such definition exists
     * @throws LanguageNotFoundException exception thrown if the language is not found and displays a message
     * @throws WordNotFoundException exception thrown if the word is not found and displays a message
     */
    public boolean removeDefinition(String word, String language, String dictionary) throws LanguageNotFoundException,
            WordNotFoundException{
        ArrayList<Word> langList = getLanguageList(language);

        if (langList == null) {
            throw new LanguageNotFoundException("removeDefinition : Error no language " + language + " found!");
        }

        if (langList.contains(new Word(word))) {
            Word foundWord = langList.get(langList.indexOf(new Word(word)));
            if (foundWord.getDefinitions().contains(new Definition(dictionary))) {
                foundWord.getDefinitions().remove(
                        foundWord.getDefinitions().indexOf(new Definition(dictionary)));
                return true;
            } else {
                System.out.println("removeDefinition : Error " + word + " does not contain any definitions from " + dictionary);
                return false;
            }

        } else
            throw new WordNotFoundException("removeDefinition : Error no word " + word + " found!");
    }

    /**
     * Method to check if a word is a singular or plural version of any word from a list of words
     * @param langList the list of words to be checked
     * @param word the word to be verified if is a singular or plural form
     * @param searchType 0 for searching if singular, 1 for searching if plural
     * @return the Word which has the parameter "word" as a singular or plural,
     * null if no such singular or plural found
     */
    public Word checkSingularOrPlural(ArrayList<Word> langList, String word, int searchType) {
        for (Word w : langList) {
            /* Check if word is a singular or plural form */
            switch (searchType){
                case 0:
                    if (w.getSingular().contains(word))
                        return w;
                    break;
                case 1:
                    if (w.getPlural().contains(word))
                        return w;
                    break;
            }
        }
        return null;
    }

    /**
     * Method to translate a word from a language to another language
     * @param word the word to be translated
     * @param fromLanguage the initial language of the word
     * @param toLanguage the language of the word after translation
     * @return the translated word if it was translated, null if it couldn't be translated
     * @throws LanguageNotFoundException exception thrown if the language is not found and displays a message
     */
    public String translateWord(String word, String fromLanguage, String toLanguage) throws LanguageNotFoundException {
        String translatedWord = "";
        /* If starting language is english, directly try to translate it*/
        if (fromLanguage.equals("en")) {
            ArrayList<Word> langList = getLanguageList(toLanguage);
            translatedWord = TranslatorTool.directEnglishTranslate(langList,
                    new Word(word), toLanguage, 2);
            if (translatedWord == null)
                return word;
            return translatedWord;
        }
        ArrayList<Word> langList = getLanguageList(fromLanguage);
        if (langList != null) {
            /* If the word is contained in the dictionary, try to translate it*/
            if (langList.contains(new Word(word))) {
                Word foundWord = langList.get(langList.indexOf(new Word(word)));
                /* Get the english version for translation*/
                String en_word = foundWord.getWord_en();

                if (toLanguage.equals("en"))
                    return en_word;

                ArrayList<Word> toLangList = getLanguageList(toLanguage);
                translatedWord = TranslatorTool.directEnglishTranslate(toLangList,
                        new Word(en_word), toLanguage, 2);

                if (translatedWord == null)
                    return word;
                return translatedWord;
            } else { /* If the word is not in the list as it is given, check if it;s a singular or
                        plural form of an existing word*/
                ArrayList<Word> toLangList = getLanguageList(toLanguage);
                 /* Check for Singular */
                Word foundWordSingular = checkSingularOrPlural(langList, word, 0);
                if (foundWordSingular != null) {
                    String en_word = foundWordSingular.getWord_en();
                    if (toLanguage.equals("en"))
                        return en_word;

                    translatedWord = TranslatorTool.directEnglishTranslate(toLangList,
                            new Word(en_word), toLanguage, 0);
                    if (translatedWord == null)
                        return word;
                    return translatedWord;
                }
                /* Check for plural */
                Word foundWordPlural = checkSingularOrPlural(langList, word, 1);
                if (foundWordPlural != null) {
                    String en_word = foundWordPlural.getWord_en();
                    if (toLanguage.equals("en"))
                        return en_word;

                    translatedWord = TranslatorTool.directEnglishTranslate(toLangList,
                            new Word(en_word), toLanguage, 1);
                    if (translatedWord == null)
                        return word;
                    return translatedWord;
                }
            }
        } else {
            throw new LanguageNotFoundException("translateWord: No language " + fromLanguage + " found");
        }
        return word;
    }

    /**
     * Method to translate a sentence word by word (if word exists in both languages in the dictionary)
     * @param sentence the sentence to be translated
     * @param fromLanguage the initial language of the sentence
     * @param toLanguage the language of the sentence after translation
     * @return the translated sentence
     */
    public String translateSentence(String sentence, String fromLanguage, String toLanguage) {
        String[] separatedWords = sentence.split(" ");
        StringBuilder finalString = new StringBuilder();
        for (String w : separatedWords) {
            String translatedWord = null;
            try {
                translatedWord = translateWord(w, fromLanguage, toLanguage);
            } catch (LanguageNotFoundException e) {
                System.out.println(e.getMessage());
                translatedWord = w;
            }
            finalString.append(translatedWord + " ");
        }

        return finalString.toString();
    }

    /**
     * Method to get all the synjonyms of a word if those exist
     * @param word the needed word
     * @param langList the list of Words that should contain word
     * @return all the synonyms of the word or null if no word found
     */
    public ArrayList<String> getSynonyms(Word word, ArrayList<Word> langList) {
        if (langList.contains(word)) {
            Word newLangWord = langList.get(langList.indexOf(word));
            ArrayList<Definition> defs = newLangWord.getDefinitions();
            for (Definition d : defs) {
                if (d.getDictType().equals("synonyms")) {
                    return d.getText();
                }
            }
        }
        return null;
    }

    /**
     * Method that gives multiple translating options(max. 3) for a sentence based on synonyms
     * @param sentence the sentence to be translated
     * @param fromLanguage the initial language
     * @param toLanguage the language of the sentence after translation
     * @return an arraylist with all the possible translations(max. 3)
     * @throws LanguageNotFoundException exception thrown if the language is not found and displays a message
     */
    public ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage)
            throws LanguageNotFoundException {
        ArrayList<String> allTranslations = new ArrayList<>();

        /* If I have to translate in english, there is only one option*/
        if (toLanguage.equals("en")) {
            String firstTranslation = translateSentence(sentence, fromLanguage, toLanguage); // Traduc normal propozitia
            allTranslations.add(firstTranslation);
            return allTranslations;
        }
        /* If translation is not from or to english then both languages should exist*/
        if (!fromLanguage.equals("en")) {
            if (getLanguageList(fromLanguage) == null || getLanguageList(toLanguage) == null) {
                throw new LanguageNotFoundException("translateSentences: One or both languages not found!");
            }
        }

        String firstTranslation = translateSentence(sentence, fromLanguage, toLanguage); /* Normal translation */
        String engTranslation = translateSentence(sentence, fromLanguage, "en"); /* English translation*/
        allTranslations.add(firstTranslation);

        ArrayList<Word> langList = getLanguageList(toLanguage);

        /* Separating the words from sentences */
        String[] originalSentence = sentence.split(" ");
        String[] translatedSentence = firstTranslation.split(" ");
        String[] engTransSentence = engTranslation.split(" ");

        int sentLong = originalSentence.length;
        ArrayList<String> synonyms = new ArrayList<>();

        int i = 0;
        /* The number of synonyms */
        int variantCounter = 0;

        /* Getting the synonyms of the first translated word */
        for (; i < sentLong; i++) {
            if (!translatedSentence[i].equals(originalSentence[i])) {
                synonyms = getSynonyms(new Word(engTransSentence[i]), langList);
                variantCounter = synonyms.size();
                break;
            }
        }

        /* The number of translations left and the number of used synonyms */
        int transCounter = 2, consumedSynonyms = 0;
        while (transCounter > 0) {
            /* If all synonyms have been consumed, looking for synonyms from another translated word */
            if (consumedSynonyms == variantCounter) {
                i++;
                for (; i < sentLong; i++) {
                    if (!translatedSentence[i].equals(originalSentence[i])) {
                        synonyms = getSynonyms(new Word(engTransSentence[i]), langList);

                        variantCounter = synonyms.size();
                        consumedSynonyms = 0;
                        break;
                    }
                }
                if (synonyms == null) /* If no more words, return all the possible translations */
                    return allTranslations;
            }
            if (i == sentLong) /* If all the words were checked, return */
                return allTranslations;

            if (synonyms.size() == 0) /* If the word does not have synonyms, look for another word */
                continue;

            StringBuilder newTranslate = new StringBuilder();
            for (int j = 0; j < i; j++) {
                newTranslate.append(translatedSentence[j]).append(" "); /* All words before the synonym */
            }
            newTranslate.append(synonyms.get(consumedSynonyms++)).append(" "); /* Adding synonym */
            for (int j = i + 1; j < sentLong; j++) {
                newTranslate.append(translatedSentence[j]).append(" "); /* The rest of the words */
            }
            allTranslations.add(newTranslate.toString());
            transCounter--;
        }
        return allTranslations;
    }

    /**
     * Method to get all thew definitions of a word sorted by the year
     * @param word the word with the definitions
     * @param language the language of the word
     * @return a list with all the sorted definitions, null if no word or language found
     * @throws LanguageNotFoundException exception thrown if the language is not found and displays a message
     * @throws WordNotFoundException exception thrown if the word is not found and displays a message
     */
    public ArrayList<Definition> getDefinitionsForWord(String word, String language)
            throws LanguageNotFoundException, WordNotFoundException {
        ArrayList<Definition> allDefinitions = new ArrayList<>();
        ArrayList<Word> langList = getLanguageList(language);
        if (langList == null)
            throw new LanguageNotFoundException("getDefinitionForWord: No language " + language + " found!");

        if (langList.contains(new Word(word))) {
            allDefinitions = langList.get(langList.indexOf(new Word(word))).getDefinitions();
            Collections.sort(allDefinitions);
        } else
            throw new WordNotFoundException("getDefinitionForWord: No word " + word + " found!");

        return allDefinitions;

    }

    /**
     * Method to get all the words from a specific language sorted alphabetically
     * @param language the language of the words
     * @return a list with all the words sorted, null if the language not found
     * @throws LanguageNotFoundException exception thrown if the language is not found and displays a message
     */
    public ArrayList<Word> getWordsSorted(String language) throws LanguageNotFoundException{

        ArrayList<Word> allWords = getLanguageList(language);
        if (allWords == null)
            throw new LanguageNotFoundException("getWordsSorted: No language " + language + " found!");

        Collections.sort(allWords);

        return allWords;
    }

    /**
     * Method to export a Dictionary of a specific language to a file
     * @param language the language of the dictionary
     */
    public void exportDictionary(String language) {
        ArrayList<Word> sortedWords = null;
        try {
            sortedWords = getWordsSorted(language);
        } catch (LanguageNotFoundException e) {
            System.out.println("ExportDictionary: " + e.getMessage());
        }

        if (sortedWords != null) {
            for (Word w : sortedWords)
                Collections.sort(w.getDefinitions());

            String path = "Dicts/" + language + "_ExportedDictionary.json";
            try {
                FileWriter file = new FileWriter(path);
                BufferedWriter output = new BufferedWriter(file);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                output.write(gson.toJson(sortedWords));
                output.close();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
}
