package ru.instamart.tests.api.v3.endpoints;

import instamart.api.common.RestBase;
import instamart.api.objects.v3.*;
import instamart.api.requests.v3.OrderV3Request;
import instamart.api.objects.v3.OrderV3;
import instamart.api.responses.v3.OrderOptionsPickupV3Response;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

public class OrdersV3Test extends RestBase {
  StoreV3 store;
  PaymentMethodV3 paymentMethod;
  ShippingMethodOptionV3 shippingMethod;
  ReplacementOptionV3 replacementMethod;
  OrderV3 orderV3;


   @BeforeClass(alwaysRun = true)
   public void preconditions() {
       store = apiV3.getStore("METRO, Ленинградское шоссе");
       OrderOptionsPickupV3Response  orderOptionsPickupV3 = apiV3.getOrderOptionsPickup(store);

       paymentMethod = apiV3.getPaymentMethod("SberAppPay",orderOptionsPickupV3.getPayment_methods());
       shippingMethod = orderOptionsPickupV3.getShipping_methods().get(0).getOptions().get(1);
       replacementMethod = apiV3.getReplacementMethod("call_or_cancel", orderOptionsPickupV3.getReplacement_options());
   }

    @Test(groups = {"api-instamart-regress"} )
    public void PostOrdersPickupFromStore() {

        final Response response = OrderV3Request.PickupFromStore.POST(
                paymentMethod.getOptions().get(0).getId(),
                shippingMethod.getId(),
                replacementMethod.getId());

        response.prettyPeek();
        checkStatusCode200(response);
        orderV3 =response.as(OrderV3.class);

    }
    @Test(groups = {"api-instamart-regress"},dependsOnMethods = "PostOrdersPickupFromStore")
    public void GetOrder() {

        final Response response = OrderV3Request.CheckV3Request.GET(orderV3.getId());

        response.prettyPeek();
        checkStatusCode200(response);
    /* когда нить я поправлю на метод из хелпера но, пока не работает
     OrderV3 orderV3 =response.as(OrderV3.class);
        System.out.println(orderV3.getId()); */
    }
    @Test(groups = {"api-instamart-regress"},dependsOnMethods = "GetOrder")
    public void CancelOrder() {

        final Response response = OrderV3Request.CancelV3Request.PUT(orderV3.getId());

        response.prettyPeek();
        checkStatusCode200(response);

    }
}
