package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.EnvironmentData;


public class JivositeHelper extends HelperBase {

    JivositeHelper (WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /** Открыть чат jivosite */
    public void open() {
        if(!kraken.detect().isJivositeChatAvailable()) {
            message("> разворачиваем виджет Jivosite");
            kraken.perform().click(Elements.Site.Jivosite.openButton());
            kraken.await().implicitly(1); // Ожидание разворачивания виджета Jivosite
        } else {
            message("> виджет Jivosite развернут");
        }
    }

    /** Свернуть чат jivosite */
    public void close() {
        if(kraken.detect().isJivositeChatAvailable()) {
            message("> сворачиваем виджет Jivosite");
            kraken.perform().click(Elements.Site.Jivosite.closeButton());
            kraken.await().implicitly(1); // Ожидание сворачивания виджета Jivosite
        } else {
            message("> виджет Jivosite свернут");
        }
    }

    /** Отправить сообщение в Jivosite */
    public void sendMessage(String text) {
        message("Jivosite");
        kraken.await().implicitly(2);
        open();
        message("> отправляем сообщение: " + text);
        kraken.perform().fillField(Elements.Site.Jivosite.messageField(), text);
        kraken.perform().click(Elements.Site.Jivosite.sendMessageButton());
        kraken.await().implicitly(2); // Ожидание отправки сообщения в Jivosite
    }
}