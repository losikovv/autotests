package ru.instamart.api.helper;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.model.delivery_club.SlotDC;
import ru.instamart.api.request.delivery_club.StoresDCRequest;

import java.util.Arrays;
import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Slf4j
public class DeliveryClubHelper {

    public SlotDC getAvailableSlot(int sid) {
        log.debug("Получаем первый активный слот в магазине sid: {}", sid);
        Response response = StoresDCRequest.Slots.Available.GET(sid);
        checkStatusCode200(response);
        List<SlotDC> slots = Arrays.asList(response.as(SlotDC[].class));
        checkFieldIsNotEmpty(slots,"слоты в магазине admin/stores/" + sid);
        return slots.get(0);
    }
}
