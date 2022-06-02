package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;

public class PromotionCodesAdminRequest extends AdminRequestBase {


    @Step("{method} /" + AdminEndpoints.PROMOTION_CODES)
    public static Response GET() {
        return givenWithAuthAndSpa()
                .get(AdminEndpoints.PROMOTION_CODES);
    }


}
