
import java.io.File;

public class Main {

    public static void main(String[] args) {
        File movieFile = new File("data/film.txt");
        File seriesFile = new File("data/serier.txt");
        StreamingPlatform streamingPlatform = new StreamingPlatform();
        streamingPlatform.setupMovies(movieFile);
        streamingPlatform.setupSeries(seriesFile);
        streamingPlatform.startMenu();
    }
}
