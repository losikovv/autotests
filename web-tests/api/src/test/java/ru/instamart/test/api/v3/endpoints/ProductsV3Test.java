package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.ProductsV3;
import ru.instamart.api.request.v3.ProductsV3Request;
import ru.instamart.api.response.v3.ProductsV3Response;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
public class ProductsV3Test extends RestBase {
    private ProductsV3 product;

    @Test(groups = {"api-instamart-regress"}, description = "Поиск продукта по категории и id таксона")
    public void getProducts() {
        Response response = ProductsV3Request.GET(1, 14168); //id таксона
       checkStatusCode200(response);

        product = response.as(ProductsV3Response.class).getProducts().get(0);
    }
}
