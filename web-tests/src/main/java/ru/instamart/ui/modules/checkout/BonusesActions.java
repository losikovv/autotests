package ru.instamart.ui.modules.checkout;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.core.common.AppManager;
import ru.instamart.core.testdata.ui.BonusPrograms;
import ru.instamart.ui.common.pagesdata.LoyaltiesData;
import ru.instamart.ui.helpers.WaitingHelper;
import ru.instamart.ui.modules.Base;
import ru.instamart.ui.modules.shop.ShippingAddressModal;
import ru.instamart.ui.objectsmap.Elements;

public final class BonusesActions extends Base {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);

    public BonusesActions(final AppManager kraken) {
        super(kraken);
    }

    /**
     * Добавление бонусной программы
     */
    @Step("Добавление бонусной программы")
    public static void add(LoyaltiesData bonus) {
        log.info("> добавляем бонусную программу {}", bonus.getName());
        kraken.perform().click(Elements.Checkout.Bonuses.Program.addButton(bonus.getName()));
        kraken.perform().fillField(Elements.Checkout.Bonuses.Modal.inputField(), bonus.getCardNumber());
        kraken.perform().click(Elements.Checkout.Bonuses.Modal.saveButton());
//        kraken.await().implicitly(1); // Ожидание добавления бонусной программы в чекауте
        // TODO добавить fluent-ожидание
    }

    @Step("Редактируем бонусную программу")
    public static void edit(LoyaltiesData bonus) throws AssertionError {
        if (kraken.detect().isBonusAdded(bonus)) {
            log.info("> редактируем бонусную программу {}", bonus.getName());
            kraken.perform().click(Elements.Checkout.Bonuses.Program.addButton(bonus.getName()));
            kraken.perform().fillField(Elements.Checkout.Bonuses.Modal.inputField(), bonus.getCardNumber());
            kraken.perform().click(Elements.Checkout.Bonuses.Modal.saveButton());
            // TODO добавить fluent-ожидание
        } else {
            throw new AssertionError("Невозможно отредактировать бонусную программу " + bonus.getName() + ", так как она не добавлена");
        }
    }

    @Step("Выбираем бонусную программу в списке добавленных: {0}")
    public static void select(LoyaltiesData bonus) throws AssertionError {
        if (kraken.detect().isBonusAdded(bonus)) {
            log.info("> выбираем бонусную программу {}", bonus.getName());
            kraken.perform().click(Elements.Checkout.Bonuses.Program.snippet(bonus.getName()));
//            WaitingHelper.simply(1); // Ожидание вы б] о ра бонусной программы в чекауте
        } else {
            throw new AssertionError("Невозможно выбрать бонусную программу " + bonus.getName() + ", так как она не добавлена");
        }
    }

    @Step("Удаляем бонусную программу: {0}")
    public static void delete(LoyaltiesData bonus) throws AssertionError {
        if (kraken.detect().isBonusAdded(bonus)) {
            log.info("> удаляем бонусную программу {}", bonus.getName());
            kraken.perform().click(Elements.Checkout.Bonuses.Program.editButton(bonus.getName()));
            kraken.perform().click(Elements.Checkout.Bonuses.Modal.deleteButton());
//            kraken.await().implicitly(1); // Ожидание удаления программы лояльности в чекауте
            // TODO добавить fluent-ожидание
        } else {
            throw new AssertionError("Невозможно удалить бонусную программу " + bonus.getName() + ", так как она не добавлена");
        }
    }

    @Step("Удаляем все бонусные программы")
    public static void deleteAll() {
        log.info("> удаляем все бонусные программы в чекауте");
        if (kraken.detect().isBonusAdded(BonusPrograms.mnogoru())) {
            delete(BonusPrograms.mnogoru());
        }
        if (kraken.detect().isBonusAdded(BonusPrograms.aeroflot())) {
            delete(BonusPrograms.aeroflot());
        }
    }

    /**
     * Операции c модалкой бонусных мрограмм
     */
    public static class Modal {

        //TODO public void open() {bonus}

        //TODO public void fill() {}

        //TODO public void submit() {}

        //TODO public void cancel() {}

        /**
         * Закрыть модалку
         */
        @Step("Закрываем модалку бонусных программ")
        public void close() {
            if (kraken.detect().isElementPresent(Elements.Checkout.Bonuses.Modal.popup())) {
                kraken.perform().click(Elements.Checkout.Bonuses.Modal.closeButton());
            } else {
                log.info("> пропускаем закрытие бонусной модалки, так как она не открыта");
            }
        }
    }
}
