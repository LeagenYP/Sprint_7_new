package data;

import com.github.javafaker.Faker;

public class CourierData {

    static Faker user = new Faker();
    public static final String LOGIN = user.name().lastName() + user.regexify("[0-9]{8}");
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String FIRST_NAME = user.name().firstName();
}
