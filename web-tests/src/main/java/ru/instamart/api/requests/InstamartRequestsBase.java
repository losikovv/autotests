package instamart.api.requests;

import instamart.api.common.RestBase;
import io.restassured.specification.RequestSpecification;

import javax.net.ssl.SSLHandshakeException;
import java.net.SocketException;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static io.restassured.RestAssured.given;

abstract class InstamartRequestsBase {
    public static ThreadLocal<String> token = new ThreadLocal<>();

    /**
     * Обходим тормоза интернета + Добавляем спеки к запросу
     */
    private static RequestSpecification givenExceptions()
            throws SSLHandshakeException, SocketException, IllegalStateException {
        return given().spec(RestBase.customerRequestSpec);
    }

    /**
     * Обходим тормоза интернета + Добавляем спеки к запросу
     */
    static RequestSpecification givenCatch() {
        for (int i = 0; i < 10; i++) {
            try {
                try {
                    try {
                        return givenExceptions();
                    } catch (SocketException socketException) {
                        verboseMessage(socketException);
                    }
                } catch (SSLHandshakeException sslHandshakeException) {
                    verboseMessage(sslHandshakeException);
                }
            } catch (IllegalStateException illegalStateException) {
                verboseMessage(illegalStateException);
            }
        }
        return given().spec(RestBase.customerRequestSpec);
    }
}
