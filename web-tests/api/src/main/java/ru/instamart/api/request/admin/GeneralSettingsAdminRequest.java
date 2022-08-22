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
import ru.instamart.kraken.common.Mapper;

import java.util.HashMap;
import java.util.Map;

public class GeneralSettingsAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.GENERAL_SETTINGS)
    public static Response POST(Settings settings) {
        Map<String, Object> params = new HashMap<>();
        params.put("_method", "put");
        params.putAll(Mapper.INSTANCE.objectToMap(settings));
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(params))
                .post(AdminEndpoints.GENERAL_SETTINGS);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Settings {
        @JsonProperty("default_seo_title")
        private String defaultSeoTitle;
        @JsonProperty("default_meta_description")
        private String defaultMetaDescription;
        @JsonProperty("feedback_email")
        private String feedbackEmail;
        @JsonProperty("press_email")
        private String pressEmail;
        @JsonProperty("content_email")
        private String contentEmail;
        @JsonProperty("b2b_email")
        private String b2bEmail;
        @JsonProperty("orders_email")
        private String ordersEmail;
        @JsonProperty("facebook_page")
        private String facebookPage;
        @JsonProperty("vkontakte_page")
        private String vkontaktePage;
        @JsonProperty("twitter_page")
        private String twitterPage;
        @JsonProperty("ok_page")
        private String okPage;
        @JsonProperty("instagram_page")
        private String instagramPage;
        @JsonProperty("support_telegram")
        private String supportTelegram;
        @JsonProperty("support_whatsapp")
        private String supportWhatsapp;
        @JsonProperty("link_faq")
        private String linkFaq;
        @JsonProperty("link_rules")
        private String linkRules;
        @JsonProperty("link_terms")
        private String linkTerms;
        @JsonProperty("link_personal_data_processing_policy")
        private String linkPersonalDataProcessingPolicy;
        @JsonProperty("link_sberprime_faq")
        private String linkSberprimeFaq;
        @JsonProperty("app_review_mode")
        private String appReviewMode;
        @JsonProperty("app_review_interval_request")
        private Integer appReviewIntervalRequest;
        @JsonProperty("app_review_interval_crash")
        private Integer appReviewIntervalCrash;
        @JsonProperty("app_review_interval_network_error")
        private Integer appReviewIntervalNetworkError;
        @JsonProperty("app_review_app_store_link")
        private String appReviewAppStoreLink;
        @JsonProperty("app_review_google_play_link")
        private String appReviewGooglePlayLink;
        @JsonProperty("app_review_app_gallery_link")
        private String appReviewAppGalleryLink;
        @JsonProperty("app_ios_min_version")
        private String appIosMinVersion;
        @JsonProperty("app_ios_recommended_version")
        private String appIosRecommendedVersion;
        @JsonProperty("app_android_min_version")
        private String appAndroidMinVersion;
        @JsonProperty("app_android_recommended_version")
        private String appAndroidRecommendedVersion;
        @JsonProperty("app_ios_analytics_url")
        private String appIosAnalyticsUrl;
        @JsonProperty("app_ios_analytics_key")
        private String appIosAnalyticsKey;
        @JsonProperty("app_android_analytics_url")
        private String appAndroidAnalyticsUrl;
        @JsonProperty("app_android_analytics_key")
        private String appAndroidAnalyticsKey;
        private String currency;
    }
}
