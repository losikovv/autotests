package ru.instamart.api.helper;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.shopper.AssemblyStateSHP;
import ru.instamart.api.enums.shopper.PackageSetLocationSHP;
import ru.instamart.api.model.shopper.app.*;
import ru.instamart.api.request.shopper.app.*;
import ru.instamart.api.response.shopper.app.*;
import ru.instamart.core.testdata.UserData;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.core.testdata.pagesdata.EnvironmentData;
import ru.instamart.core.util.ThreadUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static ru.instamart.api.checkpoint.ShopperApiCheckpoints.checkStatusCode200;

@Slf4j
public class ShopperAppApiHelper {
    private String currentAssemblyId;

    public void authorisation(UserData user) {
        SessionFactory.createSessionToken(SessionType.SHOPPER_APP, user);
    }

    public void refreshAuth() {
        Response response = AuthSHPRequest.Refresh.POST();
        checkStatusCode200(response);
        SessionAttributesSHP sessionAttributes = response
                .as(SessionsSHPResponse.class)
                .getData()
                .getAttributes();


        SessionFactory.getSession(SessionType.SHOPPER_APP).setToken(sessionAttributes.getAccessToken());
        SessionFactory.getSession(SessionType.SHOPPER_APP).setToken(sessionAttributes.getRefreshToken());

        log.info("Обновляем авторизацию");
        log.info("access_token: {}", SessionFactory.getSession(SessionType.SHOPPER_APP).getToken());
        log.info("refresh_token: {}", SessionFactory.getSession(SessionType.SHOPPER_APP).getRefreshToken());
    }

    /**
     * Получаем shipment id по shipment number
     */
    private String getShipmentIdIteration(String shipmentNumber) {
        ThreadUtil.simply(10);
        log.info("Получаем список доступных для сборки заказов");
        String shipmentId = null;
        Response response = ShopperSHPRequest.Shipments.GET();
        checkStatusCode200(response);
        List<ShipmentDataSHP> shipments = response.as(ShipmentsSHPResponse.class).getData();

        StringJoiner availableShipmentNumbers = new StringJoiner(
                "\n• ",
                "Список доступных для сборки заказов:\n• ",
                "\n");
        for (ShipmentDataSHP shipment : shipments) {
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
        return getShipmentId(shipmentNumber, "");
    }

    public String getShipmentId(String shipmentNumber, String additionalInfoForError) {
        String shipmentId;
        String error = "Оформленного заказа нет в списке " + shipmentNumber;
        for (int i = 0; i < 6; i++) {
            shipmentId = getShipmentIdIteration(shipmentNumber);
            if (shipmentId != null) {
                return shipmentId;
            } else log.error(error);
        }
        Assert.fail(error + "\n" + additionalInfoForError);
        return null;
    }

    /**
     * Получаем assembly id взятого в сборку заказа
     */
    public String getCurrentAssemblyId() {
        Response response = ShopperSHPRequest.Assemblies.GET();
        checkStatusCode200(response);
        List<AssemblyDataSHP> assemblies = response.as(AssembliesSHPResponse.class).getData();
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
            Response response = AssembliesSHPRequest.DELETE(currentAssemblyId);
            checkStatusCode200(response);
        }
    }

    /**
     * Создаем смену сборщику
     */
    public void createShopperShift() {
        Response response = ShopperSHPRequest.OperationShifts.POST(
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

        AssemblyDataSHP assembly = startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyStateSHP.COLLECTING.getState());

        suspendAssembly();
        assembly = startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyStateSHP.COLLECTING.getState());

        assemblyItems();

        pauseAssembly();
        assembly = startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyStateSHP.ON_APPROVAL.getState());

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
    private AssemblyDataSHP startAssembly(String shipmentId) {
        log.info("Берем заказ в сборку");
        Response response = AssembliesSHPRequest.POST(shipmentId);
        checkStatusCode200(response);
        AssemblyDataSHP assembly = response.as(AssemblySHPResponse.class).getData();
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
        List<AssemblyItemDataSHP> items = getItems();
        List<OfferDataSHP> offers = getOffers();

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
                    ReplacementDataSHP replacement = replaceItem(items.get(3).getId(), offers.get(0).getAttributes().getUuid());
                    approveItem(replacement.getAttributes().getToItemId().toString());

                    AssemblyItemDataSHP assemblyItem = additionalItemForReplacement(replacement.getId(), offers.get(1).getId());
                    approveItem(assemblyItem.getId());

                    if (items.size() > 4) {
                        for (int i = 4; i < items.size(); i++) {
                            assemblyItem(items.get(i).getId(), items.get(i).getAttributes().getQty());
                        }
                    }
                } else log.error("Всего три товара в заказе");
            } else log.error("Всего два товара в заказе");
        } else log.error("Всего один товар в заказе");

        AssemblyItemDataSHP assemblyItem = addItem(offers.get(2).getAttributes().getUuid());
        approveItem(assemblyItem.getId());
    }

    private List<OfferDataSHP> getOffers() {
        log.info("Поиск товаров в магазине");
        Response response = StoresSHPRequest.Offers.GET(
                EnvironmentData.INSTANCE.getDefaultShopperSid(), "хлеб");
        checkStatusCode200(response);
        return response.as(OffersSHPResponse.class).getData();
    }

    /**
     * Получаем инфу о позициях в текущей сборке
     */
    private List<AssemblyItemDataSHP> getItems() {
        log.info("Получаем инфу о позициях в сборке");
        Response response = AssembliesSHPRequest.GET(currentAssemblyId);
        checkStatusCode200(response);
        return response.as(AssemblySHPResponse.class).getIncluded();
    }

    /**
     * Собираем позицию
     * @param assemblyItemId - айди позиции
     * @param itemQty - количество
     * @return возвращается объект со всей инфой о товаре
     */
    private AssemblyItemDataSHP assemblyItem(String assemblyItemId, int itemQty) {
        log.info("Собираем товар");
        Response response = AssemblyItemsSHPRequest.PATCH(currentAssemblyId, assemblyItemId, itemQty);
        checkStatusCode200(response);
        return response.as(AssemblyItemSHPResponse.class).getData();
    }

    private AssemblyItemDataSHP addItem(String offerUuid) {
        log.info("Добавляем новый товар");
        Response response = AssembliesSHPRequest.Items.POST(currentAssemblyId, offerUuid,2);
        checkStatusCode200(response);
        return response.as(AssemblyItemSHPResponse.class).getData();
    }

    private void cancelItem(String assemblyItemId) {
        int reasonId = getCancelReasons().get(0).getId();
        log.info("Отменяем товар");
        Response response = AssemblyItemsSHPRequest.Cancellations.POST(assemblyItemId, reasonId);
        checkStatusCode200(response);
    }

    private void clarifyItem(String assemblyItemId) {
        int reasonId = getClarifyReasons().get(0).getId();
        log.info("Уточняем товар");
        Response response = AssemblyItemsSHPRequest.Clarifications.POST(assemblyItemId, reasonId);
        checkStatusCode200(response);
    }

    private ReplacementDataSHP replaceItem(String assemblyItemId, String offerUuid) {
        int reasonId = getCancelReasons().get(0).getId();
        log.info("Заменяем товар");
        Response response = AssemblyItemsSHPRequest.Replacements.POST(
                assemblyItemId, offerUuid, reasonId,2);
        checkStatusCode200(response);
        return response.as(ReplacementSHPResponse.class).getData();
    }

    private List<ReasonSHP> getCancelReasons() {
        log.info("Получаем список причин для отмен/замен");
        Response response = CancelReasonsSHPRequest.GET();
        checkStatusCode200(response);
        return Arrays.asList(response.as(ReasonSHP[].class));
    }

    private List<ReasonSHP> getClarifyReasons() {
        log.info("Получаем список причин для уточнения");
        Response response = ClarifyReasonsSHPRequest.GET();
        checkStatusCode200(response);
        return Arrays.asList(response.as(ReasonSHP[].class));
    }

    private List<ReasonSHP> getReturnReasons() {
        log.info("Получаем список причин для возврата");
        Response response = ReturnReasonsSHPRequest.GET();
        checkStatusCode200(response);
        return Arrays.asList(response.as(ReasonSHP[].class));
    }

    private AssemblyItemDataSHP additionalItemForReplacement(String replacementId, String offerUuid) {
        log.info("Дополнительный товар для замены");
        Response response = ReplacementsSHPRequest.AdditionalItems.POST(replacementId, offerUuid,2);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyItemSHPResponse.class).getData().getAttributes().getState(),
                "need_review");
        return response.as(AssemblyItemSHPResponse.class).getData();
    }

    private void approveItem(String assemblyItemId) {
        log.info("Подтверждаем позицию");
        Response response = AssemblyItemsSHPRequest.Approve.PATCH(assemblyItemId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyItemSHPResponse.class).getData().getAttributes().getState(),
                "approved");
    }

    /**
     * Завершаем сборку
     */
    private void approveAssembly() {
        log.info("Завершаем сборку");
        Response response = AssembliesSHPRequest.Approve.PATCH(currentAssemblyId);
        checkStatusCode200(response);
    }

    private void startPaymentVerification() {
        log.info("Подтверждаем прохождение оплаты у сборки для передачи упаковщику");
        Response response = AssembliesSHPRequest.StartPaymentVerification.PUT(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.PAYMENT_VERIFICATION.getState());
    }

    private void shopperCreatesPackageSets() {
        log.info("Раскладываем заказ для передачи упаковщику");
        int boxNumber = 10;
        Response response = AssembliesSHPRequest.PackageSets.POST(currentAssemblyId, boxNumber);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(PackageSetsSHPResponse.class).getData().size(), boxNumber);
    }

    private void finishAssembling() {
        log.info("Завершаем сборку для передачи упаковщику");
        Response response = AssembliesSHPRequest.FinishAssembling.PUT(currentAssemblyId);
        checkStatusCode200(response);
        AssemblyAttributesSHP attributes = response.as(AssemblySHPResponse.class).getData().getAttributes();
        Assert.assertEquals(attributes.getState(),
                AssemblyStateSHP.ASSEMBLED.getState());
        Assert.assertNull(attributes.getPackerId());
    }

    private void packer() {
        log.info("Берем сборку упаковщиком");
        Response response = AssembliesSHPRequest.Packer.PUT(currentAssemblyId);
        checkStatusCode200(response);
        AssemblyAttributesSHP attributes = response.as(AssemblySHPResponse.class).getData().getAttributes();
        Assert.assertEquals(attributes.getState(),
                AssemblyStateSHP.ASSEMBLED.getState());
        Assert.assertNotNull(attributes.getPackerId());
    }

    private void startPurchasing() {
        log.info("Начинаем оплату на кассе");
        Response response = AssembliesSHPRequest.StartPurchasing.PUT(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.ON_CASH_DESK.getState());
    }

    private void createReceipts() {
        log.info("Отправляем инфу о чеке");
        String
                total = "1.0",
                fiscalSecret = "1",
                fiscalDocumentNumber = "1",
                fiscalChecksum = "1",
                transactionDetails = "1";
        Response response = AssembliesSHPRequest.Receipts.POST(
                currentAssemblyId,
                total,
                fiscalSecret,
                fiscalDocumentNumber,
                fiscalChecksum,
                LocalDateTime.now().toString(),
                transactionDetails);
        checkStatusCode200(response);
        ReceiptsAttributesSHP receipts = response.as(ReceiptsSHPResponse.class).getData().getAttributes();
        Assert.assertEquals(receipts.getTotal(), total);
        Assert.assertEquals(receipts.getFiscalSecret(), fiscalSecret);
        Assert.assertEquals(receipts.getFiscalDocumentNumber(), fiscalDocumentNumber);
        Assert.assertEquals(receipts.getFiscalChecksum(), fiscalChecksum);
        Assert.assertEquals(receipts.getTransactionDetails(), transactionDetails);
    }

    private void startPackaging() {
        log.info("Начинаем упаковку");
        Response response = AssembliesSHPRequest.StartPackaging.PATCH(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.PACKAGING.getState());
    }

    private void getPackageSets() {
        log.info("Смотрим где лежит заказ для упаковки");
        Response response = AssembliesSHPRequest.PackageSets.GET(currentAssemblyId);
        checkStatusCode200(response);
    }

    private void packerCreatesPackageSets() {
        log.info("Раскладываем упакованный заказ");
        Response response = AssembliesSHPRequest.PackageSets.POST(
                currentAssemblyId, 1,1,1,1);
        checkStatusCode200(response);
        List<PackageSetDataSHP> packageSets = response.as(PackageSetsSHPResponse.class).getData();
        Assert.assertEquals(packageSets.get(0).getAttributes().getLocation(), PackageSetLocationSHP.BASKET.getLocation());
        Assert.assertEquals(packageSets.get(1).getAttributes().getLocation(), PackageSetLocationSHP.RACK.getLocation());
        Assert.assertEquals(packageSets.get(2).getAttributes().getLocation(), PackageSetLocationSHP.FRIDGE.getLocation());
        Assert.assertEquals(packageSets.get(3).getAttributes().getLocation(), PackageSetLocationSHP.FREEZER.getLocation());
    }

    private void finishPurchasing() {
        log.info("Завершаем упаковку");
        Response response = AssembliesSHPRequest.Purchase.PATCH(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.READY_TO_SHIP.getState());
    }

    private void pauseAssembly() {
        log.info("Ставим сборку на паузу");
        Response response = AssembliesSHPRequest.Pause.PATCH(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.PAUSED.getState());
    }

    private void suspendAssembly() {
        log.info("Отдаём сборку другому сборщику");
        Response response = AssembliesSHPRequest.Suspend.PATCH(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.SUSPENDED.getState());
    }

    private void payAssemblyByLifePay() {
        log.info("Оплачиваем сборку через LifePay");
        Response response = AssembliesSHPRequest.LifePay.PUT(currentAssemblyId);
        checkStatusCode200(response);
    }

    private void shipAssembly() {
        log.info("Отмечаем сборку как доставленную");
        Response response = AssembliesSHPRequest.Ship.PATCH(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.SHIPPED.getState());
    }
}
