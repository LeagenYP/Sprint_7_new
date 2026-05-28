import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.getOrdersList;

public class TestGetOrdersList extends BaseApiTest {

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrdersListTest() {
        getOrdersList()
                .then()
                .log().all()
                .body("orders", notNullValue());
    }
}
