package parser;

import javaModel.Concert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import org.json.*;

public class ConcertParser {

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void parse (List<Concert> concerts) {
        String jsonData = readFile("C:/Users/Jeremias/workspace/semantic-web/fetch/concerts.json");
        JSONObject jobj = new JSONObject(jsonData);
        JSONArray table = jobj.getJSONArray("table");
        for (int i = 0; i < table.length(); ++i) {
            JSONObject content = table.getJSONObject(i);
            String artistName = content.getString("artistName");
            int totalConcerts = content.getInt("totalConcerts");

            Concert concert = new Concert();
            concert.setArtistName(artistName);
            concert.setTotalConcerts(totalConcerts);

            concerts.add(concert);
        }
    }
}
