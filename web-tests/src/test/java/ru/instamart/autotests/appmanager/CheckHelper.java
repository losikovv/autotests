package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.EnvironmentData;

public class CheckHelper extends HelperBase {

    CheckHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /** Проверка скачивания документации на странице деталей заказа */
    public void orderDocuments() {
        String paymentType = kraken.grab().shipmentPayment();
        orderDocuments(paymentType);
    }

    private void orderDocuments(String paymentType) {
        switch (paymentType) {
            case "Переводом":
                orderDocument("Универсальный передаточный документ");
                kraken.await().simply(1);
                orderDocument("Счет");
                kraken.await().simply(1);
                orderDocument("Счет-фактура");
                kraken.await().simply(1);
                orderDocument("Товарная накладная");
                break;
            default:
                orderDocument("Универсальный передаточный документ");
                kraken.await().simply(1);
                orderDocument("Счет-фактура");
                kraken.await().simply(1);
                orderDocument("Товарная накладная");
                kraken.await().simply(1);
                break;
        }
    }

    private void orderDocument(String docname) {
        ElementData docLink = Elements.Site.UserProfile.OrderDetailsPage.document(docname);
        message("Скачиваем : " + docname);
        if (kraken.detect().isElementPresent(docLink)) {
            kraken.perform().click(docLink);
        } else
            throw new AssertionError("Документ \"" + docname + "\" недоступен для скачивания\nна странице " + kraken.grab().currentURL());
    }

    /** Проверка наличия элемента на странице */
    public void elementPresence(ElementData element) throws AssertionError{
        if (kraken.detect().isElementPresent(element)) {
            debugMessage("На " + kraken.grab().currentURL() + " есть " + element.getDescription());
        } else {
            throw new AssertionError(
                    "Отсутствует " + element.getDescription() + " на странице " + kraken.grab().currentURL());
        }
    }
}
