package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;
import static data.Endpoints.CREATE_COURIER_PATH;
import static data.Endpoints.DELETE_COURIER_PATH;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Создание курьера")
    public static Response createCourier(CourierModel courier) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(CREATE_COURIER_PATH)
                .then()
                .extract().response();
    }

    @Step("Удаление курьера")
    public static void deleteCourier(Response response) {
        int id = response.jsonPath().get("id");
        given()
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .delete(DELETE_COURIER_PATH + id)
                .then()
                .log().all();
    }
}
