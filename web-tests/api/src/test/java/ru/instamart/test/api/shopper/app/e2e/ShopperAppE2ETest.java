package ru.instamart.test.api.shopper.app.e2e;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.checkpoint.InstamartApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.shopper.app.AssembliesSHPRequest;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;

@Epic("Shopper Mobile API")
@Feature("E2E тесты")
public class ShopperAppE2ETest extends RestBase {
    String shipmentNumber;

    @BeforeMethod(alwaysRun = true,
            description = "Оформляем заказ")
    public void preconditions() {
        final UserData user = UserManager.getUser();
        RegistrationHelper.registration(user);
        OrderV2 order = apiV2.order(user, EnvironmentProperties.DEFAULT_SID, 4);
        if (order == null) throw new SkipException("Заказ не удалось оплатить");
        shipmentNumber = order.getShipments().get(0).getNumber();
        InstamartApiCheckpoints.checkIsDeliveryToday(order);
    }

    @AfterMethod(alwaysRun = true,
            description = "Удаляем текущую сборку")
    public void cleanup() {
    }

    @Story("Сборка заказа")
    @CaseId(1)
    @Test(description = "Собираем все позиции в заказе",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void simpleCollect() {
        shopperApp.simpleCollect(shipmentNumber);
    }

    @Story("Сборка заказа")
    @CaseId(2)
    @Test(description = "Собираем/отменяем/заменяем позиции в заказе",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void complexCollect() {
        shopperApp.complexCollect(shipmentNumber);
    }


    @Story("Сборка заказа")
    @Issue("SHP-2286")
    @Test(enabled = false,
            description = "Собираем все позиции в заказе",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void simpleCollectNonUniqueFiscalNumber() {
        String currentAssemblyId = shopperApp.simpleAssemblyPriorToReceiptCreation(shipmentNumber);
        String
                total = "1.0",
                fiscalSecret = "1",
                fiscalChecksum = "1",
                transactionDetails = "1";
        Response response = AssembliesSHPRequest.Receipts.POST(
                currentAssemblyId,
                "1.0",
                "1",
                "1", //невалидный номер
                "1",
                getDateFromMSK(),
                "1");
        checkStatusCode422(response);
        checkError(response, "Этот чек уже прикреплён к другому заказу");

    }
}
