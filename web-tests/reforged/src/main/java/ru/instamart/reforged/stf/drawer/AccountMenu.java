package ru.instamart.reforged.stf.drawer;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public class AccountMenu {

    private final Element username = new Element(By.xpath("//div[@data-qa='account-menu-username']"));
    private final Link profile = new Link(By.xpath("//a[@data-qa='account-menu-profile']"));
    private final Link terms = new Link(By.xpath("//a[@data-qa='account-menu-terms']"));
    private final Button logout = new Button(By.xpath("//button[@data-qa='account-menu-logout']"));
    private final Button delivery = new Button(By.xpath("//button[@data-qa='account-menu-delivery']"));
    private final Link faq = new Link(By.xpath("//a[@data-qa='account-menu-faq']"));

    @Step("Нажать Профиль в  меню пользователя")
    public void clickToProfile() {
        profile.click();
    }

    @Step("Нажать Условия пользования в меню пользователя")
    public void clickToTerms() {
        terms.click();
    }

    @Step("Нажать Выйти в меню пользователя")
    public void clickToLogout() {
        logout.click();
    }

    @Step("Нажать Доставка в меню пользователя")
    public void clickToDelivery() {
        delivery.click();
    }

    @Step("Нажать FAQ в меню пользователя")
    public void clickToFaq() {
        delivery.click();
    }
}
