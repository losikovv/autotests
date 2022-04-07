package ru.instamart.api.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import ru.instamart.k8s.rails_response.GetRetailerResponse;
import ru.instamart.kraken.enums.Tenant;

import java.util.List;

import static ru.instamart.api.enums.BashCommands.Instacoins.ADD_USER_INSTACOIN;
import static ru.instamart.api.enums.BashCommands.Promotions.CREATE_COMPENSATION_PROMOTIONS;
import static ru.instamart.api.enums.BashCommands.ShipmentDelays.COMPUTE_EXPECTED_DATES;
import static ru.instamart.api.enums.BashCommands.ShipmentDelays.SEND_NOTIFICATIONS;
import static ru.instamart.api.enums.RailsConsole.ApiV3.*;
import static ru.instamart.api.enums.RailsConsole.ExternalPartners.SUBSCRIPTION;
import static ru.instamart.api.enums.RailsConsole.Order.Flipper;
import static ru.instamart.api.enums.RailsConsole.Order.*;
import static ru.instamart.api.enums.RailsConsole.Other.DELETE_COMPENSATIONS_CACHE;
import static ru.instamart.api.enums.RailsConsole.Other.DELETE_SHIPMENT_RETURN;
import static ru.instamart.api.enums.RailsConsole.User.*;
import static ru.instamart.k8s.K8sConsumer.*;

public class K8sHelper {
    @Step("Перевод через консоль заказ {shipmentNumber} в статус \"Доставлено\"")
    public static void changeToShip(String shipmentNumber) {
        List<String> strings = execRailsCommandWithPod(SHIP.get(shipmentNumber));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Перевод через консоль заказ {shipmentNumber} в статус \"Собирается\"")
    public static void changeToCollecting(String shipmentNumber) {
        List<String> strings = execRailsCommandWithPod(START_COLLECTING.get(shipmentNumber));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Перевод через консоль заказ {shipmentNumber} в статус \"Готов к отправке\"")
    public static void changeToReadyToShip(String shipmentNumber) {
        List<String> strings = execRailsCommandWithPod(FINISH_COLLECTING.get(shipmentNumber));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Перевод через консоль заказ {shipmentNumber} в статус \"Доставляется\"")
    public static void changeToShipping(String shipmentNumber) {
        List<String> strings = execRailsCommandWithPod(START_SHIPPING.get(shipmentNumber));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Перевод через консоль заказ {orderNumber} в статус \"Отменен\"")
    public static void changeToCancel(String orderNumber) {
        List<String> strings = execRailsCommandWithPod(CANCEL.get(orderNumber));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }


    @Step("Перевод через консоль позиции заказа в статус \"Собрано\"")
    public static void changeToAssembled(String shipmentNumber, String itemNumber) {
        List<String> strings = execRailsCommandWithPod(ASSEMBLY_ITEMS_ORDER.get(shipmentNumber, itemNumber));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Перевод через консоль позиции заказа в статус \"Отменено\"")
    public static void changeItemToCancel(String shipmentNumber, String itemNumber) {
        List<String> strings = execRailsCommandWithPod(CANCEL_ITEMS_ORDER.get(shipmentNumber, shipmentNumber, itemNumber));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Добавление инстакоинов {instacoin} для пользователя email: {email} userId: {userId}")
    public static List<String> execRakeTaskAddBonus(String email, String instacoin, String userId) {
        List<String> consoleLog = execBashCommandWithPod(ADD_USER_INSTACOIN.get(email, instacoin, userId));
        Allure.addAttachment("Логи консоли", String.join("\n", consoleLog));
        return consoleLog;
    }

    @Step("Добавление подписки СберПрайм")
    public static void addSberPrime(String email) {
        List<String> strings = execRailsCommandWithPod(SUBSCRIPTION.get(email));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Поиск ритейлера {tenant}")
    public static GetRetailerResponse[] getRetailer(Tenant tenant) {
        String toLowerCase = tenant.toString().toLowerCase();
        return getClassWithExecRailsCommand(GET_RETAILER.get(toLowerCase) + ".to_json()", GetRetailerResponse[].class);
    }


    @Step("Поиск товарного предложения по штучному товару в {tenant} и storeId = {storeId}")
    public static <T> T getPricerPerItem(Integer tenantId, Integer storeId, Class<T> clazz) {
        String command = String.format("retailer_id: %s, store_id: %d, pricer: \"Pricer::PerItem\", deleted_at: %S",
                tenantId, storeId);
        String toJson = OFFER_WHERE_LAST.get(command) + ".to_json(:except => [:pricer])";//исключаем pricer. Иначе получим ошибку
        return getClassWithExecRailsCommand(toJson, clazz);
    }

    @Step("Поиск товарного предложения по весовому товару в {tenant} и storeId = {storeId}")
    public static <T> T getPricerPerKilo(Integer tenantId, Integer storeId, Class<T> clazz) {
        String command = String.format("retailer_id: %s, store_id: %d, pricer: \"Pricer::PerKilo\", deleted_at: nil, published: true",
                tenantId, storeId);
        String toJson = OFFER_WHERE_LAST.get(command) + ".to_json(:except => [:pricer])";//исключаем pricer. Иначе получим ошибку
        return getClassWithExecRailsCommand(toJson, clazz);
    }

    @Step("Поиск товарного предложения по фасованном товару в {tenant} и storeId = {storeId}")
    public static <T> T getPricerPerPackage(Integer tenantId, Integer storeId, Class<T> clazz) {
        String command = String.format("retailer_id: %s, store_id: %d, pricer: \"Pricer::PerPackage\", deleted_at: nil, published: true",
                tenantId, storeId);
        String toJson = OFFER_WHERE_LAST.get(command) + ".to_json(:except => [:pricer])";//исключаем pricer. Иначе получим ошибку
        return getClassWithExecRailsCommand(toJson, clazz);
    }

    @Step("Поиск товарного предложения по упакованному товару в {tenant} и storeId = {storeId}")
    public static <T> T getPricerPerPack(Integer tenantId, Integer storeId, Class<T> clazz) {
        String command = String.format("retailer_id: %s, store_id: %d, pricer: \"Pricer::PerPack\", deleted_at: nil, published: true",
                tenantId, storeId);
        String toJson = OFFER_WHERE_LAST.get(command) + ".to_json(:except => [:pricer])";//исключаем pricer. Иначе получим ошибку
        return getClassWithExecRailsCommand(toJson, clazz);
    }

    @Step("Поиск товарного предложения по штучному товару в {tenant} и storeId = {storeId}")
    public static <T> T getPricerPerItemProductFilter(Integer tenantId, Integer storeId, Integer shippingCategoryId, Class<T> clazz) {
        String command = String.format("retailer_id: %d, store_id: %d, pricer: \"Pricer::PerItem\", deleted_at: nil, published: true, spree_products: { shipping_category_id: %d }",
                tenantId, storeId, shippingCategoryId);
        String toJson = OFFER_JOIN_PRODUCT.get(command) + ".to_json(:except => [:pricer])";//исключаем pricer. Иначе получим ошибку
        return getClassWithExecRailsCommand(toJson, clazz);
    }

    @Step("Поиск товарного предложения по весовому товару в {tenant} и storeId = {storeId}")
    public static <T> T getPricerPerKiloProductFilter(Integer tenantId, Integer storeId, Integer shippingCategoryId, Class<T> clazz) {
        String command = String.format("retailer_id: %d, store_id: %d, pricer: \"Pricer::PerKilo\", deleted_at: nil, published: true, spree_products: { shipping_category_id: %d }",
                tenantId, storeId, shippingCategoryId);
        String toJson = OFFER_JOIN_PRODUCT.get(command) + ".to_json(:except => [:pricer])";//исключаем pricer. Иначе получим ошибку
        return getClassWithExecRailsCommand(toJson, clazz);
    }

    @Step("Поиск товарного предложения по фасованном товару в {tenant} и storeId = {storeId}")
    public static <T> T getPricerPerPackageProductFilter(Integer tenantId, Integer storeId, Integer shippingCategoryId, Class<T> clazz) {
        String command = String.format("retailer_id: %d, store_id: %d, pricer: \"Pricer::PerPackage\", deleted_at: nil, published: true, spree_products: { shipping_category_id: %d }",
                tenantId, storeId, shippingCategoryId);
        String toJson = OFFER_JOIN_PRODUCT.get(command) + ".to_json(:except => [:pricer])";//исключаем pricer. Иначе получим ошибку
        return getClassWithExecRailsCommand(toJson, clazz);
    }

    @Step("Поиск товарного предложения по упакованному товару в {tenant} и storeId = {storeId}")
    public static <T> T getPricerPerPackProductFilter(Integer tenantId, Integer storeId, Integer shippingCategoryId, Class<T> clazz) {
        String command = String.format("retailer_id: %d, store_id: %d, pricer: \"Pricer::PerPack\", deleted_at: nil, published: true, spree_products: { shipping_category_id: %d }",
                tenantId, storeId, shippingCategoryId);
        String toJson = OFFER_JOIN_PRODUCT.get(command) + ".to_json(:except => [:pricer])";//исключаем pricer. Иначе получим ошибку
        return getClassWithExecRailsCommand(toJson, clazz);
    }

    @Step("Добавление роли '{roleName}' пользователю '{userId}'")
    public static void addRoleToUser(String userId, String roleName) {
        List<String> strings = execRailsCommandWithPod(ADD_ROLE.get(userId, roleName));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Добавление всех ролей пользователю '{userId}'")
    public static void addAllRolesToUser(String userData) {
        List<String> strings = execRailsCommandWithPod(ADD_ALL_ROLES.get(userData));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Создание администратора через консоль")
    public static void createAdmin(String firstName, String lastName, String email, String password) {
        List<String> strings = execRailsCommandWithPod(CREATE_ADMIN.get(firstName, lastName, email, password));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Создание api-клиента через консоль")
    public static void createApiClient(String clientId, Tenant tenant) {
        List<String> strings = execRailsCommandWithPod(CREATE_API_CLIENT.get(clientId, tenant.getId()));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Установка ожидаемых дат сборки и доставки")
    public static void computeExpectedDates() {
        List<String> consoleLog = execBashCommandWithPod(COMPUTE_EXPECTED_DATES.get());
        Allure.addAttachment("Логи консоли", String.join("\n", consoleLog));
    }

    @Step("Отправка уведомления о задержке доставки")
    public static void sendNotifications() {
        List<String> consoleLog = execBashCommandWithPod(SEND_NOTIFICATIONS.get());
        Allure.addAttachment("Логи консоли", String.join("\n", consoleLog));
    }

    @Step("Включение/выключение фичи allow_export_to_external_services")
    public static void allowExportToExternalServices(Boolean enable) {
        List<String> strings = execRailsCommandWithPod(Flipper.ALLOW_EXPORT_TO_EXTERNAL_SERVICES.get(enable ? "enable" : "disable"));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Включение/выключение фичи export_to_external_services_by_webhook")
    public static void exportToExternalServicesByWebhook(Boolean enable) {
        List<String> strings = execRailsCommandWithPod(Flipper.EXPORT_TO_EXTERNAL_SERVICES_BY_WEBHOOK.get(enable ? "enable" : "disable"));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Включение/выключение фичи new_admin_roles")
    public static void newAdminRoles(Boolean enable) {
        List<String> strings = execRailsCommandWithPod(Flipper.NEW_ADMIN_ROLES.get(enable ? "enable" : "disable"));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Очистить кэш промо-компенсаций")
    public static void deleteOrderCompensationsCache() {
        List<String> strings = execRailsCommandWithPod(DELETE_COMPENSATIONS_CACHE.get());
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Создание промо для компенсаций")
    public static void createCompensationPromotions() {
        List<String> consoleLog = execBashCommandWithPod(CREATE_COMPENSATION_PROMOTIONS.get());
        Allure.addAttachment("Логи консоли", String.join("\n", consoleLog));
    }
    @Step("Удалить последний shipment_return")
    public static void deleteLastShipmentReturn() {
        List<String> strings = execRailsCommandWithPod(DELETE_SHIPMENT_RETURN.get());
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }
}
