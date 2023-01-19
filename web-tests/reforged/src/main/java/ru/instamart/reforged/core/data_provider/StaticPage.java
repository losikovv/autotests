package ru.instamart.reforged.core.data_provider;

import org.testng.annotations.DataProvider;
import ru.instamart.api.common.Specification;
import ru.instamart.api.helper.ApiV1Helper;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.reforged.core.config.UiProperties;

import java.util.List;

import static ru.instamart.reforged.selgros.page.SelgrosRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.*;

public final class StaticPage {

    private static final ApiV1Helper apiV1 = new ApiV1Helper();

    @DataProvider(name = "faqPage", parallel = true)
    public static Object[][] getFaqPage() {
        return new Object[][]{
                {UiProperties.STF_URL + about().pageUrl()},
                {UiProperties.STF_URL + contacts().pageUrl()},
                {UiProperties.STF_URL + delivery().pageUrl()},
                {UiProperties.STF_URL + faq().pageUrl()},
                {UiProperties.STF_URL + howWeWork().pageUrl()},
                {UiProperties.STF_URL + rules().pageUrl()},
                {UiProperties.STF_URL + terms().pageUrl()}
        };
    }

    @DataProvider(name = "selgrosFaqPage", parallel = true)
    public static Object[][] getSelgrosFaqPage() {
        return new Object[][]{
                {UiProperties.SELGROS_URL + selgrosAbout().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosDelivery().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosRules().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosReturnPolicy().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosFaq().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosTerms().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosContacts().pageUrl()}
        };
    }

    @DataProvider(name = "footerLinkPage", parallel = true)
    public static Object[][] getFooterLinkPage() {
        return new Object[][]{
                {"О компании", about().pageUrl()},
                {"Контакты", contacts().pageUrl()},
                {"Ретейлерам", retailerWelcomePage().pageUrl()},
                {"Ресторанам", foodPage().pageUrl()},
                {"Документы", terms().pageUrl()},
                {"Стать курьером", jobPage().pageUrl() + "?utm_source=sbermarketru_web"},
                {"Как мы работаем", howWeWork().pageUrl()},
                {"Доставка и оплата", delivery().pageUrl()},
                {"Помощь", faq().pageUrl()},
                {"Политика по противодействию коррупции", "https://storage.yandexcloud.net/sbermarket-prod-compliance/%D0%9F%D1%80%D0%B8%D0%BA%D0%B0%D0%B7%20%E2%84%96%20%D0%9F-2022-04-18%20%D0%BE%D1%82%2019.04.2022%20%D0%98%D0%A1_.pdf"},
                {"Политика возврата", rules().pageUrl()},
                {"Правила резервирования товаров", terms().pageUrl()},
        };
    }

    @DataProvider(name = "selgrosUnavailableRetailersSpree")
    public static Object[][] getSelgrosUnavailableRetailerSpree() {
        Specification.setResponseSpecDataProvider();

        final List<RetailerV2> retailerList = apiV1.getAvailableRetailers();

        Specification.setResponseSpecDefault();

        return retailerList.stream()
                .filter(r -> !r.getSlug().equals("selgros")) //Фильтрует только ретейлеров != целевому
                .map(retailer -> new Object[]{retailer})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "filteredAvailableRetailersSpree", parallel = true)
    public static Object[][] getFilteredAvailableRetailersSpree() {
        Specification.setResponseSpecDataProvider();

        final List<RetailerV2> retailerList = apiV1.getAvailableRetailers();

        Specification.setResponseSpecDefault();

        return retailerList.stream()
                .filter(RetailerV2::getAvailable) //Фильтрует только доступных ретейлеров
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "filteredUnavailableRetailersSpree", parallel = true)
    public static Object[][] getFilteredUnavailableRetailersSpree() {
        Specification.setResponseSpecDataProvider();

        final List<RetailerV2> retailerList = apiV1.getAvailableRetailers();

        Specification.setResponseSpecDefault();

        return retailerList.stream()
                .filter(r -> !r.getAvailable()) //Фильтрует только недоступных ретейлеров
                .map(retailer -> new Object[]{retailer})
                .toArray(Object[][]::new);
    }

    private StaticPage() {
    }
}
