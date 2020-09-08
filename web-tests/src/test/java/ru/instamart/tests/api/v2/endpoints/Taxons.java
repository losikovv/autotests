package ru.instamart.tests.api.v2.endpoints;

import org.testng.annotations.Test;
import ru.instamart.application.rest.Requests;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.objects.responses.TaxonsResponse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Taxons extends RestBase {

    @Test(  description = "Получаем таксоны (подкатегории)",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 6)
    public void getTaxons() {
        response = Requests.getTaxons(1);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(TaxonsResponse.class).getTaxons(), "Не вернулись таксоны");
    }
}
