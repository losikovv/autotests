package ru.instamart.reforged.core;

import org.testng.annotations.DataProvider;
import ru.instamart.api.common.Specification;
import ru.instamart.api.helper.InstamartApiHelper;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.List;

import static ru.instamart.reforged.metro.page.MetroRouter.*;
import static ru.instamart.reforged.okey.page.OkeyRouter.*;
import static ru.instamart.reforged.selgros.page.SelgrosRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.*;

public final class StaticPage {

    private static final InstamartApiHelper apiV2 = new InstamartApiHelper();

    @DataProvider(name = "faqPage", parallel = true)
    public static Object[][] getFaqPage() {
        return new Object[][] {
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + about().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + contacts().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + delivery().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + faq().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + howWeWork().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + rules().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + terms().pageUrl()}
        };
    }

    @DataProvider(name = "metroFaqPage", parallel = true)
    public static Object[][] getMetroFaqPage() {
        return new Object[][] {
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + metroAbout().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + metroDelivery().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + metroRules().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + metroReturnPolicy().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + metroFaq().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + metroTerms().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + metroContacts().pageUrl()}
        };
    }

    @DataProvider(name = "okeyFaqPage", parallel = true)
    public static Object[][] getOkeyFaqPage() {
        return new Object[][] {
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + okeyAbout().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + okeyDelivery().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + okeyRules().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + okeyReturnPolicy().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + okeyFaq().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + okeyTerms().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + okeyContacts().pageUrl()}
        };
    }

    @DataProvider(name = "selgrosFaqPage", parallel = true)
    public static Object[][] getSelgrosFaqPage() {
        return new Object[][] {
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + selgrosAbout().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + selgrosDelivery().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + selgrosRules().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + selgrosReturnPolicy().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + selgrosFaq().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + selgrosTerms().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + selgrosContacts().pageUrl()}
        };
    }

    @DataProvider(name = "servicePage", parallel = true)
    public static Object[][] getServicePage() {
        return new Object[][] {
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + certificate().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + job().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + driversHiring().pageUrl()}
        };
    }

    @DataProvider(name = "landingPage", parallel = true)
    public static Object[][] getLandingPage() {
        return new Object[][] {
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + mnogory().pageUrl()},
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + aeroflot().pageUrl()}
        };
    }

    @DataProvider(name = "metroAvailableRetailerPage", parallel = true)
    public static Object[][] getMetroAvailableRetailerPage() {
        return new Object[][] {
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + metro().pageUrl()},
        };
    }

    @DataProvider(name = "okeyAvailableRetailerPage", parallel = true)
    public static Object[][] getOkeyAvailableRetailerPage() {
        return new Object[][] {
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + okey().pageUrl()},
        };
    }

    @DataProvider(name = "selgrosAvailableRetailerPage", parallel = true)
    public static Object[][] getSelgrosAvailableRetailerPage() {
        return new Object[][] {
                {EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + selgros().pageUrl()},
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
