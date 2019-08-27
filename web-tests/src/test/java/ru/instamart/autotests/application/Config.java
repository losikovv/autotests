package ru.instamart.autotests.application;

import org.openqa.selenium.remote.BrowserType;
import ru.instamart.autotests.application.libs.PaymentTypes;
import ru.instamart.autotests.application.libs.ReplacementPolicies;
import ru.instamart.autotests.appmanager.models.*;

public class Config {

    public interface CoreSettings {
        String browser = BrowserType.FIREFOX;
        EnvironmentData environment = Environments.instamart_production();

        int basicTimeout = 2;
        int waitingTimeout = 60;

        boolean verbose = true;
        boolean debug = false;
        boolean multiSessionMode = false;
        boolean fullScreenMode = false;
        boolean doCleanupAfterTestRun = true;
    }

    public interface TestsConfiguration {

        interface AdministrationTests {
            boolean enableShipmentsSectionTests = true;
            boolean enableUsersSectionTests = true;
            boolean enablePagesSectionTests = true;
        }

        interface CheckoutTests {
            boolean enableAddressStepTests = true;
            boolean enableContactsStepTests = true;
            boolean enableReplacementsStepTests = true;
            boolean enablePaymentStepTests = true;
            boolean enableDeliveryStepTests = true;
            boolean enablePromocodesTests = true;
            boolean enableBonusesTests = true;
            boolean enableRetailerCardsTests = true;
        }

        interface OrdersTests {
            boolean enableOrderRetailersTests = true;
            boolean enableOrderCitiesTests = true;
            boolean enableOrderReplacementsTests = true;
            boolean enableOrderRepeatTests = true;
            boolean enableOrderBonusesTests = true;
            boolean enableOrderRetailerCardsTests = true;
        }

        interface PromoTests {
            boolean enablePromoFreeDeliveryTests = true;
            boolean enablePromoFixedDiscountTests = true;
            boolean enablePromoPercentDiscountTests = true;
        }

        interface AddonsTests {
            boolean enableSeoCatalogTests = false;
            boolean enableJivositeTests = true;
            boolean enableRetailRocketTest = false;
            boolean enablePage404test = true;
        }
    }

    public interface TestVariables {

        interface CompanyParams {
            String companyName = "instamart";
            String companyDomain = companyName + ".ru";
            String companyHotlinePhoneNumber = "+7 800 222-11-00";
            String companyHotlinePhoneLink = "tel:+78002221100";
            String companyHotlineWorkhours = "с 7:00 до 24:00";
            String companyHotlineWorkhoursShort = "7:00 - 24:00";
        }

        interface TestParams {
            String testDomain = "example.com";
            String testMark = "autotest";
            String testOrder = "R384014557";
            String testShipment = "H44617031667";
        }

        // TODO убрать, определять стоимость доставки во время тестов
        interface DeliveryPrices {
            int minOrderSum = 2000;
            int MetroHighDeliveryPrice = 299;
            int MetroMediumDeliveryPrice = 199;
            int MetroLowDeliveryPrice = 99;
            int VkusvillDeliveryPrice = 190;
        }

        static OrderDetailsData testOrderDetails() {
            return new OrderDetailsData(
                    new AddressDetailsData(
                            "office",
                            "1",
                            "22",
                            true, "333",
                            "44 ключ 4444",
                            "ТЕСТОВЫЙ ЗАКАЗ / НЕ СОБИРАТЬ"
                    ),
                    new ContactsDetailsData(
                            "1234567890"
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

        static OrderDetailsData DefaultCheckoutState() {
            // TODO
            return null;
        }
    }
}
