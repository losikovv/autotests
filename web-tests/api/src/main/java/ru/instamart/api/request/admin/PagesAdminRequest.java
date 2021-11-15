package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.kraken.data.StaticPageData;

import java.util.HashMap;
import java.util.Map;

import static ru.instamart.api.request.AdminRequestBase.givenWithAuth;

public class PagesAdminRequest {

    @Step("{method} /" + AdminEndpoints.PAGES)
    public static Response POST(StaticPageData data) {
        Map<String, String> params = new HashMap<>();
        params.put("utf-8", "✓");
        params.put("authenticity_token", SessionFactory.getSession(SessionType.ADMIN).getToken());
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
        Map<String, String> params = new HashMap<>();
        params.put("authenticity_token", SessionFactory.getSession(SessionType.ADMIN).getToken());
        params.put("_method", "delete");
        return givenWithAuth()
                .formParams(params)
                .delete(AdminEndpoints.PAGE, pageId);
    }
}
