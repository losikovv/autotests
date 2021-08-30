package ru.instamart.test.reforged.landings;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Проверка лендингов")
public final class DriversHiringLandingTests extends BaseTest {

    @CaseId(1686)
    @Test(
            description = "Тест лендинга найма водителей Сбермаркета",
            groups = {"testing","sbermarket-Ui-smoke"}
    )
    public void successValidateSbermarketDriversHiringLanding() {
        driversHiring().goToPage();
        driversHiring().checkPageIsAvailable();
        driversHiring().checkSubmitButton();
        driversHiring().checkCitySelector();
        driversHiring().checkCountrySelector();
        driversHiring().checkPhoneField();
        driversHiring().checkNameField();
        driversHiring().checkHowToJoin();
        driversHiring().checkWhatToDo();
        driversHiring().checkWorkConditions();
    }
}
