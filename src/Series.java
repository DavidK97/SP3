package src;
import java.util.ArrayList;
import java.util.Set;

public class Series extends Media {
    int[] releaseYears;
    ArrayList<Season> seasons;

    Series(String name, float rating, Set<String> categories, int[] releaseYears, ArrayList<Season> seasons) {
        super(name, rating, categories);
        this.releaseYears = releaseYears;
        this.seasons = seasons;
    }

    public int[] getReleaseYears(){
        return releaseYears;
    }

    public ArrayList<Season> getSeasons(){
        return seasons;
    }
}
