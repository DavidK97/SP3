import java.io.*;
import java.util.*;


public class StreamingPlatform {
    private User currentUser;
    private TextUI ui;
    private FileIO io;
    private ArrayList<Media> allMedia;
    private ArrayList<User> allUsers;

    public StreamingPlatform() {
        this.ui = new TextUI();
        this.io = new FileIO();
        this.allMedia = new ArrayList<>();
        this.allUsers = new ArrayList<>();
    }

    public void searchCategory() {

    }


    public void searchName() {
        String choice = ui.promptText("Type the name of desired media");
        try {
            for (Media m : allMedia) {
                if (m.getName().toLowerCase().contains(choice.toLowerCase())) {
                    System.out.println(m);
                }
            }
            ui.displayMsg("\n1) Play media\n" +
                           "2) Saved media\n" +
                           "3) Add to watchlist");
/*
            int choice1 = ui.promptNumeric("Please type the number of choice");
            switch(choice1) {

                case 1:
                    currentUser.addPlayedMedia(m);

                case 2:
                    currentUser.addSavedMedia(m);
            }

 */
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("No media found with the name: " + choice);
            searchName();
        }
    }



    public void startMenu() {
        String bold = "\u001B[1m";
        ui.displayMsg("Welcome to the Streaming Platform");
        int choice = ui.promptNumeric("If you want to login with an existing user, press 1. If you want to create a new user, press 2");
        if (choice == 1) {
            if (userLogin() == true) {
                mainMenu();
            } else {
                System.out.println(bold + "Wrong username or password... Please try again!");
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
                "4) See your list of watched movies \n " +
                "5) End the program");

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
            case 5:
                endSession();
                break;
            default:
                System.out.println("Please choose between the options 1-5");
                mainMenu();
        }
    }


    public void registerUser() {
        String bold = "\u001B[1m";
        String userName = ui.promptText("Enter a username: ");
        String password = ui.promptText("Enter a password: ");
        int age = ui.promptNumeric("Enter your age");

        User user = new User(userName, password, age);
        allUsers.add(user);

        List<String> userData = new ArrayList<>();
        userData.add(user.getUserName() + "," + user.getPassword() + "," + user.getAge());

        io.saveUserData(userData, "data/userdata.txt");
        ui.displayMsg("Welcome " + bold + userName + "!");
    }


    public boolean userLogin() {
        String bold = "\u001B[1m";
        String line = "";
        String userName = ui.promptText("Enter your username: ");
        String password = ui.promptText("Enter your password: ");

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("data/userdata.txt"));
            while ((line = bufferedReader.readLine()) != null) {

                String[] values = line.split(",");

                if (values[0].equals(userName) && values[1].equals(password)) {
                    ui.displayMsg("Welcome " + bold + userName+"!");
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
                float rating = Float.parseFloat(values[3].trim().replace(",", "."));

                Media m = new Movie(name, releaseYear, categories, rating);
                allMedia.add(m);
            }
        } catch (IOException e) {
            System.out.println("Problem with reading the file");
        }
    }

    public void setupSeries(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] values = line.trim().split(";");

                String name = values[0].trim();
                int[] releaseYear = new int[2];
                String[] interval = values[1].split("-");
                releaseYear[0] = Integer.parseInt(interval[0]);
                releaseYear[1] = Integer.parseInt(interval[1]);
                Set<String> categories = new HashSet<>(Arrays.asList(values[2]));
                float rating = Float.parseFloat(values[3].trim().replace(",", "."));
                ArrayList <Season>  seasons = new ArrayList<>();
                String[] seasonList = values[4].split(",");
                for (String s : seasonList) {
                    String[] episodeCount = s.split("-");
                    int[] episodeList = new int[Integer.parseInt(episodeCount[1])-1];
                    int i = 0;
                    while (i < Integer.parseInt(episodeCount[1])){
                        episodeList[i-1] = i;
                        i++;
                    }
                    seasons.add(new Season(episodeList));
                }

                Media m = new Series(name, rating, categories, releaseYear, seasons);
                allMedia.add(m);
            }
        } catch (IOException e) {
            System.out.println("Problem with reading the file");
        }
    }


public void displaySavedMedia() {
        try {
            for (Media m : currentUser.savedMedia) {
                System.out.println(m);
            }
        } catch (NullPointerException e) {
            System.out.println("\n---You have no saved media!--- \n ");
            mainMenu();
        }
    }

public void displayPlayedMedia() {
        try {
            for (Media m : currentUser.playedMedia) {
                System.out.println(m);
            }
        } catch (NullPointerException e) {
            System.out.println("\n---You have no watched media!--- \n");
            mainMenu();
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
        System.out.println("Ending program...");
        System.exit(0);
    }

}
