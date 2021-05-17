package ru.instamart.ui.helper;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.ui.data.pagesdata.ElementData;
import ru.instamart.ui.data.pagesdata.JuridicalData;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.Elements;

@Slf4j
public final class JuridicalHelper extends Base {

    public JuridicalHelper(final AppManager kraken) {
        super(kraken);
    }

    @Step("Удаляем всех юр. лиц, кроме одного")
    public static void deleteAllExceptOneJuridical() {
        if (kraken.detect().isSecondJuridicalEntered()) {
            deleteJuridical();
            deleteAllExceptOneJuridical();
        }
    }

    @Step("Удаляем юр. лицо")
    public static void deleteJuridical() {
        kraken.perform().click(Elements.Checkout.changeJuridicalButton());
        log.info("> удаляем данные юр. лица {}, ИНН: {}",
                kraken.grab().value(Elements.Checkout.JuridicalModal.nameField()),
                kraken.grab().value(Elements.Checkout.JuridicalModal.innField())
        );
        kraken.perform().click(Elements.Checkout.JuridicalModal.deleteButton());
//        kraken.await().implicitly(1); // Ожидание удаления юрлица
    }

    @Step("Добавляем новое юр. лицо")
    public static void addNewJuridical(JuridicalData juridicalData) {
        log.info("> добавляем данные юр. лица {}, ИНН: {}", juridicalData.getJuridicalName(), juridicalData.getInn());
        if (kraken.detect().isElementDisplayed(Elements.Checkout.addJuridicalButton())) {
            kraken.perform().click(Elements.Checkout.addJuridicalButton());
            fillJuridicalDetails(juridicalData);
            kraken.perform().click(Elements.Checkout.JuridicalModal.confirmButton());
//            kraken.await().implicitly(1); // Ожидание добавления нового юрлица
        } else {
            fillJuridicalDetails(juridicalData);
        }
    }

    @Step("Заполняем данные юр.лица")
    public static void fillJuridicalDetails(JuridicalData companyRequisites) {
        log.info("> заполняем поля с данными о юр. лице");
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.innField(), companyRequisites.getInn());
//        WaitingHelper.simply(1); // Ожидание подгрузки юрлица по ИНН
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.nameField(), companyRequisites.getJuridicalName());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.addressField(), companyRequisites.getJuridicalAddress());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.kppField(), companyRequisites.getKpp());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.operatingAccountField(), companyRequisites.getAccountNumber());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.bikField(), companyRequisites.getBik());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.bankField(), companyRequisites.getBankName());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.correspondentAccountField(), companyRequisites.getCorrespondentAccountNumber());
    }

    @Step("Выбираем юр. лицо")
    public static void selectJuridical(JuridicalData juridicalData) {
        ElementData title = Elements.Checkout.juridicalTitle(juridicalData);
        if (kraken.detect().isElementDisplayed(title)) {
            log.info("> выбираем данные юр. лица {}", kraken.grab().text(title));
            kraken.perform().click(title);
//            kraken.await().implicitly(1); // Ожидание применения выбранного юрлица
        } else if (kraken.detect().isJuridicalEntered()) {
            changeJuridical(juridicalData);
        } else {
            addNewJuridical(juridicalData);
        }
    }

    @Step("Меняем данные о юр. лице")
    public static void changeJuridical(JuridicalData juridicalData) {
        kraken.perform().click(Elements.Checkout.changeJuridicalButton());
        log.info("> меняем данные юр. лица С : {}, ИНН: {} НА: {}, ИНН: {}",
                kraken.grab().value(Elements.Checkout.JuridicalModal.nameField()),
                kraken.grab().value(Elements.Checkout.JuridicalModal.innField()),
                juridicalData.getJuridicalName(),
                juridicalData.getInn());
        fillJuridicalDetails(juridicalData);
        kraken.perform().click(Elements.Checkout.JuridicalModal.confirmButton());
//        kraken.await().implicitly(1); // Ожидание сохранения изменений юрлица
    }
}
