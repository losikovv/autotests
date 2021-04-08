package ru.instamart.api.checkpoints;

import ru.instamart.api.objects.shopper.ErrorSHP;
import ru.instamart.api.responses.shopper.ErrorSHPResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.StringJoiner;

public class ShopperApiCheckpoints {

    @Step("Ответ вернул 200")
    public static void checkStatusCode200(Response response) {
        switch (response.statusCode()) {
            case 200: break;
            case 401:
            case 422:
                Assert.assertEquals(response.statusCode(),200, getErrorDetails(response));
                break;
            default:
                Assert.assertEquals(response.statusCode(),200,response.body().print());
        }
    }

    @Step("Ответ вернул 401")
    public static void checkStatusCode401(Response response) {
        response.then().statusCode(401);
    }

    public static String getErrorDetails(Response response) {
        try {
            List<ErrorSHP> errors = response.as(ErrorSHPResponse.class).getErrors();
            StringJoiner stringJoiner = new StringJoiner(", ");
            if (errors != null) for (ErrorSHP error : errors) stringJoiner.add(error.getDetail());
            else stringJoiner.add(response.body().toString());
            return stringJoiner.toString();
        } catch (IllegalStateException e) {
            Assert.fail(response.statusLine());
            return null;
        }
    }
}
