package instamart.api.requests;

import instamart.api.SessionFactory;
import instamart.api.common.Specification;
import instamart.api.enums.SessionType;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLHandshakeException;
import java.net.SocketException;

import static io.restassured.RestAssured.given;

public final class InstamartRequestsBase {

    private static final Logger log = LoggerFactory.getLogger(InstamartRequestsBase.class);

    public static RequestSpecification givenApiV1() {
        return given().spec(Specification.INSTANCE.getApiV1Spec());
    }
    /**
     * Обходим тормоза интернета + Добавляем спеки к запросу
     */
    private static RequestSpecification givenExceptions()
            throws SSLHandshakeException, SocketException, IllegalStateException {
        return given().spec(Specification.INSTANCE.getApiV2Spec());
    }

    /**
     * Добавляем хедер авторизации к запросу
     */
    public static RequestSpecification givenWithAuthApiV2() {
        return given()
                .spec(Specification.INSTANCE.getApiV2Spec())
                .header(
                "Authorization",
                "Token token=" + SessionFactory.getSession(SessionType.APIV2).getToken());
    }

    /**
     * Добавляем хедер авторизации к запросу деливери клаб
     */
    public static RequestSpecification givenWithAuthDeliveryClub() {
        return given()
                .spec(Specification.INSTANCE.getApiV2Spec())
                .header("Authorization",
                        "Bearer " + SessionFactory.getSession(SessionType.DELIVERY_CLUB).getToken());
    }

    /**
     * Авторизация с кастомным токеном, для случаев когда нужна проверка на невалидный токен
     * @param token
     * @return
     */
    public static RequestSpecification givenCustomToken(final String token) {
        return given()
                .spec(Specification.INSTANCE.getApiV2Spec())
                .header(
                        "Authorization",
                        "Token token=" + token);
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
        return given().spec(Specification.INSTANCE.getApiV2Spec());
    }
}
