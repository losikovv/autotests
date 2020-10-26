package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.requests.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.Taxon;
import instamart.api.responses.v2.TaxonResponse;
import instamart.api.responses.v2.TaxonsResponse;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Taxons extends RestBase {
    private int taxonId;

    @Test(  description = "Получаем таксоны (подкатегории)",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 6)
    public void getTaxons() {
        response = ApiV2Requests.getTaxons(1);
        ApiV2Checkpoints.assertStatusCode200(response);
        List<Taxon> taxons = response.as(TaxonsResponse.class).getTaxons();
        assertNotNull(taxons, "Не вернулись таксоны");
        taxonId = taxons.get(0).getId();
    }

    @Test(  description = "Получаем таксоны (подкатегории)",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 11,
            dependsOnMethods = "getTaxons")
    public void getTaxon() {
        response = ApiV2Requests.getTaxons(taxonId,1);
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(TaxonResponse.class).getTaxon(), "Не вернулся таксон");
    }
}
