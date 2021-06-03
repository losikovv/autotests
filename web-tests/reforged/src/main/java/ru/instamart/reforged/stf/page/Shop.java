package ru.instamart.reforged.stf.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.action.JsAction;
import ru.instamart.reforged.stf.block.AuthoredHeader;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.stf.drawer.Cart;
import ru.instamart.reforged.stf.frame.Address;

@Slf4j
public final class Shop implements StfPage {

    private final Button openAddress = new Button(By.xpath("//button[@data-qa='select-button']"));
    private final AuthoredHeader header = new AuthoredHeader();

    public AuthoredHeader interactHeader() {
        return header;
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
