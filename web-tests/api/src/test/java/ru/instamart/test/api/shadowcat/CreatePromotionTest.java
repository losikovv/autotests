package ru.instamart.test.api.shadowcat;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.ShadowcatRestBase;
import ru.instamart.api.model.shadowcat.Promocode;
import ru.instamart.api.request.shadowcat.PromocodeRequest;
import ru.instamart.api.request.shadowcat.PromotionRequest.Promotions;
import ru.instamart.api.response.shadowcat.ErrorShadowcatResponse;
import ru.instamart.api.response.shadowcat.PromocodeResponse;
import ru.instamart.api.response.shadowcat.PromocodesResponse;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Shadowcat")
@Feature("Проверки создания промокодов/промоакций")
public class CreatePromotionTest extends ShadowcatRestBase {
    private static int promoId;
    private static String promocode;
    private static int promocodeId;

    @TmsLink("1")
    @Test(description = "Создание промоакции",
            groups = {"api-shadowcat"},
            priority = 1)
    public void createPromotion() {
        final Response response = Promotions.POST();
        checkStatusCode201(response);
        promoId = response.path("id");
    }

    @TmsLink("2")
    @Test(description = "Обновление промоакции",
            groups = {"api-shadowcat"},
            priority = 2,
            dependsOnMethods = "createPromotion")
    public void updatePromotion() {
        final Response response = Promotions.PUT(promoId);
        checkStatusCode204or404(response);
    }

    @TmsLink("9")
    @Test(description = "Создание промокода в промоакции",
            groups = {"api-shadowcat"},
            priority = 3)
    public void createPromocode() {
        final Response response = PromocodeRequest.Promocodes.POST(promoId);
        checkStatusCode201(response);
        PromocodeResponse pr = response.as(PromocodeResponse.class);
        promocode = pr.getCode();
        promocodeId = pr.getId();
    }

    @TmsLink("6")
    @Test(description = "Обновление промокода в промоакции",
            groups = {"api-shadowcat"},
            priority = 4,
            dependsOnMethods = "createPromocode")
    public void updatePromocode() {
        final Response response = PromocodeRequest.Promocodes.PUT(promocodeId, promoId, promocode);
        checkStatusCode200(response);
    }

    @TmsLink("5")
    @Test(description = "Получение промокода в промоакции",
            groups = {"api-shadowcat"},
            priority = 5,
            dependsOnMethods = "updatePromocode")
    public void getPromocode() {
        final Response response = PromocodeRequest.Promocodes.GET(promoId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromocodesResponse.class);
        final List<Promocode> promocodeList = response.as(PromocodesResponse.class).getItems();
        compareTwoObjects(promocodeList.get(0).getCode(), promocode);
    }

    @TmsLink("20")
    @Test(description = "Проверка наличия промокода при калькуляции",
            groups = {"api-shadowcat"},
            priority = 6,
            dependsOnMethods = "getPromocode")
    public void checkPromocodeExist() {
        final Response response = PromocodeRequest.Promocodes.GET(promocode);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromocodeResponse.class);
    }

    @TmsLink("21")
    @Test(description = "Негативная проверка наличия промокода при калькуляции",
            groups = {"api-shadowcat"},
            priority = 7,
            dependsOnMethods = "getPromocode")
    public void checkPromocodeNotExist() {
        final Response response = PromocodeRequest.Promocodes.GET(promocode+"1");
        checkStatusCode422(response);
        checkResponseJsonSchema(response, ErrorShadowcatResponse.class);
    }

    @TmsLink("8")
    @Test(description = "Удаление промокода",
            groups = {"api-shadowcat"},
            priority = 8,
            dependsOnMethods = "checkPromocodeExist")
    public void deletePromocode() {
        final Response response = PromocodeRequest.Promocodes.DELETE(promocodeId);
        checkStatusCode204or404(response);
    }

    @TmsLink("19")
    @Test(description = "Удаление промоакции",
            groups = {"api-shadowcat"},
            priority = 9,
            dependsOnMethods = "deletePromocode")
    public void deletePromotion() {
        final Response response = Promotions.DELETE(promoId);
        checkStatusCode204or404(response);
    }
}
