import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String inputFileName = "data1.csv";
        String outputFileName = "data.json";
        ClassLoader classLoader = Main.class.getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource(inputFileName)).getFile());
        Transformer transformer = new Transformer();
        List<Employee> staff = transformer.parseCSV(columnMapping, inputFile);
        String json = transformer.listToJson(staff);

        try (FileWriter file = new FileWriter(outputFileName)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
