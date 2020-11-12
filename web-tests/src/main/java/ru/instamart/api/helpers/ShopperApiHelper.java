package instamart.api.helpers;

import instamart.api.enums.shopper.AssemblyState;
import instamart.api.enums.shopper.PackageSetLocation;
import instamart.api.objects.shopper.*;
import instamart.api.requests.ShopperApiRequests;
import instamart.api.responses.shopper.*;
import instamart.core.common.AppManager;
import instamart.core.helpers.WaitingHelper;
import instamart.core.testdata.Users;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static instamart.api.checkpoints.ShopperApiCheckpoints.assertStatusCode200;

public class ShopperApiHelper extends HelperBase {
    String currentAssemblyId;

    public ShopperApiHelper() {
    }

    /**
     * Авторизация
     */
    public void authorisation(String userName, String password) {
        Response response = ShopperApiRequests.postSessions(userName, password);
        assertStatusCode200(response);
        ShopperApiRequests.token = response
                .as(SessionsResponse.class)
                .getData()
                .getAttributes()
                .getAccessToken();
        System.out.println("Авторизуемся: " + userName + " / " + password);
        System.out.println("access_token: " + ShopperApiRequests.token + "\n");
    }

    public void authorisation(UserData user) {
        authorisation(user.getLogin(), user.getPassword());
    }

    public boolean authorized() {
        return ShopperApiRequests.token != null;
    }

    /**
     * Логаут (чистим токен для авторизации)
     */
    public void logout() {
        if (authorized()) ShopperApiRequests.token = null;
    }

    /**
     * Получаем shipment id по shipment number
     */
    private String getShipmentIdIteration(String shipmentNumber) {
        WaitingHelper.simply(10);
        System.out.println("\nПолучаем список доступных для сборки заказов");
        String shipmentId = null;
        Response response = ShopperApiRequests.getShopperShipments();
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
                availableShipmentNumbers.add(greenText(number + " <<< Выбран"));
            } else availableShipmentNumbers.add(number);
        }
        System.out.println(availableShipmentNumbers);
        return shipmentId;
    }

    public String getShipmentId(String shipmentNumber) {
        String shipmentId;
        String error = "Оформленного заказа нет в списке " + shipmentNumber;
        for (int i = 0; i < 6; i++) {
            shipmentId = getShipmentIdIteration(shipmentNumber);
            if (shipmentId != null) {
                return shipmentId;
            } else System.err.println(error);
        }
        Assert.fail(error);
        return null;
    }

    /**
     * Получаем assembly id взятого в сборку заказа
     */
    public String getCurrentAssemblyId() {
        Response response = ShopperApiRequests.getShopperAssemblies();
        assertStatusCode200(response);
        List<AssemblyData> assemblies = response.as(AssembliesResponse.class).getData();
        if (assemblies.size() > 0) {
            System.out.println("Получаем id собираемой сборки: " + assemblies.get(0).getId());
            return assemblies.get(0).getId();
        } else {
            System.out.println("Нет собираемой сборки\n");
            return null;
        }
    }

    /**
     * Удаляем текущую сборку
     */
    public void deleteCurrentAssembly() {
        String currentAssemblyId = getCurrentAssemblyId();
        if (currentAssemblyId != null) {
            System.out.println("Удаляем сборку: " + currentAssemblyId);
            Response response = ShopperApiRequests.deleteAssembly(currentAssemblyId);
            assertStatusCode200(response);
        }
    }

    /**
     * Создаем смену сборщику
     */
    public void createShopperShift() {
        Response response = ShopperApiRequests.postShopperOperationShifts(
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
        authorisation(Users.shopper());
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
    }

    /**
     * Сложная сборка заказа
     */
    public void complexCollect(String shipmentNumber) {
        authorisation(Users.shopper());
        deleteCurrentAssembly();
        String shipmentId = getShipmentId(shipmentNumber);

        startAssembly(shipmentId);
        assemblyItems();
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
    }

    /**
     * Берем заказ в сборку
     */
    private AssemblyData startAssembly(String shipmentId) {
        System.out.println("Берем заказ в сборку");
        Response response = ShopperApiRequests.postAssembly(shipmentId);
        assertStatusCode200(response);
        AssemblyData assembly = response.as(AssemblyResponse.class).getData();
        Assert.assertEquals(assembly.getAttributes().getState(),
                AssemblyState.COLLECTING.getState());
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
                } else System.err.println("\nВсего три товара в заказе\n");
            } else System.err.println("\nВсего два товара в заказе\n");
        } else System.err.println("\nВсего один товар в заказе\n");

        AssemblyItemData assemblyItem = addItem(offers.get(2).getAttributes().getUuid());
        approveItem(assemblyItem.getId());
    }

    private List<OfferData> getOffers() {
        System.out.println("\nПоиск товаров в магазине");
        Response response = ShopperApiRequests.getStoreOffers(
                AppManager.environment.getDefaultShopperSid(), "хлеб");
        assertStatusCode200(response);
        return response.as(OffersResponse.class).getData();
    }

    /**
     * Получаем инфу о позициях в текущей сборке
     */
    private List<AssemblyItemData> getItems() {
        System.out.println("\nПолучаем инфу о позициях в сборке");
        Response response = ShopperApiRequests.getAssembly(currentAssemblyId);
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
        System.out.println("\nСобираем товар");
        Response response = ShopperApiRequests.patchAssemblyItem(currentAssemblyId, assemblyItemId, itemQty);
        assertStatusCode200(response);
        return response.as(AssemblyItemResponse.class).getData();
    }

    private AssemblyItemData addItem(String offerUuid) {
        System.out.println("\nДобавляем новый товар");
        Response response = ShopperApiRequests.postAssemblyItem(currentAssemblyId, offerUuid);
        assertStatusCode200(response);
        return response.as(AssemblyItemResponse.class).getData();
    }

    private void cancelItem(String assemblyItemId) {
        int reasonId = getCancelReasons().get(0).getId();
        System.out.println("\nОтменяем товар");
        Response response = ShopperApiRequests.postAssemblyItemCancellation(assemblyItemId, reasonId);
        assertStatusCode200(response);
    }

    private void clarifyItem(String assemblyItemId) {
        int reasonId = getClarifyReasons().get(0).getId();
        System.out.println("\nУточняем товар");
        Response response = ShopperApiRequests.postAssemblyItemClarification(assemblyItemId, reasonId);
        assertStatusCode200(response);
    }

    private ReplacementData replaceItem(String assemblyItemId, String offerUuid) {
        int reasonId = getCancelReasons().get(0).getId();
        System.out.println("\nЗаменяем товар");
        Response response = ShopperApiRequests.postAssemblyItemReplacement(assemblyItemId, offerUuid, reasonId);
        assertStatusCode200(response);
        return response.as(ReplacementResponse.class).getData();
    }

    private List<Reason> getCancelReasons() {
        System.out.println("\nПолучаем список причин для отмен/замен");
        Response response = ShopperApiRequests.getCancelReasons();
        assertStatusCode200(response);
        return Arrays.asList(response.as(Reason[].class));
    }

    private List<Reason> getClarifyReasons() {
        System.out.println("\nПолучаем список причин для уточнения");
        Response response = ShopperApiRequests.getClarifyReasons();
        assertStatusCode200(response);
        return Arrays.asList(response.as(Reason[].class));
    }

    private List<Reason> getReturnReasons() {
        System.out.println("\nПолучаем список причин для возврата");
        Response response = ShopperApiRequests.getReturnReasons();
        assertStatusCode200(response);
        return Arrays.asList(response.as(Reason[].class));
    }

    private AssemblyItemData additionalItemForReplacement(String replacementId, String offerUuid) {
        System.out.println("\nДополнительный товар для замены");
        Response response = ShopperApiRequests.postReplacementAdditionalItem(replacementId, offerUuid);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyItemResponse.class).getData().getAttributes().getState(),
                "need_review");
        return response.as(AssemblyItemResponse.class).getData();
    }

    private void approveItem(String assemblyItemId) {
        System.out.println("\nПодтверждаем позицию");
        Response response = ShopperApiRequests.patchAssemblyItemApprove(assemblyItemId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyItemResponse.class).getData().getAttributes().getState(),
                "approved");
    }

    /**
     * Завершаем сборку
     */
    private void approveAssembly() {
        System.out.println("\nЗавершаем сборку");
        Response response = ShopperApiRequests.patchAssemblyApprove(currentAssemblyId);
        assertStatusCode200(response);
    }

    private void startPaymentVerification() {
        System.out.println("\nПодтверждаем прохождение оплаты у сборки для передачи упаковщику");
        Response response = ShopperApiRequests.putAssemblyStartPaymentVerification(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.PAYMENT_VERIFICATION.getState());
    }

    private void shopperCreatesPackageSets() {
        System.out.println("\nРаскладываем заказ для передачи упаковщику");
        int boxNumber = 10;
        Response response = ShopperApiRequests.postAssemblyPackageSets(currentAssemblyId, boxNumber);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(PackageSetsResponse.class).getData().size(), boxNumber);
    }

    private void finishAssembling() {
        System.out.println("\nЗавершаем сборку для передачи упаковщику");
        Response response = ShopperApiRequests.putAssemblyFinishAssembling(currentAssemblyId);
        assertStatusCode200(response);
        AssemblyAttributes attributes = response.as(AssemblyResponse.class).getData().getAttributes();
        Assert.assertEquals(attributes.getState(),
                AssemblyState.ASSEMBLED.getState());
        Assert.assertNull(attributes.getPackerId());
    }

    private void packer() {
        System.out.println("\nБерем сборку упаковщиком>");
        Response response = ShopperApiRequests.putAssemblyPacker(currentAssemblyId);
        assertStatusCode200(response);
        AssemblyAttributes attributes = response.as(AssemblyResponse.class).getData().getAttributes();
        Assert.assertEquals(attributes.getState(),
                AssemblyState.ASSEMBLED.getState());
        Assert.assertNotNull(attributes.getPackerId());
    }

    private void startPurchasing() {
        System.out.println("\nНачинаем оплату на кассе");
        Response response = ShopperApiRequests.putAssemblyStartPurchasing(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.ON_CASH_DESK.getState());
    }

    private void createReceipts() {
        System.out.println("\nОтправляем инфу о чеке");
        String
                total = "1.0",
                fiscalSecret = "1",
                fiscalDocumentNumber = "1",
                fiscalChecksum = "1",
                transactionDetails = "1";
        Response response = ShopperApiRequests.postAssemblyReceipts(
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
        System.out.println("\nНачинаем упаковку");
        Response response = ShopperApiRequests.patchAssemblyStartPackaging(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.PACKAGING.getState());
    }

    private void getPackageSets() {
        System.out.println("\nСмотрим где лежит заказ для упаковки");
        Response response = ShopperApiRequests.getAssemblyPackageSets(currentAssemblyId);
        assertStatusCode200(response);
    }

    private void packerCreatesPackageSets() {
        System.out.println("\nРаскладываем упакованный заказ");
        Response response = ShopperApiRequests.postAssemblyPackageSets(
                currentAssemblyId, 1,1,1,1);
        assertStatusCode200(response);
        List<PackageSetData> packageSets = response.as(PackageSetsResponse.class).getData();
        Assert.assertEquals(packageSets.get(0).getAttributes().getLocation(), PackageSetLocation.BASKET.getLocation());
        Assert.assertEquals(packageSets.get(1).getAttributes().getLocation(), PackageSetLocation.RACK.getLocation());
        Assert.assertEquals(packageSets.get(2).getAttributes().getLocation(), PackageSetLocation.FRIDGE.getLocation());
        Assert.assertEquals(packageSets.get(3).getAttributes().getLocation(), PackageSetLocation.FREEZER.getLocation());
    }

    private void finishPurchasing() {
        System.out.println("\nЗавершаем упаковку");
        Response response = ShopperApiRequests.patchAssemblyPurchase(currentAssemblyId);
        assertStatusCode200(response);
        Assert.assertEquals(response.as(AssemblyResponse.class).getData().getAttributes().getState(),
                AssemblyState.READY_TO_SHIP.getState());
    }
}
