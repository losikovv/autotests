package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.model.common.ProductsFilterParams;
import ru.instamart.api.request.ApiV3RequestBase;
import ru.sbermarket.common.Mapper;

public class ProductsV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.Stores.StoreId.PRODUCTS)
    public static Response GET(ProductsFilterParams params, Integer storeId) {
        return givenMetroMarketPlace()
                .queryParams(Mapper.INSTANCE.objectToMap(params))
                .get(ApiV3Endpoints.Stores.StoreId.PRODUCTS, storeId);
    }
}
