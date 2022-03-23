package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.api.model.v3.*;
import ru.instamart.api.request.v3.OrderOptionsV3Request;
import ru.instamart.api.request.v3.OrderV3Request;
import ru.instamart.api.request.v3.StoresV3Request;
import ru.instamart.api.response.v3.OrderOptionsV3Response;
import ru.instamart.jdbc.dao.ApiClientsDao;
import ru.instamart.kraken.enums.Tenant;

import java.util.Arrays;
import java.util.List;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.K8sHelper.createApiClient;

@Slf4j
public final class ApiV3Helper {

    public List<StoreV3> getPickupStores() {
        log.debug("Получаем список магазинов с самовывозом");
        return Arrays.asList(StoresV3Request.PickupFromStore.GET().as(StoreV3[].class));
    }

    public StoreV3 getStore(String name) {
        log.debug("Ищем магазин по имени: " + name);
        List<StoreV3> stores = getPickupStores();
        for (StoreV3 store : stores) {
            if (name.equals(store.getName())) {
                log.debug("Найден магазин: " + store);
                return store;
            }
        }
        Assert.fail("Не найден нужный магазин");
        return stores.get(0);
    }

    public OrderOptionsV3Response getOrderOptionsDelivery(ApiV3TestData testData) {
        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);
        return response.as(OrderOptionsV3Response.class);
    }

    public PaymentMethodV3 getPaymentMethod(String type, List<PaymentMethodV3> paymentMethods) {
        log.debug("Ищем payment метод: " + type);

        for (PaymentMethodV3 paymentMethod : paymentMethods) {
            log.debug(paymentMethod.getType());
            if (type.equals(paymentMethod.getType())) {
                log.debug("Метод найден: " + type);
                return paymentMethod;
            }
        }
        Assert.fail("Метод не найден");
        return paymentMethods.get(0);
    }

    public PaymentMethodV3 getPaymentMethod(List<PaymentMethodV3> paymentMethods) {
        log.debug("Ищем payment метод c options ");

        for (PaymentMethodV3 paymentMethod : paymentMethods) {
            if (paymentMethod.getOptions() != null &&
                    !paymentMethod.getOptions().isEmpty() &&
                    paymentMethod.getOptions().get(0).getId() != null) {
                log.debug("Метод найден: " + paymentMethod.getTitle());
                return paymentMethod;
            }
        }
        Assert.fail("Метод не найден");
        return paymentMethods.get(0);
    }

    public ReplacementOptionV3 getReplacementMethod(String id, List<ReplacementOptionV3> replacementMethods) {
        log.debug("Ищем метод замен: " + id);

        for (ReplacementOptionV3 replacementMethod : replacementMethods) {
            if (id.equals(replacementMethod.getId())) {
                log.debug("Метод найден: " + id);
                return replacementMethod;
            }
        }
        log.error("Метод не найден");
        return replacementMethods.get(0);
    }

    public int getPackagesTotal(OrderV3 order) {
        int packagesTotal = 0;
        for (PackageV3 packageV3 : order.getPackages()) {
            packagesTotal += packageV3.getItemTotal();
        }
        return packagesTotal;
    }

    public OrderV3 createDefaultOrder() {
        ApiV3TestData testData = ApiV3TestData
                .builder()
                .shipTotal("15000")
                .itemId("15879")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .clientToken("6fae9cd2b268be84e2ab394b6fd0d599")
                .build();

        return createOrderDelivery(testData);
    }

    public OrderV3 createOrderDelivery(ApiV3TestData testData) {

        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);
        OrderOptionsV3Response orderOptionsV3Response = response.as(OrderOptionsV3Response.class);

        List<PaymentMethodV3> paymentMethods = orderOptionsV3Response.getPayment_methods();
        String paymentOptionId = getPaymentMethod("external_partner_pay", paymentMethods).getOptions().get(0).getId();

        String shippingMethodOptions = orderOptionsV3Response.getShipping_methods().get(0).getOptions().get(1).getId();

        List<ReplacementOptionV3> replacementOptions = orderOptionsV3Response.getReplacement_options();
        String replacementOptionId = getReplacementMethod("replace", replacementOptions).getId();

        response = OrderV3Request.Delivery.POST(paymentOptionId, shippingMethodOptions, replacementOptionId, testData);

        if (response.statusCode() == 422 && response.body().toString().contains("Выбранный интервал стал недоступен")) {
            shippingMethodOptions = OrderOptionsV3Request.Delivery.PUT(testData)
                    .as(OrderOptionsV3Response.class)
                    .getShipping_methods()
                    .get(0)
                    .getOptions()
                    .get(1)
                    .getId();
            response = OrderV3Request.Delivery.POST(paymentOptionId, shippingMethodOptions, replacementOptionId, testData);
        }
        checkStatusCode200(response);
        return response.as(OrderV3.class);
    }

    public OrderV3 createOrderPickupFromStore(ApiV3TestData testData) {

        Response response = OrderOptionsV3Request.PickupFromStore.PUT(testData);
        checkStatusCode200(response);
        OrderOptionsV3Response orderOptionsV3Response = response.as(OrderOptionsV3Response.class);

        List<PaymentMethodV3> paymentMethods = orderOptionsV3Response.getPayment_methods();
        String paymentOptionId = getPaymentMethod("cash_desk", paymentMethods).getOptions().get(0).getId();

        String shippingMethodOptions = orderOptionsV3Response.getShipping_methods().get(0).getOptions().get(1).getId();

        List<ReplacementOptionV3> replacementOptions = orderOptionsV3Response.getReplacement_options();
        String replacementOptionId = getReplacementMethod("replace", replacementOptions).getId();

        response = OrderV3Request.PickupFromStore.POST(paymentOptionId, shippingMethodOptions, replacementOptionId, testData);

        if (response.statusCode() == 422 && response.body().toString().contains("Выбранный интервал стал недоступен")) {
            shippingMethodOptions = OrderOptionsV3Request.PickupFromStore.PUT(testData)
                    .as(OrderOptionsV3Response.class)
                    .getShipping_methods()
                    .get(0)
                    .getOptions()
                    .get(1)
                    .getId();
            response = OrderV3Request.PickupFromStore.POST(paymentOptionId, shippingMethodOptions, replacementOptionId, testData);
        }
        checkStatusCode200(response);
        return response.as(OrderV3.class);
    }

    @Step("Получаем токен api-клиента")
    public static String getApiClientToken(ClientV3 client) {
        String token;
        String tokenFromDb = ApiClientsDao.INSTANCE.getClientTokenByName(client.getName());
        if (tokenFromDb != null) {
            token = tokenFromDb;
        } else {
            createApiClient(client.getName(), Tenant.SBERMARKET);
            token = ApiClientsDao.INSTANCE.getClientTokenByName(client.getName());
        }
        return token;
    }
}
