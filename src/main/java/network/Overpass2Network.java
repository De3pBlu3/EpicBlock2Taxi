package network;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Overpass2Network {
    // make a network from an overpass query
    static final URI overpassURL;

    static {
        try {
            overpassURL = new URI("https://overpass-api.de/api/interpreter?data=");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public static void getDataToJson(String overpassQuery) throws IOException, InterruptedException {
        // combine overpass query with overpassURL
        // send request to overpassURL
        // get response
        String encodedQuery = URLEncoder.encode(overpassQuery, StandardCharsets.UTF_8);
        URI requestUri = URI.create(overpassURL + encodedQuery);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestUri)
                .GET()
                .build();

        // print response
        // HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());


        // save response to file
        Files.writeString(Paths.get("overpassResponse.json"), response.body());
    }

    // get data from file and make a network
    public static Network makeNetworkFromJson(String filename) throws IOException {
        // read file
        String json = Files.readString(Paths.get(filename));

        // make network
        Network network = new Network();

        // each way will contains a list of nodes. If those nodes are ALSO found in the lists of other ways, they should bea
        // added to the network



        return network;
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
//        getDataToJson("[timeout:25][out:json];(way[\"highway\"][\"highway\"!=\"footway\"][\"highway\"!=\"pedestrian\"][\"highway\"!=\"path\"][\"highway\"!=\"construction\"][\"highway\"!=\"steps\"][\"highway\"!=\"corridor\"][\"highway\"!=\"cycleway\"][\"access\"!=\"private\"](45.11281541964,-93.255579024157,45.115540178809,-93.251202665765);node[\"highway\"][\"highway\"!=\"footway\"][\"highway\"!=\"pedestrian\"][\"highway\"!=\"path\"][\"highway\"!=\"construction\"][\"highway\"!=\"steps\"][\"highway\"!=\"corridor\"][\"highway\"!=\"cycleway\"][\"access\"!=\"private\"](45.11281541964,-93.255579024157,45.115540178809,-93.251202665765);relation[\"highway\"][\"highway\"!=\"footway\"][\"highway\"!=\"pedestrian\"][\"highway\"!=\"path\"](45.11281541964,-93.255579024157,45.115540178809,-93.251202665765););out;>;out skel qt;");
    }
}




