package other;

import lists.DynamicArray;
import network.Network;

// For processing csv file
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class CSVReader {

    public static DynamicArray<String> processData(String file_path, Network network) {

        DynamicArray<String> locations = new DynamicArray<>();
        String line;

        try {
            FileReader fr = new FileReader(file_path);
            BufferedReader br = new BufferedReader(fr);

            br.readLine();  // Skip header


            while ((line = br.readLine()) != null) {

                String[] row = line.split(", ");

                String start = row[0];
                String end = row[1];

                if (locations.indexOf(start) == -1)
                    locations.append(start);

                if (locations.indexOf(end) == -1)
                    locations.append(end);

                int weight = Integer.parseInt(row[2]);

                network.addEdge(start, end, weight);
                network.addEdge(end, start, weight);

            }
            br.close();

        } catch (IOException e) {
            Util.print(Util.Color.RED,"File could not be read");
        }

        return locations;

    }

}
