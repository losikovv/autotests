package ru.instamart.ui.checkpoints.order;

import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.common.pagesdata.ElementData;
import ru.instamart.ui.common.pagesdata.PaymentTypeData;
import ru.instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import static ru.instamart.ui.modules.Base.kraken;

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
//            kraken.await().simply(1);
            checkOrderDocumentIsDownloadable("Счет");
//            kraken.await().simply(1);
            checkOrderDocumentIsDownloadable("Счет-фактура");
//            kraken.await().simply(1);
            checkOrderDocumentIsDownloadable("Товарная накладная");
        } else {
            checkOrderDocumentIsDownloadable("Универсальный передаточный документ");
//            kraken.await().simply(1);
            checkOrderDocumentIsDownloadable("Счет-фактура");
//            kraken.await().simply(1);
            checkOrderDocumentIsDownloadable("Товарная накладная");
//            kraken.await().simply(1);
        }
    }
    @Step("Проверяем, что документ с заказом может быть загружен")
    public void checkOrderDocumentIsDownloadable(String docname) {
        ElementData docLink = Elements.UserProfile.OrderDetailsPage.document(docname);
        log.info("> скачиваем: {}", docname);
        if (kraken.detect().isElementPresent(docLink)) {
            kraken.perform().click(docLink);
        } else
            throw new AssertionError("Документ \""
                    + docname + "\" недоступен для скачивания\nна странице " + kraken.grab().currentURL());
    }

    @Step("Проверка успешного создания заказа")
    public void checkOrderSuccessCreation(){
        log.info("> проверка успешного создания заказа");
        Assert.assertTrue(kraken.detect().isOrderPlaced(),
                "Не удалось оформить заказ\n");
    }

    @Step("Проверка метода оплаты")
    public void checkPaymentMethod(PaymentTypeData PaymentType){
        log.info("> проверка корректного метода оплаты");
        Assert.assertEquals(
                kraken.grab().shipmentPayment(),
                PaymentType.getDescription(),
                failMessage("Способ оплаты в деталях заказа не совпадает с выбранным во время оформления"));
    }
}
