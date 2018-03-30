package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.UserData;

import javax.xml.xpath.XPath;

public class ShoppingHelper extends HelperBase {

    public ShoppingHelper(WebDriver driver) {
        super(driver);
    }

    public void catalog(){
        // TODO
    }

    /** Add first line item on the page to the shopping cart
     * Shipping address must be chosen before that */
    public void addFirstItemOnPageToCart(){
        // жмем на сниппет первого товара на странице
        click(By.xpath("//*[@id='home']/div[2]/ul/li[1]/ul/li[1]/a"));
        // переключаемся на модалку карточки товара
        swithchToActiveElement();
        // жмем на кнопку добавления товара
        click(By.xpath("//*[@id='product']/div[2]/div/div[1]/div"));
        // жмем на крестик закрытия карточки товара
        click(By.className("close"));
    }


    /** Find out if the order is canceled or not */
    public boolean orderIsCanceled() {
        String XPATH = "//*[@id='content']/div/table/tbody/tr[3]/td/b";
        // checks status on the order page in admin panel
        if (isElementPresent(By.xpath(XPATH))){
            return getText(By.xpath(XPATH)).equals("ЗАКАЗ ОТМЕНЕН");
        } else {
            return false;
        }
    }

    public void cancelOrderFromAdmin(){
        // click cancel button
        click(By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button"));
        // accept order cancellation alert
        closeAlertAndGetItsText();
        // fill order cancellation reason form
        fillField(By.name("cancellation[reason]"),"Тестовый заказ");
        // click confirmation button
        click(By.xpath("//*[@id='new_cancellation']/fieldset/div[2]/button"));
    }

    public void resumeOrderFromAdmin(){
        // click resume button
        click(By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button"));
        // accept order resume alert
        closeAlertAndGetItsText();
    }

}