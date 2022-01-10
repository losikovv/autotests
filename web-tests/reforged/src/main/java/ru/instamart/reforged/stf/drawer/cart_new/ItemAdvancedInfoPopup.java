package ru.instamart.reforged.stf.drawer.cart_new;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.data.ItemData;

/**
 * Попап с расширенной информацией о товаре (появляется при клике на товар)
 */
public class ItemAdvancedInfoPopup {

    //Element container = new Element(By.xpath("//div[@itemscope][.//button[@data-qa='product_cards_close_button']]"));
    Button buttonClose = new Button(By.xpath(".//button[@data-qa='product_cards_close_button']"));
    Element name = new Element(By.xpath("//div[@itemscope][.//button[@data-qa='product_cards_close_button']]//h1[@itemprop='name']"));
    Input countInput = new Input(By.xpath("//div[@itemscope][.//button[@data-qa='product_cards_close_button']]//input"));
    Element countLabel = new Element(By.xpath("//button[@data-qa='addToCart_minus']/following-sibling::div/div[1]"));
    Element totalAmount = new Element(By.xpath("//button[@data-qa='addToCart_minus']/following-sibling::div/div[2]"));
    Button buttonMinus = new Button(By.xpath("//button[@data-qa='addToCart_minus']"));
    Button buttonPlus = new Button(By.xpath("//button[@data-qa='addToCart_plus']"));
    Button buttonAddToFavourite = new Button(By.xpath("//div[@data-qa='addToCart_favorite']"));

    public boolean isDisplayed(){
        return name.isDisplayed();
    }

    @Step("Закрываем попап с расширенной информацией о продукте")
    public void closePopup(){
        buttonClose.click();
    }

    public ItemData getItemData(){
        return new ItemData()
                .setName(getItemName())
                .setCount(getCount())
                .setTotalAmount(getTotalAmount());
    }

    private String getItemName(){
        return name.getText();
    }

    private String getCount(){
        return countLabel.getText();
    }

    private String getTotalAmount(){
        return totalAmount.getText();
    }
}
