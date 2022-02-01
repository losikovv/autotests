package ru.instamart.kraken.data;

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
        String companyInstagramLink = "https://www.instagram.com/sbermarket.ru";
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

    static AddressDetailsData testAddressData() {
        return new AddressDetailsData(
                "",
                "100",
                "10",
                true,
                "1",
                "По номеру квартиры",
                "Тестовый комментарий"
        );
    }

    static AddressDetailsData testChangeAddressData() {
        return new AddressDetailsData(
                "",
                "200",
                "20",
                true,
                "12",
                "Не по номеру квартиры",
                "Нетестовый комментарий"
        );
    }
}
