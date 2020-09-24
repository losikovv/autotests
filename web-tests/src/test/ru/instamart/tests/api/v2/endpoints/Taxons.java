package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.Requests;
import instamart.api.common.RestBase;
import instamart.api.objects.Taxon;
import instamart.api.objects.responses.TaxonResponse;
import instamart.api.objects.responses.TaxonsResponse;
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
        response = Requests.getTaxons(1);

        assertEquals(response.getStatusCode(), 200);
        List<Taxon> taxons = response.as(TaxonsResponse.class).getTaxons();
        assertNotNull(taxons, "Не вернулись таксоны");
        taxonId = taxons.get(0).getId();
    }

    @Test(  description = "Получаем таксоны (подкатегории)",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 11,
            dependsOnMethods = "getTaxons")
    public void getTaxon() {
        response = Requests.getTaxons(taxonId,1);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(TaxonResponse.class).getTaxon(), "Не вернулся таксон");
    }
}
