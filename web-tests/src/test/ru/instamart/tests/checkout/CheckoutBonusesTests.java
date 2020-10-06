package ru.instamart.tests.checkout;

import instamart.api.common.RestAddresses;
import instamart.core.common.AppManager;
import instamart.ui.modules.Checkout;
import instamart.ui.modules.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestsConfiguration.CheckoutTests.enableBonusesTests;
import static instamart.core.testdata.ui.BonusPrograms.aeroflot;
import static instamart.core.testdata.ui.BonusPrograms.mnogoru;

public class CheckoutBonusesTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        User.Do.loginAs(AppManager.session.admin);

        kraken.apiV2().fillCart(AppManager.session.admin, RestAddresses.Moscow.defaultAddress());
    }

    @Test(  enabled = enableBonusesTests,
            description = "Тест успешного добавления всех доступных бонусных программ в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1701
    )
    public void successAddBonusPrograms() {
        skipTestOnTenant("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();
        Checkout.Bonuses.deleteAll();

        Checkout.Bonuses.add(mnogoru());

        softAssert.assertTrue(
                kraken.detect().isBonusAdded(mnogoru()),
                    failMessage("Не добавляется бонусная программа " + mnogoru().getName() + " в чекауте")
        );

        softAssert.assertTrue(
                kraken.detect().isBonusActive(mnogoru()),
                    failMessage("Бонусная программа " + mnogoru().getName() + " неактивна после добавления в чекауте")
        );

        Checkout.Bonuses.add(aeroflot());

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

    @Test(  enabled = enableBonusesTests,
            description = "Тест выбора добавленных бонусных программ в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1702
    )
    public void successSelectBonusPrograms() {
        skipTestOnTenant("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();
        Checkout.Bonuses.deleteAll();

        Checkout.Bonuses.add(mnogoru());
        Checkout.Bonuses.add(aeroflot());

        Checkout.Bonuses.select(mnogoru());

        softAssert.assertTrue(
                kraken.detect().isBonusActive(mnogoru()),
                    "Не выбирается бонусная программа " + mnogoru().getName() + " в чекауте\n");

        Checkout.Bonuses.select(aeroflot());

        softAssert.assertTrue(
                kraken.detect().isBonusActive(aeroflot()),
                    "Не выбирается бонусная программа " + aeroflot().getName() + " в чекауте\n");

        softAssert.assertAll();
    }

    @Test(  enabled = enableBonusesTests,
            description = "Тест удаления всех бонусных программ в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 1703
    )
    public void successDeleteBonusPrograms() {
        skipTestOnTenant("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();
        Checkout.Bonuses.deleteAll();

        Checkout.Bonuses.add(mnogoru());
        Checkout.Bonuses.delete(mnogoru());

        softAssert.assertFalse(
                kraken.detect().isBonusAdded(mnogoru()),
                    "Не удаляется бонусная программа " + mnogoru().getName() + " в чекауте");

        Checkout.Bonuses.add(aeroflot());
        Checkout.Bonuses.delete(aeroflot());

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
