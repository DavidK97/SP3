package src;
import java.util.Set;

public class Movie extends Media {
    private int releaseYear;

    // Constructor
    Movie(String name, int releaseYear, Set<String> categories, float rating) {
        super(name, rating, categories);
        this.releaseYear = releaseYear;
    }

    // Getter for releaseYear
    public int getReleaseYear() {
        return releaseYear;
    }

    // toString method
    @Override
    public String toString() {
        return "Title: " + name + ", Year: " + releaseYear + ", Categories: " + categories + ", Rating: " + rating;
    }
}
