import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

class AdministrationTest {

    public Administration initialize() {
        File directoryPath = new File("Dicts");
        HashMap<String, ArrayList<Word>> generalStorage = ReadFromJSONFile.fillHashMap(directoryPath);
        Administration ads = new Administration(generalStorage);
        return ads;
    }


    @Test
    void addWord() {
        Administration ads = initialize();

        // Test with existing word and no new information -- should return false
        Word existingWord = Utils.createWordForTest("merge");
        Assertions.assertFalse(ads.addWord(existingWord, "ro"));

        // Test with completely new word -- should return true
        Word newWord = Utils.createWordForTest("tren");
        Assertions.assertTrue(ads.addWord(newWord, "ro"));

        // Test with existing word but more information -- should return true
        Word existingWithNewSingular = Utils.createWordForTest("tren");
        ArrayList<String> plurals = new ArrayList<>();
        plurals.add("trenuri");
        existingWithNewSingular.setPlural(plurals);
        Assertions.assertTrue(ads.addWord(existingWithNewSingular, "ro"));

    }

    @Test
    void removeWord() {
        Administration ads = initialize();
        // Test to delete existing wrod -- should return true
        try {
            Assertions.assertTrue(ads.removeWord("joc", "ro"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // Test to remove inexisting word -- should return false
        try {
            Assertions.assertFalse(ads.removeWord("tren", "ro"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // Test with inexisting language -- should catch exception
        try {
            ads.removeWord("joc", "rofr");
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addDefinitionForWord() {
        Administration ads = initialize();

        // Test with Definition with from new dict -- should return true and add it
        Definition newDef = Utils.createDefinitionForTest("SomeDict", "blablabla");
        try {
            Assertions.assertTrue(ads.addDefinitionForWord("joc", "ro", newDef));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (WordNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // Test with Definition with existing dict -- should return false
        Definition existingDef = Utils.createDefinitionForTest("DEX 09", "BLBLB");
        try {
            Assertions.assertFalse(ads.addDefinitionForWord("joc", "ro", existingDef));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (WordNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // Test with non existing word -- should catch exception
        try {
            ads.addDefinitionForWord("jocjoc", "ro", newDef);
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (WordNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void removeDefinition() {
        Administration ads = initialize();
        // Test to remove existing definition -- should returrn true
        try {
            Assertions.assertTrue(ads.removeDefinition("joc", "ro", "DEX 09"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (WordNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // Test to remove non-existing definition -- should returrn false
        try {
            Assertions.assertFalse(ads.removeDefinition("joc", "ro", "randomDEX"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (WordNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void translateWord() {
        Administration ads = initialize();
        // Test to translate an existing word -- should translate
        try {
            System.out.println("TranslateWord: Translate chat-pisică");
            Assertions.assertEquals("pisică", ads.translateWord("chat", "fr","ro"));
            System.out.println(ads.translateWord("chat", "fr","ro"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // Test to translate a word that is not found in the toLanguage -- should remain the same
        try {
            System.out.println("TranslateWord: Translate manger-manger");
            Assertions.assertEquals("manger", ads.translateWord("manger", "fr","ro"));
            System.out.println(ads.translateWord("manger", "fr","ro"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // Test to catch exception if the language does not exist
        try {
            System.out.println("TranslateWord: Wrong Language");
            System.out.println(ads.translateWord("manger", "frfr","ro"));
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
    }

    @Test
    void translateSentence() {
        Administration ads = initialize();
        // Test to translate complete sentence -- should translate
        System.out.println("TranslateSentence: Translate joc pisică - jeu chat ");
        Assertions.assertEquals("jeu chat ", ads.translateSentence("joc pisică", "ro", "fr"));
        System.out.println(ads.translateSentence("joc pisică", "ro", "fr"));
        // Test to translate an incomplete sentence(not all words are in dictionary)
        System.out.println("TranslateSentence: Translate chat mangere - pisică mangere ");
        Assertions.assertEquals("pisică mangere ", ads.translateSentence("chat mangere", "fr", "ro"));
        System.out.println(ads.translateSentence("chat mangere", "fr", "ro"));
    }

    @Test
    void translateSentences() {
        Administration ads = initialize();
        // Test to translate a sentence in multiple forms - should translate in 3 ways
        System.out.println("TranslateSentences: jeu chat -- fr to ro");
        try {
            ArrayList<String> translations = ads.translateSentences("jeu chat", "fr", "ro");
            for (String str : translations) {
                System.out.println(str);
            }
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // Test with less translations if 3 are not possible
        System.out.println("TranslateSentences: jeu manger -- fr to ro");
        try {
            ArrayList<String> translations = ads.translateSentences("jeu manger", "fr", "ro");
            for (String str : translations) {
                System.out.println(str);
            }
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // Test if language not found should catch exception
        try {
            ArrayList<String> translations = ads.translateSentences("jeu manger", "frfr", "roro");
            for (String str : translations) {
                System.out.println(str);
            }
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void getDefinitionsForWord() {
        Administration ads = initialize();
        // Test with existing word -- should return definitions sorted chronologically
        try {
            ArrayList<Definition> defs = ads.getDefinitionsForWord("pisică", "ro");
            for (Definition def : defs) {
                if (def != null) {
                    System.out.println(def.getDict());
                    System.out.println(def.getYear());
                }
            }
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (WordNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // Test with bad word -- should catch exception
        try {
            ArrayList<Definition> defs = ads.getDefinitionsForWord("tren", "ro");
            for (Definition def : defs) {
                if (def != null) {
                    System.out.println(def.getDict());
                    System.out.println(def.getYear());
                }
            }
        } catch (LanguageNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (WordNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void exportDictionary() {
        Administration ads = initialize();
        // Test with existing dictionary -- should create JSON file in Dics directory
        ads.exportDictionary("ro");
        // Test with inexisting dictionary -- should print error message and no file created
        ads.exportDictionary("random");
    }
}