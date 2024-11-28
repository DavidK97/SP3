import java.io.*;
import java.util.*;


public class StreamingPlatform {
    private User currentUser;
    private TextUI ui;
    private FileIO io;
    private ArrayList<Media> allMedia;
    private ArrayList<User> allUsers;
    ArrayList <Media> searchMedia;

    public StreamingPlatform() {
        this.ui = new TextUI();
        this.io = new FileIO();
        this.allMedia = new ArrayList<>();
        this.allUsers = new ArrayList<>();
        this.searchMedia = new ArrayList<>();

    }

    public void searchCategory() {
        String choice = ui.promptText("What is your desired category?");
        try {
            boolean found = false;
            for (Media m : allMedia) {
                if (m.getCategories().contains(" " + choice)) {
                    System.out.print(m);
                    found = true;
                }
            }
            if (!found) {
                System.out.println(choice + " is not a valid category");
            }
            System.out.println();
            mainMenu();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println(choice + " is not a valid category");
            searchCategory();
        }
    }


    public void searchName() {
        String choice = ui.promptText("Type the name of desired media");
        try {
            for (Media m : allMedia) {
                if (m.getName().toLowerCase().contains(choice.toLowerCase())) {
                    System.out.println(m);

                    ui.displayMsg("Please choose an action");  //Bør måske være en metode for sig selv så vi kan bruge de options andre steder
                    int choice1 = ui.promptNumeric(
                                "1) Play media \n" +
                                    "2) Add to watchlist \n" +
                                    "3) Return to main menu");
                    switch (choice1) {
                        case 1:
                            if (m instanceof Movie) {
                                playMovie((Movie) m);
                                break;
                            }else if(m instanceof Series) {
                                int seasonChoice = ui.promptNumeric("Choose the season");
                                int episodeChoice = ui.promptNumeric("Choose the episode");
                                playSeries((Series) m, seasonChoice, episodeChoice);
                                break;
                            }
                            break;
                        case 2:
                            if (m instanceof Movie) {
                                saveMovie((Movie) m);
                                break;
                            }else if(m instanceof Series) {
                                saveSeries((Series) m);
                                break;
                            }
                        case 3:
                            mainMenu();
                            break;
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("No media found with the name: " + choice);
            searchName();
        }
    }



    public void startMenu() {
        String bold = "\u001B[1m";
        ui.displayMsg("\nWelcome to the " + bold + "Streaming Platform");
        int choice = ui.promptNumeric(bold + "\nYou have the following options: \n"+"1) Log in \n"+
                                               "2) Create an account");
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
                "1) Search for a movie or series \n " +
                "2) Search by category \n " +
                "3) See your saved watchlist \n " +
                "4) See your list of watched media \n " +
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
        currentUser = user;
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
                    currentUser = new User(userName, password, Integer.parseInt(values[2]));
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
                String releaseYear = (values[1].trim());
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
                if (values.length < 5) {
                    System.out.println("Skipping line due to insufficient data: " + line);
                    continue; // Skip lines with missing data
                }

                String name = values[0].trim();
                String releaseYear = values[1].trim();

                Set<String> categories = new HashSet<>(Arrays.asList(values[2].split(",")));
                float rating = Float.parseFloat(values[3].trim().replace(",", "."));
                ArrayList<Season> seasons = new ArrayList<>();

                if (!values[4].isEmpty()) {
                    String[] seasonList = values[4].split(",");
                    for (String s : seasonList) {
                        String[] episodeCount = s.split("-");
                        if (episodeCount.length < 2) {
                            System.out.println("Skipping invalid season data: " + s);
                            continue;
                        }

                        int episodeCountNum = Integer.parseInt(episodeCount[1]);
                        int[] episodeList = new int[episodeCountNum];
                        for (int i = 0; i < episodeCountNum; i++) {
                            episodeList[i] = i + 1;
                        }
                        seasons.add(new Season(episodeList));
                    }
                }

                Media m = new Series(name, releaseYear, categories, rating, seasons);
                allMedia.add(m);
            }
        } catch (IOException e) {
            System.out.println("Problem with reading the file");
        } catch (NumberFormatException e) {
            System.out.println("Problem with number parsing: " + e.getMessage());
        }
    }


public void displaySavedMedia() {

        try {
            if (currentUser.savedMedia == null || currentUser.savedMedia.isEmpty()) {
                System.out.println("\n---You have no saved media!--- \n");
                mainMenu();
            } else {
                for (Media m : currentUser.savedMedia) {
                    System.out.println(m);
                }
                mainMenu();
            }
          /*  int choice = ui.promptNumeric("1) Play media \n" +
                    "2) Remove from watchlist \n" +
                    "3) Return to main menu");

            switch (choice){
                case 1:
                    if (m instanceof Movie) {
                        playMovie((Movie) m);
                        break;
                    }else if(m instanceof Series) {
                        int seasonChoice = ui.promptNumeric("Choose the season");
                        int episodeChoice = ui.promptNumeric("Choose the episode");
                        playSeries((Series) m, seasonChoice, episodeChoice);
                        break;
                    };
                case 2:
                    removeMedia();

                }
           */

        mainMenu();
        } catch (NullPointerException e) {
            System.out.println("\n---Error: User or saved media is null!--- \n");
        }


    }

public void displayPlayedMedia() {
    try {
        if (currentUser.playedMedia == null || currentUser.playedMedia.isEmpty()) {
            System.out.println("\n---You have no watched media!--- \n");
            mainMenu();
        } else {
            for (Media m : currentUser.playedMedia) {
                System.out.println(m);
            }
            mainMenu();
        }
    } catch (NullPointerException e) {
        System.out.println("\n---Error: User or watched media is null!--- \n");
    }
}


public void playMovie(Movie movie){
        currentUser.getPlayedMedia().add(movie);
        ui.displayMsg("The movie " + movie.getName() + " is now playing");
        mainMenu();
    }

public void playSeries(Series series, int season, int episode){
        currentUser.getPlayedMedia().add(series);
        ui.displayMsg("Episode " + episode + " from Season " + season + " of " + series.getName() + " is now playing");
        mainMenu();
    }

public void saveMovie(Movie movie){
        currentUser.getSavedMedia().add(movie);
        ui.displayMsg(movie.getName() + " has been saved!");
        mainMenu();
    }

    public void saveSeries(Series series){
        currentUser.getSavedMedia().add(series);
        ui.displayMsg(series.getName()+ " has been saved!");
        mainMenu();
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
