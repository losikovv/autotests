package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.admin.page.usersEdit.UsersEditPage;
import ru.instamart.test.reforged.BaseTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление заказами")
public final class AdministrationShipmentsSectionTests extends BaseTest {

    //TODO продумать функцию предусловие для заполнения бд заказами

    @Skip
    @CaseId(175)
    @Story("Тест на корректное отображение элементов страницы со списком заказов в админке")
    @Test(description = "Тест на корректное отображение элементов страницы со списком заказов в админке",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void validateDefaultAdminShipmentsPage() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageTitle();
        shipments().checkOrderDateFrom();
        shipments().checkOrderDateTo();
        shipments().checkCustomerName();
        shipments().checkCustomerSurName();
    }

    @CaseId(176)
    @Story("Тест на работоспособность пагинации списка заказов")
    @Test(description = "Тест на работоспособность пагинации списка заказов",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void validatePagerOnAdminShipmentsPage() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageTitle();
        shipments().nextPagerClick();
        shipments().checkCurrentPageNumber("2");
        shipments().previousPagerClick();
        shipments().checkCurrentPageNumber("1");
        shipments().lastPageClick();
        shipments().checkLastPagePager();
        shipments().firstPageClick();
        shipments().checkFirstPagePager();
    }

    @CaseId(172)
    @Story("Тест на работоспособность фильтра ДАТА И ВРЕМЯ ДОСТАВКИ")
    @Test(description = "Тест на работоспособность фильтра ДАТА И ВРЕМЯ ДОСТАВКИ",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void validateFilterDateAndTimeAdminShipmentsPage() {
        final DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        final DateTimeFormatter dtd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final String deliveryDateFrom = dt.format(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        final String deliveryDateTo = dt.format(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageTitle();
        shipments().setDateAndTimeFilterFromTableDefault(deliveryDateFrom);
        shipments().setDateAndTimeFilterToTableDefault(deliveryDateTo);
        shipments().search();
        shipments().checkDateAndTimeShipmentsColumn(dtd.format(LocalDateTime.now()));
    }

    @CaseId(173)
    @Story("Тест на работоспособность фильтра ТЕЛЕФОН СОДЕРЖИТ")
    @Test(description = "Тест на работоспособность фильтра ТЕЛЕФОН СОДЕРЖИТ",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void validateFilterPhoneShipmentsPage() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());
        shipments().goToPage();
        shipments().checkPageTitle();
        String phone = shipments().getFirstPhoneFromTable();
        shipments().setPhoneFilterFromTableDefault(phone);
        shipments().search();
        shipments().checkPhoneShipmentsColumn(phone);
    }

    @CaseId(174)
    @Story("Тест на работоспособность мультифильтра")
    @Test(description = "Тест на работоспособность мультифильтра",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void validateMultiFiltersShipmentsPage() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageTitle();
        String phone = shipments().getFirstPhoneFromTable();
        String deliveryDate = shipments().getFirstDeliveryDateFromTable();
        shipments().setPhoneAndDateFilterDefault(phone, deliveryDate);
        shipments().search();
        shipments().checkPhoneShipmentsColumn(phone);
        shipments().checkDateAndTimeShipmentsColumn(deliveryDate);
    }

    @CaseId(1224)
    @Story("Тест на проверку изменения количества заказов после применения фильтра, без пейджера")
    @Test(description = "Тест на проверку изменения количества заказов после применения фильтра, без пейджера",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void validateShipmentsAfterFiltrationWOTPager() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageTitle();
        String shipmentsBeforeFiltration = shipments().getNumberOfShipments();
        String shipment = shipments().getShipmentNumber();
        shipments().setShipmentOrOrderNumber(shipment);
        shipments().search();
        String shipmentsAfterFiltration = shipments().getNumberOfShipments();
        shipments().checkNumberOfShipmentsAfterFiltration(shipmentsBeforeFiltration, shipmentsAfterFiltration);
        shipments().checkLastPagePager();
    }

    @CaseId(1225)
    @Story("Тест на проверку изменения количества заказов после применения фильтра, с пейджером")
    @Test(description = "Тест на проверку изменения количества заказов после применения фильтра, с пейджером",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void validateShipmentsAfterFiltrationWithPager() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageTitle();
        String shipmentsBeforeFiltration = shipments().getNumberOfShipments();
        String phone = shipments().getFirstPhoneFromTable();
        shipments().setPhoneFilterFromTableDefault(phone);
        shipments().search();
        String shipmentsAfterFiltration = shipments().getNumberOfShipments();
        String pages = shipments().getNumberOfPagesAfterFiltration(shipmentsAfterFiltration);
        shipments().checkNumberOfShipmentsAfterFiltration(shipmentsBeforeFiltration, shipmentsAfterFiltration);
        shipments().lastPageClick();
        shipments().checkCurrentPageNumber(pages);
    }

    // TODO test shipmentsTableNotEmptyByDefault

    // TODO test successShowEmptySearchPlaceholder

    @CaseId(182)
    @Story("Тест поиска заказа по номеру заказа в админке")
    @Test(description = "Тест поиска заказа по номеру заказа в админке",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successSearchOrderByOrderNumber() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        final String orderNumber = shipments().getOrderNumber();
        shipments().setShipmentOrOrderNumber(orderNumber);
        shipments().search();
        shipments().checkFoundOrderOrShipmentCount(shipments().getFoundCount(), 1);
        shipments().checkOrderOrShipmentNumber(shipments().getOrderNumber(), orderNumber);
    }

    @CaseId(445)
    @Story("Тест поиска заказа по номеру шипмента в админке")
    @Test(description = "Тест поиска заказа по номеру шипмента в админке",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successSearchOrderByShipmentNumber() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

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
    @Test(description = "Тест возобновления и отмены заказа через админку",
            groups = {"sbermarket-acceptance", "sbermarket-regression"}
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
    @Test(description = "Тест поиска B2B заказа в админке",
            groups = {"sbermarket-regression"}
    )
    public void successSearchB2BOrder() {
        final ApiHelper helper = new ApiHelper();
        final UserData userData = UserManager.getUser();
        helper.auth(userData);

        final OrderV2 orderV2 = helper.makeOrder(userData, EnvironmentData.INSTANCE.getDefaultSid(), 3);

        //TODO: Заказ появляется в админке с задержкой рандомной
    }

    @Story("Тест поиска B2B заказа после снятия признака B2B")
    @Test(description = "Тест поиска B2B заказа после снятия признака B2B",
            groups = {""}
    )
    public void successSearchB2BOrderAfterRevokeB2BRole() {
        final UserData userData = UserManager.forB2BUser();
        final UsersEditPage usersEdit = new UsersEditPage();

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().setB2BOrders();
        shipments().search();
        //вместо создания заказа получаю первый любой b2b заказ
        String shipmentNumber = shipments().getShipmentNumber();

        users().goToPage();
        users().fillSearchByPhoneNumber(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit.setB2BUser();
        usersEdit.clickToSave();

        usersEdit.unsetB2BUser();
        usersEdit.clickToSave();
        main().doLogout();

        login().goToPage();
        login().auth(userData);

        shipments().goToPage();
        shipments().setB2BOrders();
        shipments().setShipmentOrOrderNumber(shipmentNumber);
        shipments().search();

        shipments().checkOrderOrShipmentNumber(shipments().getShipmentNumber(), shipmentNumber);
    }
}
