import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Series extends Media {
    private String  releaseYears;
    private ArrayList<Season> seasons;

    Series(String name, String releaseYears, Set<String> categories, float rating, ArrayList<Season> seasons) {
        super(name, rating, categories);
        this.releaseYears = releaseYears;
        this.seasons = seasons;
    }


    public void setReleaseYears(String releaseYears) {
        this.releaseYears = releaseYears;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public String getReleaseYears(){
        return releaseYears;
    }

    public ArrayList<Season> getSeasons(){
        return seasons;
    }



    @Override
    public String toString() {
        return "\nTitle: " + name + ", Year: " + releaseYears + ", Categories: " + categories + ", Rating: " + rating + ", Seasons: " + seasons.size()+"\n";
    }
}