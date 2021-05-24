import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
    public static void write(String text, String fileName) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("src/main/textFiles/" + fileName, false);
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
