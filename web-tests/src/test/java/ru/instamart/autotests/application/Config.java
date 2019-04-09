package ru.instamart.autotests.application;

import ru.instamart.autotests.models.*;

public class Config {
    
    public final static EnvironmentData environment = Environments.instamart_staging();

    public final static int basicTimeout = 2;
    public final static int waitingTimeout = 15;

    public final static boolean multiSessionMode = false;
    public final static boolean fullScreenMode = false;
    public final static boolean doCleanupAfterTestRun = false;

    public final static boolean verbose = true;

    public final static String companyName = "instamart";
    public final static String companyDomain = companyName + ".ru";
    public final static String testDomain = "example.com";
    public final static String testMark = "autotest";
    public final static String testOrder = "R384014557";
    public final static String testShipment = "H44617031667";

    public final static int minOrderSum = 2000;

    public static OrderDetailsData testOrderDetails() {
        return new OrderDetailsData(
                new AddressDetailsData(
                        "flat",
                        "1",
                        "22",
                        true,"333",
                        "44ключ4444",
                        "ТЕСТОВЫЙ ЗАКАЗ / НЕ СОБИРАТЬ"
                ),
                new ContactsDetailsData(
                        null,
                        null,
                        null,
                        false,
                        "7777777777",
                        true
                ),
                ReplacementPolicies.callAndReplace(),
                new PaymentDetailsData(
                        PaymentTypes.cash(),
                        false,
                        new CreditCardData(
                                "4242424242424242",
                                "12",
                                "2049",
                                "IVAN IVANOV",
                                "404"),
                        false,
                        new JuridicalData(
                                "ООО \"Автотест\"",
                                "ул. Тестовская, 88",
                                "1111111111111",
                                "222222222",
                                "33333333333333333333",
                                "444444444",
                                "Банк Тестовый",
                                "55555555555555555555"
                        )
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
