package ru.instamart.reforged.admin.page.shipment.shipment.send_promo.approve_page.cancel_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface CancelPromoModalCheck extends Check, CancelPromoModalElement {

    @Step("Проверяем, что отображается модальное окно отказа промо")
    default void checkCancelPromoModalVisible() {
        modal.should().visible();
    }
}
