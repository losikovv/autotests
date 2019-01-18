package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Environments;
import ru.instamart.autotests.application.Elements;



public class JivositeHelper extends HelperBase {

    private ApplicationManager kraken;

    JivositeHelper (WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /** Открыть чат jivosite */
    public void open() {
        kraken.perform().click(Elements.Site.Jivosite.openJivosite());
    }

    /** Свернуть чат jivosite */
    public void close() {
        kraken.perform().click(Elements.Site.Jivosite.closeJivosite());
    }

    /** Отправить сообщение в Jivosite */
    public void sendMessage() {
        kraken.perform().click(Elements.Site.Jivosite.openJivosite());
        kraken.perform().fillField(Elements.Site.Jivosite.fieldJivosite(), "test");
        kraken.perform().click(Elements.Site.Jivosite.sendMessage());
    }
}