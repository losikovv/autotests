package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.v1.OrdersV1Request;
import ru.instamart.api.request.v1.PromotionsV1Request;
import ru.instamart.api.response.v1.CompensationPromotionsV1Response;
import ru.instamart.api.response.v1.CompensationV1Response;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v1.NewCompensationsV1Response;
import ru.instamart.jdbc.dao.OrderCompensationsDao;
import ru.instamart.jdbc.entity.OrderCompensationsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Optional;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkErrorText;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkOrderCompensations;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.K8sHelper.*;

@Epic("ApiV1")
@Feature("Заказы")
public class OrderCompensationsV1Tests extends RestBase {

    private MultiretailerOrderV1Response order;
    private Long compensationPromotionsId;
    private Long reasonId;
    private Long promoTypeId;
    private OrdersV1Request.Compensation compensation;
    private Long orderCompensationId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
        newAdminRoles(true);
        deleteOrderCompensationsCache();
        createCompensationPromotions();
        AddressV2 address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        Long offerId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        apiV1.fillCart(address, ShippingMethodV2.BY_COURIER.getMethod(), offerId);
        order = apiV1.getMultiRetailerOrder();
    }

    @CaseId(2246)
    @Story("Компенсации")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о промо-компенсациях")
    public void getCompensationPromotions() {
        final Response response = PromotionsV1Request.CompensationPromotions.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CompensationPromotionsV1Response.class);
        compensationPromotionsId =  response.as(CompensationPromotionsV1Response.class).getCompensationPromotions().get(0).getId();
    }

    @CaseId(2247)
    @Story("Компенсация по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о доступной компенсациях для заказа")
    public void getCompensationsInfo() {
        final Response response = OrdersV1Request.Compensations.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, NewCompensationsV1Response.class);
        NewCompensationsV1Response compensations = response.as(NewCompensationsV1Response.class);
        reasonId = compensations.getReasons().get(0).getId();
        promoTypeId = compensations.getPromoTypes().get(0).getId();
    }

    @CaseId(2247)
    @Story("Компенсация по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о доступной компенсациях для несуществующего заказа")
    public void getCompensationsInfoForNonExistentOrder() {
        final Response response = OrdersV1Request.Compensations.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2248)
    @Story("Компенсация по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание компенсации",
            dependsOnMethods = {"getCompensationPromotions", "getCompensationsInfo"})
    public void createCompensation() {
        compensation = OrdersV1Request.Compensation.builder()
                .compensation(OrdersV1Request.CompensationParams.builder()
                        .email(UserManager.getDefaultAdmin().getEmail())
                        .emailBody("Autotest-" + Generate.literalString(6))
                        .promotionId(compensationPromotionsId)
                        .promoType(promoTypeId)
                        .reason(reasonId)
                        .build())
                .build();
        final Response response = OrdersV1Request.Compensations.POST(order.getNumber(), compensation);
        checkStatusCode(response, 201);
        checkResponseJsonSchema(response, CompensationV1Response.class);
        orderCompensationId = response.as(CompensationV1Response.class).getCompensation().getId();
        Optional<OrderCompensationsEntity> orderCompensationFromDb = OrderCompensationsDao.INSTANCE.findById(orderCompensationId);
        Assert.assertTrue(orderCompensationFromDb.isPresent(), "Компенсация не создалась");
        checkOrderCompensations(orderCompensationFromDb, compensation);
    }

    @CaseId(2255)
    @Story("Компенсация по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание компенсации с несуществующим промо",
            dependsOnMethods = {"getCompensationPromotions", "getCompensationsInfo"})
    public void createCompensationWithNonExistentPromotionId() {
        OrdersV1Request.Compensation compensation = OrdersV1Request.Compensation.builder()
                .compensation(OrdersV1Request.CompensationParams.builder()
                        .email("test+ssgsgs@tst.com")
                        .emailBody("Autotest-" + Generate.literalString(6))
                        .promotionId(0L)
                        .promoType(promoTypeId)
                        .reason(reasonId)
                        .build())
                .build();
        final Response response = OrdersV1Request.Compensations.POST(order.getNumber(), compensation);
        checkStatusCode422(response);
        Assert.assertTrue(response.asString().contains("Промоакция не найдена, промокод не может быть создан"), "Пришел неверный текст ошибки");
    }

    @CaseId(2249)
    @Story("Компенсация по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Подтверждение уже подтвержденной компенсации",
            dependsOnMethods = "createCompensation")
    public void approvePreviouslyApprovedCompensation() {
        final Response response = OrdersV1Request.Compensations.PUT(order.getNumber(), orderCompensationId, compensation);
        checkStatusCode422(response);
        Assert.assertTrue(response.asString().contains("Промокод уже утверждён или не требует утверждения"), "Пришел неверный текст ошибки");
    }

    @CaseId(2250)
    @Story("Компенсация по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Подтверждение компенсации",
            dependsOnMethods = "approvePreviouslyApprovedCompensation")
    public void approveCompensation() {
        compensation.getCompensation().setApprovement(OrdersV1Request.Approvement.builder()
                .comment("Autotest-" + Generate.literalString(6))
                .build());
        OrderCompensationsDao.INSTANCE.updateState(orderCompensationId, 1);
        final Response response = OrdersV1Request.Compensations.PUT(order.getNumber(), orderCompensationId, compensation);
        checkStatusCode200(response);
        Optional<OrderCompensationsEntity> orderCompensationFromDb = OrderCompensationsDao.INSTANCE.findById(orderCompensationId);
        Assert.assertTrue(orderCompensationFromDb.isPresent(), "Компенсации нет в БД");
        checkOrderCompensations(orderCompensationFromDb, compensation);
    }

    @CaseId(2256)
    @Story("Компенсация по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Подтверждение несуществующей компенсации",
            dependsOnMethods = "approveCompensation")
    public void approveNonExistentCompensation() {
        final Response response = OrdersV1Request.Compensations.PUT(order.getNumber(), 0L, compensation);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2251)
    @Story("Компенсация по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о созданной компенсации по заказу",
            dependsOnMethods = "approveCompensation")
    public void getCompensation() {
        final Response response = OrdersV1Request.Compensations.GET(order.getNumber(), orderCompensationId);
        checkStatusCode200(response);
        Optional<OrderCompensationsEntity> orderCompensationFromDb = OrderCompensationsDao.INSTANCE.findById(orderCompensationId);
        Assert.assertTrue(orderCompensationFromDb.isPresent(), "Компенсации нет в БД");
        checkOrderCompensations(orderCompensationFromDb, compensation);
    }

    @CaseId(2257)
    @Story("Компенсация по заказу")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о несуществующей компенсации по заказу")
    public void getNonExistentCompensation() {
        final Response response = OrdersV1Request.Compensations.GET(order.getNumber(), 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @AfterClass(alwaysRun = true)
    public void postconditions() {
        newAdminRoles(false);
    }
}
