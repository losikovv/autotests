package ru.instamart.reforged.admin.page.shipment.shipment.send_promo;

import io.qameta.allure.Step;

public interface SendPromoCheck extends SendPromoElement {

    @Step("Проверяем, что отображается страница отправки промо")
    default void checkCompensationPromoPageVisible() {
        promoPage.should().visible();
    }

    @Step("Проверяем, что список промо не отображается")
    default void checkPromoCompensationNotVisible() {
        compensationItemInList.should().invisible();
    }

    @Step("Проверяем, что список промо открылся")
    default void checkPromoCompensationItemsListVisible() {
        compensationItemInList.should().visible();
        compensationItemInList.should().animationFinished();
    }
}