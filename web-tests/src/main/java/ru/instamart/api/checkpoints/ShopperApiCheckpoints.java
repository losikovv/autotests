package instamart.api.checkpoints;

import instamart.api.objects.shopper.Error;
import instamart.api.responses.shopper.ErrorResponse;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.StringJoiner;

public class ShopperApiCheckpoints {

    /**
     * Ассерт, что статус код 200
     */
    public static void assertStatusCode200(Response response) {
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

    public static String getErrorDetails(Response response) {
        try {
            List<Error> errors = response.as(ErrorResponse.class).getErrors();
            StringJoiner stringJoiner = new StringJoiner(", ");
            if (errors != null) for (Error error : errors) stringJoiner.add(error.getDetail());
            else stringJoiner.add(response.body().toString());
            return stringJoiner.toString();
        } catch (IllegalStateException e) {
            Assert.fail(response.statusLine());
            return null;
        }
    }
}
