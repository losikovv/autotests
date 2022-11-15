package ru.instamart.reforged.admin.page.partners_map;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface PartnersMapCheck extends Check, PartnersMapElement {

    @Step("Проверка загрузки карты")
    default void checkMapLoaded() {
        map.should().visible();
        map.is().animationFinished();
    }

    @Step("Проверка отображения балуна")
    default void checkPartnerBalloon() {
        balloon.should().visible();
        balloon.is().animationFinished();
    }

    @Step("Имя в балуне соответствует имени '{name}'")
    default void checkNameInBalloon(final String name) {
        Assert.assertTrue(partnerNameBalloon.is().containText(name));
    }

    @Step("Телефон в балуне соответствует имени '{name}'")
    default void checkPhoneInBalloon(final String phone) {
        Assert.assertTrue(partnerPhoneBalloon.is().containText(phone));
    }

    @Step("Номер заказа в балуне соответствует заданному '{number}'")
    default void checkOrderNumberInBalloon(final String number) {
        Assert.assertTrue(activeOrdersBalloon.is().containText(number));
    }

    @Step("Сверка скриншотов карты")
    default void checkMapScreen() {
        map.should().screenDiff();
    }

    @Step("Сверка карты с балуном партнера")
    default void checkMapWithBalloon() {
        map.should().screenDiff(activeOrdersBalloon, activityUpdateBalloon);
    }
}
