package steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static io.restassured.RestAssured.given;
import static model.OrderModel.CREATE_ORDER_PATH;

public class OrderSteps {
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

    public static Response getOrdersList() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }
}
