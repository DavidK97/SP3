package src;


import java.util.ArrayList;

public class User {
    private String userName;
    private String password;
    private int age;
    ArrayList <Media> savedMedia;
    ArrayList <Media> playedMedia;

    public User(String userName, String password, int age) {
        this.age = age;
        this.userName = userName;
        this.password = password;

        this.savedMedia = new ArrayList <> ();
        this.playedMedia = new ArrayList <> ();

    }

    public void addSavedMedia(Media media) {
        this.savedMedia.add(media);
    }



    public void addPlayedMedia(Media media){

        this.playedMedia.add(media);


    }

    public void removeSavedMedia(Media media){
        this.savedMedia.remove(media);
    }




    // Getters and Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<Media> getSavedMedia() {
        return savedMedia;
    }

    public void setSavedMedia(ArrayList<Media> savedMedia) {
        this.savedMedia = savedMedia;
    }

    public ArrayList<Media> getPlayedMedia() {
        return playedMedia;
    }

    public void setPlayedMedia(ArrayList<Media> playedMedia) {
        this.playedMedia = playedMedia;
    }
}
