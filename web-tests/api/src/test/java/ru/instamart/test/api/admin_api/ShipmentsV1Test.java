package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.v1.CombinedStateV1;
import ru.instamart.api.enums.v1.PaymentStatusV1;
import ru.instamart.api.model.v1.AdminShipmentV1;
import ru.instamart.api.model.v1.OrderPaymentMethodV1;
import ru.instamart.api.request.v1.DocumentsV1Request;
import ru.instamart.api.request.v1.admin.ShipmentsAdminV1Request;
import ru.instamart.api.response.v1.AdminShipmentV1Response;
import ru.instamart.api.response.v1.AdminShipmentsV1Response;
import ru.instamart.jdbc.dao.stf.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.kraken.util.TimeUtil.getDbDeliveryDateFrom;
import static ru.instamart.kraken.util.TimeUtil.getPastZoneDbDate;

@Epic("ApiV1")
@Feature("Заказы")
public class ShipmentsV1Test extends RestBase {

    private String orderNumber;
    private AdminShipmentV1 shipment;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @CaseIDs(value = {@CaseId(2092), @CaseId(2093)})
    @Story("Cписок заказов")
    @Test(description = "Заказы с разными статусами",
            dataProvider = "shipmentStatuses",
            dataProviderClass = RestDataProvider.class,
            groups = {"api-instamart-regress"})
    public void getShipmentsWithDifferentStates(CombinedStateV1 state) {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .combinedState(state.getValue())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments().stream().filter(s -> !s.getCombinedState().equals("dispatch_postponed")).collect(Collectors.toList());
        final SoftAssert softAssert = new SoftAssert();
        shipments.forEach(s -> compareTwoObjects(s.getCombinedState(), state.getValue(), softAssert));
        softAssert.assertAll();
    }

    @CaseId(2094)
    @Story("Cписок заказов")
    @Test(description = "Заказы с разными типами платежей",
            dataProvider = "paymentTools",
            dataProviderClass = RestDataProvider.class,
            groups = {"api-instamart-regress"})
    public void getShipmentsWithDifferentPaymentMethods(OrderPaymentMethodV1 paymentMethod) {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .paymentMethodId(paymentMethod.getId())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        final SoftAssert softAssert = new SoftAssert();
        for (AdminShipmentV1 shipment: shipments) {
            List<String> paymentMethodNames = new ArrayList<>();
            shipment.getPayments().forEach(p -> paymentMethodNames.add(p.getPaymentMethod().getName()));
            softAssert.assertTrue(paymentMethodNames.contains(paymentMethod.getName()), "Пришел неверный тип платежа");
        }
        softAssert.assertAll();
    }

    @CaseId(2095)
    @Story("Cписок заказов")
    @Test(description = "Заказы с ограничением по весу",
            groups = {"api-instamart-regress"})
    public void getShipmentsWithWeightRestriction() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .totalWeightSt(1.0)
                .totalWeightEnd(2.0)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        final SoftAssert softAssert = new SoftAssert();
        shipments.forEach(s -> softAssert.assertTrue(Double.parseDouble(s.getDisplayTotalWeight().replace(",", ".").split(" ")[0]) >= 1.0
                && Double.parseDouble(s.getDisplayTotalWeight().replace(",", ".").split(" ")[0]) <= 2.0, "Вес не совпадает"));
        softAssert.assertAll();
    }

    @CaseId(2096)
    @Story("Cписок заказов")
    @Test(description = "Заказы для определенной операционной зоны",
            groups = {"api-instamart-regress"})
    public void getShipmentsByOperationalZoneId() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .operationalZoneId(OperationalZonesDao.INSTANCE.getIdByName("Москва"))
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        orderNumber = shipments.get(0).getOrder().getNumber();
        shipment = shipments.get(0);
        final SoftAssert softAssert = new SoftAssert();
        shipments.forEach(s -> softAssert.assertTrue(List.of("Moscow", "Москва", "Elino", "Балашиха").contains(s.getOrder().getShipAddress().getCity()), "Пришел неверный город"));
        softAssert.assertAll();
    }

    @CaseId(2097)
    @Story("Cписок заказов")
    @Test(description = "Заказы с определенным количеством товаров",
            groups = {"api-instamart-regress"})
    public void getShipmentsWithCountRestriction() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .itemCountSt(2)
                .itemCountEnd(4)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        final SoftAssert softAssert = new SoftAssert();
        shipments.forEach(s -> softAssert.assertTrue(SpreeShipmentsDao.INSTANCE.getShipmentByNumber(s.getNumber()).getItemCount() >= 2 && SpreeShipmentsDao.INSTANCE.getShipmentByNumber(s.getNumber()).getItemCount() <= 4,
                "Количество товара не совпадает"));
        softAssert.assertAll();
    }

    @CaseIDs(value = {@CaseId(2098), @CaseId(2099), @CaseId(2100), @CaseId(2101), @CaseId(2102)})
    @Story("Cписок заказов")
    @Test(description = "Заказы с разными статусами оплаты",
            dataProvider = "paymentStatuses",
            dataProviderClass = RestDataProvider.class,
            groups = {"api-instamart-regress"})
    public void getShipmentsWithDifferentPaymentStatuses(PaymentStatusV1 paymentStatus) {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .paymentState(paymentStatus.getValue())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        final SoftAssert softAssert = new SoftAssert();
        shipments.forEach(s -> compareTwoObjects(s.getOrder().getPaymentState(), paymentStatus.getValue(), softAssert));
        softAssert.assertAll();
    }

    @CaseId(2103)
    @Story("Cписок заказов")
    @Test(description = "Заказы по id магазина",
            groups = {"api-instamart-regress"})
    public void getShipmentsByStoreId() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .storeId(EnvironmentProperties.DEFAULT_SID)
                .perPage(10)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        String storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_SID).get().getUuid();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shipments.size(), 10);
        shipments.forEach(s -> compareTwoObjects(s.getStore().getUuid(), storeUuid, softAssert));
        softAssert.assertAll();
    }

    @CaseId(2104)
    @Story("Cписок заказов")
    @Test(description = "Заказы по id ритейлера",
            groups = {"api-instamart-regress"})
    public void getShipmentsByRetailerId() {
        Long retailerId = SpreeRetailersDao.INSTANCE.getIdBySlug("metro");
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .retailerId(retailerId)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        final SoftAssert softAssert = new SoftAssert();
        shipments.forEach(s -> compareTwoObjects(s.getRetailer().getId(), retailerId, softAssert));
        softAssert.assertAll();
    }

    @CaseId(2105)
    @Story("Cписок заказов")
    @Test(description = "Заказы по номеру заказа",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getShipmentsByOperationalZoneId")
    public void getShipmentsByOrder() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .orderNumber(orderNumber)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        compareTwoObjects(shipments.get(0).getOrder().getNumber(), orderNumber);
    }

    @CaseId(2106)
    @Story("Cписок заказов")
    @Test(description = "Заказы по api-client id",
            groups = {"api-instamart-regress"})
    public void getShipmentsByApiClient() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .apiClientId(ApiClientsDao.INSTANCE.getClientIdByName("aliexpress"))
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        compareTwoObjects(shipments.get(0).getOrder().getPlatform(), "aliexpress");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2107)
    @Story("Cписок заказов")
    @Test(description = "Заказы по имени шоппера",
            groups = {"api-instamart-regress"})
    public void getShipmentsByShopperName() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .shopperLogin("krakenSchedule2")
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        final SoftAssert softAssert = new SoftAssert();
        shipments.forEach(s -> compareTwoObjects(s.getShopper().getLogin(), "krakenSchedule2", softAssert));
        softAssert.assertAll();
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2108)
    @Story("Cписок заказов")
    @Test(description = "Заказы по имени водителя",
            groups = {"api-instamart-regress"})
    public void getShipmentsByDriverName() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .driverLogin("krakenSchedule2")
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
        List<AdminShipmentV1> shipments = response.as(AdminShipmentsV1Response.class).getShipments();
        final SoftAssert softAssert = new SoftAssert();
        shipments.forEach(s -> compareTwoObjects(s.getDriver().getLogin(), "krakenSchedule2", softAssert));
        softAssert.assertAll();
    }

    @CaseId(2109)
    @Story("Cписок заказов")
    @Test(description = "Заказы по окнам доставки",
            groups = {"api-instamart-regress"})
    public void getShipmentsForDeliveryDates() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .deliveryWindowStartsAtSt(getPastZoneDbDate(1L))
                .deliveryWindowStartsAtEnd(getDbDeliveryDateFrom(1L))
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
    }

    @CaseId(2110)
    @Story("Cписок заказов")
    @Test(description = "Заказы по датам завершения",
            groups = {"api-instamart-regress"})
    public void getShipmentsForCompletedDates() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .completedAtSt(getPastZoneDbDate(1L))
                .completedAtEnd(getDbDeliveryDateFrom(1L))
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
    }

    @CaseId(2128)
    @Story("Заказы")
    @Test(description = "Редактирование комментария к заказу",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getShipmentsByOperationalZoneId")
    public void editShipmentsComment() {
        String comment = "Autotest" + Generate.literalString(6);
        final Response response = ShipmentsAdminV1Request.PUT(shipment.getUuid(), comment);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentV1Response.class);
        compareTwoObjects(response.as(AdminShipmentV1Response.class).getShipment().getAssemblyComment(), comment);
    }

    @CaseId(2129)
    @Story("Заказы")
    @Test(description = "Возврат на диспетчеризацию", enabled = false, //ATST-2003
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getShipmentsByOperationalZoneId")
    public void returnToDispatch() {
        final Response response = ShipmentsAdminV1Request.POST(shipment.getUuid(), "Autotest" + Generate.literalString(6));
        checkStatusCode200(response);
    }

    @CaseId(2130)
    @Story("Заказы")
    @Test(description = "Редактирование комментария к несуществующему заказу",
            groups = {"api-instamart-regress"})
    public void editNonExistentShipmentsComment() {
        final Response response = ShipmentsAdminV1Request.PUT("failedUuid", "Autotest" + Generate.literalString(6));
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2131)
    @Story("Заказы")
    @Test(description = "Возврат на диспетчеризацию несуществующего заказа", enabled = false, //ATST-2003
            groups = {"api-instamart-regress"})
    public void returnNonexistentShipmentToDispatch() {
        final Response response = ShipmentsAdminV1Request.POST("failedUuid", "Autotest" + Generate.literalString(6));
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2134)
    @Story("Заказы")
    @Test(description = "Получение документов для несуществующего заказа", //TODO:Добавить позитивные тесты, когда станут понятны предусловия
            groups = {"api-instamart-regress"})
    public void getNonExistentShipmentDocument() {
        final Response response = DocumentsV1Request.GET("failedNumber", "consignment_note");
        checkStatusCode404(response);
    }
}
