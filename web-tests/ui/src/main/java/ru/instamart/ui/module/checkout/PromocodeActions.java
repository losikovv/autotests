package ru.instamart.ui.module.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.kraken.testdata.pagesdata.PromoData;
import ru.instamart.ui.Elements;
import ru.instamart.ui.config.WaitProperties;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.module.shop.ShippingAddressModal;

public final class PromocodeActions extends Base {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);
    public PromocodeActions(final AppManager kraken) {
        super(kraken);
    }

    @Step("Применяем промокод по акции")
    public static void add(PromoData promo) {
        log.debug("> акция {}", promo.getDescription());
        add(promo.getCode());
    }

    @Step("Применить промокод: {0}")
    public static void add(String promocode) {
//        if (kraken.detect().isPromocodeApplied()) {
//            log.debug("> уже есть применённый промокод, поэтому сначала удаляем его... ");
//            delete();
//        }
        log.debug("> применяем промокод '{}'...", promocode);
        PromocodeActions.Modal.open();
        PromocodeActions.Modal.fill(promocode);
        PromocodeActions.Modal.submit();
        kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                Elements.Checkout.Promocode.deleteButton().getLocator()),
                "Промокод не удаляется", WaitProperties.BASIC_TIMEOUT);
        log.debug("> промокод успешно добавлен к заказу", promocode);
    }

    /** Удалить промокод */
    @Step("Удаляем промокод")
    public static void delete() throws AssertionError {
        log.debug("> удаляем промокод...");
        kraken.perform().click(Elements.Checkout.Promocode.deleteButton());
        kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(
                Elements.Checkout.Promocode.deleteButton().getLocator()),
                "Промокод не удаляется",WaitProperties.BASIC_TIMEOUT);
        log.debug("> промокод успешно удален");
    }

    public static class Modal {

        @Step("Открываем модалку с промокадами")
        public static void open() {
            log.debug("> открываем модалку с промокодами");
            kraken.perform().click(Elements.Checkout.Promocode.addButton());
        }

        @Step("Заполняем модалку с промокадами")
        public static void fill(String promocode) {
            log.debug("> заполняем модалку с промокодами");
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.Checkout.Promocode.Modal.popup().getLocator()),
                    "модалка для введедения промокода не появилась", WaitProperties.BASIC_TIMEOUT);
            kraken.perform().fillField(Elements.Checkout.Promocode.Modal.inputField(), promocode);
        }

        @Step("Нажимаем добавить промокод")
        public static void submit() {
            log.debug("> нажимаем добавить промокод");
            kraken.perform().click(Elements.Checkout.Promocode.Modal.submitButton());
        }

        @Step("Нажимаем кнопку Отмена")
        public static void cancel() {
            log.debug("> отменяем добавление промокода");
            kraken.perform().click(Elements.Checkout.Promocode.Modal.cancelButton());
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(
                    Elements.Checkout.Promocode.Modal.cancelButton().getLocator()),
                    "кнопка Отмена не нажимается",WaitProperties.BASIC_TIMEOUT);
            log.debug("> Ввод промокода отменен");
        }

        @Step("Закрываем модалку добавления промокода")
        public static void close() {
            log.debug("> закрываем модалку добавления промокода");
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                    Elements.Checkout.Promocode.Modal.popup().getLocator()),
                    "попап с промокодом не открывается",WaitProperties.BASIC_TIMEOUT);
            kraken.perform().click(Elements.Checkout.Promocode.Modal.closeButton());
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(
                    Elements.Checkout.Promocode.Modal.popup().getLocator()),
                    "попап с промокодом не закрывается",WaitProperties.BASIC_TIMEOUT);
            log.debug("> модалку добавления промокода закрыта");
        }
    }
}
