package other;

import lists.DynamicArray;
import network.Network;

// For processing csv file
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class DataProcessor {

    public static DynamicArray<String[]> readCSVFile(String file_path) {

        DynamicArray<String[]> rows = new DynamicArray<>();
        String line;

        try {
            FileReader fr = new FileReader(file_path);
            BufferedReader br = new BufferedReader(fr);

            br.readLine();  // Skip header

            while ((line = br.readLine()) != null) {
                rows.append(line.split(", "));
            }

            br.close();

        } catch (IOException e) {
            Util.print(Util.Color.RED,"File could not be read");
        }

        return rows;
    }

    public static DynamicArray<String> processData(Network network) {

        DynamicArray<String[]> rows = readCSVFile("src/main/csv/network_data.csv");
        DynamicArray<String> locationNames = new DynamicArray<>();

        rows.forEach(
                (row) -> {
                    String start = row[0];
                    String end = row[1];
                    int weight = Integer.parseInt(row[2]);

                    network.addEdge(start, end, weight);
                    network.addEdge(end, start, weight);

                    locationNames.addIfNotPresent(start);
                    locationNames.addIfNotPresent(end);
                }
        );

        return locationNames;
    }

}
