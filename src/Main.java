package src;
public class Main {

    public static void main(String[] args) {
       // File file = new File("data/film.txt");
        StreamingPlatform streamingPlatform = new StreamingPlatform();
       // streamingPlatform.setupMovies(file);
       streamingPlatform.startMenu();
        streamingPlatform.mainMenu();
       // streamingPlatform.searchName();
        streamingPlatform.registerUser();

    }

}
