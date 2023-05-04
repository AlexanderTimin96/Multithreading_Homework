import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.ntology.PhoneBook;

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
    public void TestAdd_SimpleAdd(int argument) {
        int excepted = argument;

        int result = 0;
        for (int i = 0; i < argument; i++) {
            result = phoneBook.add("name" + i, "nubmer");
        }

        Assertions.assertEquals(excepted, result);
    }

    @Test
    public void TestFindByName() {
        String excepted = "name";

        phoneBook.add("name", "880053535");
        phoneBook.add("name2", "555");

        String result = phoneBook.findByNumber("880053535");

        Assertions.assertEquals(excepted, result);
    }
}
