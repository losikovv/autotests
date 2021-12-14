package ru.instamart.test.reforged.stf.landings;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Проверка лендингов")
public final class DriversHiringLandingTests extends BaseTest {

    @CaseId(1686)
    @Test(description = "Тест лендинга найма водителей Сбермаркета", groups = "regression")
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
