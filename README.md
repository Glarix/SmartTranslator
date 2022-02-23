# SmartTranslator – Serviciu de traducere texte

### Codreanu Dan 321CB
___

## Description
This project is build to represent a simplified version of a 
word translator having implemented multiple features.

The program can:
* Read and store informations about words from diferent languages from
from a collection of JSON files.
* Add, update or remove Words and word Definitions
* Translate Words and Sentences
* Give different translations to a Sentence(using synonyms)
* Get all the definitions of a word (sorted by year of publication)
* Export the dictionary for a specified language
***

## Functionality

After data is read and stored, all the functionality methods can be
accessed by initiating a Administration instance and passing a 
HashMap with all the Words as argument.
#### Example:
```java
        File directoryPath = new File("path/to/directory/with/JSON/files");
        HashMap<String, ArrayList<Word>> generalStorage =
            ReadFromJSONFile.fillHashMap(directoryPath);
        Administration translator = new Administration(generalStorage);

```
#### In the program are Implemented 9 features:
#### Examples of usage:
* AddWord - add or update a word
```Java
    /**
    * @param wordToAdd is a Word instance that needs to be added or updated
    * @param language is the language of the word
    */
    translator.addWord(wordToAdd, language);
```
* RemoveWord - remove a word from dictionary
```java
    /**
    * @param word is the Word that needs to be removed
    * @param language is the language of the word
    */
    translator.removeWord(word, language);
```
* AddDefinitionForWord and Remove definition - self explanatory
```java
    /**
    * @param word is the Word that needs a new Definition
    * @param language is the language of the word
    * @param Definition a instance of the new Definition
    */
    translator.addDefinitionForWord(word, language, Definition);
    /**
    * @param word is the Word that needs to be removed
    * @param language is the language of the word
    * @param dictionary is the dictionary of the definition to be removed
    */
    translator.removeDefinition(word, language, dictionary);
```
* TranslateWord, TranslateSentenc, TranslateSentences - used to translate a word, sentence or sentence in multiple ways
```java
    /**
    * @param word is the Word that needs to be translated
    * @param fromLanguage is the initial language
    * @param toLanguage is the final language
    * @return the translated word
    */
    String translated = translator.translateWord(word, fromLanguage, toLanguage);

    /**
    * @param sentence is the Sentence that needs to be translated
    * @param fromLanguage is the initial language
    * @param toLanguage is the final language
    * @return the translated Sentence
    */
    String translated = translator.translateSentence(sentence, fromLanguage, toLanguage);

    ArrayList<String> translated = translator.translateSentences(sentence, fromLanguage, toLanguage);
```
* GetDefinitions - used to get all the definitions of a word sorted by the year of publication
```java
    /**
    * @param word is the Word that contains the definitions
    * @param language is the language of the word
    * @return an arrayList with definitions
    */
    ArrayList<Definition> definitions = translator.getDefinitionsForWord(word, language);
```
* ExportDictionary - used to export a dictionary for a specific language
```java
    /**
    * @param language is the language of the dictionary
    */
    translator.exportDictionary(language);
```
***
## Dependencies used
#### For this project are required the following dependencies:
```
<dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.5</version>
        </dependency>
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.8.0</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.8.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.8.2</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
***
## Brief Class Description
* Administration - Class containing the implementations of the functionality methods
* Definition - Class modeling a definition object
* Word - Class modeling a Word object
* Initialisation - Class for reading the JSON files and fill the data colection
* ReadFromJSONFile - Class containing the implementations of methods used to read from a JSON file
* TranslatorTool - Class to translate a word from english to a specified language
* Utils - Class containing helper methods for tests
* LanguageNotFoundException and WordNotFoundException - Exception classes created by me to handle two exception cases.
***
## Tests
The tests for every method from class Administration were implemented using junit.

The test class is AdministrationTest and it's located in src/test/java folder.

Tests include at least a normal test and an exception case.

(But I have a little problem: if I run the test class manually everything seems to be fine but when I run it from lifecycle->tests it says that there are errors even though when printed to the console the results are good and the method works as it should. I think the problem is with a romenian character.
Please run the tests manually :) )
***
### POO Project No.2 2021.