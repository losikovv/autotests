package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.admin.PaymentMethodsRequest;
import ru.instamart.api.response.v1.admin.PaymentMethodsV1Response;
import ru.instamart.jdbc.dao.stf.SpreePaymentMethodsDao;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Настройки")
public class PaymentMethodsV1Test extends RestBase {

    @CaseId(2809)
    @Story("Способы оплаты")
    @Test(description = "Получение способов оплаты",
            groups = {"api-instamart-regress"})
    public void getPaymentMethodsPositive() {
        List<Long> paymentMethodIds = SpreePaymentMethodsDao.INSTANCE.getPaymentMethodsIds();
        admin.authApi();
        final Response response = PaymentMethodsRequest.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PaymentMethodsV1Response.class);
        compareTwoObjects(response.as(PaymentMethodsV1Response.class).getPaymentMethods().size(), paymentMethodIds.size());
    }

    @CaseId(2810)
    @Story("Способы оплаты")
    @Test(description = "Попытка получения способов оплаты без авторизации",
            groups = {"api-instamart-regress"})
    public void getPaymentMethodsNegative401() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = PaymentMethodsRequest.GET();
        checkStatusCode401(response);
        checkErrorText(response, "Вам требуется войти или зарегистрироваться перед тем, как продолжить.");
    }

    @CaseId(2811)
    @Story("Способы оплаты")
    @Test(description = "Попытка получения способов оплаты пользователем без админских прав",
            groups = {"api-instamart-regress"})
    public void getPaymentMethodsNegative403() {
        apiV1.authByPhone(UserManager.getQaUser());
        final Response response = PaymentMethodsRequest.GET();
        checkStatusCode403(response);
        checkErrorText(response, "Пользователь не авторизован для этого действия");
    }

    @CaseId(2823)
    @Story("Способы оплаты")
    @Test(description = "Изменение порядка отображения способов оплаты",
            groups = {"api-instamart-regress"})
    public void updatePaymentMethodsPositions() {
        admin.authApi();
        Map<String, Integer> paymentMethodIdsAndPosition = new HashMap<>();
        paymentMethodIdsAndPosition.put("1", 2);
        paymentMethodIdsAndPosition.put("2", 1);
        final Response response = PaymentMethodsRequest.POST(paymentMethodIdsAndPosition);
        checkStatusCode(response, 204);
    }

    @CaseId(2824)
    @Story("Способы оплаты")
    @Test(description = "Попытка изменение порядка отображения способов оплаты неавторизованным",
            groups = {"api-instamart-regress"})
    public void updatePaymentMethodsPositionsNegative401() {
        SessionFactory.clearSession(SessionType.API_V1);
        Map<String, Integer> paymentMethodIdsAndPosition = new HashMap<>();
        paymentMethodIdsAndPosition.put("1", 2);
        final Response response = PaymentMethodsRequest.POST(paymentMethodIdsAndPosition);
        checkStatusCode401(response);
    }

    @CaseId(2825)
    @Story("Способы оплаты")
    @Test(description = "Попытка изменения порядка отображения способов оплаты пользователем без админских прав",
            groups = {"api-instamart-regress"})
    public void updatePaymentMethodsPositionsNegative403() {
        apiV1.authByPhone(UserManager.getQaUser());
        Map<String, Integer> paymentMethodIdsAndPosition = new HashMap<>();
        paymentMethodIdsAndPosition.put("1", 0);
        final Response response = PaymentMethodsRequest.POST(paymentMethodIdsAndPosition);
        checkStatusCode403(response);
    }

    @CaseId(2526)
    @Story("Способы оплаты")
    @Test(description = "Попытка изменения порядка отображения способов оплаты некорректным запросом",
            groups = {"api-instamart-regress"})
    public void updatePaymentMethodsPositionsNegative422() {
        admin.authApi();
        final Response response = PaymentMethodsRequest.POST();
        checkStatusCode422(response);
    }
}
