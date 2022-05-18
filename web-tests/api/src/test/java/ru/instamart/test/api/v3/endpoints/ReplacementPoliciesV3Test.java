package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v3.OrderV3Response;
import ru.instamart.api.response.v3.ReplacementPoliciesV3Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Collections;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Чекаут")
public class ReplacementPoliciesV3Test extends RestBase {

    private long replacementPolicyId;
    private UserData user;
    private MultiretailerOrderV1Response order;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        AddressV2 addressDefaultSid = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        long offerDefaultSidId = apiV2.getProductFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        apiV1.fillCart(addressDefaultSid, ShippingMethodV2.BY_COURIER.getMethod(), offerDefaultSidId, EnvironmentProperties.DEFAULT_SID);
        order = apiV1.getMultiRetailerOrder();
    }

    @CaseId(2210)
    @Story("Способы замены товаров")
    @Test(description = "Запрос на получение способов замены товаров",
            groups = "api-instamart-regress")
    public void getReplacementPolicies() {
        final Response response = CheckoutV3Request.ReplacementPolicies.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ReplacementPoliciesV3Response.class);
        replacementPolicyId = response.as(ReplacementPoliciesV3Response.class).getReplacementPolicies().get(0).getId();
    }

    @CaseId(2211)
    @Story("Способы замены товаров")
    @Test(description = "Запрос на выбор способа замены товаров",
            groups = "api-instamart-regress",
            dependsOnMethods = "getReplacementPolicies")
    public void addReplacementPolicy() {
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .replacementPolicyId(replacementPolicyId)
                        .build())
                .shipmentNumbers(Collections.singletonList(order.getShipments().get(0).getNumber()))
                .build();
        final Response response = CheckoutV3Request.PUT(orderRequest, order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV3Response.class);
        compareTwoObjects(response.as(OrderV3Response.class).getOrder().getReplacementPolicy().getId(), replacementPolicyId);
    }
}
