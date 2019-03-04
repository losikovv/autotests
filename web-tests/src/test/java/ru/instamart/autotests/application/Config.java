package ru.instamart.autotests.application;

import ru.instamart.autotests.models.*;

public class Config {

    public final static EnvironmentData environment = Environments.instamart_staging();

    public final static int basicTimeout = 2;
    public final static boolean multiSessionMode = true;
    public final static boolean fullScreenMode = false;
    public final static boolean doCleanupAfterTestRun = true;

    public final static String companyName = "instamart";
    public final static String companyDomain = companyName + ".ru";
    public final static String testDomain = "example.com";
    public final static String testMark = "autotest";

    public final static int minOrderSum = 2000;

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

    // TODO убрать, определять стоимость доставки во время тестов
    public final static int MetroHighDeliveryPrice = 299;
    public final static int MetroMediumDeliveryPrice = 199;
    public final static int MetroLowDeliveryPrice = 99;
    public final static int VkusvillDeliveryPrice = 190;
}
