import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;
import static data.OrderData.*;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.createOrder;

@RunWith(Parameterized.class)
public class TestCreateOrder extends BaseApiTest {

    public TestCreateOrder(String[] clientColor) {
        this.clientColor = clientColor;
    }

    Response response;

    String[] clientColor;

    @Parameterized.Parameters (name = "Тестовые данные: {0}")
    public static Object[][] getColor() {
        return new Object[][] {
                {new String[] {}},
                {new String[] {"BLACK"}},
                {new String[] {"GREY"}},
                {new String[] {"BLACK, GREY"}}
        };
    }

    @Test
    @DisplayName("Создание заказа с цветами: {clientColor}")
    public void createOrderTest() {
        OrderModel order = new OrderModel(CLIENT_FIRST_NAME,
                CLIENT_LAST_NAME,
                CLIENT_ADDRESS,
                CLIENT_METRO_STATION,
                CLIENT_PHONE,
                CLIENT_METRO_RENT_TIME,
                CLIENT_DELIVERY_DATE,
                CLIENT_COMMENT,
                clientColor);

        response = createOrder(order)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue())
                .extract().response();
    }

    @After
    @Step("Отмена заказа")
    public void cancelCreatedOrder() {
        int orderId = response.jsonPath().get("track");
        OrderSteps.cancelOrder(orderId);
    }
}
