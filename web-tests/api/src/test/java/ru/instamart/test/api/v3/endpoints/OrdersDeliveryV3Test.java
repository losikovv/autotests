package ru.instamart.test.api.v3.endpoints;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.*;
import ru.instamart.api.request.v3.OrderV3Request;
import ru.instamart.api.response.v3.OrderOptionsV3Response;

public class OrdersDeliveryV3Test extends RestBase {
    PaymentMethodV3 paymentMethod;
    ShippingMethodOptionV3 shippingMethod;
    ReplacementOptionV3 replacementMethod;
    OrderV3 order;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        OrderOptionsV3Response orderOptions = apiV3.getOrderOptionsDelivery();

        paymentMethod = apiV3.getPaymentMethod(orderOptions.getPayment_methods());
        shippingMethod = orderOptions.getShipping_methods().get(0).getOptions().get(1);
        replacementMethod = apiV3.getReplacementMethod("call_or_cancel", orderOptions.getReplacement_options());
    }

    @Test(groups = {"api-instamart-regress"} )
    public void postOrdersDelivery() {
        final Response response = OrderV3Request.Delivery.POST(
                paymentMethod.getOptions().get(0).getId(),
                shippingMethod.getId(),
                replacementMethod.getId());

        response.prettyPeek();
        response.then().statusCode(200);
        order = response.as(OrderV3.class);
    }

    @Test(groups = {"api-instamart-regress"},dependsOnMethods = "postOrdersDelivery")
    public void getOrder() {

        final Response response = OrderV3Request.GET(order.getId());

        response.prettyPeek();
        response.then().statusCode(200);

        OrderV3 order = response.as(OrderV3.class);
        int orderTotal = order.getItemTotal();
        int packagesTotal = apiV3.getPackagesTotal(order);

        Assert.assertEquals(orderTotal, packagesTotal);
    /* когда нить я поправлю на метод из хелпера но, пока не работает
     OrderV3 orderV3 =response.as(OrderV3.class);
        System.out.println(orderV3.getId()); */
    }

    @Test(groups = {"api-instamart-regress"},dependsOnMethods = "getOrder")
    public void cancelOrder() {

        final Response response = OrderV3Request.Cancel.PUT(order.getId());

        response.prettyPeek();
        response.then().statusCode(200);
    }
}
