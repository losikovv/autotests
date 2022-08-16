package ru.instamart.reforged.admin.enums;

import lombok.AllArgsConstructor;

public interface AdminOrders {
    @AllArgsConstructor
    enum ShipmentStatus implements AdminOrders {
        SHIPMENT_PENDING("Оформляется", "Заказ оформляется"),
        SHIPMENT_READY("Оформлен", "Заказ оформлен"),
        DISPATCH_NEW("Готов к диспетчеризации", "Готов к диспетчеризации"),
        DISPATCH_POSTPONED("В очереди на диспетчеризацию", "В очереди на диспетчеризацию"),
        DISPATCH_AUTOMATIC_ROUTING("Автоматическая диспетчеризация", "Автоматическая диспетчеризация"),
        DISPATCH_MANUAL_ROUTING("Ручная диспетчеризация", "Ручная диспетчеризация"),
        DISPATCH_OFFERING("Оффер отправлен", "Назначение отправлено партнерам"),
        DISPATCH_OFFERED("Оффер принят", "Назначение принято партнерами"),
        DISPATCH_DECLINED("Оффер отклонен", "Назначение отклонено партнером"),
        DISPATCH_REDISPATCH("Возврат на диспетчеризацию", "Возвраещается на автоматическую диспетчеризацию"),
        DISPATCH_CANCELED("Оффер отменен", "Назначение отменено"),
        DISPATCH_SHIPPED("Доставлен", "Заказ доставлен"),
        SHIPMENT_COLLECTING("Собирается", "Заказ собирается"),
        SHIPMENT_READY_TO_SHIP("Собран", "Заказ собран"),
        SHIPMENT_SHIPPING("В пути", "Заказ в пути"),
        SHIPMENT_CANCELED("Отменен", "Заказ отменен"),
        SHIPMENT_SHIPPED("Доставлен", "Заказ доставлен");
        private String name;
        private String description;

        public String getName() {
            return name;
        }
    }

    @AllArgsConstructor
    enum PaymentMethods implements AdminOrders {
        BY_CASH("Наличные"),
        BY_CARD_TO_COURIER("Картой курьеру"),
        AT_CHECKOUT("На кассе");
        private String name;

        public String getName() {
            return name;
        }
    }

    @AllArgsConstructor
    enum PaymentStatuses implements AdminOrders {
        PAID("Оплачен"),
        NOT_PAID("Не оплачен"),
        BALANCE_DUE("Недоплата"),
        OVERPAID("Переплата"),
        FAILED("Ошибка");
        private String name;

        public String getName() {
            return name;
        }
    }
}
