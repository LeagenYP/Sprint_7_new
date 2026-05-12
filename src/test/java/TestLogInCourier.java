import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierModel;
import model.LoginModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static data.CourierData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.CourierSteps.createCourier;
import static steps.CourierSteps.deleteCourier;
import static steps.LoginSteps.logInCourier;

public class TestLogInCourier extends BaseApiTest {

    Response response;

    @Before
    @Step("Создание курьера для дальнейшей проверки авторизации")
    public void prepareCourier() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);
        response = createCourier(courier);
    }

    @Test
    @DisplayName("Проверка успешной авторизации")
    public void successLogInTest() {
        LoginModel login = new LoginModel(LOGIN, PASSWORD);
        logInCourier(login)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_OK))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации при отсутствии логина")
    public void failLogInWithoutLoginTest() {
        LoginModel login = new LoginModel(null, PASSWORD);
        logInCourier(login)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_BAD_REQUEST))
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации при отсутствии пароля")
    @Description("Здесь баг, сервер не получает ответ. Из-за этого добавлен таймаут в 15 секунд для прерывания")
    public void failLogInWithoutPasswordTest() {
        LoginModel login = new LoginModel(LOGIN, null);
        logInCourier(login)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_BAD_REQUEST))
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации при неверном пароле")
    public void failLogInWithWrongPasswordTest() {
        LoginModel login = new LoginModel(LOGIN, PASSWORD + "1");
        logInCourier(login)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_NOT_FOUND))
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации при неверном логине")
    public void failLogInWithWrongLoginTest() {
        LoginModel login = new LoginModel(LOGIN + "1", PASSWORD);
        logInCourier(login)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_NOT_FOUND))
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    @Step("Удаление данных о курьре, если курьер существует")
    public void clearData() {
        try {
            LoginModel login = new LoginModel(LOGIN, PASSWORD);
            deleteCourier(logInCourier(login));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
