package ru.instamart.test.api.surge;

import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.surge.SurgeRequest;
import ru.instamart.api.response.surge.ErrorSurgeResponse;
import ru.instamart.api.response.surge.SurgeResponse;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SurgeTest extends RestBase {
    private final String storeUuid = "49303be3-8524-4ee2-8e23-17253b8300d8";

    @CaseId(1)
    @Test(description = "Активация режима HDM в магазине",
            groups = {"surge-api"},
            priority = 1)
    public void putStoresTrue() {
        Response response = SurgeRequest.Stores.PUT(storeUuid, true);

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON);
        assertEquals(response.as(SurgeResponse.class).getData().getDeliveryAreaBaseStoreUUID(), storeUuid, "DeliveryAreaBaseStoreUUID вернулся отличный от заданного");
        assertEquals(response.as(SurgeResponse.class).getData().getActive(), Boolean.TRUE, "Активация режима HDM в магазине не верул true");
    }

    @CaseId(2)
    @Test(description = "Повторная активация режима HDM в магазине",
            groups = {"surge-api"},
            priority = 2
    )
    public void putStoresTrueRepeat() {
        Response response = SurgeRequest.Stores.PUT(storeUuid, true);
        response.then().statusCode(204);
    }

    @CaseId(3)
    @Test(description = "Получение признака активного режима HDM в магазине (Active = true)",
            groups = {"surge-api"},
            dependsOnMethods = "putStoresTrue",
            priority = 3)
    public void getStoresTrue() {
        Response response = SurgeRequest.Stores.GET(storeUuid);

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON);
        assertEquals(response.as(SurgeResponse.class).getData().getDeliveryAreaBaseStoreUUID(), storeUuid, "DeliveryAreaBaseStoreUUID вернулся отличный от заданного");
        assertEquals(response.as(SurgeResponse.class).getData().getActive(), Boolean.TRUE, "Активация режима HDM в магазине не верул true");
    }

    @CaseId(4)
    @Test(description = "Деактивация режима HDM в магазине",
            groups = {"surge-api"},
            priority = 4
    )
    public void putStoresFalse() {
        Response response = SurgeRequest.Stores.PUT(storeUuid, false);

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON);
        assertEquals(response.as(SurgeResponse.class).getData().getDeliveryAreaBaseStoreUUID(), storeUuid, "DeliveryAreaBaseStoreUUID вернулся отличный от заданного");
        assertEquals(response.as(SurgeResponse.class).getData().getActive(), Boolean.FALSE, "Активация режима HDM в магазине не вернул false");
    }

    @CaseId(13)
    @Test(description = "Получение признака не активного режима HDM в магазине (Active = false)",
            groups = {"surge-api"},
            dependsOnMethods = "putStoresFalse",
            priority = 5)
    public void getStoresFalse() {
        Response response = SurgeRequest.Stores.GET(storeUuid);

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON);
        assertEquals(response.as(SurgeResponse.class).getData().getDeliveryAreaBaseStoreUUID(), storeUuid, "DeliveryAreaBaseStoreUUID вернулся отличный от заданного");
        assertEquals(response.as(SurgeResponse.class).getData().getActive(), Boolean.FALSE, "Активация режима HDM в магазине не вернул false");
    }

    @CaseId(5)
    @Test(description = "Проверка ошибки при запросе с hdmEnabled = false для магазина отсутствующего в БД",
            groups = {"surge-api"}
    )
    public void putStoresUndefined() {
        UUID uuid = UUID.randomUUID();
        Response response = SurgeRequest.Stores.PUT(String.valueOf(uuid), false);

        response.then()
                .statusCode(404)
                .contentType(ContentType.JSON);
        assertEquals(response.as(ErrorSurgeResponse.class).getMessage(), "cannot find delivery area by given UUID", "Невалидная ошибка");
    }

    @CaseId(6)
    @Test(description = "Проверка ошибки при запросе с пустым телом запроса",
            groups = {"surge-api"}
    )
    public void putStoresBodyEmpty() {
        UUID uuid = UUID.randomUUID();
        Response response = SurgeRequest.Stores.PUT(String.valueOf(uuid), null);

        response.then()
                .statusCode(400)
                .contentType(ContentType.JSON);
        assertTrue(response.as(ErrorSurgeResponse.class).getMessage().startsWith("request arguments are invalid"), "Невалидная ошибка");
    }

    @CaseId(7)
    @Test(description = "Проверка ошибки при запросе с невалидным uuid",
            groups = {"surge-api"}
    )
    public void putInvalidUuid() {
        Response response = SurgeRequest.Stores.PUT("4123456", true);

        response.then()
                .statusCode(400)
                .contentType(ContentType.JSON);
        assertTrue(response.as(ErrorSurgeResponse.class).getMessage().contains("invalid UUID"), "невалидная ошибка");
    }

    @CaseId(8)
    @Test(description = "Проверка ошибки, при запросе с параметром  hdmEnabled типа string",
            groups = {"surge-api"}
    )
    public void putInvalidHdmEnabled() {
        UUID uuid = UUID.randomUUID();
        Response response = SurgeRequest.Stores.PUT(String.valueOf(uuid), "sdfgh234");

        response.then()
                .statusCode(400)
                .contentType(ContentType.JSON);
        assertTrue(response.as(ErrorSurgeResponse.class).getMessage().contains("Unmarshal type error: expected=bool, got=string, field=hdmEnabled"), "Not valid error");
    }

    @CaseId(9)
    @Test(description = "Проверка ошибки, при запросе c невалидным токеном авторизации",
            groups = {"surge-api"}
    )
    public void putInvalidAuthToken() {
        UUID uuid = UUID.randomUUID();
        String invalidToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzZXJnZXkubWV6ZW50c2V2QGluc3RhbWFydC5ydSIsInJvbGVzIjpbInJlYWRlciIsInJvb3QiLCJhZG1pbiIsImVkaXRvciJdfQ.0qKwFP3yWbV_IYrcZq2p-eZYBaDQLsibb699BLinaaI09876";
        Response response = SurgeRequest.Stores.PUT(String.valueOf(uuid), true, invalidToken);

        response.then()
                .statusCode(401)
                .contentType(ContentType.JSON);
        assertEquals(response.as(ErrorSurgeResponse.class).getMessage(), "invalid or expired jwt", "Not valid error");
    }

    @CaseId(10)
    @Test(description = "Проверка ошибки, при запросе c недостаточными правами",
            groups = {"surge-api"}
    )
    public void putNotPermission() {
        UUID uuid = UUID.randomUUID();
        String invalidToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzZXJnZXkubWV6ZW50c2V2QGluc3RhbWFydC5ydSIsInJvbGVzIjpbInJlYWRlciIsImVkaXRvciJdfQ.dqOpC91MMFYtGFfdcjrX_bJsOXUoA-bmpCTryk5gJP4";
        Response response = SurgeRequest.Stores.PUT(String.valueOf(uuid), true, invalidToken);

        response.then()
                .statusCode(403)
                .contentType(ContentType.JSON);
        assertEquals(response.as(ErrorSurgeResponse.class).getMessage(), "user does not have permission 'hdm-enable'", "Not valid error");
    }

    @CaseId(11)
    @Test(description = "Проверка ошибки, при запросе методом Post",
            groups = {"surge-api"}
    )
    public void postStores() {
        UUID uuid = UUID.randomUUID();
        Response response = SurgeRequest.Stores.POST(String.valueOf(uuid), true);

        response.then()
                .statusCode(405)
                .contentType(ContentType.JSON);
        assertEquals(response.as(ErrorSurgeResponse.class).getMessage(), "Method Not Allowed", "Not valid error");
    }

}
