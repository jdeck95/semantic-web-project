package javaModel;

public class Concert {

    String artistName;
    int totalConcerts;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getTotalConcerts() {
        return totalConcerts;
    }

    public void setTotalConcerts(int totalConcerts) {
        this.totalConcerts = totalConcerts;
    }

    @Override
    public String toString() {
        return artistName + " " + totalConcerts;
    }
}
