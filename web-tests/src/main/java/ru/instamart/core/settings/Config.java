package instamart.core.settings;

import instamart.core.testdata.Environments;
import instamart.core.testdata.ui.PaymentCards;
import instamart.core.testdata.ui.PaymentTypes;
import instamart.core.testdata.ui.Juridicals;
import instamart.core.testdata.ui.Tenants;
import instamart.ui.common.lib.ReplacementPolicies;
import instamart.ui.common.pagesdata.*;
import org.openqa.selenium.remote.BrowserType;

public class Config {
    public static boolean isKrakenRevealen = false; // Переменная для обозначения запущен кракен или нет,
    // может понадобится для раздельного запуска API и UI пусть полежит тут, если нормально настроим
    // то удалим эту штуку
    private boolean mobileAuth;

    public interface CoreSettings {
        String defaultBrowser = BrowserType.CHROME;
        String defaultEnvironment = Environments.sbermarket.staging();
        String defaultTenant = Tenants.metro().getAlias();

        int basicTimeout = 2;
        int waitingTimeout = 45;

        boolean docker = false;
        boolean video = false;

        boolean verbose = true;
        boolean debug = true;

        boolean multiSessionMode = false;
        boolean fullScreenMode = false;
        boolean doCleanupAfterTestRun = true;
        boolean doCleanupBeforeTestRun = true; //Все существующие инстансы браузера связанные с selenium будут удалены, рабочий браузер не убивается


        boolean restIgnoreProperties = true;

    }
    /** Это временный костыль пока мы живем с разными типами авторизаци на стейдже и проде, когда переедем
     * полностью на мобилку, это нужно будет удалить */
    public boolean mobileAuth(){
        if (CoreSettings.defaultEnvironment.equals("sbermarket-production"))return mobileAuth=false;
        else return mobileAuth=true;
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
            boolean enableOrderCitiesTests = true;
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
            String companyName = "sbermarket";
            String companyDomain = companyName + ".ru";
            String companyHotlinePhoneNumber = "+7 800 222-11-00";
            String companyHotlinePhoneLink = "tel:+78002221100";
            String companyHotlineWorkhours = "Круглосуточно";
            String companyHotlineWorkhoursShort = "Круглосуточно";
            String companyFacebookLink = "https://www.facebook.com/sbermarket.ru/";
            String companyVkontakteLink = "https://vk.com/sbermarket_ru";
            String companyTwitterLink = "https://twitter.com/sbermarket_ru";
            String companyInstagramLink = "https://www.instagram.com/sbermarket.ru/";
            String companyAdjustFooterAppLink = "https://app.adjust.com/kfrpj8y?campaign=footer";
        }

        interface TestParams {
            String testDomain = "example.com";
            String testMark = "autotest";
        }

        // TODO убрать, определять стоимость доставки во время тестов
        interface DeliveryPrices {
            int minOrderSum = 2500;
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
