import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransformerTest {
    Transformer transformer;

    @BeforeEach
    public void init() {
        transformer = new Transformer();
    }

    @ParameterizedTest
    @CsvSource({"data1.csv,John", "data2.csv,Yuri"})
    public void testParseCSV(String inputFileName, String firstName) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        ClassLoader classLoader = this.getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource(inputFileName)).getFile());

        List<Employee> staff = transformer.parseCSV(columnMapping, inputFile);

        Assertions.assertEquals(staff.get(0).firstName, firstName);
        Assertions.assertEquals(staff.size(), 2);
    }

    @Test
    public void testListToJson() {
        String expectedJson = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Smith\",\"country\":\"US\",\"age\":25}]";
        List<Employee> staff = new ArrayList<>() {{
            add(Employee.builder()
                    .id(1)
                    .firstName("John")
                    .lastName("Smith")
                    .country("US")
                    .age(25)
                    .build()
            );
        }};
        String json = transformer.listToJson(staff);

        Assertions.assertEquals(json, expectedJson);
    }
}
