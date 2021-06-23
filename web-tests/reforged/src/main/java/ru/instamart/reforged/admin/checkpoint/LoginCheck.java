package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.action.WaitAction;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.component.Element;

public interface LoginCheck extends Check {

    Element title = new Element(By.xpath("//h1[text()='Вход']"));
    Element errorSetEmail = new Element(By.xpath("//div[@role='alert' and text()='Укажите email']"));
    Element errorInvalidFormatEmail = new Element(By.xpath("//div[@role='alert' and text()='Email адрес имеет неправильный формат']"));
    Element errorSetPassword = new Element(By.xpath("//div[@role='alert' and text()='Укажите пароль']"));
    Element errorShortPassword = new Element(By.xpath("//div[@role='alert' and text()='Пароль должен содержать не менее 6 символов']"));
    Element errorInvalidEmailOrPassword = new Element(By.xpath("//div[@role='alert' and text()='Неверный email или пароль']"));

    @Step("Проверяем что на странице отображается заголовок Вход")
    default void checkTitle() {
        WaitAction.shouldBeVisible(title);
    }

    @Step("Проверяем текст сообщения об ошибке с пустым полем username")
    default void checkErrorEmptyEmail() {
        WaitAction.shouldBeVisible(errorSetEmail).isDisplayed();
    }

    @Step("Проверяем текст сообщения об ошибке с неверным форматом для поля username")
    default void checkErrorInvalidEmail() {
        WaitAction.shouldBeClickable(errorInvalidFormatEmail).isSelected();
    }

    @Step("Проверяем текст сообщения об ошибке с пустым полем password")
    default void checkErrorEmptyPassword() {
        WaitAction.shouldBeVisible(errorSetPassword).isDisplayed();
    }

    @Step("Проверяем текст сообщения об ошибке с коротким значением для поля password")
    default void checkErrorShortPassword() {
        WaitAction.shouldBeVisible(errorShortPassword).isDisplayed();
    }

    @Step("Проверяем текст сообщения об ошибке для несуществующего пользователя")
    default void checkErrorInvalidEmailOrPassword() {
        WaitAction.shouldBeVisible(errorInvalidEmailOrPassword).isDisplayed();
    }
    
}
