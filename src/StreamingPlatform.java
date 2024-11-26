package src;
import java.io.*;
import java.util.*;


public class StreamingPlatform {
    private User currentUser;
    private TextUI ui;
    private FileIO io;
    private ArrayList <Media> allMedia;
    private ArrayList <User> allUsers;

    public StreamingPlatform(){
        this.ui = new TextUI();
        this.io = new FileIO();
        this.allMedia = new ArrayList<>();
        this.allUsers = new ArrayList<>();
    }

    public void searchCategory(){

        String series = "data/serier.txt";
        String movies = "data/film.txt";
        String line = "";
        String bold = "\u001B[1m";
        String reset = "\u001B[0m";
        String desiredMedia = ui.promptText("Enter your desired media (series/movies): ");
        String desiredGenre = ui.promptText("Enter your desired genre: ");

        if(desiredMedia.equalsIgnoreCase("series")){

            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(series));
                while ((line = bufferedReader.readLine()) != null) {

                    String[] values = line.split(";");
                    if (values.length < 2 || values[1].isEmpty()) {
                        continue;
                    }
                    if (values[2].contains(desiredGenre)) {
                        System.out.println(bold + "Series: " + values[0] + reset + "\n Year: " + values[1] + "\n Genre: " + values[2] + "\n Rating: " + values[3] + "\n Season + episodes: "+ values[4]);
                    }
                }
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else if(desiredMedia.equalsIgnoreCase("movies")){

            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(movies));
                while ((line = bufferedReader.readLine()) != null) {

                    String[] values = line.split(";");
                    if (values.length < 2 || values[1].isEmpty()) {
                        continue;
                    }
                    if (values[2].contains(desiredGenre)) {
                        System.out.println(bold + "Movie: " + values[0] + reset + "\n Year: " + values[1] + "\n Genre: " + values[2] + "\n Rating: " + values[3]);
                    }
                }
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public void searchName(){

    String series = "data/serier.txt";
    String movies = "data/film.txt";
    String line = "";
    String bold = "\u001B[1m";
    String reset = "\u001B[0m";
    String desiredMedia = ui.promptText("Enter your desired media (series/movies): ");
    String desiredName = ui.promptText("Search for a specific name: ");

    if(desiredMedia.equalsIgnoreCase("series")){

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(series));
            while ((line = bufferedReader.readLine()) != null) {

                String[] values = line.split(";");
                if (values.length < 2 || values[1].isEmpty()) {
                    continue;
                }
                if (values[0].contains(desiredName)) {
                    System.out.println(bold + "Series: " + values[0] + reset + "\n Year: " + values[1] + "\n Genre: " + values[2] + "\n Rating: " + values[3] + "\n Season + episodes: "+ values[4]);
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }else if(desiredMedia.equalsIgnoreCase("movies")){

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(movies));
            while ((line = bufferedReader.readLine()) != null) {

                String[] values = line.split(";");
                if (values.length < 2 || values[1].isEmpty()) {
                    continue;
                }
                if (values[0].contains(desiredName)) {
                    System.out.println(bold + "Movie: " + values[0] + reset + "\n Year: " + values[1] + "\n Genre: " + values[2] + "\n Rating: " + values[3]);
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}



    public void startMenu() {
        ui.displayMsg("Welcome to the StreamingPlatform");
        int choice = ui.promptNumeric("If you want to login with an existing user, press 1. If you want to create a new user, press 2");
        if (choice == 1) {
            if (userLogin() == true) {
                mainMenu();
            } else {
                System.out.println("Wrong username or password");
                startMenu();
            }
        } else if (choice == 2) {
            registerUser();
            mainMenu();
        }
    }

    public void mainMenu() {
        String bold = "\u001B[1m";
        int choice = ui.promptNumeric(bold + "You have the following options: \n " +
                "1) Search for a movie or serie \n " +
                "2) Search by category \n " +
                "3) See your saved wacthlist \n " +
                "4) See your list of watched movies ");


        switch (choice) {
            case 1:
                searchName();
                break;
            case 2:
                searchCategory();
                break;
            case 3:
                displaySavedMedia();
                break;
            case 4:
                displayPlayedMedia();
                break;
            default:
                System.out.println("Please choose between the options 1-4");
                mainMenu();
        }
    }


    public void registerUser() {
        String userName = ui.promptText("Enter a username: ");
        String password = ui.promptText("Enter a password: ");
        int age = ui.promptNumeric("Enter your age");

        User user = new User(userName, password, age);
        allUsers.add(user);

        List<String> userData = new ArrayList<>();
        userData.add(user.getUserName() + "," + user.getPassword() + "," + user.getAge());

        io.saveUserData(userData, "data/userdata.txt");
        ui.displayMsg("Welcome " + userName+"!");
    }


public boolean userLogin() {
        String line = "";
        String userName = ui.promptText("Enter your username: ");
        String password = ui.promptText("Enter your password: ");

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("data/userdata.txt"));
            while ((line = bufferedReader.readLine()) != null) {

                String[] values = line.split(",");

                if (values[0].equals(userName) && values[1].equals(password)) {
                    ui.displayMsg("Welcome " + userName);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
    }



public void setupMovies(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] values = line.trim().split(";");

                String name = values[0].trim();
                int releaseYear = Integer.parseInt(values[1].trim());
                Set<String> categories = new HashSet<>(Arrays.asList(values[2]));
                float rating = Float.parseFloat(values[3].trim().replace(",","."));

                Media m = new Movie(name, releaseYear, categories, rating);
                allMedia.add(m);
            }
        } catch (IOException e) {
            System.out.println("Problem with reading the file");
        }
    }

public void setupSeries(File file){

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] values = line.trim().split(";");

                String name = values[0].trim();
                int [] releaseYear = new int []{Integer.parseInt(values[1].trim())};
                Set<String> categories = new HashSet<>(Arrays.asList(values[2]));
                float rating = Float.parseFloat(values[3].trim().replace(",","."));
                //  ArrayList <Season>  seasons = new ArrayList<>();

                //Media m = new Series(name, releaseYear, categories, rating, seasons);
                //allMedia.add(m);
            }
        } catch (IOException e) {
            System.out.println("Problem with reading the file");
        }
    }


public void displaySavedMedia(){

for(Media m : currentUser.savedMedia){
    System.out.println(m);
}

}

public void displayPlayedMedia(){

    for(Media m : currentUser.playedMedia){
        System.out.println(m);
    }

}


public void playMovie(Movie movie){
        currentUser.getPlayedMedia().add(movie);
        ui.displayMsg("The movie " + movie.getName() + " is now playing");
    }

public void playSeries(Series series, int season, int episode){
        currentUser.getPlayedMedia().add(series);
        ui.displayMsg("Episode " + episode + " from Season " + season + "of" + series.getName() + "is now playing");
    }

public void saveMedia(Media media){
        currentUser.getSavedMedia().add(media);
        ui.displayMsg(media.getName() + " has been saved");
    }

public void removeMedia(Media media){
        for (Media m : currentUser.getSavedMedia()) {
            if (m.getName().equals(media.getName())) {
                currentUser.getSavedMedia().remove(m);
                ui.displayMsg(media.getName() + " has been removed");
                break;
            }
        }
    }


public void endSession(){


    }

}
