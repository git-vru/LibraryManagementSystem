package utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
//Please dont move this out of utilities
//It messes up other class
public class CSVreader {
    public static List<String[]> parseFile(String path) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found :" + e);
        }
        catch (IOException e) {
            System.out.println("Sorry, something went wrong :" + e);
        }
        return data;
    }
}
