package ru.instamart.reforged.admin.page.shipment.shipment.send_promo;

import io.qameta.allure.Step;
import org.testng.Assert;

public interface SendPromoCheck extends SendPromoElement {

    @Step("Проверяем, что отображается страница отправки промо")
    default void checkCompensationPromoPageVisible() {
        promoPage.should().visible();
    }

    @Step("Проверяем, что список промо не отображается")
    default void checkPromoCompensationNotVisible() {
        Assert.assertTrue(compensationItemInList.is().invisible());
    }

    @Step("Проверяем, что список промо открылся")
    default void checkPromoCompensationItemsListVisible() {
        compensationItemInList.should().visible();
        Assert.assertTrue(compensationItemInList.is().animationFinished());
    }
}