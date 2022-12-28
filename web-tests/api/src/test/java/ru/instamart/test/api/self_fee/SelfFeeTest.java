package ru.instamart.test.api.self_fee;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.SelfFeeBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.self_fee.SelfFeeV1Request;
import ru.instamart.api.response.self_fee.RegistryResponse;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.Group.SELF_FEE;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode403;

@Epic("On Demand")
@Feature("Self-Fee")
public class SelfFeeTest extends SelfFeeBase {

    @CaseId(315)
    @Story("Доступ к разделу (авторизация)")
    @Test(groups = {SELF_FEE},
            description = "Получение списка реестров с ролью расчётной группы")
    public void test315() {
        SessionFactory.clearSession(SessionType.ADMIN);
        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminSelfFee());

        final var response = SelfFeeV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RegistryResponse.class);
        var registryResponse = response.as(RegistryResponse.class);

        assertTrue(registryResponse.getResult().size() > 0, "result is empty");
    }

    @CaseId(316)
    @Story("Доступ к разделу (авторизация)")
    @Test(groups = {SELF_FEE},
            description = "Неуспешное получение списка реестров с ролью, отличной от роли расчётной группы")
    public void test316() {
        final var user = UserData.builder()
                .email("test_not_billing_admin@sbermarket.ru")
                .password("Notbilling1!")
                .build();
        SessionFactory.clearSession(SessionType.ADMIN);
        SessionFactory.createSessionToken(SessionType.ADMIN, user);

        final var response = SelfFeeV1Request.GET();
        checkStatusCode403(response);
    }

}
