import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import model.LoginModel;
import org.junit.After;
import org.junit.Test;
import static data.CourierData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.createCourier;
import static steps.CourierSteps.deleteCourier;
import static steps.LoginSteps.logInCourier;
public class TestCreateCourier extends BaseApiTest {

    @Test
    @DisplayName("Проверка успешного создания курьера")
    public void courierCreateSuccessTest() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка невозможности создать дубликат курьера")
    @Description("Тут баг, отличается текст ошибки от ожидаемого")
    public void cannotCreateSameCourierTwiceTest() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);

        createCourier(courier);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_CONFLICT))
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Проверка валидации: курьер без логина не создаётся")
    public void cannotCreateCourierWithoutLogin() {
        CourierModel courier = new CourierModel(null, PASSWORD, FIRST_NAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_BAD_REQUEST))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Проверка валидации: курьер без пароля не создаётся")
    public void cannotCreateCourierWithoutPassword() {
        CourierModel courier = new CourierModel(LOGIN, null, FIRST_NAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_BAD_REQUEST))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Попытка создать курьера без имени (допускается)")
    public void cannotCreateCourierWithoutFirstName() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, null);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_CREATED));
    }

    @After
    @Step("Очистка тестовых данных: удаление курьера")
    public void clearData() {
        try {
            LoginModel login = new LoginModel(LOGIN, PASSWORD);
            deleteCourier(logInCourier(login));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
