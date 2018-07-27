package parser;

import javaModel.Artist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import org.json.*;

public class ArtistParser {

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

    public void parse (List<Artist> artists) {
        String jsonData = readFile("C:/Users/Jeremias/workspace/semantic-web/fetch/artists.json");
        JSONObject jobj = new JSONObject(jsonData);
        JSONArray table = jobj.getJSONArray("table");
        for (int i = 0; i < table.length(); ++i) {
            JSONObject content = table.getJSONObject(i);
            String name = content.getString("name");
            int followers = content.getInt("followers");
            String id = content.getString("id");

            Artist artist = new Artist();
            artist.setName(name);
            artist.setFollowers(followers);
            artist.setId(id);

            artists.add(artist);
        }
    }
}
