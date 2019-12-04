package ru.instamart.application;

import org.openqa.selenium.remote.BrowserType;
import ru.instamart.application.lib.Juridicals;
import ru.instamart.application.lib.PaymentCards;
import ru.instamart.application.lib.PaymentTypes;
import ru.instamart.application.lib.ReplacementPolicies;
import ru.instamart.application.models.*;

public class Config {

    public interface CoreSettings {
        String browser = BrowserType.FIREFOX;
        ServerData server = Servers.metro_production();

        int basicTimeout = 2;
        int waitingTimeout = 20;

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
            boolean enableOrderCitiesTests = false;
            boolean enableOrderBonusesTests = true;
            boolean enableOrderReplacementsTests = true;
            boolean enableOrderRepeatTests = true;
            boolean enableOrderRetailersTests = true;
            boolean enableOrderRetailerCardsTests = true;
        }

        interface PromoTests {
            boolean enablePromoFreeDeliveryTests = true;
            boolean enablePromoFixedDiscountTests = true;
            boolean enablePromoPercentDiscountTests = true;
        }

        interface AddonsTests {
            boolean enableSeoCatalogTests = false;
            boolean enableJivositeTests = false;
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
            String companyHotlineWorkhours = "с 5:00 до 1:00";
            String companyHotlineWorkhoursShort = "5:00 - 1:00";
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
                            "test"
                    ),
                    new ContactsDetailsData(
                            "1234567890"
                    ),
                    ReplacementPolicies.callAndReplace(),
                    new PaymentDetailsData(
                            PaymentTypes.cardCourier(),
                            false,
                            PaymentCards.testCard(),
                            false,
                            Juridicals.testJuridical()
                    ),
                    new DeliveryTimeData(6, 1)
            );
        }

        static OrderDetailsData DefaultCheckoutState() {
            // TODO
            return null;
        }
    }
}
