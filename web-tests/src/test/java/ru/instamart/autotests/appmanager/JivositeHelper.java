package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.EnvironmentData;


public class JivositeHelper extends HelperBase {

    private ApplicationManager kraken;

    JivositeHelper (WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /** Открыть чат jivosite */
    public void open() {
        if(!kraken.detect().isJivositeChatAvailable()) {
            kraken.perform().printMessage("> разворачиваем виджет Jivosite");
            kraken.perform().click(Elements.Site.Jivosite.openButton());
            kraken.perform().waitingFor(1); // Ожидание разворачивания виджета Jivosite
        } else {
            kraken.perform().printMessage("> виджет Jivosite развернут");
        }
    }

    /** Свернуть чат jivosite */
    public void close() {
        if(kraken.detect().isJivositeChatAvailable()) {
            kraken.perform().printMessage("> сворачиваем виджет Jivosite");
            kraken.perform().click(Elements.Site.Jivosite.closeButton());
            kraken.perform().waitingFor(1); // Ожидание сворачивания виджета Jivosite
        } else {
            kraken.perform().printMessage("> виджет Jivosite свернут");
        }
    }

    /** Отправить сообщение в Jivosite */
    public void sendMessage(String text) {
        kraken.perform().printMessage("Jivosite");
        kraken.perform().waitingFor(2);
        open();
        kraken.perform().printMessage("> отправляем сообщение: " + text);
        kraken.perform().fillField(Elements.Site.Jivosite.messageField(), text);
        kraken.perform().click(Elements.Site.Jivosite.sendMessageButton());
        kraken.perform().waitingFor(2); // Ожидание отправки сообщения в Jivosite
    }
}