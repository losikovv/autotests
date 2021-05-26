package ru.instamart.reforged.stf.block;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.stf.component.Button;

public final class AuthoredHeader {

    private final Button profile = new Button(By.xpath("//button[@data-qa='profile-button_button']"));

    @Step("Открыть мень профиля")
    public void clickToProfile() {
        profile.click();
    }
}
