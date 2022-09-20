package ru.instamart.reforged.admin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeliveryStatus {

    WAITING_FOR_AUTOMATIC_DISPATCHING("Готов к диспетчеризации"),
    AUTOMATIC_DISPATCHING("Автоматическая диспетчеризация"),
    MANUAL_DISPATCHING("Ручная диспетчеризация"),
    OFFER_SENT("Оффер отправлен"),
    QUEUED_FOR_EXECUTION("В очереди к выполнению"),
    OFFER_REJECTED("Оффер отклонен"),
    CANCELLED("Отменён"),
    DELIVERED_AT("Доставляется"),
    DELIVERED("Доставлен"),
    READY_FOR_DISPATCHING("Готов к диспетчеризации");

    private final String name;
}
