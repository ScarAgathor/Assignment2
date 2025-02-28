import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MediaManager manager = new MediaManager(); // Create an instance of MediaManager
        manager.loadData("src/dataset.csv"); // Load data from the CSV file

        // Display total counts of different media types
        System.out.println("Total number of products: " + manager.getTotalProducts());
        System.out.println("Total number of movies: " + manager.getTotalMovies());
        System.out.println("Total number of TV shows: " + manager.getTotalTVShows());
        System.out.println("Total number of video games: " + manager.getTotalVideoGames());
        System.out.println("Total number of music albums: " + manager.getTotalMusicAlbums());

        // Find and display the oldest product
        Media oldest = manager.findOldestProduct();
        if (oldest != null)
            System.out.println("Oldest product: " + oldest.getTitle() + " (" + oldest.getReleaseYear() + ")");

        // Find and display the most popular music album
        MusicAlbum popularAlbum = manager.findMostPopularMusicAlbum();
        if (popularAlbum != null)
            System.out.println("Most popular music album: " + popularAlbum.getTitle() + " by " + popularAlbum.getArtist()
                    + " (" + popularAlbum.getGlobalSales() + " million sales)");

        // Find and display the most popular video game
        VideoGame popularGame = manager.findMostPopularVideoGame();
        if (popularGame != null)
            System.out.println("Most popular video game: " + popularGame.getTitle() + " (" + popularGame.getCopiesSold() + " million copies sold)");

        // Find and display the most common age rating
        System.out.println("Most common age rating: " + manager.findMostCommonAgeRating());

        // Find and display the shortest movie
        Movie shortestMovie = manager.findShortestMovie();
        if (shortestMovie != null)
            System.out.println("Shortest movie: " + shortestMovie.getTitle() + " (" + shortestMovie.getDuration() + " minutes)");

        // Find and display the shortest music album
        MusicAlbum shortestAlbum = manager.findShortestMusicAlbum();
        if (shortestAlbum != null)
            System.out.println("Shortest music album: " + shortestAlbum.getTitle() + " by " + shortestAlbum.getArtist()
                    + " (" + shortestAlbum.getDuration() + " minutes)");
    }
}

// Base class representing all media types
class Media {
    private int id;
    private String title;
    private int releaseYear;

    Media(int id, String title, int releaseYear) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getReleaseYear() { return releaseYear; }
}

// Represents a Movie, extending Media
class Movie extends Media {
    private String director;
    private String country;
    private String rating;
    private int duration; // Duration in minutes
    private String description;

    Movie(int id, String title, String director, String country, int releaseYear, String rating, int duration, String description) {
        super(id, title, releaseYear);
        this.director = director;
        this.country = country;
        this.rating = rating;
        this.duration = duration;
        this.description = description;
    }

    public String getRating() { return rating; }
    public int getDuration() { return duration; }
}

// Represents a TV Show, extending Media
class TVShow extends Media {
    private String director;
    private String country;
    private String rating;
    private String numberOfSeasons; // String since some values may not be numeric
    private String description;

    TVShow(int id, String title, String director, String country, int releaseYear, String rating, String numberOfSeasons, String description) {
        super(id, title, releaseYear);
        this.director = director;
        this.country = country;
        this.rating = rating;
        this.numberOfSeasons = numberOfSeasons;
        this.description = description;
    }

    public String getRating() { return rating; }
}

// Represents a Video Game, extending Media
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

    public double getCopiesSold() { return copiesSold; }
}

// Represents a Music Album, extending Media
class MusicAlbum extends Media {
    private String artist;
    private double globalSales;
    private int tracks;
    private double duration; // Duration in minutes
    private String genre;

    MusicAlbum(int id, int releaseYear, String artist, String title, double globalSales, int tracks, double duration, String genre) {
        super(id, title, releaseYear);
        this.artist = artist;
        this.globalSales = globalSales;
        this.tracks = tracks;
        this.duration = duration;
        this.genre = genre;
    }

    public String getArtist() { return artist; }
    public double getGlobalSales() { return globalSales; }
    public double getDuration() { return duration; }
}

class MediaManager {
    private ArrayList<Media> products = new ArrayList<>();
    private int totalMovies = 0;
    private int totalTvShows = 0;
    private int totalVideoGames = 0;
    private int totalMusicAlbums = 0;

    void loadData(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {

                // Skip completely empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String type = data[1].trim();

                switch (type) {
                    case "Movie":
                        products.add(new Movie(id, data[2], data[3], data[4], Integer.parseInt(data[5]), data[6], Integer.parseInt(data[7]), data[8]));
                        totalMovies++;
                        break;
                    case "TV Show":
                        products.add(new TVShow(id, data[2], data[3], data[4], Integer.parseInt(data[5]), data[6], data[7], data[8]));
                        totalTvShows++;
                        break;
                    case "Video Game":
                        products.add(new VideoGame(id, data[2], data[3], Integer.parseInt(data[4]), data[5], data[6], Double.parseDouble(data[7])));
                        totalVideoGames++;
                        break;
                    case "Music Album":
                        products.add(new MusicAlbum(id, Integer.parseInt(data[2]), data[3], data[4], Double.parseDouble(data[5]), Integer.parseInt(data[6]), Double.parseDouble(data[7]), data[8]));
                        totalMusicAlbums++;
                        break;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }

    public int getTotalProducts() { return products.size(); }
    public int getTotalMovies() { return totalMovies; }
    public int getTotalTVShows() { return totalTvShows; }
    public int getTotalVideoGames() { return totalVideoGames; }
    public int getTotalMusicAlbums() { return totalMusicAlbums; }

    public Media findOldestProduct() {
        if (products.isEmpty()) return null;

        Media oldest = products.get(0);
        for (Media item : products) {
            if (item.getReleaseYear() < oldest.getReleaseYear()) {
                oldest = item;
            }
        }
        return oldest;
    }

    public MusicAlbum findMostPopularMusicAlbum() {
        MusicAlbum mostPopular = null;
        double maxSales = 0;

        for (Media item : products) {
            if (item instanceof MusicAlbum) {
                MusicAlbum album = (MusicAlbum) item;
                if (album.getGlobalSales() > maxSales) {
                    maxSales = album.getGlobalSales();
                    mostPopular = album;
                }
            }
        }
        return mostPopular;
    }

    public VideoGame findMostPopularVideoGame() {
        VideoGame mostPopular = null;
        double maxCopies = 0;

        for (Media item : products) {
            if (item instanceof VideoGame) {
                VideoGame game = (VideoGame) item;
                if (game.getCopiesSold() > maxCopies) {
                    maxCopies = game.getCopiesSold();
                    mostPopular = game;
                }
            }
        }
        return mostPopular;
    }

    public String findMostCommonAgeRating() {
        Map<String, Integer> ratingCount = new HashMap<>();
        for (Media item : products) {
            if (item instanceof Movie) {
                ratingCount.put(((Movie) item).getRating(),
                        ratingCount.getOrDefault(((Movie) item).getRating(), 0) + 1);
            } else if (item instanceof TVShow) {
                ratingCount.put(((TVShow) item).getRating(),
                        ratingCount.getOrDefault(((TVShow) item).getRating(), 0) + 1);
            }
        }

        return ratingCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No ratings found");
    }

    public Movie findShortestMovie() {
        Movie shortest = null;
        int minDuration = Integer.MAX_VALUE;

        for (Media item : products) {
            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                if (movie.getDuration() < minDuration) {
                    minDuration = movie.getDuration();
                    shortest = movie;
                }
            }
        }
        return shortest;
    }

    public MusicAlbum findShortestMusicAlbum() {
        MusicAlbum shortest = null;
        double minDuration = Double.MAX_VALUE;

        for (Media item : products) {
            if (item instanceof MusicAlbum) {
                MusicAlbum album = (MusicAlbum) item;
                if (album.getDuration() < minDuration) {
                    minDuration = album.getDuration();
                    shortest = album;
                }
            }
        }
        return shortest;
    }
}


