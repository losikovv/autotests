package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.*;
import ru.instamart.api.request.v1.SettingsV1Request;
import ru.instamart.api.response.v1.*;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Конфигурация")
public class SettingsV1Test extends RestBase {

    private CompanySettingsV1 companySettings;
    private FeatureSettingsV1 featureSettings;
    private GeneralSettingsV1 generalSettings;
    private SmsSettingsV1 smsSettings;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authApiWithAdminNewRoles();
    }

    @CaseId(2535)
    @Skip(onServer = Server.STAGING)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о настройках компании")
    public void getCompanySettings() {
        final Response response = SettingsV1Request.CompanySettings.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CompanySettingsV1Response.class);
        companySettings = response.as(CompanySettingsV1Response.class).getCompanySettings();
        compareTwoObjects(companySettings.getPreferences().getCompanyName(), "ООО «Инстамарт Сервис»");
    }

    @CaseId(2536)
    @Skip(onServer = Server.STAGING)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование информации о настройках компании",
            dependsOnMethods = "getCompanySettings")
    public void editCompanySettings() {
        String companyBank = "Сбербанк" + Generate.literalString(6);
        companySettings.getPreferences().setCompanyBank(companyBank);
        final Response response = SettingsV1Request.CompanySettings.PATCH(companySettings);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CompanySettingsV1Response.class);
        CompanySettingsV1 companySettingsFromResponse = response.as(CompanySettingsV1Response.class).getCompanySettings();
        compareTwoObjects(companySettingsFromResponse.getPreferences().getCompanyBank(), companyBank);
    }

    @CaseId(2537)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о фича-флагах")
    public void getFeatureSettings() {
        final Response response = SettingsV1Request.FeatureSettings.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FeatureSettingsV1Response.class);
        featureSettings = response.as(FeatureSettingsV1Response.class).getFeatureSettings();
    }

    @CaseId(2538)
    @Skip(onServer = Server.STAGING)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование информации о фича-флагах",
            dependsOnMethods = "getFeatureSettings")
    public void editFeatureSettings() {
        featureSettings.setApplePayAdminSettings(!featureSettings.getApplePayAdminSettings());
        final Response response = SettingsV1Request.FeatureSettings.PATCH(featureSettings);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FeatureSettingsV1Response.class);
        FeatureSettingsV1 featureSettingsFromResponse = response.as(FeatureSettingsV1Response.class).getFeatureSettings();
        compareTwoObjects(featureSettingsFromResponse.getApplePayAdminSettings(), featureSettings.getApplePayAdminSettings());
    }

    @CaseId(2539)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации об общих настройках")
    public void getGeneralSettings() {
        final Response response = SettingsV1Request.GeneralSettings.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, GeneralSettingsV1Response.class);
        generalSettings = response.as(GeneralSettingsV1Response.class).getGeneralSettings();
    }

    @CaseId(2540)
    @Skip(onServer = Server.STAGING)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование информации об общих настройках",
            dependsOnMethods = "getGeneralSettings")
    public void editGeneralSettings() {
        String feedbackEmail = String.format("hello+%s@sbermarket.ru", RandomUtils.nextInt(1, 10));
        generalSettings.getEmails().setFeedbackEmail(feedbackEmail);
        final Response response = SettingsV1Request.GeneralSettings.PATCH(generalSettings);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, GeneralSettingsV1Response.class);
        compareTwoObjects(response.as(GeneralSettingsV1Response.class).getGeneralSettings().getEmails().getFeedbackEmail(), feedbackEmail);
    }

    @CaseId(2541)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о логистической плотности")
    public void getLogisticDensities() {
        final Response response = SettingsV1Request.LogisticDensities.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, LogisticDensitiesV1Response.class);
    }

    @CaseId(2542)
    @Skip(onServer = Server.STAGING)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование информации о логистической плотности")
    public void editLogisticDensities() {
        int density = RandomUtils.nextInt(1, 100);
        final Response response = SettingsV1Request.LogisticDensities.PATCH(density);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, LogisticDensitiesV1Response.class);
        compareTwoObjects(response.as(LogisticDensitiesV1Response.class).getLogisticDensities().getDefaultLogisticDensity(), (double) density);
    }


    @Skip(onServer = Server.STAGING)
    @CaseId(2543)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о настройках смс")
    public void getSmsSettings() {
        final Response response = SettingsV1Request.SmsSettings.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SmsSettingsV1Response.class);
        smsSettings = response.as(SmsSettingsV1Response.class).getSmsSettings();
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2544)
    @Story("Конфигурация системы")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование информации о настройках смс",
            dependsOnMethods = "getSmsSettings")
    public void editSmsSettings() {
        smsSettings.getBase().setSendShipmentCollectingViolationApologiesSms(!smsSettings.getBase().getSendShipmentCollectingViolationApologiesSms());
        final Response response = SettingsV1Request.SmsSettings.PATCH(smsSettings);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SmsSettingsV1Response.class);
        SmsSettingsV1 smsSettingsFromResponse = response.as(SmsSettingsV1Response.class).getSmsSettings();
        compareTwoObjects(smsSettingsFromResponse.getBase().getSendShipmentCollectingViolationApologiesSms(), smsSettings.getBase().getSendShipmentCollectingViolationApologiesSms());
    }
}
