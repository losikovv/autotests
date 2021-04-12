package ru.instamart.api.helpers;

import io.restassured.response.Response;
import ru.instamart.api.objects.v3.*;
import ru.instamart.api.requests.v3.OrderOptionsV3Request;
import ru.instamart.api.requests.v3.StoresV3Request;
import ru.instamart.api.responses.v3.OrderOptionsV3Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ApiV3Helper {

    private static final Logger log = LoggerFactory.getLogger(InstamartApiHelper.class);

  //  PaymentMethodOptionV3 paymentMethod;
  //  OrderOptionsPickupV3Response orderOptionsPickupV3;


    public List<StoreV3> getPickupStores() {
        log.info("Получаем список магазинов с самовызом");
        return Arrays.asList(StoresV3Request.PickupFromStore.GET().as(StoreV3[].class));
    }

    public StoreV3 getStore(String name) {
        log.info("Ищем магазин по имени: " + name);
        List<StoreV3> stores = getPickupStores();
        for (StoreV3 store : stores) {
            if (name.equals(store.getName())) {
                log.info("Найден магазин: " + store);
                return store;
            }
        }
        Assert.fail("Не найден нужный магазин");
        return stores.get(0);
    }

    public OrderOptionsV3Response getOrderOptionsPickup(StoreV3 store) {
    // StoreV3 store = getStore("METRO, Ленинградское шоссе");
        log.info("Получаем список опций заказа дла магазинов с самовывозом");
        return OrderOptionsV3Request.PickupFromStore.PUT(
                "metro",
                store.getId(),
                "15879").as(OrderOptionsV3Response.class);
    }

    public OrderOptionsV3Response getOrderOptionsDelivery() {
        Response response = OrderOptionsV3Request.Delivery.PUT();
        response.then().statusCode(200);
        return response.as(OrderOptionsV3Response.class);
    }

    public PaymentMethodV3 getPaymentMethod(String title, List<PaymentMethodV3> paymentMethods) {
        log.info("Ищем payment метод: " + title);

        for (PaymentMethodV3 paymentMethod : paymentMethods) {
            if (title.equals(paymentMethod.getTitle())) {
                log.info("Метод найден: " + title);
                return paymentMethod;
            }
        }
        Assert.fail("Метод не найден");
        return paymentMethods.get(0);
    }

    public PaymentMethodV3 getPaymentMethod(List<PaymentMethodV3> paymentMethods) {
        log.info("Ищем payment метод c options");

        for (PaymentMethodV3 paymentMethod : paymentMethods) {
            if (paymentMethod.getOptions() != null &&
                    !paymentMethod.getOptions().isEmpty() &&
                    paymentMethod.getOptions().get(0).getId() != null) {
                log.info("Метод найден: " + paymentMethod.getTitle());
                return paymentMethod;
            }
        }
        Assert.fail("Метод не найден");
        return paymentMethods.get(0);
    }

    public ReplacementOptionV3 getReplacementMethod(String id, List<ReplacementOptionV3> replacementMethods) {
        log.info("Ищем метод замен: " + id);

        for (ReplacementOptionV3 replacementMethod : replacementMethods) {
            if (id.equals(replacementMethod.getId())) {
                log.info("Метод найден: " + id);
                return replacementMethod;
            }
        }
        log.error("Метод не найден");
        return replacementMethods.get(0);
    }

/* public OrderV3 getOrderMethod (OrderV3 orderV3) {
    log.info("Получаем ордер");
    return CreateOrderV3Request.PickupFromStore.POST(
            paymentMethod.getOptions().get(0).getId(),
            shippingMethod.getId(),
            replacementMethod.getId()).as(OrderV3.class);
} */

    public int getPackagesTotal(OrderV3 order) {
        int packagesTotal = 0;
        for (PackageV3 packageV3 : order.getPackages()) {
            packagesTotal += packageV3.getItemTotal();
        }
        return packagesTotal;
    }
}
