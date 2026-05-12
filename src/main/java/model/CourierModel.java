package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourierModel {
    private String login;
    private String password;
    private String firstName;

    public static final String CREATE_COURIER_PATH = "/api/v1/courier";
    public static final String DELETE_COURIER_PATH = "/api/v1/courier/"; // При удалении нужно прибавить id курьера
}
