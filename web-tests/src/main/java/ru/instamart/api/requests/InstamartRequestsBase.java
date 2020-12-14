package instamart.api.requests;

import instamart.api.common.Specification;
import io.restassured.specification.RequestSpecification;

import javax.net.ssl.SSLHandshakeException;
import java.net.SocketException;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static io.restassured.RestAssured.given;

abstract class InstamartRequestsBase {
    protected static ThreadLocal<String> token = new ThreadLocal<>();

    public static String getToken() {
        return token.get();
    }

    public static void setToken(String token) {
        InstamartRequestsBase.token.set(token);
    }

    /**
     * Обходим тормоза интернета + Добавляем спеки к запросу
     */
    private static RequestSpecification givenExceptions()
            throws SSLHandshakeException, SocketException, IllegalStateException {
        return given().spec(Specification.INSTANCE.getCustomerRequestSpec());
    }

    /**
     * Обходим тормоза интернета + Добавляем спеки к запросу
     */
    static RequestSpecification givenCatch() {
        for (int i = 0; i < 10; i++) {
            try {
                return givenExceptions();
            } catch (SocketException | IllegalStateException | SSLHandshakeException exception) {
                verboseMessage(exception);
            }
        }
        return given().spec(Specification.INSTANCE.getCustomerRequestSpec());
    }
}
