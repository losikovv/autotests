package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.ApiV1RequestBase;

public class ShoppingContextV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.SHOPPING_CONTEXT)
    public static Response PUT(AddressV2 address, String shippingMethod, Integer storeId) {
        JSONObject body = new JSONObject();
        JSONObject shoppingContext = new JSONObject();
        JSONObject shippingAddress = new JSONObject();
        shippingAddress.put("lat", address.getLat());
        shippingAddress.put("lon", address.getLon());
        shippingAddress.put("street", address.getStreet());
        shippingAddress.put("building", address.getBuilding());
        shippingAddress.put("full_address", address.getFullAddress());
        shippingAddress.put("city", address.getCity());
        shippingAddress.put("city_in", "");
        shoppingContext.put("ship_address", shippingAddress);
        shoppingContext.put("shipping_method_kind", shippingMethod);
        body.put("shopping_context", shoppingContext);
        if(storeId != null) body.put("store_id", storeId);

        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.SHOPPING_CONTEXT);
    }

    @Step("{method} /" + ApiV1Endpoints.SHOPPING_CONTEXT)
    public static Response PUT(AddressV2 address, String shippingMethod) {
        return PUT(address, shippingMethod, null);
    }
}
