package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginModel {
    private String login;
    private String password;

    public static final String LOG_IN_COURIER_PATH = "/api/v1/courier/login";
}
