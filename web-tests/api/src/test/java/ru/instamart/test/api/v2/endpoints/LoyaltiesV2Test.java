package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.NoSberIdV2;
import ru.instamart.api.model.v2.NotRegisteredV2;
import ru.instamart.api.model.v2.RegisteredV2;
import ru.instamart.api.request.v2.LoyaltiesV2Request;
import ru.instamart.api.response.v2.RegisteredV2Response;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic(value = "ApiV2")
@Feature(value = "Программа лояльности")
public class LoyaltiesV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @Story("Список программ лояльности")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверка статус сообщения об отключении программы лояльности")
    public void loyalities422() {
        final Response response = LoyaltiesV2Request.GET();
        checkStatusCode422(response);
        checkError(response, "Функционал сервиса Loyalty отключен");
    }

    @Story("Запрос информации о лояльности СберЛоялти")
    @Test(groups = {"api-instamart-regress"},
            description = "Информация о лояльности СберЛоялти")
    public void getSberLoyaltyInfo200() {
        final Response response = LoyaltiesV2Request.SberLoyaltyInfo.GET();
        checkStatusCode200(response);
        response.prettyPeek();
        final RegisteredV2Response registeredV2Response = response.as(RegisteredV2Response.class);
        final SoftAssert softAssert = new SoftAssert();

        RegisteredV2 registered = registeredV2Response.getRegistered();
        softAssert.assertEquals(registered.getTitle(), "Бонусы от СберСпасибо", "Наименование программы не совпадает");
        softAssert.assertTrue(registered.getDescription().isEmpty(), "Описание программы не совпадает");
        softAssert.assertEquals(registered.getButtonTitle(), "Как это работает", "button_title не совпадает");
        softAssert.assertTrue(registered.getBanners().size() > 0, "Банеры пришли пустые");
        softAssert.assertFalse(registered.getUrls().getPersonalAgreementUrl().isEmpty(), "personal_agreement_url пришел пустым");
        softAssert.assertFalse(registered.getUrls().getMarketingAgreementUrl().isEmpty(), "marketing_agreement_url пришел пустым");
        softAssert.assertFalse(registered.getUrls().getHowToUrl().isEmpty(), "how_to_url пришел пустым");

        NotRegisteredV2 notRegistered = registeredV2Response.getNotRegistered();
        softAssert.assertEquals(notRegistered.getTitle(), "СберСпасибо помогает экономить", "Наименование программы не совпадает");
        softAssert.assertEquals(notRegistered.getDescription(), "Бонусы появятся после заказа", "description программы не совпадает");
        softAssert.assertEquals(notRegistered.getButtonTitle(), "Участвовать", "button_title программы не совпадает");
        softAssert.assertTrue(notRegistered.getBanners().size() > 0, "Банеры пришли пустые");
        softAssert.assertFalse(notRegistered.getUrls().getPersonalAgreementUrl().isEmpty(), "personal_agreement_url пришел пустым");
        softAssert.assertFalse(notRegistered.getUrls().getMarketingAgreementUrl().isEmpty(), "marketing_agreement_url пришел пустым");
        softAssert.assertFalse(notRegistered.getUrls().getHowToUrl().isEmpty(), "how_to_url пришел пустым");

        NoSberIdV2 noSberId = registeredV2Response.getNoSberId();
        softAssert.assertEquals(noSberId.getTitle(), "Безопасный вход по Сбер ID", "Наименование программы не совпадает");
        softAssert.assertEquals(noSberId.getDescription(), "Чтобы покупать выгоднее и копить бонусы", "description программы не совпадает");
        softAssert.assertEquals(noSberId.getButtonTitle(), "Войти по Сбер ID", "button_title программы не совпадает");
        softAssert.assertTrue(noSberId.getBanners().size() > 0, "Банеры пришли пустые");
        softAssert.assertFalse(noSberId.getUrls().getPersonalAgreementUrl().isEmpty(), "personal_agreement_url пришел пустым");
        softAssert.assertFalse(noSberId.getUrls().getMarketingAgreementUrl().isEmpty(), "marketing_agreement_url пришел пустым");
        softAssert.assertFalse(noSberId.getUrls().getHowToUrl().isEmpty(), "how_to_url пришел пустым");

        softAssert.assertAll();
    }


}
