package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.SubscriptionV1;
import ru.instamart.api.model.v2.ServicesV2;
import ru.instamart.api.request.v1.ExternalPartnersV1Request;
import ru.instamart.api.response.v1.PrimeCategoryV1;
import ru.instamart.api.response.v1.SberPrimeBannersWithSubscriptionV1Response;
import ru.instamart.api.response.v1.SberPrimeBannersWithoutSubscriptionV1Response;
import ru.instamart.api.response.v1.SubscriptionV1Response;
import ru.instamart.api.response.v2.ExternalPartnersServicesV2Response;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkExternalPartnersServices;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.K8sHelper.addSberPrime;

@Epic("ApiV1")
@Feature("Внешние партнеры")
public class ExternalPartnersV1Tests extends RestBase {

    private Long subscriptionId;


    @CaseId(1433)
    @Story("Получение списка подписок для пользователя")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Подписка SberPrime неактивна")
    public void getInactiveSubscription() {
        admin.authApi();
        final Response response = ExternalPartnersV1Request.Services.GET();
        checkStatusCode200(response);
        List<ServicesV2> services = response.as(ExternalPartnersServicesV2Response.class).getServices();
        compareTwoObjects(services.size(), 0);
    }

    @CaseId(1434)
    @Story("Получение списка подписок для пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Подписка SberPrime активна",
            dependsOnMethods = "getInactiveSubscription")
    public void getActiveSubscription() {
        UserData user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        addSberPrime(user.getEmail());
        ThreadUtil.simplyAwait(2);
        final Response response = ExternalPartnersV1Request.Services.GET();
        checkStatusCode200(response);
        checkExternalPartnersServices(response, true, "Бесплатная доставка");
        subscriptionId = response.as(ExternalPartnersServicesV2Response.class).getServices().get(0).getSubscription().getId();
    }

    @CaseId(2509)
    @Story("Получение списка подписок для пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о подписке",
            dependsOnMethods = "getActiveSubscription")
    public void getSubscription() {
        admin.authApiWithAdminNewRoles();
        final Response response = ExternalPartnersV1Request.Subscriptions.GET(subscriptionId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SubscriptionV1Response.class);
        SubscriptionV1 subscription = response.as(SubscriptionV1Response.class).getSubscription();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(subscription.getId(), subscriptionId, softAssert);
        compareTwoObjects(subscription.getPacketKind(), "prime_basic", softAssert);
        compareTwoObjects(subscription.getFreeDeliveriesInfo().getRemained(), 3, softAssert);
        compareTwoObjects(subscription.getService().getType(), "sber_prime", softAssert);
        softAssert.assertAll();
    }

    @CaseId(1493)
    @Test(groups = {"api-instamart-regress"},
            description = "Отправка запроса c store_id",
            dependsOnMethods = "getActiveSubscription")
    public void getSberPrimeBannersWithSubscription() {
        final Response response = ExternalPartnersV1Request.Banners.SberPrime.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SberPrimeBannersWithSubscriptionV1Response.class);
        PrimeCategoryV1 primeCategory = response.as(SberPrimeBannersWithSubscriptionV1Response.class).getPrimeCategoryWithSubscription();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(primeCategory.getDesktop().getPromocode(), "PRIME", softAssert);
        compareTwoObjects(primeCategory.getMobile().getPromocode(), "PRIME", softAssert);
        compareTwoObjects(primeCategory.getMobile().getHeader(), "Скидки для подписчиков СберПрайм", softAssert);
        compareTwoObjects(primeCategory.getDesktop().getText(), "Все скидки по подписке СберПрайм на одной странице. До -10% по промокоду", softAssert);
        compareTwoObjects(primeCategory.getMobile().getText(), "Собрали на одной странице, чтобы не искать. До -10% по промокоду", softAssert);
        softAssert.assertAll();
    }

    @CaseId(1494)
    @Test(groups = {"api-instamart-regress"},
            description = "Отправка запроса c store_id",
            dependsOnMethods = "getSberPrimeBannersWithSubscription")
    public void getSberPrimeBannersWithoutSubscription() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = ExternalPartnersV1Request.Banners.SberPrime.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SberPrimeBannersWithoutSubscriptionV1Response.class);
        PrimeCategoryV1 primeCategory = response.as(SberPrimeBannersWithoutSubscriptionV1Response.class).getPrimeCategoryWithoutSubscription();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(primeCategory.getDesktop().getText(), "Скидки в этом разделе действуют только для подписчиков СберПрайм", softAssert);
        compareTwoObjects(primeCategory.getMobile().getHeader(), "Для подписчиков СберПрайм", softAssert);
        compareTwoObjects(primeCategory.getMobile().getText(), "Скидки в этом разделе действуют только для подписчиков СберПрайм", softAssert);
        softAssert.assertAll();
    }
}
