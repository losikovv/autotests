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
                kraken.check().orderDocument("Универсальный передаточный документ");
                kraken.check().orderDocument("Счет");
                kraken.check().orderDocument("Счет-фактура");
                kraken.check().orderDocument("Товарная накладная");
                break;
            default:
                kraken.check().orderDocument("Универсальный передаточный документ");
                kraken.check().orderDocument("Счет-фактура");
                kraken.check().orderDocument("Товарная накладная");
                break;
        }
    }

    private void orderDocument(String docname) {
        ElementData docLink = Elements.Site.UserProfile.OrderDetailsPage.document(docname);
        printMessage("Скачиваем : " + docname);
        if (kraken.detect().isElementPresent(docLink)) {
            kraken.perform().click(docLink);
        } else
            throw new AssertionError("Документ \"" + docname + "\" недоступен на странице " + kraken.grab().currentURL());
    }
}
