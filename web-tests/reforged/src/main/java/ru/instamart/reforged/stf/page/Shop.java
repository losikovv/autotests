package ru.instamart.reforged.stf.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.action.JsAction;
import ru.instamart.reforged.stf.block.AuthoredHeader;
import ru.instamart.reforged.stf.component.Button;
import ru.instamart.reforged.stf.drawer.Cart;
import ru.instamart.reforged.stf.frame.Address;

@Slf4j
public final class Shop implements Page {

    private final Button openAddress = new Button(By.xpath("//button[@data-qa='select-button']"));
    private final AuthoredHeader header = new AuthoredHeader();
    private final Address address = new Address();
    private final Cart cart = new Cart();

    public AuthoredHeader interactHeader() {
        return header;
    }

    public Cart interactCart(){
        return cart;
    }

    public Address interactAddress() {
        return address;
    }

    @Step("Открыть окно ввода адреса доставки")
    public void openAddressFrame() {
        openAddress.click();
        JsAction.ymapReady();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
