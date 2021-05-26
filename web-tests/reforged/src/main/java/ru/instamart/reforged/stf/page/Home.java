package ru.instamart.reforged.stf.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.stf.checkpoint.HomeCheck;
import ru.instamart.reforged.stf.component.Button;
import ru.instamart.reforged.stf.frame.auth.AuthModal;

@Slf4j
public final class Home implements Page, HomeCheck {

    private static final String PAGE = "";

    private final Button loginButton = new Button(By.xpath("//button[contains(@class, 'home_header')]"));
    private final AuthModal authModal = new AuthModal();

    @Override
    public String pageUrl() {
        return PAGE;
    }

    @Step("Открыть модалку авторизации")
    public void openLoginModal() {
        log.info("Открываем модальное окно авторизации");
        loginButton.click();
    }

    public AuthModal useAuthModal() {
        return authModal;
    }
}
