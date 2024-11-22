package src;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class StreamingPlatform {
    private User currentUser;
    private TextUI ui;
    private FileIO io;
    private HashMap<String, String> loginDatabase;
    private ArrayList <Media> allMedia;
    private ArrayList <User> allUsers;

    public StreamingPlatform(){
        this.ui = new TextUI();
        this.io = new FileIO();
        this.loginDatabase = new HashMap<>();


    }

    public void searchCategory(){

        String series = "serier.txt";
        String movies = "film.txt";
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
                        System.out.println(bold + "Series: " + values[0] + reset + ".\n Year: " + values[1] + ".\n Genre: " + values[2] + ".\n Rating: " + values[3] + ".\n Season + episodes: "+ values[4]+".");
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
                        System.out.println(bold + "Movie: " + values[0] + reset + ".\n Year: " + values[1] + ".\n Genre: " + values[2] + ".\n Rating: " + values[3] + ".");
                    }
                }
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public void searchName(String name){

    String series = "serier.txt";
    String movies = "film.txt";
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
                    System.out.println(bold + "Series: " + values[0] + reset + ".\n Year: " + values[1] + ".\n Genre: " + values[2] + ".\n Rating: " + values[3] + ".\n Season + episodes: "+ values[4]+".");
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
                    System.out.println(bold + "Movie: " + values[0] + reset + ".\n Year: " + values[1] + ".\n Genre: " + values[2] + ".\n Rating: " + values[3] + ".");
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}



public void startMenu(){
    ui.displayMsg("Welcome to the StreamingPlatform");
    int choice = ui.promptNumeric("If you want to login with an existing user, press 1. If you want to create a new user, press 2");
    if (choice == 1) {
        //userLogin();
    } else if (choice == 2){
        registerUser();
    }
}



public void registerUser(){
    String userName = ui.promptText("Enter a username: ");
    String password = ui.promptText("Enter a password: ");
    int age = ui.promptNumeric("Enter your age");

    User user = new User(userName, password, age);
    allUsers.add(user);
   // logingDatabase.put(userName, password);

}

public void userLogin(String userName, String password){


}

public void setupMedia(){



}

public void displaySavedMedia(){



}

public void displayPlayedMedia(){



}

public void playMedia(Media media){



}

public void saveMedia(Media media){



}

public void removeMedia(Media media){



}

public void endSession(){



}

}
