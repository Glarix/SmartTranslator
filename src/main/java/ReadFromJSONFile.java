import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ReadFromJSONFile {

    /**
     * Method to read all the words from a JSON file
     * @param JSONFile path to JSON file
     * @return a list with all the words
     */
    public static ArrayList<Word> readJSON(String JSONFile) {

        ArrayList<Word> allWords = null;
        Gson gson = new Gson();
        try {
            Type foundListType = new TypeToken<ArrayList<Word>>(){}.getType();
            allWords = gson.fromJson(new FileReader(
                    JSONFile),
                    foundListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return allWords;
    }


    /**
     * Method to read and store all the words from all the JSON files
     * @param directoryPath path to the directory containing all the JSON files
     * @return the collection containing all the words, null if no files read
     */
    public static HashMap<String, ArrayList<Word>> fillHashMap(File directoryPath) {
        File fileList[] = directoryPath.listFiles();

        if (fileList.length == 0) {
            System.out.println("No files found in specified Dictionary!");
            return null;
        }

        HashMap<String, ArrayList<Word>> generalStorage = new HashMap<>();

        for (File f : fileList) {
            try {
                String extension = FilenameUtils.getExtension(f.getName());
                /* Verifying the file to have the json extension */
                if (extension.equals("json")) {
                    ArrayList<Word> words = ReadFromJSONFile.readJSON(f.getCanonicalPath());
                    String key = f.getName().substring(0,2);

                    generalStorage.put(key, words);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (generalStorage == null)
            return null;
        return generalStorage;
    }

}
