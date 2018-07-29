package rdfMaker;

import javaModel.Event;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.XSD;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class EventRDFMaker {

    public void makeRDF(List<Event> events){

        OntModel ontModel = this.makeRDFGraph(events);

        this.makeRDFFile(ontModel);

    }

    private OntModel makeRDFGraph(List<Event> events){
        //RDF Ontologie erzeugen
        //Verweis auf eigenen Namespace
        String nameSpaceHTWK = "http://www.imn.htwk-leipzig.de/~jdeck/semanticweb/ontologie#";

        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        ontModel.setNsPrefix("jdeck", nameSpaceHTWK);

        OntClass EventClass = ontModel.createClass(nameSpaceHTWK + "Event");

        // Properties erstellen sowie Range und Domain definieren
        // Property: Name des Events
        DatatypeProperty EventTitleProperty = ontModel.createDatatypeProperty(nameSpaceHTWK + "title");
        EventTitleProperty.addDomain(EventClass);
        EventTitleProperty.addRange(XSD.xstring);
        EventTitleProperty.addComment("Veranstaltungsname des Events", "DE");

        //Property: Stratzeit des Events
        DatatypeProperty EventStartTimeProperty = ontModel.createDatatypeProperty(nameSpaceHTWK + "start_time");
        EventStartTimeProperty.addDomain(EventClass);
        EventStartTimeProperty.addRange(XSD.xstring);
        EventStartTimeProperty.addComment("Startzeitpunkt des Events", "DE");

        //Property: Name des Veranstaltungsortes
        DatatypeProperty EventVenueNameProperty = ontModel.createDatatypeProperty(nameSpaceHTWK + "venue_name");
        EventVenueNameProperty.addDomain(EventClass);
        EventVenueNameProperty.addRange(XSD.xstring);
        EventVenueNameProperty.addComment("Name des Veranstaltungsortes", "DE");

        //Property: Name des Künstlers
        DatatypeProperty EventArtistProperty = ontModel.createDatatypeProperty(nameSpaceHTWK + "artist");
        EventArtistProperty.addDomain(EventClass);
        EventArtistProperty.addRange(XSD.xstring);
        EventArtistProperty.addComment("Name des Künstlers", "DE");

        // -- Graph mit Individuen/Individuals erstellen --
        // Individuen (events) zum Model hinzufügen
        for (Event event : events) {
            // Individuum hinzufügen
            Individual i = ontModel.createIndividual(nameSpaceHTWK + event.getTitle().replaceAll(" ", ""), EventClass);
            // Properties hinzufügen
            i.addProperty(EventTitleProperty, event.getTitle());
            i.addProperty(EventStartTimeProperty, event.getStart_time());
            i.addProperty(EventVenueNameProperty, event.getVenue_name());
            //i.addProperty(EventArtistProperty, event.getArtist());
            String artistName = event.getArtist().replaceAll("[\\[\\](){}]", "_").replaceAll(" ", "_").replaceAll("'", "");
            Resource resource = ontModel.createResource(nameSpaceHTWK + artistName.toLowerCase());
            i.addProperty(EventArtistProperty, resource);
        }

        return ontModel; // OntModel zurückgeben
    }

    private void makeRDFFile(OntModel ontModel){
        // Ausgabe in Datei
        File file = new File("C:/Users/Jeremias/workspace/semantic-web/Java-RDF-Maker/src/rdfFiles/", "events.rdf");

        try {
            ontModel.write(new FileOutputStream(file), "RDF/XML-ABBREV");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
