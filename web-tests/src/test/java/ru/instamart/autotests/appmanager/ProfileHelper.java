package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfileHelper extends HelperBase {
    public ProfileHelper(WebDriver driver) {
        super(driver);
    }

    // хелпер раздела "Профиль" на сайте

    // РАЗДЕЛ ЗАКАЗЫ
    // перейти на страницу Аккаунт
    public void goToAccountPage() {
        click(By.xpath("//*[@id='wrap']/div/div/div/div[1]/div/div/ul/li[1]'))"));
    }

    // TODO changePassword(String newPassword, String password) - изменение пароля

    // TODO changeEmail(String newEmail, String password) - изменение email

    // TODO changeFIO(String newName, String newFamily) - изменение имени и фамилии


    // РАЗДЕЛ ЗАКАЗЫ
    // перейти на страницу Заказы
    public void goToOrdersPage() {
        click(By.xpath("//*[@id='wrap']/div/div/div/div[1]/div/div/ul/li[2]'))"));
    }

    // перейти в детали крайнего заказа
    public void goToLastOrderPage() {
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/a/button"));
    }

    // перейти в детали заказа по позиции в списке заказов, начиная сверху
    public void goTotOrderPage(int orderPosition) {
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/a/button"));
    }

    // повторить крайний заказ
    public void repeatLastOrder(){
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button"));
    }

    // повторить заказ по позиции в списке заказов, начиная сверху
    public void repeatOrder (int orderPosition){
        click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[" + orderPosition + "]/div/div/div[1]/div[2]/button"));
    }

    // TODO cancelLastOrder - отменить крайний заказ

    // TODO cancelOrder(int n) - отменить заказ N - нумерация обратная от верха списка

    // TODO cancelAllOrders - отменить все незавершенные заказы

    // РАЗДЕЛ АДРЕСА
    // перейти на страницу Адреса
    public void goToAddressesPage() {
        click(By.xpath("//*[@id='wrap']/div/div/div/div[1]/div/div/ul/li[3]'))"));
    }

}