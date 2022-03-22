package ru.instamart.reforged.business.frame.auth;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public final class B2BAuthSberBusinessID {

    private final Input login = new Input(By.xpath("//input[@data-test-id='login--input']"), "Поле для ввода логина пользователя СББОЛ");
    private final Input password = new Input(By.xpath("//input[@data-test-id='password--input']"), "Поле для ввода пароля пользователя СББОЛ");
    private final Input smsCodeCell = new Input(By.xpath("//input[@data-test-id='smsCode--input']"), "Первая ячейка для ввода смс-кода");

    private final Button nextButton = new Button(By.xpath("//button[@data-test-id='login-submit--button']"), "Кнопка 'Далее' на странице ввода логина/пароля");

    @Step("Ввести логин {0} на странице авторизации через СберБизнесID")
    public void setLogin(final String text) {
        login.fill(text);
    }

    @Step("Ввести пароль {0} на странице авторизации через СберБизнесID")
    public void setPassword(final String text) {
        password.fill(text);
    }

    @Step("Нажать Далее на странице авторизации через СберБизнесID")
    public void clickToNext() {
        nextButton.click();
    }

    @Step("Заполнить смс-код {0} на странице авторизации через СберБизнесID")
    public void enterCode(final String code) {
        smsCodeCell.fill(code);
    }

    @Step("Авторизуемся с использованием СберБизнеc ID")
    public void authViaSberBusinessID(final UserData userData) {
        setLogin(userData.getEmail());
        setPassword(userData.getPassword());
        clickToNext();
        enterCode(userData.getSmsCode());
    }

    @Step("Авторизуемся с использованием СберБизнеc ID")
    public void authViaSberBusinessID() {
        setLogin(UserManager.getDefaultSberBusinessIdUser().getEmail());
        setPassword(UserManager.getDefaultSberBusinessIdUser().getPassword());
        clickToNext();
        enterCode(UserManager.getDefaultSberBusinessIdUser().getSmsCode());
    }
}
