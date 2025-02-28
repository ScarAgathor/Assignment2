import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Assignment2{
	public static void main(String []args) {
		Manager m = new Manager();
		
			String file = "dataset.csv";
			
			m.readFile(file);
			
			System.out.println("There are " + m.totalProducts() + " total items in stock.");
			System.out.println("There are " + m.printTotalMovies() + " movies in stock.");
			System.out.println("There are " + m.printTotalShows() + " shows in stock.");
			System.out.println("There are " + m.printTotalGames() + " games in stock");
			System.out.println("There are " + m.printTotalAlbums() + " albums on stock");
			System.out.println("The oldest product in stock is " + m.findOldestProduct());
			System.out.println("The most popular album in stock is " + m.findMostPopularAlbum());
			System.out.println("The most popular game in stock is " + m.findMostPopularGame());
			System.out.println("The most common age rating among movies and shows is " + m.findMostCommonAgeRating());
			System.out.println("The shortest movie in stock is " + m.findShortestMovie());
			System.out.println("The shortest album in stock is " + m.findShortestAlbum());
		}
}

class Manager {

		static ArrayList<Item> stock = new ArrayList<>();
		
		void readFile(String file) {
		
			try (BufferedReader reader = new BufferedReader(new FileReader(file))){
				String line; //one line of all the data of one specific movie/show/game/album
				while((line = reader.readLine()) != null) {	
					determineType(line);
				}
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		
		void createNewMovie(String[] itemInfo) {
			
			int id = Integer.parseInt(itemInfo[0]);
			int release_year = Integer.parseInt(itemInfo[5]);
			int duration = Integer.parseInt(itemInfo[7]);
			
			Item movie = new Movie(id, itemInfo[2], itemInfo[3], itemInfo[4], release_year,itemInfo[6], duration, itemInfo[8]);
			stock.add(movie);
			
		}
		
		void createNewShow(String[] itemInfo) {
			
			int id = Integer.parseInt(itemInfo[0]);
			int release_year = Integer.parseInt(itemInfo[5]);
			
			Item show = new Show(id, itemInfo[2], itemInfo[3], itemInfo[4], release_year,itemInfo[6], itemInfo[7], itemInfo[8]);
			stock.add(show);
			
		}
		
		void createNewGame(String[] itemInfo) {
			
			int id = Integer.parseInt(itemInfo[0]);
			int release_year = Integer.parseInt(itemInfo[4]);
			double copies_sold = Double.parseDouble(itemInfo[7]);
			
			Item game = new Game(id, itemInfo[2], itemInfo[3], release_year, itemInfo[5], itemInfo[6], copies_sold);
			stock.add(game);
			
		}

		void createNewAlbum(String[] itemInfo) {
			
			int id = Integer.parseInt(itemInfo[0]);
			int release_year = Integer.parseInt(itemInfo[2]);
			int global_sales = Integer.parseInt(itemInfo[5]);
			int tracks = Integer.parseInt(itemInfo[6]);
			double duration = Double.parseDouble(itemInfo[7]);
			
			Item album = new Album(id, release_year, itemInfo[3], itemInfo[4], global_sales, tracks, duration, itemInfo[8]);
			stock.add(album);
			
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
		
		public int totalProducts() {
			return stock.size();
		}
		
		public int printTotalMovies() {
			int totalMovies = 0;
			for(int i = 0; i < stock.size(); i ++) {
				if(stock.get(i) instanceof Movie) {
					totalMovies++;
				}
			}
			return totalMovies;
		}
		
		public int printTotalShows() {
			
			int totalShows = 0;
			for(int i = 0; i < stock.size(); i ++) {
				if(stock.get(i) instanceof Show) {
					totalShows++;
				}
			}
			
			return totalShows;
		}
		
		public int printTotalGames() {
			
			int totalGames = 0;
			for(int i = 0; i < stock.size(); i ++) {
				if(stock.get(i) instanceof Game) {
					totalGames++;
				}
			}
			return totalGames;
		}
		
		public int printTotalAlbums() {
			
			int totalAlbums = 0;
			for(int i = 0; i < stock.size(); i ++) {
				if(stock.get(i) instanceof Album) {
					totalAlbums++;
				}
			}
			return totalAlbums;
		}
		
		
		public String findOldestProduct() {
		
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
			return oldestTitle;
		}
		
		public String findMostPopularAlbum() {
		
			int global_sales = 0;
			int mostSales = 0;
			String mostPopularAlbum = "";
			
			
			for(int i = 0; i< stock.size();i++) {
				if(stock.get(i) instanceof Album) { //checks for instances of the album class
					Album album = (Album) stock.get(i); //if the object at the current index is an album, a new album object is created by type casting the corresponding object in the stock array list  
					global_sales = album.getGlobalSales();
					if(global_sales > mostSales) {  //compares the global sales of the albums
						mostSales = global_sales;
						mostPopularAlbum = album.getTitle();
						
					}	
				}
			}
			return mostPopularAlbum;
		}
		
		public String findMostPopularGame(){
			double copiesSold = 0;
			double most_CopiesSold = 0;
			String mostPopularGame = "";

			for (int i = 0; i < stock.size(); i++){
				if(stock.get(i) instanceof Game) {
					Game game = (Game) stock.get(i);
					copiesSold = game.getCopiesSold();
					if ( copiesSold > most_CopiesSold ) {
						most_CopiesSold = copiesSold;
						mostPopularGame = stock.get(i).getTitle();
				}
				}
			}
			return mostPopularGame;
		}
		
		public String findMostCommonAgeRating() {
			
			String mostCommonRating = "";
			String currentRating = "";
			int tvMA = 0;
			int pg13 = 0;
			int tv14 = 0;
			int tvY7 = 0;
			
			for(int i = 0; i < stock.size(); i++) {
				if(stock.get(i) instanceof Movie) {
					Movie movie = (Movie) stock.get(i);
					currentRating = movie.getMovieRating();
					
				}
				
				if(stock.get(i) instanceof Show) {
					Show show = (Show) stock.get(i);
					currentRating = show.getShowRating();
				}
				
				if(currentRating.equalsIgnoreCase("TV-MA")) {
					tvMA++;
				}
				if(currentRating.equalsIgnoreCase("TV-14")) {
					tv14++;
				}
				if(currentRating.equalsIgnoreCase("TV-Y7")) {
					tvY7++;
				}
				if(currentRating.equalsIgnoreCase("TV-14")) {
					pg13++;
				}
				
			}
			if(tvMA > tv14 && tvMA > pg13 && tvMA > tvY7) {
				mostCommonRating = "TV-MA";
			}
			if(tv14 > tvMA && tv14 > pg13 && tv14 > tvY7) {
				mostCommonRating = "TV-14";
			}
			if(pg13 > tv14 && pg13 > tvMA && pg13 > tvY7) {
				mostCommonRating = "PG-13";
			}
			if(tvY7 > tv14 && tvY7 > pg13 && tvY7 > tvMA) {
				mostCommonRating = "TV-Y7";
			}
			return mostCommonRating;
		}
		
	
		public String findShortestMovie() {
			String shortestMovie = "";
			int currentDuration = 0;
			int shortestDuration = 0;
			
			//checks for first instance of a movie in the array list
			for(int i = 0; i < stock.size(); i++) {
				if(stock.get(i) instanceof Movie) {
					Movie movie = (Movie) stock.get(i);
					shortestMovie = movie.getTitle();
					currentDuration = movie.getDuration();
					shortestDuration = movie.getDuration();
					break;
				}
				else {
					continue;
				}
			}
			
			for(int i = 0; i < stock.size(); i++) {
				if(stock.get(i) instanceof Movie) {
					Movie movie = (Movie) stock.get(i);
					currentDuration = movie.getDuration();
					
					if(currentDuration < shortestDuration) {
						shortestDuration = currentDuration;
						shortestMovie = movie.getTitle();
					}
					
				}
			}
			
			return shortestMovie;
		}
		
		public String findShortestAlbum() {
			
			String shortestAlbum = "";
			double currentDuration = 0;
			double shortestDuration = 0;
			
			//find first instance of an album in the arrayList
			for(int i = 0; i < stock.size(); i++) {
				if(stock.get(i) instanceof Album) {
					Album album = (Album) stock.get(i);
					shortestAlbum = album.getTitle();
					currentDuration = album.getDuration();
					shortestDuration = album.getDuration();
					break;
				}
				else {
					continue;
				}
			}
			
			
			for(int j = 0; j < stock.size(); j++) {
				if(stock.get(j) instanceof Album) {
					Album album = (Album) stock.get(j);
					currentDuration = album.getDuration();
					if(currentDuration < shortestDuration) {
						shortestDuration = currentDuration;
						shortestAlbum = album.getTitle();
					}
				}
				
			}
			
			return shortestAlbum;
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
