package ru.instamart.autotests.application;

public class Config {

    public final static String companyName = "instamart";
    public final static String productionHost = "instamart.ru";
    public final static String stagingHost = "staging.instamart.ru";

    public final static String testUserEmailBase = "testuser@example.com";
    public final static String testUsersList = "users?q%5Bemail_cont%5D=testuser%40example.com";
    public final static String testOrdersList ="shipments?search%5Bemail%5D=autotestuser%40instamart.ru&search%5Bonly_completed%5D=1&search%5Bstate%5D%5B%5D=ready";

    public final static int minOrderSum = 1800;

    // TODO обернуть в интерфейс shippingCost
    public final static int MetroHighDeliveryPrice = 299;
    public final static int MetroMediumDeliveryPrice = 199;
    public final static int MetroLowDeliveryPrice = 99;
    public final static int VkusvillDeliveryPrice = 190;
}
