import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Assignment2 {

public static void main(String []args) {
	
	int choice;
	Driver d = new Driver();
	Scanner s = new Scanner(System.in);
	
		//fix this to allow all .csv files as opposed to just this one
		String file = "dataset.csv";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String line; //one line of all the data of one specific movie/show/game/album
			while((line = reader.readLine()) != null) {	
				d.determineType(line);
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("What would you like to do?");
		System.out.println("1 - Count the total number of products");
		System.out.println("2 - Count the total number of Movies");
		System.out.println("3 - Count the total number of TV Shows");
		System.out.println("4 - Count the total number of Video Games");
		System.out.println("5 - Count the total number of Music ALbums");
		System.out.println("6 - Find the oldest product(by release year)");
		
		choice = s.nextInt();
		
		if (choice == 1) {
			d.totalProducts();
		}
		if (choice == 2) {
			d.printTotalMovies();
		}
		if (choice == 3) {
			d.printTotalShows();
		}
		if(choice == 4) {
			d.printTotalGames();
		}
		if(choice == 5) {
			d.printTotalAlbums();
		}
		if(choice == 6) {
			d.findOldestProduct();
		}
	}
	
}





class Driver {
	
	//array lists for each type of item 
	
	
	static ArrayList<Item> stock = new ArrayList<>();
	private static int totalMovies = 0;
	private static int totalGames = 0;
	private static int totalShows = 0;
	private static int totalAlbums = 0;
	
	public static void createNewMovie(String[] itemInfo) {
		
		int id = Integer.parseInt(itemInfo[0]);
		int release_year = Integer.parseInt(itemInfo[5]);
		int duration = Integer.parseInt(itemInfo[7]);
		
		Item movie = new Movie(id, itemInfo[2], itemInfo[3], itemInfo[4], release_year,itemInfo[6], duration, itemInfo[8]);
		stock.add(movie);
		totalMovies++;
		//stock.add(movie);
		
		
	}
	
	public static void createNewShow(String[] itemInfo) {
		
		int id = Integer.parseInt(itemInfo[0]);
		int release_year = Integer.parseInt(itemInfo[5]);
		
		Item show = new Show(id, itemInfo[2], itemInfo[3], itemInfo[4], release_year,itemInfo[6], itemInfo[7], itemInfo[8]);
		stock.add(show);
		totalShows++;
		
		
		
	}
	
	public static void createNewGame(String[] itemInfo) {
		
		int id = Integer.parseInt(itemInfo[0]);
		int release_year = Integer.parseInt(itemInfo[4]);
		double copies_sold = Double.parseDouble(itemInfo[7]);
		
		Item game = new Game(id, itemInfo[2], itemInfo[3], release_year, itemInfo[5], itemInfo[6], copies_sold);
		stock.add(game);
		totalGames++;
		
		
	}

	public static void createNewAlbum(String[] itemInfo) {
		
		int id = Integer.parseInt(itemInfo[0]);
		int release_year = Integer.parseInt(itemInfo[2]);
		int global_sales = Integer.parseInt(itemInfo[5]);
		int tracks = Integer.parseInt(itemInfo[6]);
		double duration = Double.parseDouble(itemInfo[7]);
		
		Item album = new Album(id, release_year, itemInfo[3], itemInfo[4], global_sales, tracks, duration, itemInfo[8]);
		stock.add(album);
		totalAlbums++;
		
		
		
	}

	public void determineType(String line){
		
		String[] itemInfo = line.split(","); //creates an array for each item in the list and splits each attribute into its own index
		String type = "";
		
		
		/*if the length of the data array is greater than one, the program is ran
		 *if this is not present there will be an error as for some reason an array of length 1
		 *is always created on the final iteration and item[1] does not exist and therefore cannot be accessed
		 *when it works, the program compares the type(movie, show, game, album) and calls the 
		 *appropriate class to create a new object of the specified type
		 */
		
		if(itemInfo.length>1) {
		type = itemInfo[1];
		
		
			if(type.equalsIgnoreCase("Movie")) {
				createNewMovie(itemInfo);	
			}
		
			if(type.equalsIgnoreCase("TV Show")) {
				createNewShow(itemInfo);
			}
		
			if(type.equalsIgnoreCase("Video Game")) {
				createNewGame(itemInfo);
			}
		
			if(type.equalsIgnoreCase("Music Album")) {
				createNewAlbum(itemInfo);
			}	
		}
	}
	
	public void totalProducts() {
		
		System.out.println("there are " + stock.size() + " products in stock");
	}
	
	public void printTotalMovies() {
		System.out.println("There are " + totalMovies + " movies in stock");	
	}
	
	public void printTotalShows() {
		System.out.println("There are " + totalShows + " shows in stock");
	}
	
	public void printTotalGames() {
		System.out.println("There are " + totalGames + " games in stock");
	}
	
	public void printTotalAlbums() {
		System.out.println("There are " + totalAlbums + " albums in stock");
	}
	
	
	public void findOldestProduct() {
	
		int release_year = 0;
		int oldest_year = stock.get(0).getReleaseYear();
		String oldestTitle = ""; 
		
		//compares release year of every item to find the oldest
		for(int i = 0; i < stock.size(); i++) {
			release_year = stock.get(i).getReleaseYear();
			
			if (release_year<oldest_year){
				oldest_year = release_year;
				oldestTitle = stock.get(i).getTitle();
			} 
		}
		
		System.out.println("The oldest product is " + oldestTitle + ", released in the year " + oldest_year);
	}
	
	public void findMostPopularAlbum() {
	
		
		
	}
}

class Item{
	
	private int id;
	private int release_year;
	private String title;
	
	public Item (int id, int release_year, String title) {
		this.id = id;
		this.release_year = release_year;
		this.title = title;
	}
	
	int getReleaseYear() {
		return release_year;
	}
	
	String getTitle() {
		return title;
	}

	int getID() {
		return id;
	}
	
}
	
	class Movie extends Item{
		private String director;
		private String country;
		private String rating;
		private int duration; //minutes
		private String description;
		
		Movie(int id, String title, String director,String country, int release_year, String rating, int duration, String description){
			super(id,release_year,title);
			this.director = director;
			this.country = country;
			this.rating = rating;
			this.duration = duration;
			this.description = description;	
		}
		
		String getMovieDirector() {
			return director;
		}
		
		String getMovieCountry() {
			return country;
		}
		
		String getMovieRating() {
			return rating;
		}
		
		int getDuration() {
			return duration;
		}
		
		String getMovieDescription() {
			return description;
		}
	}
	
	class Show extends Item{
		private String director;
		private String country;
		private String rating;
		private String number_of_seasons; 
		private String description;
		
		Show(int id, String title, String director,String country, int release_year, String rating, String number_of_seasons, String description){
			super(id,release_year,title);
			this.director = director;
			this.country = country;
			this.rating = rating;
			this.number_of_seasons = number_of_seasons;
			this.description = description;	
		}
		
		String getShowDirector() {
			return director;
		}
		
		String getShowCountry() {
			return country;
		}
		
		String getShowRating() {
			return rating;
		}
		
		String getNumberOfSeasons() {
			return number_of_seasons;
		}
		
		String getShowDescription() {
			return description;
		}
		
	}
	
	class Album extends Item{
		private String artist;
		private int global_sales;
		private int tracks;
		private double duration; //minutes
		private String genre;
		
		Album(int id, int release_year, String artist, String title, int global_sales, int tracks, double duration, String genre){
			super(id,release_year,title);
			this.artist = artist;
			this.global_sales = global_sales;
			this.tracks = tracks;
			this.duration = duration;
			this.genre = genre;
		}
		
		String getArtist() {
			return artist;
		}
		int getGlobalSales() {
			return global_sales;
		}
		int getTracks() {
			return tracks;
		}
		double getDuration(){
			return duration;
		}
		String getGenre() {
			return genre;
		}
	}
	
	class Game extends Item{
		private String platform;
		private String genre;
		private String publisher; 
		private double copies_sold; //millions
		
		Game(int id, String title, String platform, int release_year, String genre, String publisher, double copies_sold){
			super(id,release_year,title);
			this.platform = platform;
			this.genre = genre;
			this.publisher = publisher;
			this.copies_sold = copies_sold;	
		}
		
		String getPlatform() {
			return platform;
		}
		
		String getGenre() {
			return genre;	
		}
		
		String getPublisher() {
			return publisher;
		}
		
		double getCopiesSold() {
			return copies_sold;
		}
	}

	
