package steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;
import static io.restassured.RestAssured.given;
import static model.CourierModel.CREATE_COURIER_PATH;
import static model.CourierModel.DELETE_COURIER_PATH;

public class CourierSteps {

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
