package ru.instamart.reforged.stf.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.action.JsAction;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.block.AuthoredHeader;
import ru.instamart.reforged.stf.frame.ProductCard;

@Slf4j
public final class Shop implements StfPage {

    private final Button openAddress = new Button(By.xpath("//button[@data-qa='select-button']"));
    private final Element openFirstProductCard = new Element(By.xpath("//li[@class='product']"));
    private final AuthoredHeader header = new AuthoredHeader();
    private final ProductCard productCard = new ProductCard();

    public AuthoredHeader interactHeader() {
        return header;
    }

    public ProductCard interactProductCard() {
        return productCard;
    }

    @Step("Открыть окно ввода адреса доставки")
    public void openAddressFrame() {
        openAddress.click();
        JsAction.ymapReady();
    }

    public void openFirstProductCard() {
        openFirstProductCard.click(); //для теста
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
