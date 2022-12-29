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
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
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

    @TmsLinks(value = {@TmsLink("2092"), @TmsLink("2093")})
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы с разными статусами",
            dataProvider = "shipmentStatuses",
            dataProviderClass = RestDataProvider.class,
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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

    @TmsLink("2094")
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы с разными типами платежей",
            dataProvider = "paymentTools",
            dataProviderClass = RestDataProvider.class,
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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

    @TmsLink("2095")
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы с ограничением по весу",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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

    @TmsLink("2096")
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы для определенной операционной зоны",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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

    @TmsLink("2097")
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы с определенным количеством товаров",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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

    @TmsLinks(value = {@TmsLink("2098"), @TmsLink("2099"), @TmsLink("2100"), @TmsLink("2101"), @TmsLink("2102")})
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы с разными статусами оплаты",
            dataProvider = "paymentStatuses",
            dataProviderClass = RestDataProvider.class,
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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

    @TmsLink("2103")
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы по id магазина",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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
        shipments.forEach(s -> compareTwoObjects(s.getStore().getUuid(), storeUuid, softAssert));
        softAssert.assertAll();
    }

    @TmsLink("2104")
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы по id ритейлера",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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

    @TmsLink("2105")
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы по номеру заказа",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
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

    @TmsLink("2106")
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы по api-client id",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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
    @TmsLink("2107")
    @Story("Cписок заказов")
    @Test(description = "Заказы по имени шоппера",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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
    @TmsLink("2108")
    @Story("Cписок заказов")
    @Test(description = "Заказы по имени водителя",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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

    @TmsLink("2109")
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы по окнам доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getShipmentsForDeliveryDates() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .deliveryWindowStartsAtSt(getPastZoneDbDate(1L))
                .deliveryWindowStartsAtEnd(getDbDeliveryDateFrom(1L))
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
    }

    @TmsLink("2110")
    @Skip(onServer = Server.STAGING)
    @Story("Cписок заказов")
    @Test(description = "Заказы по датам завершения",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getShipmentsForCompletedDates() {
        final Response response = ShipmentsAdminV1Request.GET(ShipmentsAdminV1Request.ShipmentsData.builder()
                .completedAtSt(getPastZoneDbDate(1L))
                .completedAtEnd(getDbDeliveryDateFrom(1L))
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentsV1Response.class);
    }

    @TmsLink("2128")
    @Skip(onServer = Server.STAGING)
    @Story("Заказы")
    @Test(description = "Редактирование комментария к заказу",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "getShipmentsByOperationalZoneId")
    public void editShipmentsComment() {
        String comment = "Autotest" + Generate.literalString(6);
        final Response response = ShipmentsAdminV1Request.PUT(shipment.getUuid(), comment);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AdminShipmentV1Response.class);
        compareTwoObjects(response.as(AdminShipmentV1Response.class).getShipment().getAssemblyComment(), comment);
    }

    @TmsLink("2129")
    @Skip //TODO ATST-2003
    @Story("Заказы")
    @Test(description = "Возврат на диспетчеризацию",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "getShipmentsByOperationalZoneId")
    public void returnToDispatch() {
        final Response response = ShipmentsAdminV1Request.POST(shipment.getUuid(), "Autotest" + Generate.literalString(6));
        checkStatusCode200(response);
    }

    @TmsLink("2130")
    @Skip(onServer = Server.STAGING)
    @Story("Заказы")
    @Test(description = "Редактирование комментария к несуществующему заказу",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void editNonExistentShipmentsComment() {
        final Response response = ShipmentsAdminV1Request.PUT("failedUuid", "Autotest" + Generate.literalString(6));
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @TmsLink("2131")
    @Skip //TODO ATST-2003
    @Story("Заказы")
    @Test(description = "Возврат на диспетчеризацию несуществующего заказа",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void returnNonexistentShipmentToDispatch() {
        final Response response = ShipmentsAdminV1Request.POST("failedUuid", "Autotest" + Generate.literalString(6));
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @TmsLink("2134")
    @Skip(onServer = Server.STAGING)
    @Story("Заказы")
    @Test(description = "Получение документов для несуществующего заказа", //TODO:Добавить позитивные тесты, когда станут понятны предусловия
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getNonExistentShipmentDocument() {
        final Response response = DocumentsV1Request.GET("failedNumber", "consignment_note");
        checkStatusCode404(response);
    }
}
