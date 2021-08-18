package ru.instamart.reforged.core;

import org.testng.annotations.DataProvider;
import ru.instamart.api.common.Specification;
import ru.instamart.api.helper.InstamartApiHelper;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import java.util.List;

import static ru.instamart.reforged.stf.page.StfRouter.*;

public final class StaticPage {

    private static final InstamartApiHelper apiV2 = new InstamartApiHelper();

    @DataProvider(name = "faqPage", parallel = true)
    public static Object[][] getFaqPage() {
        return new Object[][] {
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + about().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + contacts().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + delivery().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + faq().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + howWeWork().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + rules().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + terms().pageUrl()}
        };
    }

    @DataProvider(name = "metroFaqPage", parallel = true)
    public static Object[][] getMetroFaqPage() {
        return new Object[][] {
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + metroAbout().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + metroDelivery().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + metroRules().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + metroReturnPolicy().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + metroFaq().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + metroTerms().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + metroContacts().pageUrl()}
        };
    }

    @DataProvider(name = "servicePage", parallel = true)
    public static Object[][] getServicePage() {
        return new Object[][] {
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + certificate().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + job().pageUrl()}
        };
    }

    @DataProvider(name = "landingPage", parallel = true)
    public static Object[][] getLandingPage() {
        return new Object[][] {
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + mnogory().pageUrl()},
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + aeroflot().pageUrl()}
        };
    }

    @DataProvider(name = "metroAvailableRetailerPage", parallel = true)
    public static Object[][] getMetroAvailableRetailerPage() {
        return new Object[][] {
                {EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + metro().pageUrl()},
        };
    }

    @DataProvider(name = "filteredAvailableRetailersSpree", parallel = true)
    public static Object[][] getFilteredAvailableRetailersSpree() {
        Specification.setResponseSpecDataProvider();

        List<RetailerV2> retailerList = apiV2.getAvailableRetailersSpree();

        Specification.setResponseSpecDefault();

        return retailerList.stream()
                .filter(RetailerV2::getAvailable) //Фильтрует только доступных ретейлеров
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "filteredUnavailableRetailersSpree", parallel = true)
    public static Object[][] getFilteredUnavailableRetailersSpree() {
        Specification.setResponseSpecDataProvider();

        final List<RetailerV2> retailerList = apiV2.getAvailableRetailersSpree();

        Specification.setResponseSpecDefault();

        return retailerList.stream()
                .filter(r -> !r.getAvailable()) //Фильтрует только недоступных ретейлеров
                .map(retailer -> new Object[]{retailer})
                .toArray(Object[][]::new);
    }

    private StaticPage() {}
}
