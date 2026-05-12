package steps;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.LoginModel;

import static io.restassured.RestAssured.given;
import static model.LoginModel.LOG_IN_COURIER_PATH;

public class LoginSteps {
    public static Response logInCourier(LoginModel loginModel) {
        return given()
                .config(RestAssured.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.socket.timeout", 15000)   // таймаут чтения данных
                                .setParam("http.connection.timeout", 15000) // таймаут соединения
                        ))
                .log().all()
                .contentType(ContentType.JSON)
                .body(loginModel)
                .when()
                .post(LOG_IN_COURIER_PATH)
                .then()
                .extract().response();
    }
}
