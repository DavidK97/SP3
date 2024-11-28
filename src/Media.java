import java.util.Set;

public abstract class Media {
    protected String name;
    protected String releaseYear;
    protected float rating;
    protected Set<String> categories;

    Media(String name, String releaseYear, float rating, Set<String> categories) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.categories = categories;
    }


    public String getName() {
        return name;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public float getRating() {
        return rating;
    }

    public Set<String> getCategories() {
        return categories;
    }
}