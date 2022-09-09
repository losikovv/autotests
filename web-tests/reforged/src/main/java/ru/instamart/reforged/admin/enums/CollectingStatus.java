package ru.instamart.reforged.admin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CollectingStatus {

    WAITING_FOR_AUTOMATIC_DISPATCHING("Ожидает автоматической диспетчеризации"),
    AUTOMATIC_DISPATCHING("Автоматическая диспетчеризация"),
    MANUAL_DISPATCHING("Ручная диспетчеризация"),
    OFFER_SENT("Оффер отправлен"),
    QUEUED_FOR_EXECUTION("В очереди к выполнению"),
    OFFER_REJECTED("Оффер отклонен"),
    CANCELLED("Отменён"),
    ASSEMBLED("Собирается"),
    COLLECTED("Собран"),
    READY_FOR_DISPATCHING("Готов к диспетчеризации");

    private final String name;
}
