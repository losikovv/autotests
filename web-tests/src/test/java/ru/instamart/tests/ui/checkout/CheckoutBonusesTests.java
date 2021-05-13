package ru.instamart.tests.ui.checkout;

import ru.instamart.api.common.RestAddresses;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.module.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;
import ru.instamart.ui.module.checkout.BonusesActions;

import static ru.instamart.ui.module.testdata.BonusPrograms.aeroflot;
import static ru.instamart.ui.module.testdata.BonusPrograms.mnogoru;

public class CheckoutBonusesTests extends TestBase {
    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.apiV2().fillCart(UserManager.getDefaultAdmin(), RestAddresses.Moscow.defaultAddress());
    }

    @Test(  description = "Тест успешного добавления всех доступных бонусных программ в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successAddBonusPrograms() {
        skipTestOnTenant("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();
        BonusesActions.deleteAll();

        BonusesActions.add(mnogoru());

        softAssert.assertTrue(
                kraken.detect().isBonusAdded(mnogoru()),
                    failMessage("Не добавляется бонусная программа " + mnogoru().getName() + " в чекауте")
        );

        softAssert.assertTrue(
                kraken.detect().isBonusActive(mnogoru()),
                    failMessage("Бонусная программа " + mnogoru().getName() + " неактивна после добавления в чекауте")
        );

        BonusesActions.add(aeroflot());

        softAssert.assertTrue(
                kraken.detect().isBonusAdded(aeroflot()),
                    failMessage("Не добавляется бонусная программа " + aeroflot().getName() + " в чекауте")
        );

        softAssert.assertTrue(
                kraken.detect().isBonusActive(aeroflot()),
                    failMessage("Бонусная программа " + aeroflot().getName() + " неактивна после добавления в чекауте")
        );

        softAssert.assertAll();
    }

    @Test(  description = "Тест выбора добавленных бонусных программ в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successSelectBonusPrograms() {
        skipTestOnTenant("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();
        BonusesActions.deleteAll();

        BonusesActions.add(mnogoru());
        BonusesActions.add(aeroflot());

        BonusesActions.select(mnogoru());

        softAssert.assertTrue(
                kraken.detect().isBonusActive(mnogoru()),
                    "Не выбирается бонусная программа " + mnogoru().getName() + " в чекауте\n");

        BonusesActions.select(aeroflot());

        softAssert.assertTrue(
                kraken.detect().isBonusActive(aeroflot()),
                    "Не выбирается бонусная программа " + aeroflot().getName() + " в чекауте\n");

        softAssert.assertAll();
    }

    @Test(  description = "Тест удаления всех бонусных программ в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successDeleteBonusPrograms() {
        skipTestOnTenant("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();
        BonusesActions.deleteAll();

        BonusesActions.add(mnogoru());
        BonusesActions.delete(mnogoru());

        softAssert.assertFalse(
                kraken.detect().isBonusAdded(mnogoru()),
                    "Не удаляется бонусная программа " + mnogoru().getName() + " в чекауте");

        BonusesActions.add(aeroflot());
        BonusesActions.delete(aeroflot());

        softAssert.assertFalse(
                kraken.detect().isBonusAdded(aeroflot()),
                    "Не удаляется бонусная программа " + aeroflot().getName() + " в чекауте");

        softAssert.assertAll();
    }

    // TODO расширить тесты бонусных программ - ATST-237

        // TODO public void noAddBonusProgramOnCancel() {}

        // TODO public void noAddBonusProgramOnModalClose() {}

        // TODO public void noAddBonusProgramWithEmptyCardNumber() {}

        // TODO public void noAddBonusProgramWithWrongCardNumber() {}

        // TODO public void successEditBonusProgram() {}

        // TODO public void noEditBonusProgramOnCancel() {}

        // TODO public void noEditBonusProgramOnModalClose() {}

        // TODO public void noEditBonusProgramWithSameCardNumber() {}

        // TODO public void noEditBonusProgramWithEmptyCardNumber() {}

        // TODO public void noDeleteBonusProgramOnCancel() {}

        // TODO public void noDeleteBonusProgramOnModalClose() {}
}
