package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.model.v1.*;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.v1.*;
import ru.instamart.api.response.v1.*;

import java.util.List;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Slf4j
public class ApiV1Helper {

    private final ThreadLocal<LineItemV1> currentLineItem = new ThreadLocal<>();
    private final ThreadLocal<Long> currentReplacementPolicyId = new ThreadLocal<>();
    private final ThreadLocal<MultiretailerOrderShipmentV1> currentShipment = new ThreadLocal<>();

    @Step("Получаем список всех операционных зон")
    public List<OperationalZoneV1> getAllOperationalZones() {
        log.debug("Получаем список всех операционных зон");
        Response response = OperationalZonesV1Request.GET();
        checkStatusCode200(response);
        return response.as(OperationalZonesV1Response.class).getOperationalZones();
    }

    @Step("Получаем информацию о заказе")
    public MultiretailerOrderV1Response getMultiRetailerOrder() {
        final Response response = MultiretailerOrderV1Request.GET();
        checkStatusCode200(response);
        return response.as(MultiretailerOrderV1Response.class);
    }

    @Step("Добавляем товар в корзину")
    public LineItemV1 addItemToCart(Long offerId) {
        final Response response = LineItemsV1Request.POST(offerId);
        checkStatusCode200(response);
        currentLineItem.set(response.as(LineItemV1Response.class).getLineItem());
        return currentLineItem.get();
    }

    @Step("Изменяем количество товара")
    public void changeItemCountInCart(int count) {
        currentLineItem.get().setPacks(count);
        final Response response = LineItemsV1Request.PUT(currentLineItem.get());
        checkStatusCode200(response);
    }

    @Step("Меняем адрес пользователя")
    public void changeAddress(AddressV2 address, String shippingMethod) {
        final Response response = ShoppingContextV1Request.PUT(address, shippingMethod);
        checkStatusCode200(response);
    }

    @Step("Получаем все политики замены")
    public List<ReplacementPolicyV1> getReplacementPolicies() {
        final Response response = ReplacementPoliciesV1Request.GET();
        checkStatusCode200(response);
        List<ReplacementPolicyV1> replacementPolicies = response.as(ReplacementPoliciesV1Response.class).getReplacementPolicies();
        currentReplacementPolicyId.set(replacementPolicies.get(0).getId());
        return replacementPolicies;
    }

    @Step("Получаем доступные способы оплаты")
    public List<PaymentToolV1> getPaymentTools() {
        final Response response = AvailablePaymentToolsV1Request.GET();
        checkStatusCode200(response);
        return response.as(PaymentToolsV1Response.class).getPaymentTools();
    }

    @Step("Наполняем корзину")
    public void fillCart(AddressV2 address, String shippingMethod, Long offerId, int count) {
        changeAddress(address, shippingMethod);
        addItemToCart(offerId);
        changeItemCountInCart(count);
    }

    @Step("Получаем доставку пользователя")
    public UserShipmentV1 getUserShipment(String userId, String shipmentNumber) {
        final Response response = UsersV1Request.GET(userId, shipmentNumber);
        checkStatusCode200(response);
        return response.as(UserShipmentV1Response.class).getShipment();
    }

    @Step("Добавляем политику замен")
    public MultiretailerOrderShipmentV1 addReplacementPolicy() {
        final Response response = CheckoutV1Request.PUT(currentReplacementPolicyId.get());
        checkStatusCode200(response);
        MultiretailerOrderShipmentV1 shipment = response.as(MultiretailerOrderV1Response.class).getShipments().get(0);
        currentShipment.set(shipment);
        return shipment;
    }

    @Step("Добавляем окно доставки")
    public void addDeliveryWindow() {
        final Response response = CheckoutV1Request.PUT(currentShipment.get());
        checkStatusCode200(response);
    }
}
