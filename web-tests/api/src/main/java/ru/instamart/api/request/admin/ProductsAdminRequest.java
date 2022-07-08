package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;

import java.util.HashMap;
import java.util.Map;

public class ProductsAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.Products.BY_PERMALINK)
    public static Response GET(final String permalink) {
        return givenWithAuth()
                .get(AdminEndpoints.Products.BY_PERMALINK, permalink);
    }

    @Step("{method} /" + AdminEndpoints.PRODUCTS)
    public static Response GET(final String name,
                               final String brandIds,
                               final String taxonIds,
                               final String ids,
                               final String skus,
                               final String ean,
                               final String includingDeleted,
                               final String page,
                               final String perPage) {
        final Map<String, Object> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("brand_ids", brandIds);
        formParams.put("taxon_ids", taxonIds);
        formParams.put("ids", ids);
        formParams.put("skus", skus);
        formParams.put("ean", ean);
        formParams.put("including_deleted", includingDeleted);
        formParams.put("page", page);
        formParams.put("per_page", perPage);
        return givenWithAuth()
                .formParams(formParams)
                .get(AdminEndpoints.PRODUCTS);
    }

    public static final class Preferences {

        @Step("{method} /" + AdminEndpoints.Products.REFERENCES)
        public static Response GET() {
            return givenWithAuth()
                    .get(AdminEndpoints.Products.REFERENCES);
        }
    }
}
