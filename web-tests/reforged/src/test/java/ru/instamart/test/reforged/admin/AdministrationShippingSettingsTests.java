package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.shippingMethod;

//TODO: Для корректной работы нужны методы в хелпере для удаления правил
@Epic("Админка STF")
@Feature("Настройки")
public final class AdministrationShippingSettingsTests extends BaseTest {

    @CaseId(527)
    @Test(description = "При клике на 'Калькулятор цены' отображается выпадающий список с типами", groups = {"acceptance", "regression"})
    public void testPriceCalculator() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToMarketingSelectCalculator();
        shippingMethod().checkValueInSelector("Фиксированная цена");
        shippingMethod().checkValueInSelector("Цена с учетом сложности");
    }

    @CaseId(528)
    @Test(description = "Выбор калькулятора 'Фиксированная цена'", groups = {"acceptance", "regression"})
    public void testSelectFixPriceCalculator() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToNominalSelectCalculator();
        shippingMethod().selectValue("Фиксированная цена");
        shippingMethod().fillDeliveryPrice("10");
        shippingMethod().fillAssemblySurcharge("10");
        shippingMethod().fillBagSurcharge("10");
    }

    @Skip
    @CaseId(529)
    @Test(description = "Валидация полей калькулятора 'Фиксированная цена'", groups = {"acceptance", "regression"})
    public void testValidateFixPriceCalculator() {
        //Одно и тоже с 528
    }

    @CaseId(530)
    @Test(description = "Выбор калькулятора 'Цена с учётом сложности'", groups = {"acceptance", "regression"})
    public void testSelectCalculatorPriceWithDifficulty() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToAddNewNominalRule();
        shippingMethod().clickToNominalSelectRule();
        shippingMethod().selectValue("Первый заказ");
        shippingMethod().clickToNominalSelectCalculator();
        shippingMethod().selectValue("Цена с учетом сложности");
        shippingMethod().clickToSubmitChanges();
        shippingMethod().fillBasePrice("10");
        shippingMethod().fillBaseCount("10");
        shippingMethod().fillAssemblySurcharge("10");
        shippingMethod().fillBagSurcharge("10");
        shippingMethod().fillBaseWeight("10");
        shippingMethod().fillAddedWeight("10");
        shippingMethod().fillSurchargeWeight("10");
        shippingMethod().fillAddedCount("10");
        shippingMethod().fillSurchargeCount("10");
        shippingMethod().clickToSubmitChanges();
    }

    @Skip
    @CaseId(530)
    @Test(description = "Валидация полей калькулятора 'Цена с учётом сложности'", groups = {"acceptance", "regression"})
    public void testValidatePriceWithDifficulty() {
        //Одно и тоже с 529
    }
}
