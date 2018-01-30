package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;

// хелпер глобальной навигации по админке
public class AdminNavHelper extends HelperBase {
    public AdminNavHelper(WebDriver driver) {
        super(driver);
    }

    // переход в админку
    public void goToAdmin(){
        driver.get(baseUrl+"admin");
    }

    // переход в раздел ORDERS
    public void goToAdminShipments(){
        driver.get(baseUrl+"admin/shipments");
    }

    // переход в раздел LOGISTICS
    public void goToAdminLogistics(){
        driver.get(baseUrl+"admin/logistics");
    }

    // переход в раздел PRODUCTS
    public void goToAdminProducts(){
        driver.get(baseUrl+"admin/products");
    }

    // переход в раздел REPORTS
    public void goToAdminReports(){
        driver.get(baseUrl+"admin/reports");
    }

    // переход в раздел CONFIGURATION
    public void goToAdminConfiguration(){
        driver.get(baseUrl+"admin/general_settings/edit");
    }

    // переход в раздел MARKETING
    public void goToAdminMarketing(){
        driver.get(baseUrl+"/admin/promo_cards");
    }

    // переход в раздел USERS
    public void goToAdminUsers(){
        driver.get(baseUrl+"/admin/users");
    }

    // переход в раздел PAGES
    public void goToAdminPages(){
        driver.get(baseUrl+"/admin/pages");
    }
}
