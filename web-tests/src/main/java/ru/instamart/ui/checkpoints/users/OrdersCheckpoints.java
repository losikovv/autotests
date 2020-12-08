package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.pagesdata.ElementData;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static instamart.ui.modules.Base.kraken;

public class OrdersCheckpoints extends BaseUICheckpoints {

    /** Проверка скачивания документации на странице деталей заказа */
    @Step("Проверка скачивания документации на странице деталей заказа")
    public void checkOrderDocumentsAreDownloadable() {
        verboseMessage("> проверка скачивания документации на странице деталей заказа");
        String paymentType = kraken.grab().shipmentPayment();
        orderDocuments(paymentType);
        verboseMessage("✓ Успешно");
    }

    private void orderDocuments(String paymentType) {
        switch (paymentType) {
            case "Переводом":
                checkOrderDocumentIsDownloadable("Универсальный передаточный документ");
                kraken.await().simply(1);
                checkOrderDocumentIsDownloadable("Счет");
                kraken.await().simply(1);
                checkOrderDocumentIsDownloadable("Счет-фактура");
                kraken.await().simply(1);
                checkOrderDocumentIsDownloadable("Товарная накладная");
                break;
            default:
                checkOrderDocumentIsDownloadable("Универсальный передаточный документ");
                kraken.await().simply(1);
                checkOrderDocumentIsDownloadable("Счет-фактура");
                kraken.await().simply(1);
                checkOrderDocumentIsDownloadable("Товарная накладная");
                kraken.await().simply(1);
                break;
        }
    }

    public void checkOrderDocumentIsDownloadable(String docname) {
        ElementData docLink = Elements.UserProfile.OrderDetailsPage.document(docname);
        verboseMessage("Скачиваем : " + docname);
        if (kraken.detect().isElementPresent(docLink)) {
            kraken.perform().click(docLink);
        } else
            throw new AssertionError("Документ \""
                    + docname + "\" недоступен для скачивания\nна странице " + kraken.grab().currentURL());
    }
}
