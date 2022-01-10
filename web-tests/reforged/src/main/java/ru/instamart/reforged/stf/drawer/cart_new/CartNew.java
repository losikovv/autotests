package ru.instamart.reforged.stf.drawer.cart_new;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Корзина. Основные элементы
 */
public class CartNew {

    public RemoveRetailerConfirmPopup removeRetailerConfirmPopup;
    public ItemAdvancedInfoPopup itemAdvancedInfoPopup;

    Element cartContainer = new Element(By.xpath("//div[@data-qa='cart']")); //Для контекстного поиска, например, чтобы однозначно определить блок популярных товаров
    Element title = new Element(By.xpath("//div[@class='new-cart__title']"), "заголовок Корзины");
    Button closeButton = new Button(By.xpath("//button[@data-qa='cart_close-button']"), "кнопка Закрыть");
    Element cartIsEmptyPlaceholder = new Element(By.xpath("//div[@class='new-cart-empty']"), "плейсхолдер пустой корзины");
    Button backToCatalogButton = new Button(By.xpath("//button[@data-qa='cart_return_to_catalog_button']"), "кнопка Вернуться в каталог");
    Button confirmOrderButton = new Button(By.xpath("//button[@data-qa='cart_checkout_button']"), "кнопка Сделать заказ");
    Element orderAmount = new Element(By.xpath("//div[@class='cart-checkout-link__well']"), "Лейбл суммы заказа");

    ElementCollection retailers = new ElementCollection(By.xpath("//div[@class='cart-retailer']"));
    String patternRetailerByName = ".//div[@class='cart-retailer'][.//div[@class='cart-retailer-details__name'][contains(.,'%s')]]";

    public CartNew(){
        removeRetailerConfirmPopup = new RemoveRetailerConfirmPopup();
        itemAdvancedInfoPopup = new ItemAdvancedInfoPopup();
    }

    public boolean isDisplayed(){
        return cartContainer.isDisplayed();
    }

    public void closeCart(){
        closeButton.click();
        Kraken.waitAction().shouldNotBeVisible(cartContainer);
    }

    public boolean isCartEmpty(){
        return cartIsEmptyPlaceholder.isDisplayed();
    }

    private List<Retailer> getAllRetailers() {
        return retailers.getElements().stream().map(el -> new Retailer(new Element(el))).collect(Collectors.toList());
    }

    public Retailer getRetailerByName(String retailerName) {
        return new Retailer(new Element(cartContainer.getElement().findElement(By.xpath(String.format(patternRetailerByName, retailerName)))));
    }

    public List<String> getAllRetailerNames() {
        return getAllRetailers().stream().map(Retailer::getName).collect(Collectors.toList());
    }

}
