package ru.instamart.reforged.stf.drawer;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.stf.component.Button;
import ru.instamart.reforged.stf.component.Element;

public class Cart {

    private final Element cart = new Element(By.xpath("//div[@data-qa='cart']"));//может быть лишним тут
    private static final Button close = new Button(By.xpath("//button[@data-qa='cart_close-button']"));
    private final Button returnToCatalog = new Button(By.xpath("//button[@data-qa='cart_return_to_catalog_button']"));
    private final Element placeholder = new Element(By.xpath("//div[@class='new-cart-empty']"));


    private final Button clearCart = new Button(By.xpath("//button[@data-qa='cart_remove_shipments_button']"));
    private final Element mergeChecker = new Element(By.xpath("//div[@class='cart-retailer__merge-checker']"));//локатор из 2х классов
    private final Button mergeButton = new Button(By.xpath("//button[@data-qa='merge_products_button']"));
    private final Element itemCounter = new Element(By.xpath("//div[@data-qa='line-item-counter']"));
    private final Element minSumAlert = new Element(By.xpath("//div[@class='cart-retailer__alert-message-box']"));
    private final Button increaseCount = new Button(By.xpath("//button[@data-qa='increase-button']"));
    private final Button decreaseCount = new Button(By.xpath("//button[@data-qa='decrease-button']"));
    private final Element item = new Element(By.xpath("//div[@data-qa='line-item']"));
    private final Element openItemCard = new Element(By.xpath("//a[@data-qa='open-button']"));
    private final Button mobileRemoveItem = new Button(By.xpath("//button[@data-qa='remove-button-mobile']"));
    private final Button removeItem = new Button(By.xpath("//button[@data-qa='cart_delete_item_button']"));
    private final Element costSpinner = new Element(By.xpath("//div[@data-qa='line-item']//div[contains(@class,'Spinner')]"));
    private final Element retailRocketBlock = new Element(By.xpath("//div[@data-qa='cart']//div[contains(@class, 'retail-rocket-block')]"));
    private final Button nextRetailSlide = new Button(By.xpath("//button[@aria-label='Next slide']"));
    private final Button prevRetailSlide = new Button(By.xpath("//button[@aria-label='Previous slide']"));

    private final Button submitOrder = new Button(By.xpath("//button[@data-qa='cart_checkout_button']"));

    //todo сделать метод получение текста для елемента

    @Step("Закрыть корзину")
    public void closeCart(){ close.click(); }

    @Step("Вернуться в каталог")
    public void returnToCatalog(){ returnToCatalog.click(); }

    @Step("Очистить корзину")
    public void clearCart(){ clearCart.click(); }

    @Step("Сделать заказ")
    public void submitOrder(){submitOrder.click();}

    @Step("Увеличить кол-во товара")
    public void increaseCount(){
        increaseCount.mouseOver();
        increaseCount.click();
    }

    @Step("Уменьшить кол-во товара")
    public void decreaseCount(){
        decreaseCount.mouseOver();
        decreaseCount.click();
    }

    @Step("Открыть карточку товара")
    public void openItemCard(){openItemCard.click();}

    @Step("Удалить товар")
    public void removeItem(){
        removeItem.mouseOver();
        removeItem.click();
    }

    @Step("Удалить товар")
    public void mobileRemoveItem(){mobileRemoveItem.click();}

    @Step("Следующий слайд в блоке Не забудьте купить")
    public void nextRetailSlide(){nextRetailSlide.click();}

    @Step("Предыдущий слайд в блоке Не забудьте купить")
    public void prevRetailSlide(){prevRetailSlide.click();}

}
