import java.util.ArrayList;

public class Definition implements Comparable<Definition>{

    private String dict;
    private String dictType;
    private int year;
    private ArrayList<String> text;

    public Definition(String dict) {
        this.dict = dict;
    }

    public Definition(String dict,
                      String dictType,
                      int year,
                      ArrayList<String> text) {
        this.dict = dict;
        this.dictType = dictType;
        this.year = year;
        if (text == null)
            this.text = new ArrayList<>();
        else
            this.text = text;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }

    public String getDict() {
        return dict;
    }

    public String getDictType() {
        return dictType;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<String> getText() {
        return text;
    }


    /**
     * Method to see if two definitions are equal based on "dict" parameter
     * @param obj the definition object to compare to
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this.dict.equals(((Definition)obj).getDict())) {
            return true;
        }
        return false;
    }

    /**
     * Method to update a definition if necessary
     * @param possibleUpdate the definition with possible new information
     * @return false if no updates were made, true if at least some update was made
     */
    public boolean updateDefinition (Definition possibleUpdate) {
        boolean modified = false;
        ArrayList<String> texts = possibleUpdate.getText();
        if (texts != null) {
            for (String tex : texts) {
                if (this.text == null) {
                    this.text = new ArrayList<>();
                    this.text.add(tex);
                    modified = true;
                } else {
                    if (!this.text.contains(tex)) {
                        this.text.add(tex);
                        modified = true;
                    }
                }
            }
        }

        return modified;
    }

    /**
     * Method to compare two definitions by year for sorting purpose
     * @param o the definition to compare to
     * @return 1 if this year is grater, -1 if this year is lower, 0 if equal
     */
    @Override
    public int compareTo(Definition o) {
        if (year > o.year)
            return 1;
        else if (year < o.year)
            return -1;
        else
            return 0;
    }
}
