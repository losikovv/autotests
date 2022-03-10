package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.EventsV1Request;
import ru.instamart.jdbc.dao.*;
import ru.instamart.jdbc.entity.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.UUID;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.request.v1.EventsV1Request.getAssemblyData;
import static ru.instamart.kraken.util.TimeUtil.getZonedDate;

@Epic("ApiV1")
@Feature("Shoppers Events")
public class ShoppersEventsV1Tests extends RestBase {

    private OrderV2 order;
    private SpreeShipmentsEntity shipment;
    private SpreeLineItemsEntity lineItemFromDb;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
        SessionFactory.makeSession(SessionType.API_V2);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
    }


    @CaseId(2149)
    @Test(description = "Healthcheck магазина",
            groups = {"api-instamart-regress"})
    public void checkStores(){
        final Response response = EventsV1Request.StoreHealthCheck.POST(new String[]{StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_SID).get().getUuid()});
        checkStatusCode(response, 202);
    }

    @CaseId(2150)
    @Test(description = "Обновление способа оплаты",
            groups = {"api-instamart-regress"})
    public void updateOrderPaymentTool(){
        Long paymentToolId = apiV1.getPaymentTools().get(0).getId();
        final Response response = EventsV1Request.OrderPaymentTool.POST(order.getNumber(), paymentToolId);
        checkStatusCode(response, 202);
        ThreadUtil.simplyAwait(1);
        SpreeOrdersEntity orderFromDb = SpreeOrdersDao.INSTANCE.getOrderByNumber(order.getNumber());
        compareTwoObjects(orderFromDb.getPaymentToolId(), paymentToolId);
    }

    @CaseId(2153)
    @Test(description = "Добавление нового товара",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "updateOrderPaymentTool")
    public void addNewLineItem(){
        OffersEntity offer = OffersDao.INSTANCE.getOfferByStoreId(EnvironmentProperties.DEFAULT_SID);
        shipment = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber());
        EventsV1Request.EventData lineItemData = EventsV1Request.EventData.builder()
                .uuid(UUID.randomUUID().toString())
                .timestamp(getZonedDate())
                .lineItemUuid(UUID.randomUUID().toString())
                .offerUuid(offer.getUuid())
                .shipmentUuid(shipment.getUuid())
                .price(order.getShipments().get(0).getLineItems().get(0).getPrice().toString())
                .itemsPerPack(1)
                .retailerShelfPrice("145")
                .quantity(1)
                .foundQuantity(1)
                .assemblyIssue("Все собрано")
                .assembled(false)
                .build();

        final Response response = EventsV1Request.LineItem.Created.POST(lineItemData);
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        lineItemFromDb = SpreeLineItemsDao.INSTANCE.getLineItemByOfferIdAndShipmentId(offer.getId(), shipment.getId());
        checkFieldIsNotEmpty(lineItemFromDb, "добавленный товар");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(lineItemFromDb.getRetailerShelfPrice().toString(), "145.0", softAssert);
        compareTwoObjects(lineItemFromDb.getQuantity(), 1, softAssert);
        compareTwoObjects(lineItemFromDb.getFoundQuantity(), 1.0, softAssert);
        compareTwoObjects(lineItemFromDb.getAssemblyIssue(), "Все собрано", softAssert);
        compareTwoObjects(lineItemFromDb.getAssembled(), 0, softAssert);
        softAssert.assertAll();
    }

    @CaseId(2154)
    @Test(description = "Отмена товара",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "addNewLineItem")
    public void cancelLineItem(){
        EventsV1Request.EventData lineItemData = EventsV1Request.EventData.builder()
                .uuid(UUID.randomUUID().toString())
                .timestamp(getZonedDate())
                .lineItemUuid(lineItemFromDb.getUuid())
                .assemblyIssue("Отмена: нет в наличии")
                .assembled(false)
                .build();

        final Response response = EventsV1Request.LineItem.Cancelled.POST(lineItemData);
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        SpreeLineItemsEntity cancelledLineItemFromDb = SpreeLineItemsDao.INSTANCE.findById(lineItemFromDb.getId()).get();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(cancelledLineItemFromDb.getAssemblyIssue(), "Отмена: нет в наличии", softAssert);
        checkFieldIsNotEmpty(cancelledLineItemFromDb.getDeletedAt(), "дата удаления", softAssert);
        softAssert.assertAll();
    }

    @CaseId(2155)
    @Test(description = "Восстановление товара",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "cancelLineItem")
    public void restoreLineItem(){
        EventsV1Request.EventData lineItemData = EventsV1Request.EventData.builder()
                .uuid(UUID.randomUUID().toString())
                .timestamp(getZonedDate())
                .lineItemUuid(lineItemFromDb.getUuid())
                .build();

        final Response response = EventsV1Request.LineItem.Cancelled.POST(lineItemData);
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        SpreeLineItemsEntity cancelledLineItemFromDb = SpreeLineItemsDao.INSTANCE.findById(lineItemFromDb.getId()).get();
        Assert.assertNull(cancelledLineItemFromDb.getAssemblyIssue());
    }

    @CaseId(2156)
    @Test(description = "Изменение товара",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "restoreLineItem")
    public void updateLineItem(){
        EventsV1Request.EventData lineItemData = EventsV1Request.EventData.builder()
                .uuid(UUID.randomUUID().toString())
                .timestamp(getZonedDate())
                .lineItemUuid(lineItemFromDb.getUuid())
                .assemblyIssue("Собрано")
                .assembled(true)
                .price("120")
                .quantity(4)
                .foundQuantity(4)
                .build();

        final Response response = EventsV1Request.LineItem.Updated.POST(lineItemData);
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        SpreeLineItemsEntity updatedLineItemFromDb = SpreeLineItemsDao.INSTANCE.findById(lineItemFromDb.getId()).get();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(updatedLineItemFromDb.getQuantity(), 4, softAssert);
        compareTwoObjects(updatedLineItemFromDb.getFoundQuantity(), 4.0, softAssert);
        compareTwoObjects(updatedLineItemFromDb.getAssemblyIssue(), "Собрано", softAssert);
        compareTwoObjects(updatedLineItemFromDb.getAssembled(), 1, softAssert);
        softAssert.assertAll();
    }

    @CaseId(2157)
    @Test(description = "Возврат товара",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "updateLineItem")
    public void returnLineItem(){
        EventsV1Request.EventData lineItemData = EventsV1Request.EventData.builder()
                .uuid(UUID.randomUUID().toString())
                .timestamp(getZonedDate())
                .lineItemUuid(lineItemFromDb.getUuid())
                .assemblyIssue("Возврат: Забыли доставить")
                .assembled(false)
                .price("120")
                .quantity(0)
                .foundQuantity(0)
                .returnQuantity(4)
                .build();

        final Response response = EventsV1Request.LineItem.Returned.POST(lineItemData);
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        SpreeLineItemsEntity updatedLineItemFromDb = SpreeLineItemsDao.INSTANCE.findById(lineItemFromDb.getId()).get();
        compareTwoObjects(updatedLineItemFromDb.getAssemblyIssue(), "Возврат: Забыли доставить");
    }

    @CaseId(2158)
    @Test(description = "Начало сборки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "returnLineItem")
    public void createAssembly(){
        final Response response = EventsV1Request.Assembly.Created.POST(getAssemblyData(shipment.getUuid()));
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        SpreeShipmentsEntity updatedShipment = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber());
        compareTwoObjects(updatedShipment.getState(), StateV2.COLLECTING.getValue());

    }

    @CaseId(2159)
    @Test(description = "Изменение сборки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createAssembly")
    public void updateAssembly(){
        EventsV1Request.EventData eventData = getAssemblyData(shipment.getUuid());
        eventData.setAssemblyInvoiceNumber("invoice123");
        eventData.setAssemblyInvoiceTotal(100.0);
        final Response response = EventsV1Request.Assembly.Updated.POST(eventData);
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(2);
        SpreeShipmentsEntity updatedShipment = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber());
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(updatedShipment.getState(), StateV2.COLLECTING.getValue(), softAssert);
        compareTwoObjects(updatedShipment.getInvoiceNumber(), "invoice123", softAssert);
        compareTwoObjects(updatedShipment.getInvoiceTotal(), 100.0, softAssert);
        softAssert.assertAll();
    }

    @CaseId(2160)
    @Test(description = "Разбор сборки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "updateAssembly")
    public void destroyAssembly(){
        final Response response = EventsV1Request.Assembly.Destroyed.POST(getAssemblyData(shipment.getUuid()));
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(2);
        SpreeShipmentsEntity updatedShipment = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber());
        compareTwoObjects(updatedShipment.getState(), StateV2.READY.getValue());
    }

    @CaseId(2161)
    @Test(description = "Подтверждение сборки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "destroyAssembly")
    public void approveAssembly(){
        final Response startResponse = EventsV1Request.Assembly.Created.POST(getAssemblyData(shipment.getUuid()));
        checkStatusCode(startResponse, 202);
        final Response response = EventsV1Request.Assembly.Approved.POST(shipment.getUuid());
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        SpreeShipmentsEntity updatedShipment = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber());
        compareTwoObjects(updatedShipment.getState(), StateV2.COLLECTING.getValue());
    }

    @CaseId(2162)
    @Test(description = "Оплата сборки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "destroyAssembly")
    public void purchaseAssembly(){
        final Response response = EventsV1Request.Assembly.Purchased.POST(getAssemblyData(shipment.getUuid()));
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        SpreeShipmentsEntity updatedShipment = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber());
        compareTwoObjects(updatedShipment.getState(), StateV2.READY_TO_SHIP.getValue());
    }

    @CaseId(2163)
    @Test(description = "Начало доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "purchaseAssembly")
    public void startShippingAssembly(){
        final Response response = EventsV1Request.Assembly.ShippingStarted.POST(getAssemblyData(shipment.getUuid()));
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        SpreeShipmentsEntity updatedShipment = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber());
        compareTwoObjects(updatedShipment.getState(), StateV2.SHIPPING.getValue());
    }

    @CaseId(2164)
    @Test(description = "Приостановка доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "startShippingAssembly")
    public void stopShippingAssembly(){
        final Response response = EventsV1Request.Assembly.ShippingStopped.POST(getAssemblyData(shipment.getUuid()));
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        SpreeShipmentsEntity updatedShipment = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber());
        compareTwoObjects(updatedShipment.getState(), StateV2.READY_TO_SHIP.getValue());
    }

    @CaseId(2165)
    @Test(description = "Окончание доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "stopShippingAssembly")
    public void shipAssembly(){
        EventsV1Request.EventData eventData = getAssemblyData(shipment.getUuid());
        eventData.setAssemblyShippedAt(getZonedDate());
        final Response response = EventsV1Request.Assembly.Shipped.POST(eventData);
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        SpreeShipmentsEntity updatedShipment = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber());
        compareTwoObjects(updatedShipment.getState(), StateV2.SHIPPED.getValue());
    }

    @CaseId(2150)
    @Test(description = "Создание чека",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "shipAssembly")
    public void createReceipt(){
        final Response response = EventsV1Request.ReceiptCreated.POST(shipment.getUuid());
        checkStatusCode(response, 202);

        ThreadUtil.simplyAwait(1);
        FiscalReceiptsEntity fiscalReceipt = FiscalReceiptsDao.INSTANCE.getReceiptByShipmenId(shipment.getId());
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(fiscalReceipt.getTotal(), 100.0, softAssert);
        compareTwoObjects(fiscalReceipt.getFiscalSecret(), "9400000000000000", softAssert);
        compareTwoObjects(fiscalReceipt.getFiscalChecksum(), "10000", softAssert);
        compareTwoObjects(fiscalReceipt.getFiscalDocumentNumber(), "11", softAssert);
        compareTwoObjects(fiscalReceipt.getTransactionDetails(), "t=20210622T120000", softAssert);
        softAssert.assertAll();
    }
}
