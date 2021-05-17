package ru.instamart.tests.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.StoreV2;
import ru.instamart.api.model.v2.TaxonV2;
import ru.instamart.api.request.v2.TaxonsV2Request;
import ru.instamart.api.response.v2.TaxonV2Response;
import ru.instamart.api.response.v2.TaxonsV2Response;
import ru.instamart.core.dataprovider.RestDataProvider;
import ru.instamart.core.testdata.pagesdata.EnvironmentData;

import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Получение таксонов")
public final class TaxonsV2Test extends RestBase {

    private static final Logger log = LoggerFactory.getLogger(TaxonsV2Test.class);

    private int taxonId;

    @CaseId(11)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Получаем таксоны (подкатегории)")
    public void getTaxons() {
        response = TaxonsV2Request.GET(EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode200(response);
        List<TaxonV2> taxons = response.as(TaxonsV2Response.class).getTaxons();
        assertNotNull(taxons, "Не вернулись таксоны");
        taxonId = taxons.get(0).getId();
    }

    @CaseId(6)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            dependsOnMethods = "getTaxons",
            description = "Получаем таксон (подкатегорию)")
    public void getTaxon() {
        response = TaxonsV2Request.GET(taxonId, EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode200(response);
        assertNotNull(response.as(TaxonV2Response.class).getTaxon(), "Не вернулся таксон");
    }

    @CaseId(6)
    @Test(groups = {},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "stores-parallel",
            description = "Получаем каждый таксон (подкатегорию) у каждого магазина")
    public void getTaxonsFromEachStore(StoreV2 store) {
        Set<Integer> taxonIds = apiV2.getTaxonIds(store.getId());
        log.info("Taxon size {}", taxonIds.size());
        taxonIds.parallelStream().forEach(taxonId -> apiV2.getTaxon(taxonId, store.getId()));
    }

    @CaseId(250)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий sid")
    public void testWithInvalidSid() {
        final Response response = TaxonsV2Request.GET(6666);
        checkStatusCode404(response);
    }

    @CaseId(252)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Информация о категории с невалидной категорией")
    public void testGetCategoryWithInvalidCategory() {
        final Response response = TaxonsV2Request.GET(0, EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode404(response);
    }

    @CaseId(254)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dependsOnMethods = "getTaxons",
            description = "Информация о категории с валидной категорией и несуществующим sid")
    public void testGetCategoryWithInvalidSid() {
        final Response response = TaxonsV2Request.GET(taxonId, 6666);
        checkStatusCode404(response);
    }
}
