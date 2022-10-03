package ru.instamart.reforged.admin.page.shipment.shipment.send_promo.approve_page;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface ApprovePromoCheck extends Check, ApprovePromoElement {

    @Step("Проверяем, что отображается страница подтверждения промо")
    default void checkApprovePromoPageVisible() {
        approvePromoPage.should().visible();
    }
}