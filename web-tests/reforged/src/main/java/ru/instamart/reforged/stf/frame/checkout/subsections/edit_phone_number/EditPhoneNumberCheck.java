package ru.instamart.reforged.stf.frame.checkout.subsections.edit_phone_number;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface EditPhoneNumberCheck extends Check, EditPhoneNumberElement {

    @Step("Проверяем, что окно редактирования телефона закрыто")
    default void checkPhoneEditModalClosed() {
        Assert.assertTrue(phoneEditModal.is().invisible());
    }
}
