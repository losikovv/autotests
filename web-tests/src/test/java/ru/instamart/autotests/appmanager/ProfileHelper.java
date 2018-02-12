package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfileHelper extends HelperBase {
    public ProfileHelper(WebDriver driver) {
        super(driver);
    }



    // хелпер раздела "Профиль"



    // АККАУНТ

    // перейти на страницу Аккаунт
    public void goToAccount() {
        click(By.xpath("//*[@id='wrap']/div/div/div/div[1]/div/div/ul/li[1]'))"));
    }

    // TODO changePassword(String newPassword, String password) - изменение пароля

    // TODO changeEmail(String newEmail, String password) - изменение email

    // TODO changeFIO(String newName, String newFamily) - изменение имени и фамилии



    // ЗАКАЗЫ

    // перейти на страницу Заказы
    public void goToOrders() {
        click(By.xpath("//*[@id='wrap']/div/div/div/div[1]/div/div/ul/li[2]'))"));
    }

    // перейти в детали заказа по позиции в списке
    public void goToOrderPage(int orderPosition) {
        goToOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/a/button"));
    }

    // перейти в детали крайнего заказа
    public void goToLastOrderPage() {
        goToOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/a/button"));
    }

    // повторить заказ по позиции в списке
    public void repeatOrder (int orderPosition){
        goToOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/button"));
    }

    // повторить крайний заказ
    public void repeatLastOrder(){
        goToOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button"));
    }

    // отменить заказ по позиции в списке
    public void cancelOrder (int orderPosition){
        goToOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/button[1]"));
    }

    // отменить крайний заказ
    public void cancelLastOrder (){
        goToOrders();
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button[1]"));
    }

    // TODO cancelAllOrders - отменить все незавершенные заказы



    // АДРЕСА

    // перейти на страницу Адреса
    public void goToAddresses() {
        click(By.xpath("//*[@id='wrap']/div/div/div/div[1]/div/div/ul/li[3]'))"));
    }

}