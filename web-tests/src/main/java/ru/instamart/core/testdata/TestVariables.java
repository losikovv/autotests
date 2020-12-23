package instamart.core.testdata;

import instamart.core.testdata.ui.Juridicals;
import instamart.core.testdata.ui.PaymentCards;
import instamart.core.testdata.ui.PaymentTypes;
import instamart.ui.common.lib.ReplacementPolicies;
import instamart.ui.common.pagesdata.*;

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
        String companyAdjustFooterAppLink = "https://app.adjust.com/kfrpj8y";
    }

    interface TestParams {
        String testDomain = "example.com";
        String testMark = "autotest";

        interface ItemSearch {
            String testQuery = "хлеб";
            String emptyResultsQuery = "смысл жизни";
        }
    }

    // TODO вынести в тестовые данные
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
}
