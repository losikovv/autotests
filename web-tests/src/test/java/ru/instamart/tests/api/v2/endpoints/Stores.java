package ru.instamart.tests.api.v2.endpoints;

import org.testng.annotations.Test;
import ru.instamart.application.rest.Requests;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.objects.responses.StoreResponse;
import ru.instamart.application.rest.objects.responses.StoresResponse;

import static org.testng.Assert.*;

public class Stores extends RestBase {

    @Test(  description = "Получаем магазин",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 1)
    public void getStore() {
        response = Requests.getStores(1);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(StoreResponse.class).getStore(), "Не вернулся магазин");
    }

    @Test(  description = "Получаем список всех магазинов",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 7)
    public void getAllStores() {
        response = Requests.getStores();

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(StoresResponse.class).getStores(), "Не вернулись магазины");
    }

}
