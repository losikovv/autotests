package ru.instamart.tests.api.v2.endpoints;

import instamart.api.action.Taxons;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.Store;
import instamart.api.objects.v2.Taxon;
import instamart.api.responses.v2.TaxonResponse;
import instamart.api.responses.v2.TaxonsResponse;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qase.api.annotation.CaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static org.testng.Assert.assertNotNull;

public class TaxonsTest extends RestBase {

    private static final Logger log = LoggerFactory.getLogger(TaxonsTest.class);

    private int taxonId;

    @CaseId(11)
    @Test(  description = "Получаем таксоны (подкатегории)",
            groups = {"api-instamart-smoke"})
    public void getTaxons() {
        response = Taxons.GET(EnvironmentData.INSTANCE.getDefaultSid());
        assertStatusCode200(response);
        List<Taxon> taxons = response.as(TaxonsResponse.class).getTaxons();
        assertNotNull(taxons, "Не вернулись таксоны");
        taxonId = taxons.get(0).getId();
    }

    @CaseId(6)
    @Test(  description = "Получаем таксон (подкатегорию)",
            groups = {"api-instamart-smoke"},
            dependsOnMethods = "getTaxons")
    public void getTaxon() {
        response = Taxons.GET(taxonId, EnvironmentData.INSTANCE.getDefaultSid());
        assertStatusCode200(response);
        assertNotNull(response.as(TaxonResponse.class).getTaxon(), "Не вернулся таксон");
    }

    @CaseId(6)
    @Test(  description = "Получаем каждый таксон (подкатегорию) у каждого магазина",
            groups = {},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "stores-parallel")
    public void getTaxonsFromEachStore(Store store) {
        Set<Integer> taxonIds = apiV2.getTaxonIds(store.getId());
        log.info("Taxon size {}", taxonIds.size());
        taxonIds.parallelStream().forEach(taxonId -> apiV2.getTaxon(taxonId, store.getId()));
    }
}
