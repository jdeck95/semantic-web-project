package rdfMaker;

import javaModel.Artist;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.XSD;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ArtistRDFMaker {

    public void makeRDF(List<Artist> artists){

        OntModel ontModel = this.makeRDFGraph(artists);

        this.makeRDFFile(ontModel);

    }

    private OntModel makeRDFGraph(List<Artist> artists){
        //RDF Ontologie erzeugen
        //Verweis auf eigenen Namespace
        String nameSpaceHTWK = "http://www.imn.htwk-leipzig.de/~jdeck/semanticweb/ontologie#";

        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        ontModel.setNsPrefix("jdeck", nameSpaceHTWK);

        OntClass artistClass = ontModel.createClass(nameSpaceHTWK + "Artist");

        // Properties erstellen sowie Range und Domain definieren
        // Property: Name des Künstlers
        DatatypeProperty artistNameProperty = ontModel.createDatatypeProperty(nameSpaceHTWK + "name");
        artistNameProperty.addDomain(artistClass);
        artistNameProperty.addRange(XSD.xstring);
        artistNameProperty.addComment("Name des Künstlers", "DE");

        //Property: Anzahl der Follower des Künstlers
        DatatypeProperty artistFollowerProperty = ontModel.createDatatypeProperty(nameSpaceHTWK + "followers");
        artistFollowerProperty.addDomain(artistClass);
        artistFollowerProperty.addRange(XSD.integer);
        artistFollowerProperty.addComment("Anzahl der Follower des Künstlers", "DE");

        //Property: ID des Künstlers
        DatatypeProperty artistIDProperty = ontModel.createDatatypeProperty(nameSpaceHTWK + "id");
        artistIDProperty.addDomain(artistClass);
        artistIDProperty.addRange(XSD.xstring);
        artistIDProperty.addComment("ID des Künstlers", "DE");

        // -- Graph mit Individuen/Individuals erstellen --
        // Individuen (Artists) zum Model hinzufügen
        for (Artist artist : artists) {
            String artistName = artist.getName().replaceAll("[\\[\\](){}]", "_").replaceAll(" ", "_").replaceAll("'", "");
            // Individuum hinzufügen
            Individual i = ontModel.createIndividual(nameSpaceHTWK + artistName.toLowerCase(), artistClass);
            // Properties hinzufügen
            i.addProperty(artistNameProperty, artistName.toLowerCase());


            String followers = Integer.toString(artist.getFollowers());
            i.addLiteral(artistFollowerProperty, artist.getFollowers());

            i.addProperty(artistIDProperty, artist.getId());
        }

        return ontModel; // OntModel zurückgeben
    }

    private void makeRDFFile(OntModel ontModel){
        // Ausgabe in Datei
        File file = new File("C:/Users/Jeremias/workspace/semantic-web/Java-RDF-Maker/src/rdfFiles/", "artists.rdf");

        try {
            ontModel.write(new FileOutputStream(file), "RDF/XML-ABBREV");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
