package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.pagesdata.ElementData;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static instamart.ui.modules.Base.kraken;

public class OrdersCheckpoints extends BaseUICheckpoints {

    private static final Logger log = LoggerFactory.getLogger(OrdersCheckpoints.class);

    /** Проверка скачивания документации на странице деталей заказа */
    @Step("Проверка скачивания документации на странице деталей заказа")
    public void checkOrderDocumentsAreDownloadable() {
        log.info("> проверка скачивания документации на странице деталей заказа");
        String paymentType = kraken.grab().shipmentPayment();
        orderDocuments(paymentType);
        log.info("✓ Успешно");
    }

    private void orderDocuments(String paymentType) {
        if ("Переводом".equals(paymentType)) {
            checkOrderDocumentIsDownloadable("Универсальный передаточный документ");
            kraken.await().simply(1);
            checkOrderDocumentIsDownloadable("Счет");
            kraken.await().simply(1);
            checkOrderDocumentIsDownloadable("Счет-фактура");
            kraken.await().simply(1);
            checkOrderDocumentIsDownloadable("Товарная накладная");
        } else {
            checkOrderDocumentIsDownloadable("Универсальный передаточный документ");
            kraken.await().simply(1);
            checkOrderDocumentIsDownloadable("Счет-фактура");
            kraken.await().simply(1);
            checkOrderDocumentIsDownloadable("Товарная накладная");
            kraken.await().simply(1);
        }
    }

    public void checkOrderDocumentIsDownloadable(String docname) {
        ElementData docLink = Elements.UserProfile.OrderDetailsPage.document(docname);
        log.info("Скачиваем: {}", docname);
        if (kraken.detect().isElementPresent(docLink)) {
            kraken.perform().click(docLink);
        } else
            throw new AssertionError("Документ \""
                    + docname + "\" недоступен для скачивания\nна странице " + kraken.grab().currentURL());
    }
}
