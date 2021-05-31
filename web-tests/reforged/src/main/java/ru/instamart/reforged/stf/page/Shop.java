package ru.instamart.reforged.stf.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.stf.block.AuthoredHeader;
import ru.instamart.reforged.stf.component.Button;
import ru.instamart.reforged.stf.frame.Address;
import ru.instamart.reforged.stf.drawer.Cart;
import ru.instamart.reforged.stf.frame.ClearCart;

@Slf4j
public final class Shop implements Page {

    private final Button openAddress = new Button(By.xpath("//button[@data-qa='select-button']"));
    private final AuthoredHeader header = new AuthoredHeader();
    private final Address address = new Address();
    private final Cart cart = new Cart();
    private final ClearCart clearCart = new ClearCart();

    public AuthoredHeader useHeader() {
        return header;
    }

    public Cart cart(){
        return cart;
    }

    public Address useAddress() {
        return address;
    }

    public ClearCart clearCart() {
        return clearCart;
    }

    @Step("Открыть окно ввода адреса доставки")
    public void openAddressFrame() {
        openAddress.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
