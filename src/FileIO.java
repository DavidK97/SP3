import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class FileIO {

    // Scanner scanner
    // FileWriter fileWriter
    // ArrayList<String> readData (path)
    public static ArrayList<String> readUserData (String path){

        ArrayList<String> userData = new ArrayList<>();
        File file = new File(path);

        try{

            Scanner scanner = new Scanner(file);
            scanner.nextLine();

            while(scanner.hasNextLine()){

                String line = scanner.nextLine();
                userData.add(line);

            }

        } catch (FileNotFoundException e){

            System.out.println("File was not found...");
            
        }

        return userData;

    }

    // void saveData (ArrayList<String> userData, String path)

    public static void saveUserData(List<String> data, String path, String header){

        try{

            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(header + "\n");

            for (String s : data){

                fileWriter.write(s + "\n");

            }

            fileWriter.close();

        }catch(IOException e){

            System.out.println("Something went wrong during the saving process...");

        }
    }
}
