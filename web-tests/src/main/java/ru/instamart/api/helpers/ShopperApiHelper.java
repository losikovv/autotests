package instamart.api.helpers;

import instamart.api.SessionFactory;
import instamart.api.enums.SessionType;
import instamart.api.enums.shopper.AssemblyState;
import instamart.api.enums.shopper.PackageSetLocation;
import instamart.api.objects.shopper.*;
import instamart.api.requests.shopper.*;
import instamart.api.responses.shopper.*;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.helpers.WaitingHelper;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static instamart.api.checkpoints.ShopperApiCheckpoints.assertStatusCode200;

public class ShopperApiHelper {

    private static final Logger log = LoggerFactory.getLogger(ShopperApiHelper.class);

    private String currentAssemblyId;

    public void authorisation(UserData user) {
        SessionFactory.createSessionToken(SessionType.SHOPPER, user);
    }

    public void refreshAuth() {
        Response response = AuthRequest.Refresh.POST();
        assertStatusCode200(response);
        SessionAttributes sessionAttributes = response
                .as(SessionsResponse.class)
                .getData()
                .getAttributes();


        SessionFactory.getSession(SessionType.SHOPPER).setToken(sessionAttributes.getAccessToken());
        SessionFactory.getSession(SessionType.SHOPPER).setToken(sessionAttributes.getRefreshToken());

        log.info("Обновляем авторизацию");
        log.info("access_token: {}", SessionFactory.getSession(SessionType.SHOPPER).getToken());
        log.info("refresh_token: {}", SessionFactory.getSession(SessionType.SHOPPER).getRefreshToken());
    }

    /**
     * Получаем shipment id по shipment number
     */
    private String getShipmentIdIteration(String shipmentNumber) {
        WaitingHelper.simply(10);
        log.info("Получаем список доступных для сборки заказов");
        String shipmentId = null;
        Response response = ShopperRequest.Shipments.GET();
        assertStatusCode200(response);
        List<ShipmentData> shipments = response.as(ShipmentsResponse.class).getData();

        StringJoiner availableShipmentNumbers = new StringJoiner(
                "\n• ",
                "Список доступных для сборки заказов:\n• ",
                "\n");
        for (ShipmentData shipment : shipments) {
            String number = shipment.getAttributes().getNumber();
            if (shipment.getAttributes().getNumber().equalsIgnoreCase(shipmentNumber)) {
                shipmentId = shipment.getId();
                availableShipmentNumbers.add(number + " <<< Выбран");
            } else availableShipmentNumbers.add(number);
        }
        log.info(availableShipmentNumbers.toString());
        return shipmentId;
    }

    public String getShipmentId(String shipmentNumber) {
        String shipmentId;
        String error = "Оформленного заказа нет в списке " + shipmentNumber;
        for (int i = 0; i < 6; i++) {
            shipmentId = getShipmentIdIteration(shipmentNumber);
            if (shipmentId != null) {
                return shipmentId;
            } else log.error(error);
        }
        Assert.fail(error);
        return null;
    }

    /**
     * Получаем assembly id взятого в сборку заказа
     */
    public String getCurrentAssemblyId() {
        Response response = ShopperRequest.Assemblies.GET();
        assertStatusCode200(response);
        List<AssemblyData> assemblies = response.as(AssembliesResponse.class).getData();
        if (assemblies.size() > 0) {
            log.info("Получаем id собираемой сборки: {}", assemblies.get(0).getId());
            return assemblies.get(0).getId();
        } else {
            log.info("Нет собираемой сборки");
            return null;
        }
    }

    /**
     * Удаляем текущую сборку
     */
    public void deleteCurrentAssembly() {
        String currentAssemblyId = getCurrentAssemblyId();
        if (currentAssemblyId != null) {
            log.info("Удаляем сборку: {}", currentAssemblyId);
            Response response = AssembliesRequest.DELETE(currentAssemblyId);
            assertStatusCode200(response);
        }
    }

    /**
     * Создаем смену сборщику
     */
    public void createShopperShift() {
        Response response = ShopperRequest.OperationShifts.POST(
                1,
                true,
                String.valueOf(LocalDateTime.now()),
                String.valueOf(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),
                55.751244,
                37.618423);
        response.prettyPeek();
    }

    /**
     * Простая сборка заказа
     */
    public void simpleCollect(String shipmentNumber) {
        authorisation(UserManager.getDefaultShopper());
        deleteCurrentAssembly();
        String shipmentId = getShipmentId(shipmentNumber);

        startAssembly(shipmentId);
        assemblyItemsWithOriginalQty();
        startPaymentVerification();
        shopperCreatesPackageSets();
        finishAssembling();

        packer();
        startPurchasing();
        createReceipts();
        startPackaging();
        getPackageSets();
        packerCreatesPackageSets();
        finishPurchasing();

        shipAssembly();
    }

    /**
     * Сложная сборка заказа
     */
    public void complexCollect(String shipmentNumber) {
        authorisation(UserManager.getDefaultShopper());
        deleteCurrentAssembly();
        String shipmentId = getShipmentId(shipmentNumber);

        AssemblyData assembly = startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyState.COLLECTING.getState());

        suspendAssembly();
        assembly = startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyState.COLLECTING.getState());

        assemblyItems();

        pauseAssembly();
        assembly = startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyState.ON_APPROVAL.getState());

        startPaymentVerification();
        shopperCreatesPackageSets();
        finishAssembling();

        packer();
        startPurchasing();
        createReceipts();
        startPackaging();
        getPackageSets();
        packerCreatesPackageSets();
        finishPurchasing();

        payAssemblyByLifePay();
        shipAssembly();
    }

    /**
     * Берем заказ в сборку
     */
    private AssemblyData startAssembly(String shipmentId) {
        log.info("Берем заказ в сборку");
        Response response = AssembliesRequest.POST(shipmentId);
        assertStatusCode200(response);
        AssemblyData assembly = response.as(AssemblyResponse.class).getData();
        currentAssemblyId = assembly.getId();
        return assembly;
    }

    /**
     * Собираем все позиции в текущей сборке с изначальным количеством
     */
    private void assemblyItemsWithOriginalQty() {
        getItems().forEach(item -> assemblyItem(item.getId(), item.getAttributes().getQty()));
    }

    /**
     * Собираем все позиции в текущей сборке по разному
     * 0. Сборка с изначальным количеством
     * 1. Сборка с измененным количеством
     * 2. Отмена товара
     * 3. Замена товара
     * 4. Допзамена для замены
     * 5. Новый товар
     */
    private void assemblyItems() {
        List<AssemblyItemData> items = getItems();
        List<OfferData> offers = getOffers();

        assemblyItem(items.get(0).getId(), items.get(0).getAttributes().getQty());
        clarifyItem(items.get(0).getId());
        approveItem(items.get(0).getId());

        if (items.size() > 1) {
            assemblyItem(items.get(1).getId(), items.get(1).getAttributes().getQty() * 50);
            approveItem(items.get(1).getId());

            if (items.size() > 2) {
                cancelItem(items.get(2).getId());
                approveItem(items.get(2).getId());

                if (items.size() > 3) {
                    ReplacementData replacement = replaceItem(items.get(3).getId(), offers.get(0).getAttributes().getUuid());
                    approveItem(replacement.getAttributes().getToItemId().toString());

                    AssemblyItemData assemblyItem = additionalItemForReplacement(replacement.getId(), offers.get(1).getId());
                    approveItem(assemblyItem.getId());

                    if (items.size() > 4) {
                        for (int i = 4; i < items.size(); i++) {
                            assemblyItem(items.get(i).getId(), items.get(i).getAttributes().getQty());
                        }
                    }
                } else log.error("Всего три товара в заказе");
            } else log.error("Всего два товара в заказе");
        } else log.error("Всего один товар в заказе");

        AssemblyItemData assemblyItem = addItem(offers.get(2).getAttributes().getUuid());
        approveItem(assemblyItem.getId());
    }

    private List<OfferData> getOffers() {
        log.info("Поиск товаров в магазине");
        Response response = StoresRequest.Offers.GET(
                EnvironmentData.INSTANCE.getDefaultShopperSid(), "хлеб");
        assertStatusCode200(response);
        return response.as(OffersResponse.class).getData();
    }

    /**
     * Получаем инфу о позициях в текущей сборке
     */
    private List<AssemblyItemData> getItems() {
        log.info("Получаем инфу о позициях в сборке");
        Response response = AssembliesRequest.GET(currentAssemblyId);
        assertStatusCode200(response);
        return response.as(AssemblyResponse.class).getIncluded();
    }

    /**
     * Собираем позицию
     * @param assemblyItemId - айди позиции
     * @param itemQty - количество
     * @return возвращается объект со всей инфой о товаре
     */
    private AssemblyItemData assemblyItem(String assemblyItemId, int itemQty) {
        log.info("Собираем товар");
        Response response = AssemblyItemsRequest.PATCH(currentAssemblyId, assemblyItemId, itemQty);
        assertStatusCode200(response);
        return response.as(AssemblyItemResponse.class).getData();
    }

    private AssemblyItemData addItem(String offerUuid) {
        log.info("Добавляем новый товар");
        Response response = AssembliesRequest.Items.POST(currentAssemblyId, offerUuid,2);
        assertStatusCode200(response);
        return response.as(AssemblyItemResponse.class).getData();
    }

    private void cancelItem(String assemblyItemId) {
        int reasonId = getCancelReasons().get(0).getId();
        log.info("Отменяем товар");
        Response response = AssemblyItemsRequest.Cancellations.POST(assemblyItemId, reasonId);
        assertStatusCode200(response);
    }

    private void clarifyItem(String assemblyItemId) {
        int reasonId = getClarifyReasons().get(0).getId();
        log.info("Уточняем товар");
        Response response = AssemblyItemsRequest.Clarifications.POST(assemblyItemId, reasonId);
        assertStatusCode200(response);
    }

    private ReplacementData replaceItem(String assemblyItemId, String offerUuid) {
        int reasonId = getCancelReasons().get(0).getId();
        log.info("Заменяем товар");
        Response response = AssemblyItemsRequest.Replacements.POST(
                assemblyItemId, offerUuid, reasonId,2);
        assertStatusCode200(response);
        return response.as(ReplacementResponse.class).getData();
    }

    private List<Reason> getCancelReasons() {
        log.info("Получаем список причин для отмен/замен");
        Response response = CancelReasonsRequest.GET();
        assertStatusCode200(response);
        return Arrays.asList(response.as(Reason[].class));
    }

    private List<Reason> getClarifyReasons() {
        log.info("Получаем список причин для уточнения");
        Response response = ClarifyReasonsRequest.GET();
        assertStatusCode200(response);
        return Arrays.asList(response.as(Reason[].class));
    }

    private List<Reason> getReturnReasons() {
        log.info("Получаем список причин для возврата");
        Response response = ReturnReasonsRequest.GET();
        assertStatusCode200(response);
        return Arrays.asList(response.as(Reason[].class));
    }

    private AssemblyItemData additionalItemForReplacement(String replacementId, String offerUuid) {
        log.info("Дополнительный товар для замены");
        Response response = ReplacementsRequest.AdditionalItems.POST(replacementId, offerUuid,2);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyItemResponse.class).getData().getAttributes().getState(),
                "need_review");
        return response.as(AssemblyItemResponse.class).getData();
    }

    private void approveItem(String assemblyItemId) {
        log.info("Подтверждаем позицию");
        Response response = AssemblyItemsRequest.Approve.PATCH(assemblyItemId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyItemResponse.class).getData().getAttributes().getState(),
                "approved");
    }

    /**
     * Завершаем сборку
     */
    private void approveAssembly() {
        log.info("Завершаем сборку");
        Response response = AssembliesRequest.Approve.PATCH(currentAssemblyId);
        assertStatusCode200(response);
    }

    private void startPaymentVerification() {
        log.info("Подтверждаем прохождение оплаты у сборки для передачи упаковщику");
        Response response = AssembliesRequest.StartPaymentVerification.PUT(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.PAYMENT_VERIFICATION.getState());
    }

    private void shopperCreatesPackageSets() {
        log.info("Раскладываем заказ для передачи упаковщику");
        int boxNumber = 10;
        Response response = AssembliesRequest.PackageSets.POST(currentAssemblyId, boxNumber);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(PackageSetsResponse.class).getData().size(), boxNumber);
    }

    private void finishAssembling() {
        log.info("Завершаем сборку для передачи упаковщику");
        Response response = AssembliesRequest.FinishAssembling.PUT(currentAssemblyId);
        assertStatusCode200(response);
        AssemblyAttributes attributes = response.as(AssemblyResponse.class).getData().getAttributes();
        Assert.assertEquals(attributes.getState(),
                AssemblyState.ASSEMBLED.getState());
        Assert.assertNull(attributes.getPackerId());
    }

    private void packer() {
        log.info("Берем сборку упаковщиком");
        Response response = AssembliesRequest.Packer.PUT(currentAssemblyId);
        assertStatusCode200(response);
        AssemblyAttributes attributes = response.as(AssemblyResponse.class).getData().getAttributes();
        Assert.assertEquals(attributes.getState(),
                AssemblyState.ASSEMBLED.getState());
        Assert.assertNotNull(attributes.getPackerId());
    }

    private void startPurchasing() {
        log.info("Начинаем оплату на кассе");
        Response response = AssembliesRequest.StartPurchasing.PUT(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.ON_CASH_DESK.getState());
    }

    private void createReceipts() {
        log.info("Отправляем инфу о чеке");
        String
                total = "1.0",
                fiscalSecret = "1",
                fiscalDocumentNumber = "1",
                fiscalChecksum = "1",
                transactionDetails = "1";
        Response response = AssembliesRequest.Receipts.POST(
                currentAssemblyId,
                total,
                fiscalSecret,
                fiscalDocumentNumber,
                fiscalChecksum,
                LocalDateTime.now().toString(),
                transactionDetails);
        assertStatusCode200(response);
        ReceiptsAttributes receipts = response.as(ReceiptsResponse.class).getData().getAttributes();
        Assert.assertEquals(receipts.getTotal(), total);
        Assert.assertEquals(receipts.getFiscalSecret(), fiscalSecret);
        Assert.assertEquals(receipts.getFiscalDocumentNumber(), fiscalDocumentNumber);
        Assert.assertEquals(receipts.getFiscalChecksum(), fiscalChecksum);
        Assert.assertEquals(receipts.getTransactionDetails(), transactionDetails);
    }

    private void startPackaging() {
        log.info("Начинаем упаковку");
        Response response = AssembliesRequest.StartPackaging.PATCH(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.PACKAGING.getState());
    }

    private void getPackageSets() {
        log.info("Смотрим где лежит заказ для упаковки");
        Response response = AssembliesRequest.PackageSets.GET(currentAssemblyId);
        assertStatusCode200(response);
    }

    private void packerCreatesPackageSets() {
        log.info("Раскладываем упакованный заказ");
        Response response = AssembliesRequest.PackageSets.POST(
                currentAssemblyId, 1,1,1,1);
        assertStatusCode200(response);
        List<PackageSetData> packageSets = response.as(PackageSetsResponse.class).getData();
        Assert.assertEquals(packageSets.get(0).getAttributes().getLocation(), PackageSetLocation.BASKET.getLocation());
        Assert.assertEquals(packageSets.get(1).getAttributes().getLocation(), PackageSetLocation.RACK.getLocation());
        Assert.assertEquals(packageSets.get(2).getAttributes().getLocation(), PackageSetLocation.FRIDGE.getLocation());
        Assert.assertEquals(packageSets.get(3).getAttributes().getLocation(), PackageSetLocation.FREEZER.getLocation());
    }

    private void finishPurchasing() {
        log.info("Завершаем упаковку");
        Response response = AssembliesRequest.Purchase.PATCH(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.READY_TO_SHIP.getState());
    }

    private void pauseAssembly() {
        log.info("Ставим сборку на паузу");
        Response response = AssembliesRequest.Pause.PATCH(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.PAUSED.getState());
    }

    private void suspendAssembly() {
        log.info("Отдаём сборку другому сборщику");
        Response response = AssembliesRequest.Suspend.PATCH(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.SUSPENDED.getState());
    }

    private void payAssemblyByLifePay() {
        log.info("Оплачиваем сборку через LifePay");
        Response response = AssembliesRequest.LifePay.PUT(currentAssemblyId);
        assertStatusCode200(response);
    }

    private void shipAssembly() {
        log.info("Отмечаем сборку как доставленную");
        Response response = AssembliesRequest.Ship.PATCH(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.SHIPPED.getState());
    }
}
