package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;
import static data.Endpoints.CANCEL_ORDER_PATH;
import static data.Endpoints.CREATE_ORDER_PATH;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа")
    public static Response createOrder(OrderModel order) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }

    @Step("Получение списка заказов")
    public static Response getOrdersList() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }

    @Step("Отмена заказа")
    public static void cancelOrder(int orderId) {

        String jsonBody = String.format("{\"track\": %d}", orderId);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .put(CANCEL_ORDER_PATH)
                .then()
                .log().all()
                .extract().response();

        System.out.println("Попытка отмены заказа: " + jsonBody);
    }
}
