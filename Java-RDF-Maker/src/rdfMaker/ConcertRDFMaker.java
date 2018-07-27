package rdfMaker;

import javaModel.Concert;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ConcertRDFMaker {

    public void makeRDF(List<Concert> concerts){

        OntModel ontModel = this.makeRDFGraph(concerts);

        this.makeRDFFile(ontModel);

    }

    private OntModel makeRDFGraph(List<Concert> concerts){
        //RDF Ontologie erzeugen
        //Verweis auf eigenen Namespace
        String nameSpaceHTWK = "htpp://www.imn.htwk-leipzig.de/~jdeck/semanticweb/ontologie#";

        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        ontModel.setNsPrefix("jdeck", nameSpaceHTWK);

        OntClass concertClass = ontModel.createClass(nameSpaceHTWK + "Concert");

        // Properties erstellen sowie Range und Domain definieren
        // Property: Name des Künstlers
        DatatypeProperty artistNameProperty = ontModel.createDatatypeProperty(nameSpaceHTWK + "artistName");
        artistNameProperty.addDomain(concertClass);
        artistNameProperty.addRange(XSD.xstring);
        artistNameProperty.addComment("Name des Künstlers", "DE");

        //Property: Anzahl der Follower des Künstlers
        DatatypeProperty totalConcertsProperty = ontModel.createDatatypeProperty(nameSpaceHTWK + "totalConcerts");
        totalConcertsProperty.addDomain(concertClass);
        totalConcertsProperty.addRange(XSD.integer);
        totalConcertsProperty.addComment("Anzahl der Konzerte des Künstlers", "DE");

        // -- Graph mit Individuen/Individuals erstellen --
        // Individuen (Artists) zum Model hinzufügen
        for (Concert concert : concerts) {
            // Individuum hinzufügen
            Individual i = ontModel.createIndividual(nameSpaceHTWK + concert.getArtistName().replaceAll(" ", ""), concertClass);
            // Properties hinzufügen
            i.addProperty(artistNameProperty, concert.getArtistName());

            String concertCount = Integer.toString(concert.getTotalConcerts());
            i.addLiteral(totalConcertsProperty, concert.getTotalConcerts());
        }

        return ontModel; // OntModel zurückgeben
    }

    private void makeRDFFile(OntModel ontModel){
        // Ausgabe in Datei
        File file = new File("C:/Users/Jeremias/workspace/semantic-web/Java-RDF-Maker/src/rdfFiles/", "concerts.rdf");

        try {
            ontModel.write(new FileOutputStream(file), "RDF/XML-ABBREV");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
