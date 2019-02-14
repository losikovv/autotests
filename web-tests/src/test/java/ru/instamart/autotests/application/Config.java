package ru.instamart.autotests.application;

import ru.instamart.autotests.models.*;

public class Config {

    public final static String companyName = "instamart";
    public final static String productionHost = "instamart.ru"; // TODO убрать
    public final static String testHost = "example.com";

    public final static String testUserPhone = "1488148814";
    public final static String testUserNameBase = "testuser";
    public final static String testAdminNameBase = "testadmin";


    // TODO убрать весь блок
    public final static String testUserEmailBase = testUserNameBase + "@" + testHost;
    public final static String testAdminEmailBase = testAdminNameBase + "@" + productionHost;
    public final static String testUsersList = "users?q%5Bemail_cont%5D=" + testUserNameBase + "%40" + testHost;
    public final static String testAdminsList = "users?q%5Bemail_cont%5D=" + testAdminNameBase + "%40" + productionHost;
    public final static String testOrdersList = "shipments?search%5Border_phone%5D=%2B7" + testUserPhone + "&search%5Bstate%5D%5B%5D=ready";


    public final static int minOrderSum = 2000;

    // TODO убрать, определять стоимость доставки во время теста
    public final static int MetroHighDeliveryPrice = 299;
    public final static int MetroMediumDeliveryPrice = 199;
    public final static int MetroLowDeliveryPrice = 99;
    public final static int VkusvillDeliveryPrice = 190;


    public static OrderDetailsData testOrderDetails() {
        return new OrderDetailsData(
                new AddressDetailsData(
                        "flat",
                        "1",
                        "22",
                        true,"333",
                        "4444ключ4444",
                        "ТЕСТОВЫЙ ЗАКАЗ / НЕ СОБИРАТЬ"
                ),
                new ContactsDetailsData(
                        null,
                        null,
                        null,
                        false,
                        "1488148814",
                        true
                ),
                ReplacementPolicies.callAndReplace(),
                new PaymentDetailsData(
                        PaymentTypes.cash(),
                        false,
                        null,
                        false,
                        null
                ),
                new DeliveryTimeData(
                        7,
                        7
                )
        );
    }
}
