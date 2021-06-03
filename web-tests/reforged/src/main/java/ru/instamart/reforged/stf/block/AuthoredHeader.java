package ru.instamart.reforged.stf.block;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.DropDown;
import ru.instamart.reforged.core.component.Input;

public final class AuthoredHeader {

    private final Input searchInput = new Input(By.xpath("//div[@data-qa='search']/form/input"));
    private final Button searchButton = new Button(By.xpath("//div[@data-qa='search']/form/button"));
    private final DropDown searchDropDown = new DropDown(By.xpath("//div[@data-qa='offer']"));

    private final Button profile = new Button(By.xpath("//button[@data-qa='profile-button_button']"));
    private final Button cart = new Button(By.xpath("//button[@data-qa='open-cart-button']"));

    @Step("Ввести текст для поиска {0}")
    public void fillSearch(final String text) {
        searchInput.fill(text);
    }

    @Step("Выбрать элемент поиска")
    public void selectSearch() {
        searchDropDown.selectAny();
    }

    @Step("Открыть мень профиля")
    public void clickToProfile() {
        profile.click();
    }

    @Step("Открыть корзину")
    public void clickToCart() {
        cart.click();
    }
}
