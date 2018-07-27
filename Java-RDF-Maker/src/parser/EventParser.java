package parser;

import javaModel.Artist;
import javaModel.Event;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class EventParser {

    public void parse (List<Event> events) {
        String jsonData = ArtistParser.readFile("C:/Users/Jeremias/workspace/semantic-web/fetch/events.json");
        JSONObject jobj = new JSONObject(jsonData);
        JSONArray table = jobj.getJSONArray("table");
        for (int i = 0; i < table.length(); ++i) {
            JSONObject content = table.getJSONObject(i);
            String title = content.getString("title");
            String start_time = content.getString("start_time");
            String venue_name = content.getString("venue_name");
            String artist = content.getString("artist");

            Event event = new Event();
            event.setTitle(title);
            event.setStart_time(start_time);
            event.setVenue_name(venue_name);
            event.setArtist(artist);

            events.add(event);
        }
    }
}
