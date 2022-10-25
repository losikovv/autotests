package ru.instamart.reforged.hr_ops_partners.frame.success_sent_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface SuccessSentModalCheck extends SuccessSentModalElement, Check {

    @Step("Проверяем, что окно отклика отображается")
    default void checkModalVisible() {
        Kraken.waitAction().shouldBeVisible(modalTitle);
        Kraken.waitAction().shouldNotBeAnimated(modalTitle);
    }

    @Step("Проверяем, что окно отклика не отображается")
    default void checkModalNotVisible() {
        Assert.assertTrue(Kraken.waitAction().shouldNotBeVisible(modalTitle));
    }
}
