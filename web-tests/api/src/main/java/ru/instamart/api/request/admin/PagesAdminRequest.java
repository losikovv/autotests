package ru.instamart.api.request.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;
import ru.sbermarket.common.Mapper;

import java.util.HashMap;
import java.util.Map;

public class PagesAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.PAGES)
    public static Response POST(Page page) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(page))
                .post(AdminEndpoints.PAGES);
    }

    @Step("{method} /" + AdminEndpoints.PAGE)
    public static Response DELETE(Long pageId) {
        return givenWithAuth()
                .formParam("_method", "delete")
                .delete(AdminEndpoints.PAGE, pageId);
    }

    @Step("{method} /" + AdminEndpoints.PAGE)
    public static Response PATCH(Page page, Long pageId) {
        Map<String, Object> params = new HashMap<>();
        params.put("_method", "patch");
        params.putAll(Mapper.INSTANCE.objectToMap(page));
        return givenWithAuth()
                .formParams(params)
                .patch(AdminEndpoints.PAGE, pageId);
    }

    @Step("{method} /" + AdminEndpoints.PAGES)
    public static Response GET() {
        return givenWithAuthAndSpa()
                .get(AdminEndpoints.PAGES);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Page {
        @JsonProperty("page[title]")
        private String title;
        @JsonProperty("page[slug]")
        private String slug;
        @JsonProperty("page[body]")
        private String body;
        @JsonProperty("page[meta_title]")
        private String metaTitle;
        @JsonProperty("page[meta_keywords]")
        private String metaKeywords;
        @JsonProperty("page[meta_description]")
        private String metaDescription;
        @JsonProperty("page[layout]")
        private String layout;
        @JsonProperty("page[foreign_link]")
        private String foreignLink;
        @JsonProperty("page[position]")
        private Integer position;
        @JsonProperty("page[show_in_sidebar]")
        private Integer showInSidebar;
        @JsonProperty("page[show_in_header]")
        private Integer showInHeader;
        @JsonProperty("page[show_in_footer]")
        private Integer showInFooter;
        @JsonProperty("page[visible]")
        private Integer visible;
        @JsonProperty("page[render_layout_as_partial]")
        private Integer renderLayoutAsPartial;
    }
}
