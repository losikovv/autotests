package ru.instamart.test.reforged.stf.landings;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.driversHiring;

@Epic("STF UI")
@Feature("Проверка лендингов")
public final class DriversHiringLandingTests {

    @CaseId(1686)
    @Test(description = "Тест лендинга найма водителей Сбермаркета", groups = REGRESSION_STF)
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
