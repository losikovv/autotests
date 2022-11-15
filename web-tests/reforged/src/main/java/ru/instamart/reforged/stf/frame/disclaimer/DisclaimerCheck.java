package ru.instamart.reforged.stf.frame.disclaimer;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface DisclaimerCheck extends DisclaimerElement, Check {

    @Step("Проверяем, что кнопка 'Мне есть 18 лет' на модалке дисклеймера видима")
    default void checkApproveButtonVisible() {
        waitAction().shouldBeVisible(approve);
    }

    @Step("Проверяем, дисклеймер о продаже алкоголя виден")
    default void checkDisclaimerModalVisible() {
        waitAction().shouldBeVisible(disclaimerModal);
    }

    @Step("Проверяем, дисклеймер о продаже алкоголя не виден")
    default void checkDisclaimerModalNotVisible() {
        Assert.assertTrue(disclaimerModal.is().invisible());
    }

    @Step("Убедиться что модалка дисклеймера не анимируется")
    default void checkDisclaimerModalNotAnimated() {
        disclaimerModal.should().visible();
        Assert.assertTrue(disclaimerModal.is().animationFinished());
    }
}
