package ru.instamart.tests.api.v3.endpoints;

import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.*;
import ru.instamart.api.request.v3.OrderV3Request;
import ru.instamart.api.model.v3.OrderV3;
import ru.instamart.api.response.v3.OrderOptionsV3Response;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.api.dataprovider.ApiV3DataProvider;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

public class OrdersPickupFromStoreV3Test extends RestBase {
    StoreV3 store;
    PaymentMethodV3 paymentMethod;
    ShippingMethodOptionV3 shippingMethod;
    ReplacementOptionV3 replacementMethod;
    OrderV3 order;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
       store = apiV3.getStore("METRO, Ленинградское шоссе");
       OrderOptionsV3Response orderOptions = apiV3.getOrderOptionsPickup(store);

       paymentMethod = apiV3.getPaymentMethod("SberAppPay", orderOptions.getPayment_methods());
       shippingMethod = orderOptions.getShipping_methods().get(0).getOptions().get(1);
       replacementMethod = apiV3.getReplacementMethod("call_or_cancel", orderOptions.getReplacement_options());
    }

    @Test(groups = {"api-instamart-regress"},
            dataProvider = "itemIds",
            dataProviderClass = ApiV3DataProvider.class)
    public void postOrdersPickupFromStore(ApiV3TestData testData) {

        final Response response = OrderV3Request.PickupFromStore.POST(
               paymentMethod.getOptions().get(0).getId(),
               shippingMethod.getId(),
               replacementMethod.getId(),
               testData.getItemId(),
               testData.getClientToken(),
               testData.getShipTotal(),
               testData.getItemQuantity(),
               testData.getItemPrice(),
               testData.getItemDiscount());

        response.prettyPeek();
        response.then().statusCode(testData.getStatusCode());
        order = response.as(OrderV3.class);

    }

    @Test(groups = {"api-instamart-regress"},dependsOnMethods = "postOrdersPickupFromStore")
    public void getOrder() {

        final Response response = OrderV3Request.GET(order.getId());

        response.prettyPeek();
        checkStatusCode200(response);
    /* когда нить я поправлю на метод из хелпера но, пока не работает
     OrderV3 orderV3 =response.as(OrderV3.class);
        System.out.println(orderV3.getId()); */
    }

    @Test(groups = {"api-instamart-regress"},dependsOnMethods = "getOrder")
    public void cancelOrder() {

        final Response response = OrderV3Request.Cancel.PUT(order.getId());

        response.prettyPeek();
        checkStatusCode200(response);
    }
}
