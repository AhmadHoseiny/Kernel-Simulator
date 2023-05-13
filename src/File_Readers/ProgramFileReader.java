package File_Readers;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProgramFileReader {
    public static ArrayList<String> readProgramFile(String fileName) throws FileNotFoundException {
        ArrayList<String> res = new ArrayList<>();
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            res.add(line);
        }
        return res;
    }
}
