package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Element;

public interface MainCheck extends Check {

    Element user = new Element(By.xpath("//span[@class='ant-avatar ant-avatar-circle ant-avatar-icon']"));

    @Step("Пользователь авторизовался")
    default void checkAuth() {
        Kraken.waitAction().shouldBeVisible(user);
    }
}
