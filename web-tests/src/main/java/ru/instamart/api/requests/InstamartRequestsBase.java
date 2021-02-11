package instamart.api.requests;

import instamart.api.common.Specification;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLHandshakeException;
import java.net.SocketException;

import static io.restassured.RestAssured.given;

public abstract class InstamartRequestsBase {

    private static final Logger log = LoggerFactory.getLogger(InstamartRequestsBase.class);

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
    public static RequestSpecification givenCatch() {
        for (int i = 0; i < 10; i++) {
            try {
                return givenExceptions();
            } catch (SocketException | IllegalStateException | SSLHandshakeException exception) {
                log.error("[givenCatch] ", exception);
            }
        }
        return given().spec(Specification.INSTANCE.getCustomerRequestSpec());
    }
}
