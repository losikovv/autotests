package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;
import ru.instamart.kraken.data.StaticPageData;

import java.util.HashMap;
import java.util.Map;

public class PagesAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.PAGES)
    public static Response POST(StaticPageData data) {
        Map<String, String> params = new HashMap<>();
        params.put("page[title]", data.getPageName());
        params.put("page[slug]", data.getPageURL());
        params.put("page[body]", data.getDescription());
        params.put("page[meta_title]", data.getText());
        params.put("page[meta_description]", data.getText());
        params.put("page[meta_keywords]", data.getText());
        params.put("page[foreign_link]", data.getPageURL());
        params.put("page[position]", data.getPosition());
        return givenWithAuth()
                .formParams(params)
                .post(AdminEndpoints.PAGES);
    }

    @Step("{method} /" + AdminEndpoints.PAGE)
    public static Response DELETE(Integer pageId) {
        return givenWithAuth()
                .formParam("_method", "delete")
                .delete(AdminEndpoints.PAGE, pageId);
    }
}
