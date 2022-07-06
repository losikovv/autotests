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
        return new Object[][] {
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
        return new Object[][] {
                {UiProperties.SELGROS_URL + selgrosAbout().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosDelivery().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosRules().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosReturnPolicy().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosFaq().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosTerms().pageUrl()},
                {UiProperties.SELGROS_URL + selgrosContacts().pageUrl()}
        };
    }

    @DataProvider(name = "servicePage", parallel = true)
    public static Object[][] getServicePage() {
        return new Object[][] {
                {UiProperties.STF_URL + certificate().pageUrl()},
                {UiProperties.STF_URL + driversHiring().pageUrl()},
                {job().pageUrl()}
        };
    }

    @DataProvider(name = "footerLinkPage", parallel = true)
    public static Object[][] getFooterLinkPage() {
        return new Object[][] {
                {"О компании", UiProperties.STF_URL + about().pageUrl()},
                {"Контакты", UiProperties.STF_URL + contacts().pageUrl()},
                {"Наши вакансии", UiProperties.JOB_LANDING_URL},
                {"Документы", UiProperties.STF_URL + terms().pageUrl()},
                {"Стать партнёром", UiProperties.JOB_LANDING_URL},
                {"Как мы работаем", UiProperties.STF_URL + howWeWork().pageUrl()},
                {"Доставка и оплата", UiProperties.STF_URL + delivery().pageUrl()},
                {"Помощь", UiProperties.STF_URL + faq().pageUrl()},
                {"Политика возврата", UiProperties.STF_URL + rules().pageUrl()},
        };
    }

    @DataProvider(name = "landingPage", parallel = true)
    public static Object[][] getLandingPage() {
        return new Object[][] {
                {UiProperties.STF_URL + aeroflot().pageUrl()}
        };
    }

    @DataProvider(name = "selgrosAvailableRetailerPage")
    public static Object[][] getSelgrosAvailableRetailerPage() {
        return new Object[][] {
                {UiProperties.SELGROS_URL + selgros().pageUrl()},
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

        List<RetailerV2> retailerList = apiV1.getAvailableRetailers();

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

    private StaticPage() {}
}
