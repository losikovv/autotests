package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v3.CheckoutOrderV3;
import ru.instamart.api.model.v3.ErrorV3;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v3.ErrorsV3Response;
import ru.instamart.api.response.v3.OrderV3Response;
import ru.instamart.jdbc.dao.stf.PromotionCodesDao;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.jdbc.dto.stf.PromotionCodesFilters;
import ru.instamart.jdbc.entity.stf.PromotionCodesEntity;
import ru.instamart.jdbc.entity.stf.SpreeOrdersEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import java.util.Collections;
import java.util.List;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.ApiV3Checkpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.PromotionCode.getExpiredPromotionCode;
import static ru.instamart.api.helper.PromotionCode.getPromotionCode;

@Epic("ApiV3")
@Feature("Чекаут")
public class CheckoutPromotionsV3Test extends RestBase {

    private UserData user;
    private MultiretailerOrderV1Response order;
    private String promoСode;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        AddressV2 addressDefaultSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        long offerDefaultSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId, EnvironmentProperties.DEFAULT_SID);
        order = apiV1.getMultiRetailerOrder();
        promoСode = getPromotionCode();
    }

    @TmsLink("2607")
    @Story("Промокоды")
    @Test(description = "Запрос на применение промокода",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"})
    public void addPromoСode() {
        final Response response = CheckoutV3Request.Promotions.POST(order.getNumber(), promoСode, Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        CheckoutOrderV3 orderFromResponse = response.as(OrderV3Response.class).getOrder();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(orderFromResponse.getPromotionCodes().get(0).getValue(), promoСode, softAssert);
        compareTwoObjects(orderFromResponse.getNumber(), order.getNumber(), softAssert);
        softAssert.assertAll();
    }

    @TmsLink("2608")
    @Story("Промокоды")
    @Test(description = "Запрос на применение истекшего промокода",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"})
    public void addExpiredPromoСode() {
        String promoСode = getExpiredPromotionCode();
        final Response response = CheckoutV3Request.Promotions.POST(order.getNumber(), promoСode, Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode422(response);
        checkError(response, "promotion_errors", "Данный промокод истек");
    }

    @TmsLink("2610")
    @Story("Промокоды")
    @Test(description = "Запрос на применение промокода к чужому заказу",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"})
    public void addPromoСodeToAnotherUserOrder() {
        SpreeOrdersEntity anotherOrder = SpreeOrdersDao.INSTANCE.getOrderOfAnotherUser(Long.parseLong(user.getId()));
        final Response response = CheckoutV3Request.Promotions.POST(anotherOrder.getNumber(), promoСode, Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode403(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Пользователь не может выполнить это действие");
    }

    @TmsLink("2611")
    @Story("Промокоды")
    @Test(description = "Запрос на применение промокода к чужому шипменту",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"})
    public void addPromoСodeToAnotherUserShipment() {
        String anotherShipmentNumber = SpreeShipmentsDao.INSTANCE.getShipmentOfAnotherUser(Long.parseLong(user.getId())).getNumber();
        final Response response = CheckoutV3Request.Promotions.POST(order.getNumber(), promoСode, Collections.singletonList(anotherShipmentNumber));
        checkStatusCode422(response);
        checkError(response, "invalid_current_shipments", "Вы уже оформляете заказ в другом магазине");
    }

    @TmsLink("2612")
    @Story("Промокоды")
    @Test(description = "Запрос на применение несуществующего промокода",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"})
    public void addNonExistentPromoСode() {
        final Response response = CheckoutV3Request.Promotions.POST(order.getNumber(), "failedPromoCode", Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode422(response);
        checkError(response, "promotion_errors", "Промокод не существует");
    }

    @TmsLink("2613")
    @Story("Промокоды")
    @Test(description = "Запрос на применение нескольких прокодов",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"})
    public void addSeveralPromoСodes() {
        List<PromotionCodesEntity> promoCodes = PromotionCodesDao.INSTANCE.findAll(PromotionCodesFilters.builder()
                .value("auto%")
                .limit(5000)
                .build());
        final Response responseWithFirstPromo = CheckoutV3Request.Promotions.POST(order.getNumber(), promoCodes.get(0).getValue(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode200(responseWithFirstPromo);
        compareTwoObjects(responseWithFirstPromo.as(OrderV3Response.class).getOrder().getPromotionCodes().get(0).getValue(), promoCodes.get(0).getValue());
        final Response responseWithSecondPromo = CheckoutV3Request.Promotions.POST(order.getNumber(), promoCodes.get(1).getValue(), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode200(responseWithSecondPromo);
        compareTwoObjects(responseWithSecondPromo.as(OrderV3Response.class).getOrder().getPromotionCodes().get(0).getValue(), promoCodes.get(1).getValue());
    }

    @TmsLink("2615")
    @Story("Промокоды")
    @Test(description = "Запрос на применение промокода, когда не выполнены условия для применения",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"})
    public void addPromoСodeWithIncorrectConditions() {
        String promoСode = PromotionCodesDao.INSTANCE.findAll(PromotionCodesFilters.builder()
                .promotionId(53266)
                .limit(1)
                .build()).get(0).getValue();
        final Response response = CheckoutV3Request.Promotions.POST(order.getNumber(), promoСode, Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode422(response);
        checkError(response, "promotion_errors", "Код работает с заказами от 3 000 ₽. Наберите еще немного.");
    }

    @TmsLink("2619")
    @Story("Промокоды")
    @Test(description = "Удаление промокода",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"},
            priority = 1)
    public void removePromoСode() {
        final Response response = CheckoutV3Request.PromotionRemove.POST(order.getNumber(), promoСode, Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        compareTwoObjects(response.as(OrderV3Response.class).getOrder().getPromotionCodes().size(), 0);
    }

    @TmsLink("2616")
    @Story("Промокоды")
    @Test(description = "Удаление промокода по чужому заказу",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"},
            priority = 100)
    public void removeAnotherUserPromoСode() {
        apiV1.authByPhone(UserManager.getQaUser());
        final Response response = CheckoutV3Request.PromotionRemove.POST(order.getNumber(), promoСode, Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode403(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Пользователь не может выполнить это действие");
    }

    @TmsLink("2990")
    @Story("Промокоды")
    @Test(description = "Удаление несуществующего промокода",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"},
            priority = 2)
    public void removePromoCodeNotFound() {
        final Response response = CheckoutV3Request.PromotionRemove.POST(order.getNumber(), Generate.literalString(8), Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode404(response);
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        compareTwoObjects(errors.get(0).getMessage(), "Промокод не существует");
    }

    @TmsLink("2614")
    @Story("Промокоды")
    @Test(description = "Запрос на применение промокода без авторизации",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"},
            priority = 100)
    public void addPromoCodeWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = CheckoutV3Request.Promotions.POST(order.getNumber(), promoСode, Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode401(response);
    }

    @TmsLink("2618")
    @Story("Промокоды")
    @Test(description = "Удаление промокода без авторизации",
            groups = {API_INSTAMART_REGRESS, "debug", "api-v3"},
            priority = 100)
    public void deletePromoCodeWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = CheckoutV3Request.PromotionRemove.POST(order.getNumber(), promoСode, Collections.singletonList(order.getShipments().get(0).getNumber()));
        checkStatusCode401(response);
    }
}
