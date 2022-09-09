package ru.instamart.reforged.admin.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CollectingStatus {

    //Ожидает автоматической диспетчеризации(это свеже созданный заказ еще не отправленный на диспетчерезацию)
    WAITING_FOR_AUTOMATIC_DISPATCHING("Ожидает автоматической диспетчеризации"),
    AUTOMATIC_DISPATCHING("Автоматическая диспетчеризация"),
    MANUAL_DISPATCHING("Ручная диспетчеризация"),
    OFFER_SENT("Оффер отправлен"),
    QUEUED_FOR_EXECUTION("В очереди к выполнению"),
    OFFER_REJECTED("Оффер отклонен"),
    CANCELLED("Отменён"),
    ASSEMBLED("Собирается"),
    COLLECTED("Собран"),
    //ready_to_automatic_routing этот статус когда заказ распределяется диспатчем, но допустим первая попытка не нашел кандидатат,
    // не назначил, у диспатча еще есть попытки(), это от настроек зависит)
    // и пока все попытки не закончатся то в этом статусе, а уже потом в ручник уходит
    READY_FOR_DISPATCHING("Готов к диспетчеризации");

    private final String name;
}
