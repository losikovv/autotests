package ru.instamart.test.api.shopper.app.e2e;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.shopper.AssemblyStateSHP;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.shopper.app.AssemblySHP;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.shopper.app.AssembliesSHPRequest;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Objects;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkIsDeliveryToday;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;
import static ru.instamart.kraken.util.ThreadUtil.simplyAwait;

@Epic("Shopper Mobile API")
@Feature("E2E тесты")
public class ShopperAppE2ETest extends RestBase {
    private String shipmentNumber;
    private String shipmentId;
    private final String COMMENT = "SHP-TEST-MULTI";

    @BeforeMethod(alwaysRun = true,
            description = "Оформляем заказ")
    public void preconditions() {
        shopperApp.authorisation(UserManager.getDefaultShopper());
        shopperApp.deleteCurrentAssembly();
        shipmentId = shopperApp.getShipmentIdByComment(COMMENT);

        if (Objects.isNull(shipmentId)) {
            SessionFactory.makeSession(SessionType.API_V2);
            OrderV2 order = apiV2.order(
                    SessionFactory.getSession(SessionType.API_V2).getUserData(),
                    EnvironmentProperties.DEFAULT_SID,
                    5,
                    COMMENT);
            if (Objects.isNull(order)) throw new SkipException("Заказ не удалось оплатить");
            shipmentNumber = order.getShipments().get(0).getNumber();
            String errorMessageIfDeliveryIsNotToday = checkIsDeliveryToday(order);
            shipmentId = shopperApp.getShipmentId(shipmentNumber, errorMessageIfDeliveryIsNotToday);
        }
    }

    @AfterMethod(alwaysRun = true,
            description = "Удаляем текущую сборку")
    public void cleanup() {
        shopperApp.authorisation(UserManager.getDefaultShopper());
        shopperApp.deleteCurrentAssembly();
    }

    @Story("Сборка заказа")
    @CaseId(1)
    @Test(description = "Собираем все позиции в заказе",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void simpleCollect() {
        shopperApp.startAssembly(shipmentId);
        shopperApp.assemblyItemsWithOriginalQty();
        shopperApp.startPaymentVerification();
        shopperApp.shopperCreatesPackageSets();
        shopperApp.finishAssembling();

        shopperApp.packer();
        shopperApp.startPurchasing();
        shopperApp.createReceipts();
        shopperApp.startPackaging();
        shopperApp.getPackageSets();
        shopperApp.packerCreatesPackageSets();
        shopperApp.finishPurchasing();

        shopperApp.shipAssembly();
    }

    @Story("Сборка заказа")
    @CaseId(2)
    @Test(description = "Собираем/отменяем/заменяем позиции в заказе",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void complexCollect() {
        AssemblySHP.Data assembly = shopperApp.startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyStateSHP.COLLECTING.getState(), "Статус сборки не валиден");

        simplyAwait(3); //Пауза перед передачей в сборку. В БД не успевает поменяться статус
        //Отдаём сборку другому сборщику
        shopperApp.suspendAssembly();
        simplyAwait(3); //Пауза перед получением в сборку. В БД не успевает поменяться статус
        assembly = shopperApp.startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyStateSHP.COLLECTING.getState(), "Статус сборки не валиден");

        //Сборка всех позиций заказа
        shopperApp.assemblyItems();
        simplyAwait(3); //Пауза после сборки заказа

        //Ставим сборку на паузу
        shopperApp.pauseAssembly();
        simplyAwait(3); //пауза перед сборкой
        assembly = shopperApp.startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyStateSHP.ON_APPROVAL.getState(), "Статус сборки не валиден");

        shopperApp.startPaymentVerification();
        shopperApp.shopperCreatesPackageSets();
        shopperApp.finishAssembling();

        shopperApp.packer();
        shopperApp.startPurchasing();
        shopperApp.createReceipts();
        shopperApp.startPackaging();
        shopperApp.getPackageSets();
        shopperApp.packerCreatesPackageSets();
        shopperApp.finishPurchasing();

        shopperApp.payAssemblyByLifePay();
        shopperApp.shipAssembly();
    }


    @Story("Сборка заказа")
    @Issue("SHP-2286")
    @Test(enabled = false,
            description = "Дублирование существующего чека",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void simpleCollectNonUniqueFiscalNumber() {
        String currentAssemblyId = shopperApp.startAssembly(shipmentId).getId();
        shopperApp.assemblyItemsWithOriginalQty();
        shopperApp.startPaymentVerification();
        shopperApp.shopperCreatesPackageSets();
        shopperApp.finishAssembling();

        shopperApp.packer();
        shopperApp.startPurchasing();

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
