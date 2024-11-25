package src;

import java.util.Set;

public class Movie extends Media {
    private int releaseYear;

    Movie(String name, float rating, Set<String> categories, int releaseYear) {
        super(name, rating, categories);
        this.releaseYear = releaseYear;
    }

    public int getReleaseYear() {
        return releaseYear;
    }
}
