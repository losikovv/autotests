package ru.instamart.reforged.hr_ops_partners.frame.confirn_SMS_form_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface ConfirmSMSModalCheck extends ConfirmSMSModalElement, Check {

    @Step("Проверяем, что окно отклика отображается")
    default void checkModalVisible() {
        modalTitle.should().visible();
        modalTitle.should().animationFinished();
    }

    @Step("Проверяем, что окно отклика не отображается")
    default void checkModalNotVisible() {
        modalTitle.should().invisible();
    }
}
