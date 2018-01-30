package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;

// помощник навигации по админке
public class AdminHelper extends HelperBase {
    public AdminHelper(WebDriver driver) {
        super(driver);
    }

    // переход в /admin
    public void goToAdmin(){
        driver.get(baseUrl+"admin/shipments");
    }

    // переход в /admin/shipments
    public void goToShipments(){
        driver.get(baseUrl+"admin/shipments");
    }

    // переход в /admin/users
    public void goToUsers(){
        driver.get(baseUrl+"admin/users");
    }

}
