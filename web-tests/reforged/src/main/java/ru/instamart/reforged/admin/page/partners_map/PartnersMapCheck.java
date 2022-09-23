package ru.instamart.reforged.admin.page.partners_map;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface PartnersMapCheck extends Check, PartnersMapElement {

    @Step("Проверка загрузки карты")
    default void checkMapLoaded() {
        map.should().visible();
        map.should().notAnimated();
    }

    @Step("Проверка отображения балуна")
    default void checkPartnerBalloon() {
        balloon.should().visible();
        balloon.should().notAnimated();
    }

    @Step("Имя в балуне соответсвует имени '{name}'")
    default void checkNameInBalloon(final String name) {
        partnerNameBalloon.is().containText(name);
    }
}
