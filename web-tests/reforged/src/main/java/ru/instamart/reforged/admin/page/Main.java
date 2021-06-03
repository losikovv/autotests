package ru.instamart.reforged.admin.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.admin.checkpoint.MainCheck;
import ru.instamart.reforged.core.component.Element;

public final class Main implements AdminPage, MainCheck {

    private final Element logout = new Element(By.xpath("//span[@class='anticon anticon-logout ant-dropdown-menu-item-icon']"));

    @Step("Нажать на меню профиля")
    public void clickToProfileMenu() {
        user.click();
    }

    @Step("Нажать на кнопку выхода")
    public void clickToLogout() {
        logout.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
