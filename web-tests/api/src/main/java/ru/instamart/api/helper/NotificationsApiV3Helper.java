package ru.instamart.api.helper;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.enums.v3.NotificationTypeV3;
import ru.instamart.api.request.v3.NotificationsV3Request;

@Slf4j
public final class NotificationsApiV3Helper extends NotificationsV3Request {
    public Notifications bodyCanceled(String shipmentNumber) {
        return Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.CANCELED.getValue())
                        .payload(Payload.builder()
                                .orderId(shipmentNumber)
                                .build())
                        .build())
                .build();
    }
    public Notifications bodyInWork(String shipmentNumber) {
        return Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.IN_WORK.getValue())
                        .payload(Payload.builder()
                                .orderId(shipmentNumber)
                                .build())
                        .build())
                .build();
    }
    public Notifications bodyReadyForDelivery(String shipmentNumber) {
        return Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.READY_FOR_DELIVERY.getValue())
                        .payload(Payload.builder()
                                .orderId(shipmentNumber)
                                .order(Order.builder()
                                        .originalOrderId(shipmentNumber)
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
    }
    public Notifications bodyDelivering(String shipmentNumber) {
        return Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERING.getValue())
                        .payload(Payload.builder()
                                .orderId(shipmentNumber)
                                .build())
                        .build())
                .build();
    }
    public Notifications bodyDelivered(String shipmentNumber) {
        return Notifications.builder()
                .event(Event.builder()
                        .type(NotificationTypeV3.DELIVERED.getValue())
                        .payload(Payload.builder()
                                .orderId(shipmentNumber)
                                .order(Order.builder()
                                        .originalOrderId(shipmentNumber)
                                        .changed(false)
                                        .build())
                                .build())
                        .build())
                .build();
    }


}
