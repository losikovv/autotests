package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.RetailerPromoCardV1;
import ru.instamart.api.request.v1.NextV1Request;
import ru.instamart.api.response.v1.*;
import ru.instamart.jdbc.dao.stf.PromoCardsRetailersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.api.helper.PromotionCode.getPromotionCode;

@Epic("ApiV1")
@Feature("Next")
public class NextV1Tests extends RestBase {

    @CaseId(2317)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение фронтенд конфигурации")
    public void getAppConfig() {
        final Response response = NextV1Request.AppConfig.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AppConfigV1Response.class);
    }

    @CaseId(2318)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение динамического пути для next js роутинга")
    public void getDynamicRouteType() {
        final Response response = NextV1Request.DynamicRouteType.GET("metro?sid=" + EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
    }

    @CaseId(2319)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение данных для серверного рендеринга / страницы")
    public void getPageServer() {
        final Response response = NextV1Request.Server.GET("metro?sid=" + EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PageServerV1Response.class);
    }

    @CaseId(2320)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение данных для серверного рендеринга / футер")
    public void getPageFooter() {
        final Response response = NextV1Request.Footer.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PageFooterV1Response.class);
        PageFooterV1Response pageFooter = response.as(PageFooterV1Response.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(pageFooter.getComponent(), "Footer", softAssert);
        compareTwoObjects(pageFooter.getConfig().getCompanyName(), "ООО «Инстамарт Сервис»", softAssert);
        softAssert.assertAll();
    }

    @CaseId(2321)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение данных для head из браузера")
    public void getPageBrowserHead() {
        final Response response = NextV1Request.BrowserHead.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PageBrowserHeadV1Response.class);
    }

    @CaseId(2322)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение данных для рельсовых flash из браузера")
    public void getFlashes() {
        final Response response = NextV1Request.Flashes.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PageFlashesV1Response.class);
    }

    @CaseId(2323)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение данных для страницы реферальной программы")
    public void getReferralProgram() {
        final Response response = NextV1Request.ReferralProgram.GET(getPromotionCode());
        checkStatusCode200(response);
    }

    @CaseId(2324)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение промо-карточек ретейлера")
    public void getRetailerPromoCards() {
        final Response response = NextV1Request.RetailerPromoCards.GET(1L);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerPromoCardsV1Response.class);
        List<RetailerPromoCardV1> retailerPromoCards = response.as(RetailerPromoCardsV1Response.class).getPromoCards();
        int retailerPromoCardsFromDbCount = PromoCardsRetailersDao.INSTANCE.getCountByRetailerId(1L);
        compareTwoObjects(retailerPromoCards.size(), retailerPromoCardsFromDbCount);
    }

    @CaseId(2325)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение промо-карточек несуществующего ретейлера")
    public void getNonExistentRetailerPromoCards() {
        final Response response = NextV1Request.RetailerPromoCards.GET(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2326)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение Welcome-баннеров ретейлера")
    public void getWelcomeBanners() {
        final Response response = NextV1Request.RetailerWelcomeBanners.GET(1L, "metro");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerWelcomeBannerV1Response.class);
    }

    @CaseId(2327)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение Welcome-баннеров несуществующего ретейлера")
    public void getWelcomeBannersOfNonExistentRetailer() {
        final Response response = NextV1Request.RetailerWelcomeBanners.GET(0L, "metro");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2328)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение продукта с категориями и настройками для несуществующего permalink")
    public void getProductByNonExistentPermalink() {
        final Response response = NextV1Request.Permalink.GET("permalink");
        checkStatusCode404(response); // TODO: поправить, если получится узнать, как получить ответ (сейчас для существующего permalink не работает)
    }

    @CaseId(2329)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение параметров редиректа для несуществующего permalink")
    public void getRedirectParamsByNonExistentPermalink() {
        final Response response = NextV1Request.RedirectParams.GET("permalink");
        checkStatusCode404(response); // TODO: поправить, если получится узнать, как получить ответ (сейчас для существующего permalink не работает)
    }

    @CaseId(2330)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение сессии")
    public void getSession() {
        final Response response = NextV1Request.Session.GET("metro?sid=" + EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, NextSessionV1Response.class);
    }

    @CaseId(2331)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка активных трекеров")
    public void getTrackers() {
        final Response response = NextV1Request.Trackers.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TrackersV1Response.class);
    }
}
