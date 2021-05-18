package ru.instamart.ui.module.checkout;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.kraken.testdata.pagesdata.PromoData;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.module.shop.ShippingAddressModal;
import ru.instamart.ui.Elements;

public final class PromocodeActions extends Base {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);
    public PromocodeActions(final AppManager kraken) {
        super(kraken);
    }

    @Step("Применяем промокод по акции")
    public static void add(PromoData promo) {
        log.info("> акция {}", promo.getDescription());
        add(promo.getCode());
    }

    @Step("Применить промокод: {0}")
    public static void add(String promocode) {
        if (kraken.detect().isPromocodeApplied()) {
            log.info("> уже есть применённый промокод, поэтому сначала удаляем его... ");
            delete();
        }
        log.info("> применяем промокод '{}'...", promocode);
        PromocodeActions.Modal.open();
        PromocodeActions.Modal.fill(promocode);
        PromocodeActions.Modal.submit();
//        kraken.await().implicitly(1); // Ожидание применения промокода в чекауте
        // TODO добавить fluent-ожидание
    }

    /** Удалить промокод */
    @Step("Удаляем промокод")
    public static void delete() throws AssertionError {
        if (kraken.detect().isPromocodeApplied()) {
            log.info("> удаляем промокод...");
            kraken.perform().click(Elements.Checkout.Promocode.deleteButton());
//            kraken.await().implicitly(1); // Ожидание удаления промокода в чекауте
            // TODO добавить fluent-ожидание
        } else {
            throw new AssertionError("Невозможно удалить промокод, так как он не применен");
        }
    }

    public static class Modal {

        @Step("Открываем модалку с промокадами")
        public static void open() {
            log.info("> открываем модалку с промокодами");
            if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.addButton())) {
                kraken.perform().click(Elements.Checkout.Promocode.addButton());
            } else {
                throw new AssertionError("Невозможно открыть модалку ввода промокода, так как в данный момент применен промокод");
            }
        }

        @Step("Заполняем модалку с промокадами")
        public static void fill(String promocode) {
            log.info("> заполняем модалку с промокодами");
            if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                kraken.perform().fillField(Elements.Checkout.Promocode.Modal.inputField(), promocode);
            } else {
                throw new AssertionError("Невозможно ввести промокод, так как не открыта модалка промокода");
            }
        }

        @Step("Нажимаем добавить промокод")
        public static void submit() {
            log.info("> нажимаем добавить промокод");
            if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                kraken.perform().click(Elements.Checkout.Promocode.Modal.submitButton());
            } else {
                throw new AssertionError("Невозможно нажать кнопку \"Отмена\", так как не открыта модалка промокода");
            }
        }

        @Step("Нажимаем кнопку Отмена")
        public static void cancel() {
            log.info("> отменяем добавление промокода");
            if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                kraken.perform().click(Elements.Checkout.Promocode.Modal.cancelButton());
            } else {
                throw new AssertionError("Невозможно нажать кнопку \"Отмена\", так как не открыта модалка промокода");
            }
        }

        @Step("Закрываем модалку добавления промокода")
        public static void close() {
            log.info("> закрываем модалку добавления промокода");
            if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                kraken.perform().click(Elements.Checkout.Promocode.Modal.closeButton());
            } else {
                log.info("> пропускаем закрытие модалки промокода, так как она не открыта");
            }
        }
    }
}
