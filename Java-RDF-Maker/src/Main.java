import javaModel.Artist;
import javaModel.Concert;
import javaModel.Event;
import parser.ArtistParser;
import parser.ConcertParser;
import parser.EventParser;
import rdfMaker.ArtistRDFMaker;
import rdfMaker.ConcertRDFMaker;
import rdfMaker.EventRDFMaker;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Artist> artists = new ArrayList<Artist>();
        // artists.json parsen und Liste füllen
        ArtistParser artistParser = new ArtistParser();
        artistParser.parse(artists);

        // Test-Ausgabe
        System.out.println("Artists:");
        for (Artist artist : artists) {
            System.out.println(artist.toString());
        }

        List<Event> events = new ArrayList<Event>();
        // events.json parsen und Liste füllen
        EventParser eventParser = new EventParser();
        eventParser.parse(events);

        // Test-Ausgabe
        System.out.println("Events:");
        for (Event event : events) {
            System.out.println(event.toString());
        }

        List<Concert> concerts = new ArrayList<Concert>();
        // concerts.json parsen und Liste füllen
        ConcertParser concertParser = new ConcertParser();
        concertParser.parse(concerts);

        // Test-Ausgabe
        System.out.println("Concerts:");
        for (Concert concert : concerts) {
            System.out.println(concert.toString());
        }

        //RDF erzeugen
        ArtistRDFMaker artistRDFMaker = new ArtistRDFMaker();
        artistRDFMaker.makeRDF(artists);

        EventRDFMaker eventRDFMaker = new EventRDFMaker();
        eventRDFMaker.makeRDF(events);

        ConcertRDFMaker concertRDFMaker = new ConcertRDFMaker();
        concertRDFMaker.makeRDF(concerts);
    }
}
