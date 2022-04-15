package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.GeneralSettingsAdminRequest;
import ru.instamart.jdbc.dao.stf.SpreePreferencesDao;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;

@Epic("Admin")
@Feature("Основные настройки")
public class GeneralSettingsAdminTest extends RestBase {

    @CaseId(1829)
    @Test(groups = {"api-instamart-regress"}, description = "Изменение основных настроек")
    public void updateGeneralSettings() {
        admin.auth();
        GeneralSettingsAdminRequest.Settings  settings = GeneralSettingsAdminRequest.Settings.builder()
                .feedbackEmail(String.format("hello+%s@sbermarket.ru", RandomUtils.nextInt(1, 10)))
                .linkFaq("https://sbermarket.ru/" + RandomUtils.nextInt(1, 10))
                .appReviewIntervalRequest(RandomUtils.nextInt(1, 10))
                .appReviewIntervalCrash(RandomUtils.nextInt(1, 10))
                .appReviewIntervalNetworkError(RandomUtils.nextInt(1, 10))
                .supportTelegram("https://t.me/SberMarket_chat_bot" + RandomUtils.nextInt(1, 10))
                .appAndroidAnalyticsUrl("https://sbermarket.ru/" + RandomUtils.nextInt(1, 10))
                .build();
        final Response response = GeneralSettingsAdminRequest.POST(settings);
        checkStatusCode302(response);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(settings.getFeedbackEmail(), SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("feedback_email"), softAssert);
        compareTwoObjects(settings.getLinkFaq(), SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("link_faq"), softAssert);
        compareTwoObjects(settings.getAppReviewIntervalRequest().toString(), SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("app_review_interval_request"), softAssert);
        compareTwoObjects(settings.getAppReviewIntervalCrash().toString(), SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("app_review_interval_crash"), softAssert);
        compareTwoObjects(settings.getAppReviewIntervalNetworkError().toString(), SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("app_review_interval_network_error"), softAssert);
        compareTwoObjects(settings.getSupportTelegram(), SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("support_telegram"), softAssert);
        compareTwoObjects(settings.getAppAndroidAnalyticsUrl(), SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("app_android_analytics_url"), softAssert);
        checkFieldIsNotEmpty(SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("default_seo_title"), "заголовок СЕО", softAssert);
        checkFieldIsNotEmpty(SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("default_meta_description"), "описание", softAssert);
        checkFieldIsNotEmpty(SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("content_email"), "email службы контента", softAssert);
        checkFieldIsNotEmpty(SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("facebook_page"), "ссылка на Facebook", softAssert);
        checkFieldIsNotEmpty(SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("vkontakte_page"), "ссылка на VK", softAssert);
        checkFieldIsNotEmpty(SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("twitter_page"), "ссылка на Twitter", softAssert);
        checkFieldIsNotEmpty(SpreePreferencesDao.INSTANCE.getPreferencesKeyByValue("instagram_page"), "ссылка на Instagram", softAssert);
        softAssert.assertAll();
    }
}
