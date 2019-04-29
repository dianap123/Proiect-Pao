package serviceClasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by diana on 27.04.2019.
 */
public class CsvParser {
    public Map<String, List<String>> read(Path path) {
        List<String> header = new ArrayList<>();
        boolean isHeader = true;
        Map<String, List<String>> data = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    String[] values = line.split(",");
                    for (String item: values)
                        header.add(item);
                    isHeader = false;
                }
                else {
                    String[] values = line.split(",");
                    for (int i = 0; i < header.size(); i++) {
                        data.computeIfAbsent(header.get(i), k -> new ArrayList<>()).add(values[i]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading from file");
        }
        return data;
    }

    public void write(String action, Timestamp time) {
        try (FileWriter writer = new FileWriter("/home/diana/An2/PAO/proiect/Files/actions.csv", true)) {
            writer.append(action);
            writer.append(", ");
            writer.append(String.valueOf(time));
            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while writing to file");
        }
    }

}
