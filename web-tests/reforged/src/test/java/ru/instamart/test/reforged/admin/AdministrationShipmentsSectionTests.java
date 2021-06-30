package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.UserManager;

import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.shipments;

@Epic("Админка STF")
@Feature("Управление заказами")
public final class AdministrationShipmentsSectionTests {

    @BeforeClass(alwaysRun = true,
            description = "Выполняем шаги предусловий для теста")
    public void beforeTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());
    }

    @CaseId(175)
    @Story("Тест на корректное отображение элементов страницы со списком заказов в админке")
    @Test(  description = "Тест на корректное отображение элементов страницы со списком заказов в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void validateDefaultAdminShipmentsPage() {
        //TODO: Не понимаю такие тесты
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

    }

    // Нужен юзер
    @Story("Тест поиска B2B заказа в админке")
    @Test(  description = "Тест поиска B2B заказа в админке",
            groups = {"sbermarket-regression"}
    )
    public void successSearchB2BOrder() {

    }
}
