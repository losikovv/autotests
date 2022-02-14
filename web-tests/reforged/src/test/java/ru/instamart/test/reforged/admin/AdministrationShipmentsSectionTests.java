package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.NotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.admin.ShipmentsAdminV1Request;
import ru.instamart.api.response.v1.admin.ShipmentsAdminV1Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.kraken.util.TimeUtil;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление заказами")
public final class AdministrationShipmentsSectionTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private ShipmentsAdminV1Response.Shipment shipment;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        var shipments = helper.getShipments(ShipmentsAdminV1Request.ShipmentsData
                .builder()
                .page(1)
                .perPage(1)
                .completedShipments(true)
                .paymentMethodId(3)
                .build());
        shipment = shipments.getShipments().stream().findFirst().orElseThrow(NotFoundException::new);
    }

    @Skip
    @CaseId(175)
    @Story("Тест на корректное отображение элементов страницы со списком заказов в админке")
    @Test(description = "Тест на корректное отображение элементов страницы со списком заказов в админке", groups = {"acceptance", "regression"})
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
    @Test(description = "Тест на работоспособность пагинации списка заказов", groups = {"acceptance", "regression", "smoke"})
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
    @Test(description = "Тест на работоспособность фильтра ДАТА И ВРЕМЯ ДОСТАВКИ", groups = {"acceptance", "regression", "smoke"})
    public void validateFilterDateAndTimeAdminShipmentsPage() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageTitle();
        shipments().setDateAndTimeFilterFromTableDefault(TimeUtil.getDeliveryDateFrom());
        shipments().setDateAndTimeFilterToTableDefault(TimeUtil.getDeliveryDateTo());
        shipments().search();
        shipments().waitPageLoad();
        shipments().checkDateAndTimeShipmentsColumn(TimeUtil.getDateWithoutTime());
    }

    @CaseId(173)
    @Story("Тест на работоспособность фильтра ТЕЛЕФОН СОДЕРЖИТ")
    @Test(description = "Тест на работоспособность фильтра ТЕЛЕФОН СОДЕРЖИТ", groups = {"acceptance", "regression", "smoke"})
    public void validateFilterPhoneShipmentsPage() {
        var phone = StringUtil.getPhoneNumber(shipment.order.shipAddress.phoneNumber);
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());
        shipments().goToPage();
        shipments().checkPageTitle();
        shipments().setPhoneFilterFromTableDefault(phone);
        shipments().search();
        shipments().waitPageLoad();
        shipments().checkPhoneShipmentsColumn(phone);
    }

    @CaseId(174)
    @Story("Тест на работоспособность мультифильтра")
    @Test(description = "Тест на работоспособность мультифильтра", groups = {"acceptance", "regression", "smoke"})
    public void validateMultiFiltersShipmentsPage() {
        var phone = StringUtil.getPhoneNumber(shipment.order.shipAddress.phoneNumber);
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageTitle();
        shipments().setPhoneAndDateFilterDefault(phone, TimeUtil.getDeliveryDateFrom());
        shipments().setDateAndTimeFilterToTableDefault(TimeUtil.getDeliveryDateTo());
        shipments().search();
        shipments().waitPageLoad();
        shipments().checkPhoneShipmentsColumn(phone);
        shipments().checkDateAndTimeShipmentsColumn(TimeUtil.getDateWithoutTime());
    }

    @CaseId(1224)
    @Story("Тест на проверку изменения количества заказов после применения фильтра, без пейджера")
    @Test(  description = "Тест на проверку изменения количества заказов после применения фильтра, без пейджера",
            groups = {"acceptance", "regression", "smoke"})
    public void validateShipmentsAfterFiltrationWOTPager() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageTitle();
        String shipmentsBeforeFiltration = shipments().getNumberOfShipments();
        shipments().setShipmentOrOrderNumber(shipment.order.number);
        shipments().search();
        String shipmentsAfterFiltration = shipments().getNumberOfShipments();
        shipments().checkNumberOfShipmentsAfterFiltration(shipmentsBeforeFiltration, shipmentsAfterFiltration);
        shipments().checkLastPagePager();
    }

    @CaseId(1225)
    @Story("Тест на проверку изменения количества заказов после применения фильтра, с пейджером")
    @Test(  description = "Тест на проверку изменения количества заказов после применения фильтра, с пейджером",
            groups = {"acceptance", "regression", "smoke"})
    public void validateShipmentsAfterFiltrationWithPager() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().checkPageTitle();
        String shipmentsBeforeFiltration = shipments().getNumberOfShipments();
        shipments().setPhoneFilterFromTableDefault(shipment.order.shipAddress.phoneNumber);
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
    @Test(description = "Тест поиска заказа по номеру заказа в админке", groups = {"acceptance", "regression", "smoke"})
    public void successSearchOrderByOrderNumber() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().waitPageLoad();
        final String orderNumber = shipment.order.number;
        shipments().setShipmentOrOrderNumber(orderNumber);
        shipments().search();
        shipments().checkFoundOrderOrShipmentCount(shipments().getFoundCount(), 1);
        shipments().checkOrderOrShipmentNumber(shipments().getOrderNumber(), orderNumber);
    }

    @CaseId(445)
    @Story("Тест поиска заказа по номеру шипмента в админке")
    @Test(description = "Тест поиска заказа по номеру шипмента в админке", groups = {"acceptance", "regression", "smoke"})
    public void successSearchOrderByShipmentNumber() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shipments().goToPage();
        shipments().waitPageLoad();
        final String shipmentNumber = shipment.number;
        shipments().setShipmentOrOrderNumber(shipmentNumber);
        shipments().search();
        shipments().checkFoundOrderOrShipmentCount(shipments().getFoundCount(), 1);
        shipments().checkOrderOrShipmentNumber(shipments().getShipmentNumber(), shipmentNumber);
    }

    // TODO тест можно ускорить - использовать тестовый заказ из конфига
    // TODO поправить тест после того как починб тест заказа
    @Story("Тест возобновления и отмены заказа через админку")
    @Test(enabled = false, description = "Тест возобновления и отмены заказа через админку", groups = {"acceptance", "regression"})
    public void successResumeAndCancelOrder() {
        final ApiHelper helper = new ApiHelper();
        final UserData userData = UserManager.getQaUser();
        final OrderV2 orderV2 = helper.makeOrder(userData, EnvironmentProperties.DEFAULT_SID, 3);
        helper.cancelOrder(userData, orderV2.getNumber());

        //TODO: Заказ появляется в админке с задержкой рандомной
    }

    @CaseId(183)
    @Story("Тест поиска B2B заказа в админке")
    @Test(description = "Тест поиска B2B заказа в админке", groups = {"acceptance", "regression"})
    public void successSearchB2BOrder() {
        final var shipmentNumber = shipment.number;

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        shipments().goToPage();
        shipments().setB2BOrders();
        shipments().setShipmentOrOrderNumber(shipmentNumber);
        shipments().search();
        shipments().waitPageLoad();
        shipments().checkOrderOrShipmentNumber(shipments().getShipmentNumber(), shipmentNumber);
    }

    @Story("Тест поиска B2B заказа после снятия признака B2B")
    @Test(description = "Тест поиска B2B заказа после снятия признака B2B", groups = {"acceptance", "regression"})
    public void successSearchB2BOrderAfterRevokeB2BRole() {
        //вместо создания заказа получаю первый любой b2b заказ
        final var shipmentNumber = shipment.number;

        login().goToPage();
        login().auth(UserManager.forB2BUser());

        shipments().goToPage();
        shipments().setB2BOrders();
        shipments().setShipmentOrOrderNumber(shipmentNumber);
        shipments().search();
        shipments().waitPageLoad();
        shipments().checkOrderOrShipmentNumber(shipments().getShipmentNumber(), shipmentNumber);
    }
}
