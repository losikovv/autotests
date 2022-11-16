package ru.instamart.reforged.stf.page.checkout_new.b2b_order_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface B2BOrderModalCheck extends B2BOrderModalElement, Check {

    @Step("Проверяем, что модальное окно отображается")
    default void checkModalVisible() {
        modalTitle.should().visible();
    }

    @Step("Проверяем, что модальное окно не отображается")
    default void checkModalNotVisible() {
        modalTitle.should().invisible();
    }
}