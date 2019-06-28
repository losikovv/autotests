package ru.instamart.autotests.application;

import org.openqa.selenium.remote.BrowserType;
import ru.instamart.autotests.models.*;

public class Config {

    // BROWSER
    public final static String browser = BrowserType.FIREFOX;

    // ENVIRONMENT
    public final static EnvironmentData environment = Environments.instamart_staging();

    // TIMEOUTS
    public final static int basicTimeout = 2;
    public final static int waitingTimeout = 15;

    // TEST EXECUTION SETTINGS
    public final static boolean verbose = true;
    public final static boolean debug = false;
    public final static boolean multiSessionMode = false;
    public final static boolean fullScreenMode = false;
    public final static boolean doCleanupAfterTestRun = true;

    // CONFIGURE TESTS
    public final static boolean enableAdministrationTests = true;
    public final static boolean enableSeoCatalogTests = false;
    public final static boolean enableJivositeTests = true;
    public final static boolean enableRetailRocketTests = false;
    public final static boolean enablePage404Tests = true;

    // CONFIGURE ORDER TESTS
    public final static boolean testRetailerOrders = true;
    public final static boolean testCitiesOrders = false;
    public final static boolean testReplacementsOrders = true;
    public final static boolean testRepeatOrders = true;
    public final static boolean testOrderBonuses = true;
    public final static boolean testOrderRetailerBonuses = true;

    // COMPANY PARAMS
    public final static String companyName = "instamart";
    public final static String companyDomain = companyName + ".ru";
    public final static String companyHotlinePhoneNumber = "+7 800 222-11-00";
    public final static String companyHotlinePhoneLink = "tel:+78002221100";
    public final static String companyHotlineWorkhours = "с 7:00 до 24:00";
    public final static String companyHotlineWorkhoursShort = "7:00 - 24:00";

    // TEST PARAMS
    public final static String testDomain = "example.com";
    public final static String testMark = "autotest";
    public final static String testOrder = "R384014557";
    public final static String testShipment = "H44617031667";

    // DELIVERY PARAMS // TODO убрать, определять стоимость доставки во время тестов
    public final static int minOrderSum = 2000;
    public final static int MetroHighDeliveryPrice = 299;
    public final static int MetroMediumDeliveryPrice = 199;
    public final static int MetroLowDeliveryPrice = 99;
    public final static int VkusvillDeliveryPrice = 190;

    // DEFAULT TEST ORDER PARAMS
    public static OrderDetailsData testOrderDetails() {
        return new OrderDetailsData(
                new AddressDetailsData(
                        "flat",
                        "1",
                        "22",
                        true, "333",
                        "44ключ4444",
                        "ТЕСТОВЫЙ ЗАКАЗ / НЕ СОБИРАТЬ"
                ),
                new ContactsDetailsData(
                        null,
                        null,
                        null,
                        false,
                        "1234567890",
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
                new DeliveryTimeData(7)
        );
    }
}
