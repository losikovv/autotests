package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import ru.instamart.autotests.models.EnvironmentData;

public class HelperBase {
    WebDriver driver;
    ApplicationManager kraken;
    public String baseUrl;
    public String fullBaseUrl;
    public String adminUrl;
    private boolean acceptNextAlert = true;

    HelperBase(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        this.driver = driver;
        this.baseUrl = environment.getBaseURL(false);
        this.fullBaseUrl = environment.getBaseURL(true);
        this.adminUrl = environment.getAdminURL();
        this.kraken = app;
    }
    //private final Wait<WebDriver> wait = new WebDriverWait(driver, 5).ignoring(StaleElementReferenceException.class, ElementNotVisibleException.class);

    //public final Wait<WebDriver> waitFluently = new FluentWait<WebDriver>(driver).withMessage("Element was not found").withTimeout(10, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS);


    /**
     * Отправить сообщение в консоль
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Обработать алерт в зависимости от настройки acceptNextAlert
     */
    void handleAlert() {
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

    /**
     * Удалить все куки
     */
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    /**
     * Округлить цену до целого числа, отбросив копейки, пробелы и знак рубля
     */
    int round(String price) {
        if (price == null) {
            return 0;
        } else {
            return Integer.parseInt(((price).substring(0, (price.length() - 5))).replaceAll("\\s", ""));
        }
    }

    /**
     * Выбрать 10-значный номер телефона из строки, отбросив скобки и +7
     */
    String strip(String phoneNumber) {
        String phone = phoneNumber.replaceAll("\\D", "");
        return phone.substring(phone.length()-10);
    }
}
