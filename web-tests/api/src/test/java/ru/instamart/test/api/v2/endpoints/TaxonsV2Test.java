package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
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

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Slf4j
@Epic("ApiV2")
@Feature("Категории товаров (taxons)")
public final class TaxonsV2Test extends RestBase {
    private int taxonId;

    @CaseId(249)
    @Test(  groups = {"api-instamart-regress"},
            description = "Получаем таксоны (подкатегории)")
    public void getTaxons() {
        Response response = TaxonsV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TaxonsV2Response.class);
        List<TaxonV2> taxons = response.as(TaxonsV2Response.class).getTaxons();
        taxonId = taxons.get(0).getId();
    }

    @CaseId(249)
    @Test(  groups = {"api-instamart-regress"},
            dependsOnMethods = "getTaxons",
            description = "Получаем таксон (подкатегорию)")
    public void getTaxon() {
        Response response = TaxonsV2Request.GET(taxonId, EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TaxonV2Response.class);
    }

    // пока отключен, так как слишком затратно пробегаться по всем таксонам (10-15 минут)
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
