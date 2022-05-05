package ru.instamart.api.request.v1.admin;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.model.v1.AdminStoreLabelsItemV1;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.sbermarket.common.Mapper;

public class StoreLabelsAdminV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.StoreLabels.STORE_LABELS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.StoreLabels.STORE_LABELS);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.StoreLabels.STORE_LABELS)
    public static Response POST(AdminStoreLabelsItemV1 storeLabel) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(Mapper.INSTANCE.objectToString(storeLabel))
                .post(ApiV1Endpoints.Admin.StoreLabels.STORE_LABELS);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.StoreLabels.STORE_LABEL)
    public static Response DELETE(int storeLabelId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.Admin.StoreLabels.STORE_LABEL, storeLabelId);
    }
}
