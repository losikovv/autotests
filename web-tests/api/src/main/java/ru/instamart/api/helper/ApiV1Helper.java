package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.model.v1.LineItemV1;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.request.v1.LineItemsV1Request;
import ru.instamart.api.request.v1.MultiretailerOrderV1Request;
import ru.instamart.api.request.v1.OperationalZonesV1Request;
import ru.instamart.api.request.v1.ShoppingContextV1Request;
import ru.instamart.api.response.v1.LineItemV1Response;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v1.OperationalZonesV1Response;

import java.util.List;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Slf4j
public class ApiV1Helper {

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

    @Step("Добавляем товар в корзин")
    public LineItemV1 addItemToCart(Long offerId) {
        final Response response = LineItemsV1Request.POST(offerId);
        checkStatusCode200(response);
        return response.as(LineItemV1Response.class).getLineItem();
    }

    @Step("Меняем адрес пользователя")
    public void changeAddress(AddressV2 address, String shippingMethod) {
        final Response response = ShoppingContextV1Request.PUT(address, shippingMethod);
        checkStatusCode200(response);
    }
}
