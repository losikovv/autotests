package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.ApiV3DataProvider;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.enums.v3.NotificationTypeV3;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.K8sHelper;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.api.request.v3.NotificationsV3Request;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.kraken.util.ThreadUtil.simplyAwait;

@Epic("ApiV3")
@Feature("Нотификации")
public class NotificationsPositionsV3Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        K8sHelper.allowExportToExternalServices(true);
        K8sHelper.exportToExternalServicesByWebhook(true);
        admin.auth();
        admin.editStore(58, StoresAdminRequest.getStoreLentaOrekhoviyBulvar());
    }

    @Story("Позиции заказа")
    @CaseId(945)
    @Test(description = "Заказ собран без изменений (все типы упаковок)",
            groups = {"api-instamart-regress"},
            dataProvider = "ordersWithDifferentPricers",
            dataProviderClass = ApiV3DataProvider.class)
    public void noChanges(OrderV2 order) {
        String retailerSku = order.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = order.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getPacks(), quantity,
                "Количество товаров отличается после сборки");
    }

    @Story("Позиции заказа")
    @CaseId(874)
    @Test(enabled = false,
    description = "Собрано меньше изначального количества",
            groups = {"api-instamart-regress"},
            dataProvider = "ordersWithDifferentPricers",
            dataProviderClass = ApiV3DataProvider.class)
    //todo включить когда появится возможность создавать заказы с весовыми товарами
    public void less(OrderV2 order) {
        String retailerSku = order.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = order.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                --quantity);
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getPacks(), quantity,
                "Количество товаров отличается от переданного");
    }

    @Story("Позиции заказа")
    @CaseId(875)
    @Test(description = "Собрано больше изначального количества",
            groups = {"api-instamart-regress"},
            dataProvider = "ordersWithDifferentPricers",
            dataProviderClass = ApiV3DataProvider.class)
    public void more(OrderV2 order) {
        String retailerSku = order.getShipments().get(0).getLineItems().get(0).getProduct().getRetailerSku();
        Integer quantity = order.getShipments().get(0).getLineItems().get(0).getPacks();

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity);
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                ++quantity);
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getPacks(), quantity,
                "Количество товаров отличается от переданного");
    }

    @Story("Передача точного веса")
    @CaseId(949)
    @Test(enabled = false,
    description = "Вес передан ритейлером (весовые товары)",
            groups = {"api-instamart-regress"})
    //todo включить когда появится возможность создавать заказы с весовыми товарами
    public void weightKilo() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_KILO);

        LineItemV2 lineItem = order.getShipments().get(0).getLineItems().get(0);

        String retailerSku = lineItem.getProduct().getRetailerSku();
        Integer quantity = lineItem.getPacks();
        Double gramsPerUnit = lineItem.getProduct().getGramsPerUnit() * quantity + 250;
        Double expQuantity = Math.floor(gramsPerUnit / lineItem.getProduct().getGramsPerUnit());
        Double expTotal = Math.floor(lineItem.getProduct().getUnitPrice() * gramsPerUnit / 100);

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity + 3,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());
        Double total = Math.floor(readyOrder.getShipments().get(0).getLineItems().get(0).getTotal() * 10);

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getUnitQuantity(), expQuantity,
                "Количество товаров отличается от расчетного значения");
        Assert.assertEquals(total, expTotal,
                "Сумма отличается от расчетного значения");
    }

    @Story("Передача точного веса")
    @CaseId(1959)
    @Test(description = "Вес передан ритейлером (фасованные товары)",
            groups = {"api-instamart-regress"})
    public void weightPackage() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_PACKAGE);

        LineItemV2 lineItem = order.getShipments().get(0).getLineItems().get(0);

        String retailerSku = lineItem.getProduct().getRetailerSku();
        Integer quantity = lineItem.getPacks();
        Double gramsPerUnit = lineItem.getProduct().getGramsPerUnit() * quantity + 250;
        Double expQuantity = Math.floor(gramsPerUnit / lineItem.getProduct().getGramsPerUnit());
        Double expTotal = Math.floor(lineItem.getProduct().getUnitPrice() * gramsPerUnit / 100);

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity + 3,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());
        Double total = Math.floor(readyOrder.getShipments().get(0).getLineItems().get(0).getTotal() * 10);

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getUnitQuantity(), expQuantity,
                "Количество товаров отличается от расчетного значения");
        Assert.assertEquals(total, expTotal,
                "Сумма отличается от расчетного значения");
    }

    @Story("Передача точного веса")
    @CaseId(950)
    @Test(description = "Вес передан ритейлером (штучные товары)",
            groups = {"api-instamart-regress"})
    public void weightItem() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_ITEM);

        LineItemV2 lineItem = order.getShipments().get(0).getLineItems().get(0);

        String retailerSku = lineItem.getProduct().getRetailerSku();
        Integer quantity = lineItem.getPacks();
        Double gramsPerUnit = lineItem.getProduct().getGramsPerUnit() * 2 * quantity + 250;

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                ++quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());
        Double expTotal = lineItem.getProduct().getUnitPrice() * quantity;
        Double total = readyOrder.getShipments().get(0).getLineItems().get(0).getTotal();

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getPacks(), quantity,
                "Количество товаров отличается от расчетного значения");
        Assert.assertEquals(total, expTotal,
                "Сумма отличается от расчетного значения");
    }

    @Story("Передача точного веса")
    @CaseId(1960)
    @Test(description = "Вес передан ритейлером (упаковки)",
            groups = {"api-instamart-regress"})
    public void weightPack() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_PACK);

        LineItemV2 lineItem = order.getShipments().get(0).getLineItems().get(0);

        String retailerSku = lineItem.getProduct().getRetailerSku();
        Integer quantity = lineItem.getPacks();
        Double gramsPerUnit = lineItem.getProduct().getGramsPerUnit() * 2 * quantity + 250;

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                ++quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());
        Double expTotal = lineItem.getProduct().getUnitPrice() * quantity;
        Double total = readyOrder.getShipments().get(0).getLineItems().get(0).getTotal();

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getPacks(), quantity,
                "Количество товаров отличается от расчетного значения");
        Assert.assertEquals(total, expTotal,
                "Сумма отличается от расчетного значения");
    }

    @Story("Передача точного веса")
    @CaseId(2042)
    @Test(description = "Передан 0 вес ритейлером (штучные товары)",
            groups = {"api-instamart-regress"})
    public void weightNullItem() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_ITEM);

        LineItemV2 lineItem = order.getShipments().get(0).getLineItems().get(0);

        String retailerSku = lineItem.getProduct().getRetailerSku();
        Integer quantity = lineItem.getPacks();
        Double expTotal = lineItem.getProduct().getUnitPrice() * quantity;

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity,
                "0");
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity,
                "0");
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());
        Double total = readyOrder.getShipments().get(0).getLineItems().get(0).getTotal();

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getPacks(), quantity,
                "Количество товаров отличается от расчетного значения");
        Assert.assertEquals(total, expTotal,
                "Сумма отличается от расчетного значения");
    }

    @Story("Передача точного веса")
    @CaseId(2043)
    @Test(description = "Передан 0 вес ритейлером (упаковки)",
            groups = {"api-instamart-regress"})
    public void weightNullPack() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_PACK);

        LineItemV2 lineItem = order.getShipments().get(0).getLineItems().get(0);

        String retailerSku = lineItem.getProduct().getRetailerSku();
        Integer quantity = lineItem.getPacks();
        Double expTotal = lineItem.getProduct().getUnitPrice() * quantity;

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity,
                "0");
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity,
                "0");
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());
        Double total = readyOrder.getShipments().get(0).getLineItems().get(0).getTotal();

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getPacks(), quantity,
                "Количество товаров отличается от расчетного значения");
        Assert.assertEquals(total, expTotal,
                "Сумма отличается от расчетного значения");
    }

    @Story("Передача точного веса")
    @CaseId(976)
    @Test(enabled = false,
    description = "Передано 0 квантов при не 0 весе ритейлером (весовые)",
            groups = {"api-instamart-regress"})
    //todo включить когда появится возможность создавать заказы с весовыми товарами
    public void weightNotNullKilo() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_KILO);

        LineItemV2 lineItem = order.getShipments().get(0).getLineItems().get(0);

        String retailerSku = lineItem.getProduct().getRetailerSku();
        Integer quantity = lineItem.getPacks();
        Double gramsPerUnit = lineItem.getProduct().getGramsPerUnit() * quantity + 250;
        Double expQuantity = Math.floor(gramsPerUnit / lineItem.getProduct().getGramsPerUnit());
        Double expTotal = Math.floor(lineItem.getProduct().getUnitPrice() * gramsPerUnit / 100);

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity + 3,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());
        Double total = Math.floor(readyOrder.getShipments().get(0).getLineItems().get(0).getTotal() * 10);

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getUnitQuantity(), expQuantity,
                "Количество товаров отличается от расчетного значения");
        Assert.assertEquals(total, expTotal,
                "Сумма отличается от расчетного значения");
    }

    @Story("Передача точного веса")
    @CaseId(1959)
    @Test(description = "Передано 0 квантов при не 0 весе ритейлером (фасованные)",
            groups = {"api-instamart-regress"})
    public void weightNotNullPackage() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_PACKAGE);

        LineItemV2 lineItem = order.getShipments().get(0).getLineItems().get(0);

        String retailerSku = lineItem.getProduct().getRetailerSku();
        Integer quantity = lineItem.getPacks();
        Double gramsPerUnit = lineItem.getProduct().getGramsPerUnit() * quantity + 250;
        Double expQuantity = Math.floor(gramsPerUnit / lineItem.getProduct().getGramsPerUnit());
        Double expTotal = Math.floor(lineItem.getProduct().getUnitPrice() * gramsPerUnit / 100);

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                0,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());
        Double total = Math.floor(readyOrder.getShipments().get(0).getLineItems().get(0).getTotal() * 10);

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getUnitQuantity(), expQuantity,
                "Количество товаров отличается от расчетного значения");
        Assert.assertEquals(total, expTotal,
                "Сумма отличается от расчетного значения");
    }

    @Story("Передача точного веса")
    @CaseId(1092)
    @Test(enabled = false,
    description = "Собрано меньше 1 кванта (весовые товары)",
            groups = {"api-instamart-regress"})
        //todo включить когда появится возможность создавать заказы с весовыми товарами
    public void weightLessKilo() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_KILO);

        LineItemV2 lineItem = order.getShipments().get(0).getLineItems().get(0);

        String retailerSku = lineItem.getProduct().getRetailerSku();
        Integer quantity = lineItem.getPacks();
        Double gramsPerUnit = lineItem.getProduct().getGramsPerUnit() * quantity - 20;
        Double expQuantity = Math.floor(gramsPerUnit / lineItem.getProduct().getGramsPerUnit());
        Double expTotal = Math.floor(lineItem.getProduct().getUnitPrice() * gramsPerUnit / 100);

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());
        Double total = Math.floor(readyOrder.getShipments().get(0).getLineItems().get(0).getTotal() * 10);

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getUnitQuantity(), expQuantity,
                "Количество товаров отличается от расчетного значения");
        Assert.assertEquals(total, expTotal,
                "Сумма отличается от расчетного значения");
    }

    @Story("Передача точного веса")
    @CaseId(1961)
    @Test(description = "Собрано меньше 1 кванта (фасованные товары)",
            groups = {"api-instamart-regress"})
    public void weightLessPackage() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_PACKAGE);

        LineItemV2 lineItem = order.getShipments().get(0).getLineItems().get(0);

        String retailerSku = lineItem.getProduct().getRetailerSku();
        Integer quantity = lineItem.getPacks();
        Double gramsPerUnit = lineItem.getProduct().getGramsPerUnit() * quantity - 20;
        Double expQuantity = Math.floor(gramsPerUnit / lineItem.getProduct().getGramsPerUnit());
        Double expTotal = Math.floor(lineItem.getProduct().getUnitPrice() * gramsPerUnit / 100);

        Response responseInWork = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.IN_WORK.getValue(),
                retailerSku,
                quantity,
                quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseInWork);
        Response responseReady = NotificationsV3Request.POST(
                order.getShipments().get(0).getNumber(),
                NotificationTypeV3.READY_FOR_DELIVERY.getValue(),
                retailerSku,
                quantity,
                quantity,
                String.valueOf(gramsPerUnit));
        checkStatusCode200(responseReady);

        simplyAwait(2);
        OrderV2 readyOrder = apiV2.getOrder(order.getNumber());
        Double total = Math.floor(readyOrder.getShipments().get(0).getLineItems().get(0).getTotal() * 10);

        Assert.assertEquals(readyOrder.getShipments().get(0).getLineItems().get(0).getUnitQuantity(), expQuantity,
                "Количество товаров отличается от расчетного значения");
        Assert.assertEquals(total, expTotal,
                "Сумма отличается от расчетного значения");
    }
}
