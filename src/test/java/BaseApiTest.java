import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class BaseApiTest {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";


    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

}
