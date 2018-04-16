package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



    // Profile helper
    // Contains methods for all operations within user profile section



public class ProfileHelper extends HelperBase {

    public ProfileHelper(WebDriver driver){
        super(driver);
    }



    // ======= Account =======

    // перейти на страницу Аккаунт
    public void goToAccount(){
        printMessage("Going to User profile > Account");
        click(By.xpath("//*[@id='wrap']/div/div/div/div[1]/div/div/ul/li[1]'))"));
    }

    // TODO changePassword(String newPassword, String password) - изменение пароля

    // TODO changeEmail(String newEmail, String password) - изменение email

    // TODO changeFIO(String newName, String newFamily) - изменение имени и фамилии



    // ======= Orders =======

    // перейти на страницу Заказы
    public void getOrders(){
        printMessage("GET /user/orders");
        getUrl(baseUrl + "user/orders");
    }

    // перейти в детали крайнего заказа
    public void goToLastOrderPage(){
        getOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/a/button"));
    }

    // перейти в детали заказа по позиции в списке
    public void goToOrderPage(int orderPosition){
        getOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/a/button"));
    }

    // повторить крайний заказ
    public void repeatLastOrder(){
        printMessage("Repeating the last order");
        getOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button"));
    }

    // повторить заказ по позиции в списке
    public void repeatOrder (int orderPosition){
        printMessage("Repeating order by position " + orderPosition);
        getOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/button"));
    }

    // отменить крайний заказ
    public void cancelLastOrder (){
        printMessage("Cancelling the last order");
        getOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button[1]"));
    }

    // отменить заказ по позиции в списке
    public void cancelOrder (int orderPosition){
        printMessage("Cancelling order by position " + orderPosition);
        getOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/button[1]"));
    }

    public boolean isOrderActive(){
        if(isElementPresent(By.xpath(""))){
            return true;
        } else {
            return false;
        }
    }

    // TODO
    public boolean isOrderDelivered(){
        if(isElementPresent(By.xpath("// TODO "))){
            return true;
        } else {
            return false;
        }
    }

    // TODO
    public boolean isOrderCanceled(){
        if(isElementPresent(By.xpath("// TODO "))){
            return true;
        } else {
            return false;
        }
    }

    // TODO cancelAllOrders - отменить все незавершенные заказы



    // ======= Addresses =======

    // перейти на страницу Адреса
    public void goToAddresses(){
        printMessage("Going to User profile > Addresses");
        click(By.xpath("//*[@id='wrap']/div/div/div/div[1]/div/div/ul/li[3]'))"));
    }

}