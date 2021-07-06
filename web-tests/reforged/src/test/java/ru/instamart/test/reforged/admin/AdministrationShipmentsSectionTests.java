package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.Kraken;

import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.shipments;

@Epic("Админка STF")
@Feature("Управление заказами")
public final class AdministrationShipmentsSectionTests {

    @BeforeMethod(alwaysRun = true,
            description = "Выполняем шаги предусловий для теста")
    public void beforeTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());
    }

    @AfterMethod(alwaysRun = true)
    public void close() {
        Kraken.closeBrowser();
    }

    @Skip
    @CaseId(175)
    @Story("Тест на корректное отображение элементов страницы со списком заказов в админке")
    @Test(  description = "Тест на корректное отображение элементов страницы со списком заказов в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void validateDefaultAdminShipmentsPage() {
        shipments().goToPage();
        shipments().checkPageTitle();
        shipments().checkOrderDateFrom();
        shipments().checkOrderDateTo();
        shipments().checkCustomerName();
        shipments().checkCustomerSurName();
    }

    // TODO test shipmentsTableNotEmptyByDefault

    // TODO test successShowEmptySearchPlaceholder

    @CaseId(182)
    @Story("Тест поиска заказа по номеру заказа в админке")
    @Test(  description = "Тест поиска заказа по номеру заказа в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void successSearchOrderByOrderNumber() {
        shipments().goToPage();
        final String orderNumber = shipments().getOrderNumber();
        shipments().setShipmentOrOrderNumber(orderNumber);
        shipments().search();
        shipments().checkFoundOrderOrShipmentCount(shipments().getFoundCount(), 1);
        shipments().checkOrderOrShipmentNumber(shipments().getOrderNumber(), orderNumber);
    }

    @CaseId(445)
    @Story("Тест поиска заказа по номеру шипмента в админке")
    @Test(  description = "Тест поиска заказа по номеру шипмента в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void successSearchOrderByShipmentNumber() {
        shipments().goToPage();
        final String shipmentNumber = shipments().getShipmentNumber();
        shipments().setShipmentOrOrderNumber(shipmentNumber);
        shipments().search();
        shipments().checkFoundOrderOrShipmentCount(shipments().getFoundCount(), 1);
        shipments().checkOrderOrShipmentNumber(shipments().getShipmentNumber(), shipmentNumber);
    }

    // TODO тест можно ускорить - использовать тестовый заказ из конфига
    // TODO поправить тест после того как починб тест заказа
    @Story("Тест возобновления и отмены заказа через админку")
    @Test(  description = "Тест возобновления и отмены заказа через админку",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successResumeAndCancelOrder() {
        final ApiHelper helper = new ApiHelper();
        final UserData userData = UserManager.getUser();
        helper.auth(userData);
        final OrderV2 orderV2 = helper.makeOrder(userData, EnvironmentData.INSTANCE.getDefaultSid(), 3);
        helper.cancelOrder(userData, orderV2.getNumber());

        //TODO: Заказ появляется в админке с задержкой рандомной
    }

    // Нужен юзер
    @Story("Тест поиска B2B заказа в админке")
    @Test(  description = "Тест поиска B2B заказа в админке",
            groups = {"sbermarket-regression"}
    )
    public void successSearchB2BOrder() {
        final ApiHelper helper = new ApiHelper();
        final UserData userData = UserManager.getUser();
        helper.auth(userData);

        final OrderV2 orderV2 = helper.makeOrder(userData, EnvironmentData.INSTANCE.getDefaultSid(), 3);

        //TODO: Заказ появляется в админке с задержкой рандомной
    }
}
