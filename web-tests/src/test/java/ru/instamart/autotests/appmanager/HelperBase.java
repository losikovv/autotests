package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import ru.instamart.autotests.application.Environments;

public class HelperBase {
    WebDriver driver;
    private String environmentName;
    private String host;
    public String baseUrl;
    public String fullBaseUrl;
    private boolean acceptNextAlert = true;

    HelperBase(WebDriver driver, Environments environment) {
        this.driver = driver;
        this.environmentName = environment.getEnvironmentName();
        this.host = environment.getHost();
        this.baseUrl = environment.getBaseURL(false);
        this.fullBaseUrl = environment.getBaseURL(true);
    }

    /** Отправить сообщение в консоль*/
    public void printMessage(String message) {
        System.out.println(message);
    }

    /** Обработать алерт в зависимости от настройки acceptNextAlert */
    void handleAlert(){
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            // DEBUG
            //printMessage("> handling alert [" + alertText + "]");
        } finally {
            acceptNextAlert = true;
        }
    }

    /** Удалить все куки */
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    /** Округлить цену до целого числа, отбросив копейки, пробелы и знак рубля */
    int round(String price) {
        if(price == null) {
            return 0;
        } else {
            return Integer.parseInt(((price).substring(0, (price.length() - 5))).replaceAll("\\s", ""));
        }
    }

    /** Обновить страницу */
    public void refresh() {
        driver.navigate().refresh();
    }

}
