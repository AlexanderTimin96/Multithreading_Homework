import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.ntology.PhoneBook;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class PhoneBookTests {
    private PhoneBook phoneBook;

    @BeforeEach
    public void beforeEachTests() {
        phoneBook = new PhoneBook();
    }

    @AfterEach
    public void afterEachTests() {
        phoneBook = null;
    }

    @Test
    public void TestAdd_WithReplay() {
        int excepted = 1;

        int result = 0;
        for (int i = 0; i < 3; i++) {
            result = phoneBook.add("name", "nubmer");
        }

        Assertions.assertEquals(excepted, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 8})
    public void TestAdd(int argument) {
        int excepted = argument;

        int result = 0;
        for (int i = 0; i < argument; i++) {
            result = phoneBook.add("name" + i, "nubmer");
        }

        Assertions.assertEquals(excepted, result);
    }

    @Test
    public void TestFindByNumber() {
        String excepted = "name";

        phoneBook.add("name", "880053535");
        phoneBook.add("name2", "555");

        String result = phoneBook.findByNumber("880053535");

        Assertions.assertEquals(excepted, result);
    }

    @Test
    public void TestFindByName() {
        String excepted = "880053535";

        phoneBook.add("name", "880053535");
        phoneBook.add("name2", "555");

        String result = phoneBook.findByName("name");

        Assertions.assertEquals(excepted, result);
    }

    @Test
    public void TestPrintAllNames() {
        List<String> excepted = new ArrayList<>();
        excepted.add("A");
        excepted.add("B");
        excepted.add("C");

        phoneBook.add("B", "48484");
        phoneBook.add("A", "466");
        phoneBook.add("C", "6226");

        List<String> result = phoneBook.printAllName();

        Assertions.assertEquals(excepted, result);
    }
}
