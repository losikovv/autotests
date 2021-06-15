package ru.instamart.reforged.stf.page;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.action.JsAction;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.block.AuthoredHeader;
import ru.instamart.reforged.stf.drawer.Cart;
import ru.instamart.reforged.stf.frame.Address;
import ru.instamart.reforged.stf.frame.ProductCard;
import ru.instamart.ui.manager.AppManager;

@Slf4j
public final class Shop implements StfPage {

    private final Button openAddress = new Button(By.xpath("//button[@data-qa='select-button']"));
    private final Element firstProductCard = new Element(By.xpath("//li[@class='product']"));
    private final Element plusFirstItemToCart = new Element(By.xpath("//div[contains(@class, 'add-cart__up')]"));
    private final Element minusFirstItemFromCart = new Element(By.xpath("//div[contains(@class, 'add-cart__down')]"));
    private final Element addFirstItemToFavorite = new Element(By.xpath("//div[contains(@class, 'favorite-button-default')]"));
    private final Element deleteFirstItemFromFavorite = new Element(By.xpath("//div[contains(@class, 'favorite-button--active')]"));

    private final AuthoredHeader header = new AuthoredHeader();
    private final ProductCard productCard = new ProductCard();
    private final Cart cart = new Cart();
    private final Address address = new Address();

    public AuthoredHeader interactHeader() {
        return header;
    }

    public ProductCard interactProductCard() {
        return productCard;
    }

    public Cart interactCart() {
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

    @Step("Нажать на плюс у первого товара")
    public void plusFirstItemToCart() {
        firstProductCard.mouseOver();
        plusFirstItemToCart.click();
    }

    @Step("Нажать на минус у первого товара")
    public void minusFirstItemFromCart() {
        firstProductCard.mouseOver();
        minusFirstItemFromCart.click();
    }

    @Step("Добавить первый товар в избранное")
    public void addFirstItemToFavorite() {
        firstProductCard.mouseOver();
        addFirstItemToFavorite.click();
    }

    @Step("Добавить первый товар в избранное")
    public void deleteFirstItemFromFavorite() {
        firstProductCard.mouseOver();
        deleteFirstItemFromFavorite.click();
    }

    @Step("Открыть карточку первого товара")
    public void openFirstProductCard() {
        firstProductCard.click();
    }

    @Override
    public void goToPage() {
        goToPage(ShopUrl.DEFAULT);
    }

    public void goToPage(final ShopUrl shop) {
        AppManager.getWebDriver().get(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + shop.getUrl());
    }

    @Override
    public String pageUrl() {
        return "";
    }

    @RequiredArgsConstructor
    @Getter
    public enum ShopUrl {
        DEFAULT(""),
        METRO("metro"),
        LENTA("lenta"),
        AUCHAN("auchan");

        private final String url;
    }
}
