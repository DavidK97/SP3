import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Series extends Media {
    private ArrayList<Season> seasons;

    Series(String name, String releaseYear, Set<String> categories, float rating, ArrayList<Season> seasons) {
        super(name, releaseYear, rating, categories);
        this.seasons = seasons;
    }


    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }


    public ArrayList<Season> getSeasons(){
        return seasons;
    }


    @Override
    public String toString() {
        return "\nTitle: " + name + ", Year: " + releaseYear + ", Categories: " + categories + ", Rating: " + rating + ", Seasons: " + seasons.size()+"\n";
    }
}