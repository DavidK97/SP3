package src;

import java.util.Set;

public abstract class Media {
    private String name;
    private float rating;
    private Set<String> categories;

    Media(String name, float rating, Set<String> categories) {
        this.name = name;
        this.rating = rating;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public Set<String> getCategories() {
        return categories;
    }
}