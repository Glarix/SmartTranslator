import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Initialisation {

    public static void main(String[] args) {
        File directoryPath = new File("Dicts");

        HashMap<String, ArrayList<Word>> generalStorage =
                ReadFromJSONFile.fillHashMap(directoryPath);

        Administration ads = new Administration(generalStorage);
    }
}
