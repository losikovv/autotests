package ru.instamart.autotests.tests.checkout;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.appmanager.CheckoutHelper;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.application.BonusPrograms.aeroflot;
import static ru.instamart.autotests.application.BonusPrograms.mnogoru;
import static ru.instamart.autotests.application.Config.TestsConfiguration.CheckoutTests.enableBonusesTests;

public class CheckoutBonusesTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }

    @Test(  enabled = enableBonusesTests,
            description = "Тест успешного добавления всех доступных бонусных программ в чекауте",
            groups = {"acceptance","regression"},
            priority = 1701
    )
    public void successAddBonusPrograms() {
        skipOn("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();
        CheckoutHelper.Bonuses.deleteAll();

        CheckoutHelper.Bonuses.add(mnogoru());

        softAssert.assertTrue(
                kraken.detect().isBonusAdded(mnogoru()),
                    failMessage("Не добавляется бонусная программа " + mnogoru().getName() + " в чекауте")
        );

        softAssert.assertTrue(
                kraken.detect().isBonusActive(mnogoru()),
                    failMessage("Бонусная программа " + mnogoru().getName() + " неактивна после добавления в чекауте")
        );

        CheckoutHelper.Bonuses.add(aeroflot());

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
            groups = {"acceptance","regression"},
            priority = 1702
    )
    public void successSelectBonusPrograms() {
        skipOn("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();
        CheckoutHelper.Bonuses.deleteAll();

        CheckoutHelper.Bonuses.add(mnogoru());
        CheckoutHelper.Bonuses.add(aeroflot());

        CheckoutHelper.Bonuses.select(mnogoru());

        softAssert.assertTrue(
                kraken.detect().isBonusActive(mnogoru()),
                    "Не выбирается бонусная программа " + mnogoru().getName() + " в чекауте\n");

        CheckoutHelper.Bonuses.select(aeroflot());

        softAssert.assertTrue(
                kraken.detect().isBonusActive(aeroflot()),
                    "Не выбирается бонусная программа " + aeroflot().getName() + " в чекауте\n");

        softAssert.assertAll();
    }

    @Test(  enabled = enableBonusesTests,
            description = "Тест удаления всех бонусных программ в чекауте",
            groups = {"acceptance","regression"},
            priority = 1703
    )
    public void successDeleteBonusPrograms() {
        skipOn("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();
        CheckoutHelper.Bonuses.deleteAll();

        CheckoutHelper.Bonuses.add(mnogoru());
        CheckoutHelper.Bonuses.delete(mnogoru());

        softAssert.assertFalse(
                kraken.detect().isBonusAdded(mnogoru()),
                    "Не удаляется бонусная программа " + mnogoru().getName() + " в чекауте");

        CheckoutHelper.Bonuses.add(aeroflot());
        CheckoutHelper.Bonuses.delete(aeroflot());

        softAssert.assertFalse(
                kraken.detect().isBonusAdded(aeroflot()),
                    "Не удаляется бонусная программа " + aeroflot().getName() + " в чекауте");

        softAssert.assertAll();
    }

    // TODO расширить тесты бонусных программ:

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
