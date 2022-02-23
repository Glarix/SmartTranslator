import java.util.ArrayList;
import java.util.Collections;

public class Word implements Comparable<Word> {
    private String word;
    private String word_en;
    private String type;
    private ArrayList<String> singular;
    private ArrayList<String> plural;
    private ArrayList<Definition> definitions;

    public Word(String word) {
        this.word = word;
    }

    public Word(String word,
                String word_en,
                String type,
                ArrayList<String> singular,
                ArrayList<String> plural,
                ArrayList<Definition> definitions) {
        this.word = word;
        this.word_en = word_en;
        this.type = type;
        if (singular == null)
            this.singular = new ArrayList<>();
        else
            this.singular = singular;

        if (plural == null)
            this.plural = new ArrayList<>();
        else
            this.plural = plural;
        if (definitions == null)
            this.definitions = new ArrayList<>();
        else
            this.definitions = definitions;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setWord_en(String word_en) {
        this.word_en = word_en;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSingular(ArrayList<String> singular) {
        this.singular = singular;
    }

    public void setPlural(ArrayList<String> plural) {
        this.plural = plural;
    }

    public void setDefinitions(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public String getWord_en() {
        return word_en;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getSingular() {
        return singular;
    }

    public ArrayList<String> getPlural() {
        return plural;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    /**
     * Method to see if two Word objects are equal used for contains and getIndex arrayList methods
     * @param obj the object to compare to
     * @return true if objects are the same, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this.word.equals(((Word) obj).getWord())){
            return true;
        }

        if (this.word.equals(((Word) obj).getWord_en()))
            return true;

        return false;
    }

    /**
     * Method to update a word if possible, the possible updates might be:
     * new Singular or Plural forms or new or updated definitions
     * @param possibleUpdates the word with the possible new information
     * @return false if no updates were made, true if at least some update was made
     */
    public boolean updateWord(Word possibleUpdates) {
        boolean modified = false;

        ArrayList<String> singulars = possibleUpdates.getSingular();
        if (singulars != null) {
            for (String sing : singulars)  {
                if (this.singular == null) {
                    this.singular = new ArrayList<>();
                    this.singular.add(sing);
                    modified = true;
                } else {
                    if (!this.singular.contains(sing)){
                        this.singular.add(sing);
                        modified = true;
                    }
                }
            }
        }


        ArrayList<String> plurals = possibleUpdates.getPlural();
        if (plurals != null) {
            for (String plur : plurals) {
                if (this.plural == null) {
                    this.plural = new ArrayList<>();
                    this.plural.add(plur);
                    modified = true;
                } else {
                    if (!this.plural.contains(plur)){
                        this.plural.add(plur);
                        modified = true;
                    }
                }
            }
        }


        ArrayList<Definition> defs = possibleUpdates.getDefinitions();
        if (defs != null) {
            for (Definition def : defs) {
                if (this.definitions == null) {
                    this.definitions = new ArrayList<>();
                    this.definitions.add(def);
                    modified = true;
                } else {
                    if (!this.definitions.contains(def)) {
                        this.definitions.add(def);
                        modified = true;
                    }
                    else {
                        Definition deff = this.definitions.get(this.definitions.indexOf(def));
                        boolean defUpdateRez = deff.updateDefinition(def);
                        if (defUpdateRez)
                            modified = true;
                    }
                }
            }
        }

        return modified;
    }

    @Override
    public int compareTo(Word o) {
        return word.compareTo(o.word);
    }
}
