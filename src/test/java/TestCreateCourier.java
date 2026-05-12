import io.qameta.allure.Step;
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
    @Step("Проверка успешного создания курьера")
    public void CourierCreateSuccessTest() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @Step("Проверка невозможности создать дубликат курьера")
    public void CannotCreateSameCourierTwiceTest() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);

        createCourier(courier);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_CONFLICT));
    }

    @Test
    @Step("Проверка валидации: курьер без логина не создаётся")
    public void CannotCreateCourierWithoutLogin() {
        CourierModel courier = new CourierModel(null, PASSWORD, FIRST_NAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_BAD_REQUEST));
    }
    @Test
    @Step("Проверка валидации: курьер без пароля не создаётся")
    public void CannotCreateCourierWithoutPassword() {
        CourierModel courier = new CourierModel(LOGIN, null, FIRST_NAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(equalTo(HTTP_BAD_REQUEST));
    }

    @Test
    @Step("Попытка создать курьера без имени (допускается)")
    public void CannotCreateCourierWithoutFirstName() {
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
