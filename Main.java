import java.util.*

public class Main {


}

class Media {
    private int id;
    private String title;
    private int release_year;

    Media(int id, String title, int release_year) {
        this.id = id;
        this.title = title;
        this.release_year = release_year;
    }

}

class Movie extends Media {
    private String director;
    private String country;
    private String rating;
    private int duration; //minutes
    private String description;

    Movie(int id, String title, int release_year, String director, String country, String rating, int duration, String description) {
        super(id, title, release_year);
        this.director = director;
        this.country = country;
        this.rating = rating;
        this.duration = duration;
        this.description = description;
    }

}

class TVShow extends Media {
    private String director;
    private String country;
    private String rating;
    private int numberOfSeasons;
    private String description;

    TVShow(int id, String title, String director, String country, int releaseYear, String rating, int numberOfSeasons, String description) {
        super(id, title, releaseYear);
        this.director = director;
        this.country = country;
        this.rating = rating;
        this.numberOfSeasons = numberOfSeasons;
        this.description = description;
    }
}

class VideoGame extends Media {
    private String platform;
    private String genre;
    private String publisher;
    private double copiesSold;

    VideoGame(int id, String title, String platform, int releaseYear, String genre, String publisher, double copiesSold) {
        super(id, title, releaseYear);
        this.platform = platform;
        this.genre = genre;
        this.publisher = publisher;
        this.copiesSold = copiesSold;
    }
}

class MusicAlbum extends Media {
    private String artist;
    private double globalSales;
    private int tracks;
    private double duration;
    private String genre;

    public MusicAlbum(int id, String title, int releaseYear, String artist, double globalSales, int tracks, double duration, String genre) {
        super(id, title, releaseYear);
        this.artist = artist;
        this.globalSales = globalSales;
        this.tracks = tracks;
        this.duration = duration;
        this.genre = genre;
    }
}

class MediaManager {
    ArrayList<Media> products = new ArrayList<>();
    int a = 2;
    
}
