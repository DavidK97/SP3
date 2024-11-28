import java.util.Set;

public class Movie extends Media {

    // Constructor
    Movie(String name, String releaseYear, Set<String> categories, float rating) {
        super(name, releaseYear, rating, categories);
    }


    // toString method
    @Override
    public String toString() {
        return "\nTitle: " + name + ", Year: " + releaseYear + ", Categories: " + categories + ", Rating: " + rating+"\n";
    }
}
