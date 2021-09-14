package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v2.StoreV2;
import ru.instamart.api.model.v2.TaxonV2;
import ru.instamart.api.request.v2.TaxonsV2Request;
import ru.instamart.api.response.v2.TaxonV2Response;
import ru.instamart.api.response.v2.TaxonsV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

@Slf4j
@Epic("ApiV2")
@Feature("Получение таксонов")
@Deprecated
public final class TaxonsV2Test extends RestBase {
    private int taxonId;

    @Deprecated
    @Test(  groups = {},
            description = "Получаем таксоны (подкатегории)")
    public void getTaxons() {
        response = TaxonsV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        List<TaxonV2> taxons = response.as(TaxonsV2Response.class).getTaxons();
        assertFalse(taxons.isEmpty(), "Не вернулись таксоны");
        taxonId = taxons.get(0).getId();
    }

    @Deprecated
    @Test(  groups = {},
            dependsOnMethods = "getTaxons",
            description = "Получаем таксон (подкатегорию)")
    public void getTaxon() {
        response = TaxonsV2Request.GET(taxonId, EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        assertNotNull(response.as(TaxonV2Response.class).getTaxon(), "Не вернулся таксон");
    }

    @Deprecated
    @Test(  groups = {},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "stores-parallel",
            description = "Получаем каждый таксон (подкатегорию) у каждого магазина")
    public void getTaxonsFromEachStore(StoreV2 store) {
        Set<Integer> taxonIds = apiV2.getTaxonIds(store.getId());
        log.debug("Taxon size {}", taxonIds.size());
        taxonIds.parallelStream().forEach(taxonId -> apiV2.getTaxon(taxonId, store.getId()));
    }

    @Deprecated
    @Test(  groups = {},
            description = "Несуществующий sid")
    public void testWithInvalidSid() {
        final Response response = TaxonsV2Request.GET(6666);
        checkStatusCode404(response);
    }

    @Deprecated
    @Test(  groups = {},
            description = "Информация о категории с невалидной категорией")
    public void testGetCategoryWithInvalidCategory() {
        final Response response = TaxonsV2Request.GET(0, EnvironmentProperties.DEFAULT_SID);
        checkStatusCode404(response);
    }

    @Deprecated
    @Test(  groups = {},
            dependsOnMethods = "getTaxons",
            description = "Информация о категории с валидной категорией и несуществующим sid")
    public void testGetCategoryWithInvalidSid() {
        final Response response = TaxonsV2Request.GET(taxonId, 6666);
        checkStatusCode404(response);
    }
}
