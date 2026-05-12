package data;

import com.github.javafaker.Faker;

public class OrderData {
    static Faker user = new Faker();
    public static final String CLIENT_FIRST_NAME = user.name().firstName();
    public static final String CLIENT_LAST_NAME = user.name().lastName();
    public static final String CLIENT_ADDRESS = user.address().streetAddress();
    public static final int CLIENT_METRO_STATION = 5;
    public static final String CLIENT_PHONE = user.phoneNumber().cellPhone();
    public static final int CLIENT_METRO_RENT_TIME = 4;
    public static final String CLIENT_DELIVERY_DATE = "2026-06-25";
    public static final String CLIENT_COMMENT = "Буду ждать";
}
