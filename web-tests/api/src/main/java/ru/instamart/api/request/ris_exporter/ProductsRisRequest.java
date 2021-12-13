package ru.instamart.api.request.ris_exporter;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.RisExporterEndpoints;
import ru.instamart.api.request.RisExporterRequestBase;

public class ProductsRisRequest extends RisExporterRequestBase {

    @Step("{method} /" + RisExporterEndpoints.Stores.Catalog.PRODUCTS)
    public static Response GET(final int sid) {
        return givenWithAuth()
                .get(RisExporterEndpoints.Stores.Catalog.PRODUCTS, sid);
    }
}
