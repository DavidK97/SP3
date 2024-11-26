import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class FileIO {

    public static ArrayList<String> readUserData(String path) {

        ArrayList<String> userData = new ArrayList<>();
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                userData.add(line);

            }

        } catch (FileNotFoundException e) {

            System.out.println("File was not found...");

        }

        return userData;

    }

    public static void saveUserData(List<String> data, String path) {
        try {
            // Brug FileWriter til at skrive dataen til filen
            FileWriter fileWriter = new FileWriter(path, true); // true for at tilføje i stedet for at overskrive

            // Skriv dataen linje for linje
            for (String s : data) {
                fileWriter.write(s + "\n");
            }

            fileWriter.close(); // Luk fileWriter
        } catch (IOException e) {
            System.out.println("Something went wrong during the saving process...");
            e.printStackTrace(); // For at få mere detaljerede fejlmeddelelser
        }
    }
}