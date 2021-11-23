package ru.instamart.api.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.SkipException;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.shopper.AssemblyStateSHP;
import ru.instamart.api.enums.shopper.PackageSetLocationSHP;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.shopper.admin.ShipmentsItemSHP;
import ru.instamart.api.model.shopper.app.*;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.request.shopper.app.*;
import ru.instamart.api.response.shopper.app.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.ThreadUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.kraken.helper.DateTimeHelper.getDateFromMSK;
import static ru.instamart.kraken.util.ThreadUtil.simplyAwait;

@Slf4j
public class ShopperAppApiHelper {
    private String currentAssemblyId;

    @Step("Авторизация пользовалтелем: {user.email} в шопере")
    public void authorisation(UserData user) {
        SessionFactory.createSessionToken(SessionType.SHOPPER_APP, user);
    }

    public void refreshAuth() {
        Response response = AuthSHPRequest.Refresh.POST();
        checkStatusCode200(response);
        SessionSHP.Data.Attributes sessionAttributes = response
                .as(SessionsSHPResponse.class)
                .getData()
                .getAttributes();


        SessionFactory.getSession(SessionType.SHOPPER_APP).setToken(sessionAttributes.getAccessToken());
        SessionFactory.getSession(SessionType.SHOPPER_APP).setToken(sessionAttributes.getRefreshToken());

        log.debug("Обновляем авторизацию");
        log.debug("access_token: {}", SessionFactory.getSession(SessionType.SHOPPER_APP).getToken());
        log.debug("refresh_token: {}", SessionFactory.getSession(SessionType.SHOPPER_APP).getRefreshToken());
    }

    /**
     * Получаем shipment id по shipment number
     */
    private String getShipmentIdIteration(String shipmentNumber) {
        ThreadUtil.simplyAwait(10);
        log.debug("Получаем список доступных для сборки заказов");
        String shipmentId = null;
        Response response = ShopperSHPRequest.Shipments.GET();
        checkStatusCode200(response);
        List<ShipmentSHP.Data> shipments = response.as(ShipmentsSHPResponse.class).getData();

        StringJoiner availableShipmentNumbers = new StringJoiner(
                "\n• ",
                "Список доступных для сборки заказов:\n• ",
                "\n");
        for (ShipmentSHP.Data shipment : shipments) {
            String number = shipment.getAttributes().getNumber();
            if (shipment.getAttributes().getNumber().equalsIgnoreCase(shipmentNumber)) {
                shipmentId = shipment.getId();
                availableShipmentNumbers.add(number + " <<< Выбран");
            } else availableShipmentNumbers.add(number);
        }
        log.debug(availableShipmentNumbers.toString());
        return shipmentId;
    }

    @Step("Получаем ShipmentId заказа")
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
        List<AssemblySHP.Data> assemblies = response.as(AssembliesSHPResponse.class).getData();
        if (assemblies.size() > 0) {
            log.debug("Получаем id собираемой сборки: {}", assemblies.get(0).getId());
            return assemblies.get(0).getId();
        } else {
            log.debug("Нет собираемой сборки");
            return null;
        }
    }

    /**
     * Удаляем текущую сборку
     */
    @Step("Удаляем текущую сборку в шопере")
    public void deleteCurrentAssembly() {
        String currentAssemblyId = getCurrentAssemblyId();
        if (currentAssemblyId != null) {
            log.debug("Удаляем сборку: {}", currentAssemblyId);
            Allure.step("Удаляем сборку: " + currentAssemblyId);
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
    }

    /**
     * Простая сборка заказа с генерацией фискального номера чека
     */
    public void simpleCollect(String shipmentNumber){
        simpleCollect(shipmentNumber, Generate.digitalString(10));
    }

    /**
     * Простая сборка заказа
     */
    @Step("Простая сборка заказа {shipmentNumber} с номером чека {fiscalDocumentNumber}")
    public void simpleCollect(String shipmentNumber, String fiscalDocumentNumber) {
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
        createReceipts(fiscalDocumentNumber);
        startPackaging();
        getPackageSets();
        packerCreatesPackageSets();
        finishPurchasing();

        shipAssembly();
    }

    @Step("Простая сборка заказа {shipmentNumber} до регистрации чека")
    public String simpleAssemblyPriorToReceiptCreation(String shipmentNumber){
        authorisation(UserManager.getDefaultShopper());
        deleteCurrentAssembly();
        String shipmentId = getShipmentId(shipmentNumber);

        currentAssemblyId = startAssembly(shipmentId).getId();
        assemblyItemsWithOriginalQty();
        startPaymentVerification();
        shopperCreatesPackageSets();
        finishAssembling();

        packer();
        startPurchasing();
        return currentAssemblyId;
    }

    @Step("Проверяем обновление информации о заказе")
    public void shopperReceivedDelivery(String shipmentNumber, Integer retryCount) {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdmin());
        List<ShipmentsItemSHP> filterCollect;
        int i = 0;
        do {
            Response response = ShopperAdminRequest.Shipments.GET(EnvironmentProperties.DEFAULT_SHOPPER_SID, getDateFromMSK().toString());
            checkStatusCode200(response);
            final List<ShipmentsItemSHP> shipments = response.as(ru.instamart.api.response.shopper.admin.ShipmentsSHPResponse.class).getShipments();
            filterCollect = shipments.stream()
                    .filter(e -> e.getNumber().equals(shipmentNumber))
                    .collect(Collectors.toList());
            log.info("Попытка: " + (i++) + ". filterCollect size" + filterCollect.size());
            simplyAwait(10);
        } while (filterCollect.size() > 0 || i > retryCount);
        if (filterCollect.size() == 0) {
            System.out.println("count: "+filterCollect.size());
            throw new SkipException("Оформленный заказ не пришел на шопер");
        }
    }

    /**
     * Сложная сборка заказа
     */
    @Step("Сложная сборка заказа для shipmentNumber: {shipmentNumber}")
    public void complexCollect(String shipmentNumber) {
        authorisation(UserManager.getDefaultShopper());
        //Удаляем текущую сборку
        deleteCurrentAssembly();
        String shipmentId = getShipmentId(shipmentNumber);

        AssemblySHP.Data assembly = startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyStateSHP.COLLECTING.getState(), "Статус сборки не валиден");

        simplyAwait(3); //Пауза перед передачей в сборку. В БД не успевает поменяться статус
        //Отдаём сборку другому сборщику
        suspendAssembly();
        simplyAwait(3); //Пауза перед получением в сборку. В БД не успевает поменяться статус
        assembly = startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyStateSHP.COLLECTING.getState(), "Статус сборки не валиден");

        //Сборка всех позиций заказа
        assemblyItems();
        simplyAwait(3); //Пауза после сборки заказа

        //Ставим сборку на паузу
        pauseAssembly();
        simplyAwait(3); //пауза перед сборкой
        assembly = startAssembly(shipmentId);
        Assert.assertEquals(assembly.getAttributes().getState(), AssemblyStateSHP.ON_APPROVAL.getState(), "Статус сборки не валиден");

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
    @Step("Берем заказ в сбоку: shipmentId = {shipmentId}")
    public AssemblySHP.Data startAssembly(String shipmentId) {
        log.debug("Берем заказ в сборку");
        Response response = AssembliesSHPRequest.POST(shipmentId);
        checkStatusCode200(response);
        AssemblySHP.Data assembly = response.as(AssemblySHPResponse.class).getData();
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
    @Step("Сборка всех позиций заказа")
    private void assemblyItems() {
        List<AssemblyItemSHP.Data> items = getItems();
        List<OfferSHP.Data> offers = getOffers();

        Allure.step("Сборка с изначальным количеством: ID = " + items.get(0).getId() + " QTY=" + items.get(0).getAttributes().getQty());
        assemblyItem(items.get(0).getId(), items.get(0).getAttributes().getQty());
        clarifyItem(items.get(0).getId());
        approveItem(items.get(0).getId());

        if (items.size() > 1) {
            Allure.step("Сборка с измененным количеством: ID = " + items.get(1).getId() + " QTY=" + items.get(1).getAttributes().getQty());
            assemblyItem(items.get(1).getId(), items.get(1).getAttributes().getQty() * 50);
            approveItem(items.get(1).getId());

            if (items.size() > 2) {
                Allure.step("Отмена товара: ID = " + items.get(2).getId() + " QTY=" + items.get(2).getAttributes().getQty());
                cancelItem(items.get(2).getId());
                approveItem(items.get(2).getId());

                if (items.size() > 3) {
                    Allure.step("Замена товара: ID = " + items.get(3).getId() + " QTY=" + items.get(3).getAttributes().getQty());
                    ReplacementSHP.Data replacement = replaceItem(items.get(3).getId(), offers.get(0).getAttributes().getUuid());
                    approveItem(replacement.getAttributes().getToItemId().toString());

                    AssemblyItemSHP.Data assemblyItem = additionalItemForReplacement(replacement.getId(), offers.get(1).getId());
                    approveItem(assemblyItem.getId());

                    if (items.size() > 4) {
                        Allure.step("Допзамена для замены: ID = " + items.get(4).getId() + " QTY=" + items.get(4).getAttributes().getQty());
                        for (int i = 4; i < items.size(); i++) {
                            assemblyItem(items.get(i).getId(), items.get(i).getAttributes().getQty());
                        }
                    }
                } else log.error("Всего три товара в заказе");
            } else log.error("Всего два товара в заказе");
        } else log.error("Всего один товар в заказе");

        AssemblyItemSHP.Data assemblyItem = addItem(offers.get(2).getAttributes().getUuid());
        approveItem(assemblyItem.getId());
    }

    private List<OfferSHP.Data> getOffers() {
        log.debug("Поиск товаров в магазине");
        Response response = StoresSHPRequest.Offers.GET(
                EnvironmentProperties.DEFAULT_SHOPPER_SID, "хлеб");
        checkStatusCode200(response);
        return response.as(OffersSHPResponse.class).getData();
    }

    /**
     * Получаем инфу о позициях в текущей сборке
     */
    public List<AssemblyItemSHP.Data> getItems() {
        log.debug("Получаем инфу о позициях в сборке");
        Response response = AssembliesSHPRequest.GET(currentAssemblyId);
        checkStatusCode200(response);
        return response.as(AssemblySHPResponse.class).getIncluded();
    }

    /**
     * Собираем позицию
     *
     * @param assemblyItemId - айди позиции
     * @param itemQty        - количество
     * @return возвращается объект со всей инфой о товаре
     */
    @Step("Сборка позиции с id = {assemblyItemId} и количеством {itemQty}")
    private AssemblyItemSHP.Data assemblyItem(String assemblyItemId, int itemQty) {
        log.debug("Собираем товар");
        Response response = AssemblyItemsSHPRequest.PATCH(currentAssemblyId, assemblyItemId, itemQty);
        checkStatusCode200(response);
        return response.as(AssemblyItemSHPResponse.class).getData();
    }

    private AssemblyItemSHP.Data addItem(String offerUuid) {
        log.debug("Добавляем новый товар");
        Response response = AssembliesSHPRequest.Items.POST(currentAssemblyId, offerUuid, 2);
        checkStatusCode200(response);
        return response.as(AssemblyItemSHPResponse.class).getData();
    }

    public void cancelItem(String assemblyItemId) {
        int reasonId = getCancelReasons().get(0).getId();
        log.debug("Отменяем товар");
        Response response = AssemblyItemsSHPRequest.Cancellations.POST(assemblyItemId, reasonId);
        checkStatusCode200(response);
    }

    private void clarifyItem(String assemblyItemId) {
        int reasonId = getClarifyReasons().get(0).getId();
        log.debug("Уточняем товар");
        Response response = AssemblyItemsSHPRequest.Clarifications.POST(assemblyItemId, reasonId);
        checkStatusCode200(response);
    }

    private ReplacementSHP.Data replaceItem(String assemblyItemId, String offerUuid) {
        int reasonId = getCancelReasons().get(0).getId();
        log.debug("Заменяем товар");
        Response response = AssemblyItemsSHPRequest.Replacements.POST(
                assemblyItemId, offerUuid, reasonId, 2);
        checkStatusCode200(response);
        return response.as(ReplacementSHPResponse.class).getData();
    }

    private List<ReasonSHP> getCancelReasons() {
        log.debug("Получаем список причин для отмен/замен");
        Response response = CancelReasonsSHPRequest.GET();
        checkStatusCode200(response);
        return Arrays.asList(response.as(ReasonSHP[].class));
    }

    private List<ReasonSHP> getClarifyReasons() {
        log.debug("Получаем список причин для уточнения");
        Response response = ClarifyReasonsSHPRequest.GET();
        checkStatusCode200(response);
        return Arrays.asList(response.as(ReasonSHP[].class));
    }

    private List<ReasonSHP> getReturnReasons() {
        log.debug("Получаем список причин для возврата");
        Response response = ReturnReasonsSHPRequest.GET();
        checkStatusCode200(response);
        return Arrays.asList(response.as(ReasonSHP[].class));
    }

    private AssemblyItemSHP.Data additionalItemForReplacement(String replacementId, String offerUuid) {
        log.debug("Дополнительный товар для замены");
        Response response = ReplacementsSHPRequest.AdditionalItems.POST(replacementId, offerUuid, 2);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyItemSHPResponse.class).getData().getAttributes().getState(),
                "need_review");
        return response.as(AssemblyItemSHPResponse.class).getData();
    }

    private void approveItem(String assemblyItemId) {
        log.debug("Подтверждаем позицию");
        Response response = AssemblyItemsSHPRequest.Approve.PATCH(assemblyItemId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyItemSHPResponse.class).getData().getAttributes().getState(),
                "approved");
    }

    /**
     * Завершаем сборку
     */
    private void approveAssembly() {
        log.debug("Завершаем сборку");
        Response response = AssembliesSHPRequest.Approve.PATCH(currentAssemblyId);
        checkStatusCode200(response);
    }

    @Step("Подтверждаем прохождение оплаты у сборки для передачи упаковщику")
    private void startPaymentVerification() {
        log.debug("Подтверждаем прохождение оплаты у сборки для передачи упаковщику");
        Response response = AssembliesSHPRequest.StartPaymentVerification.PUT(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.PAYMENT_VERIFICATION.getState());
    }

    @Step("Раскладываем заказ для передачи упаковщику")
    private void shopperCreatesPackageSets() {
        log.debug("Раскладываем заказ для передачи упаковщику");
        int boxNumber = 10;
        Response response = AssembliesSHPRequest.PackageSets.POST(currentAssemblyId, boxNumber);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(PackageSetsSHPResponse.class).getData().size(), boxNumber);
    }

    @Step("Завершаем сборку для передачи упаковщику")
    private void finishAssembling() {
        log.debug("Завершаем сборку для передачи упаковщику");
        Response response = AssembliesSHPRequest.FinishAssembling.PUT(currentAssemblyId);
        checkStatusCode200(response);
        AssemblySHP.Data.Attributes attributes = response.as(AssemblySHPResponse.class).getData().getAttributes();
        Assert.assertEquals(attributes.getState(),
                AssemblyStateSHP.ASSEMBLED.getState());
        Assert.assertNull(attributes.getPackerId());
    }

    @Step("Берем сборку упаковщиком")
    private void packer() {
        log.debug("Берем сборку упаковщиком");
        Response response = AssembliesSHPRequest.Packer.PUT(currentAssemblyId);
        checkStatusCode200(response);
        AssemblySHP.Data.Attributes attributes = response.as(AssemblySHPResponse.class).getData().getAttributes();
        Assert.assertEquals(attributes.getState(),
                AssemblyStateSHP.ASSEMBLED.getState());
        checkFieldIsNotEmpty(attributes.getPackerId(), "packerId");
    }

    @Step("Оплата на кассе")
    private void startPurchasing() {
        log.debug("Начинаем оплату на кассе");
        Response response = AssembliesSHPRequest.StartPurchasing.PUT(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.ON_CASH_DESK.getState());
    }

    private void createReceipts(){
        createReceipts(Generate.digitalString(10));
    }


    @Step("Регистрация чека: {fiscalDocumentNumber}")
    private void createReceipts(String fiscalDocumentNumber) {
        log.debug("Отправляем инфу о чеке");
        String
                total = "1.0",
                fiscalSecret = "1",
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
        ReceiptsSHP.Data.Attributes receipts = response.as(ReceiptsSHPResponse.class).getData().getAttributes();
        Assert.assertEquals(receipts.getTotal(), total);
        Assert.assertEquals(receipts.getFiscalSecret(), fiscalSecret);
        Assert.assertEquals(receipts.getFiscalDocumentNumber(), fiscalDocumentNumber);
        Assert.assertEquals(receipts.getFiscalChecksum(), fiscalChecksum);
        Assert.assertEquals(receipts.getTransactionDetails(), transactionDetails);
    }

    @Step("Упаковка заказа")
    private void startPackaging() {
        log.debug("Начинаем упаковку");
        Response response = AssembliesSHPRequest.StartPackaging.PATCH(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.PACKAGING.getState());
    }

    @Step("Получаем данные о ячейке собранного заказа")
    private void getPackageSets() {
        log.debug("Смотрим где лежит заказ для упаковки");
        Response response = AssembliesSHPRequest.PackageSets.GET(currentAssemblyId);
        checkStatusCode200(response);
    }

    @Step("Раскладываем упакованный заказ")
    private void packerCreatesPackageSets() {
        log.debug("Раскладываем упакованный заказ");
        Response response = AssembliesSHPRequest.PackageSets.POST(
                currentAssemblyId, 1, 1, 1, 1);
        checkStatusCode200(response);
        List<PackageSetSHP.Data> packageSets = response.as(PackageSetsSHPResponse.class).getData();
        Assert.assertEquals(packageSets.get(0).getAttributes().getLocation(), PackageSetLocationSHP.BASKET.getLocation());
        Assert.assertEquals(packageSets.get(1).getAttributes().getLocation(), PackageSetLocationSHP.RACK.getLocation());
        Assert.assertEquals(packageSets.get(2).getAttributes().getLocation(), PackageSetLocationSHP.FRIDGE.getLocation());
        Assert.assertEquals(packageSets.get(3).getAttributes().getLocation(), PackageSetLocationSHP.FREEZER.getLocation());
    }

    @Step("Завершаем упаковку")
    private void finishPurchasing() {
        log.debug("Завершаем упаковку");
        Response response = AssembliesSHPRequest.Purchase.PATCH(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.READY_TO_SHIP.getState());
    }

    @Step("Ставим сборку на паузу")
    private void pauseAssembly() {
        log.debug("Ставим сборку на паузу");
        Response response = AssembliesSHPRequest.Pause.PATCH(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.PAUSED.getState());
    }

    @Step("Отдаём сборку другому сборщику: ")
    private void suspendAssembly() {
        log.debug("Отдаём сборку другому сборщику");
        Response response = AssembliesSHPRequest.Suspend.PATCH(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.SUSPENDED.getState());
    }

    @Step("Оплачиваем сборку через LifePay")
    private void payAssemblyByLifePay() {
        log.debug("Оплачиваем сборку через LifePay");
        Response response = AssembliesSHPRequest.LifePay.PUT(currentAssemblyId);
        checkStatusCode200(response);
    }

    @Step("Отмечаем сборку как доставленную")
    private void shipAssembly() {
        log.debug("Отмечаем сборку как доставленную");
        Response response = AssembliesSHPRequest.Ship.PATCH(currentAssemblyId);
        checkStatusCode200(response);
        Assert.assertEquals(response.as(AssemblySHPResponse.class).getData().getAttributes().getState(),
                AssemblyStateSHP.SHIPPED.getState());
    }
}
