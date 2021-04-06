package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestBase;
import instamart.api.objects.v2.Store;
import instamart.api.objects.v2.Taxon;
import instamart.api.requests.v2.TaxonsRequest;
import instamart.api.responses.v2.TaxonResponse;
import instamart.api.responses.v2.TaxonsResponse;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode404;
import static org.testng.Assert.assertNotNull;

@Epic("ApiV2")
@Feature("Получение таксонов")
public final class TaxonsV2Test extends RestBase {

    private static final Logger log = LoggerFactory.getLogger(TaxonsV2Test.class);

    private int taxonId;

    @CaseId(11)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Получаем таксоны (подкатегории)")
    public void getTaxons() {
        response = TaxonsRequest.GET(EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode200(response);
        List<Taxon> taxons = response.as(TaxonsResponse.class).getTaxons();
        assertNotNull(taxons, "Не вернулись таксоны");
        taxonId = taxons.get(0).getId();
    }

    @CaseId(6)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "getTaxons",
            description = "Получаем таксон (подкатегорию)")
    public void getTaxon() {
        response = TaxonsRequest.GET(taxonId, EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode200(response);
        assertNotNull(response.as(TaxonResponse.class).getTaxon(), "Не вернулся таксон");
    }

    @CaseId(6)
    @Test(groups = {},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "stores-parallel",
            description = "Получаем каждый таксон (подкатегорию) у каждого магазина")
    public void getTaxonsFromEachStore(Store store) {
        Set<Integer> taxonIds = apiV2.getTaxonIds(store.getId());
        log.info("Taxon size {}", taxonIds.size());
        taxonIds.parallelStream().forEach(taxonId -> apiV2.getTaxon(taxonId, store.getId()));
    }

    @CaseId(250)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий sid")
    public void testWithInvalidSid() {
        final Response response = TaxonsRequest.GET(6666);
        checkStatusCode404(response);
    }

    @CaseId(252)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Информация о категории с невалидной категорией")
    public void testGetCategoryWithInvalidCategory() {
        final Response response = TaxonsRequest.GET(0, EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode404(response);
    }

    @CaseId(254)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dependsOnMethods = "getTaxons",
            description = "Информация о категории с валидной категорией и несуществующим sid")
    public void testGetCategoryWithInvalidSid() {
        final Response response = TaxonsRequest.GET(taxonId, 6666);
        checkStatusCode404(response);
    }
}
