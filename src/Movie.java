import java.util.Set;

public class Movie extends Media {
    private String releaseYear;

    // Constructor
    Movie(String name, String releaseYear, Set<String> categories, float rating) {
        super(name, rating, categories);
        this.releaseYear = releaseYear;
    }

    // Getter for releaseYear
    public String getReleaseYear() {
        return releaseYear;
    }

    // toString method
    @Override
    public String toString() {
        return "\nTitle: " + name + ", Year: " + releaseYear + ", Categories: " + categories + ", Rating: " + rating+"\n";
    }
}
