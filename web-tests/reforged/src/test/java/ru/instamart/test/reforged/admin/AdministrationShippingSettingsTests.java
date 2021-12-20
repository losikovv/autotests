package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

//TODO: Для корректной работы нужны методы в хелпере для удаления правил
@Epic("Админка STF")
@Feature("Настройки")
public final class AdministrationShippingSettingsTests extends BaseTest {

    @BeforeMethod()
    public void beforeMethod() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());
    }

    @CaseId(527)
    @Test(description = "При клике на 'Калькулятор цены' отображается выпадающий список с типами", groups = {"acceptance", "regression"})
    public void testPriceCalculator() {
        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToMarketingSelectCalculator();
        shippingMethod().checkValueInSelector("Фиксированная цена");
        shippingMethod().checkValueInSelector("Цена с учетом сложности");
    }

    @CaseId(528)
    @Test(description = "Выбор калькулятора 'Фиксированная цена'", groups = {"acceptance", "regression"})
    public void testSelectFixPriceCalculator() {
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

    @CaseId(511)
    @Test(description = "Создать новый способ доставки", groups = {"acceptance", "regression"})
    public void testCreateNewDelivery() {
        shippingMethod().goToPage();
        shippingMethod().addNewDelivery();

        shippingNewMethod().fillMethodName("Вывоз Алкоголя");
        shippingNewMethod().selectType("Самовывоз");
        shippingNewMethod().addDeliveryCategory("Алкоголь");
        shippingNewMethod().clickToSubmit();
        shippingNewMethod().interactFlashAlert().checkSuccessFlash();
    }

    @Skip
    @CaseId(525)
    @Test(description = "Редактировать способ доставки", groups = {"acceptance", "regression"})
    public void testEditDeliveryMethod() {
        //Одно и тоже с 528, 530
    }

    //Нельзя добавить только маркетинговое правило
    @Skip
    @CaseId(512)
    @Test(description = "Добавить правило маркетинговой стоимости доставки", groups = {"acceptance", "regression"})
    public void testAddMarketingRule() {
        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToAddNewMarketingRule();
        shippingMethod().clickToMarketingSelectRule();
        shippingMethod().clickToMarketingSelectCalculator();
        shippingMethod().selectValue("Фиксированная цена");
        shippingMethod().fillDeliveryPrice("10.00");
        shippingMethod().fillAssemblySurcharge("10");
        shippingMethod().fillBagSurcharge("10");
        shippingMethod().clickToSubmitChanges();
        shippingMethod().interactFlashAlert().checkSuccessFlash();
    }

    //Нельзя добавить только номинальное правило
    @Skip
    @CaseId(513)
    @Test(description = "Добавить правило номинальной стоимости доставки", groups = {"acceptance", "regression"})
    public void testAddNominalRule() {
        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToAddNewNominalRule();
        shippingMethod().clickToNominalSelectRule();
        shippingMethod().clickToNominalSelectCalculator();
        shippingMethod().selectValue("Фиксированная цена");
        shippingMethod().fillDeliveryPrice("10.00");
        shippingMethod().fillAssemblySurcharge("10");
        shippingMethod().fillBagSurcharge("10");
        shippingMethod().clickToSubmitChanges();
        shippingMethod().interactFlashAlert().checkSuccessFlash();
    }

    @CaseId(522)
    @Test(description = "Удалить правило маркетинговой стоимости доставки", groups = {"acceptance", "regression"})
    public void testRemoveMarketingRule() {
        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToAddNewMarketingRule();
        shippingMethod().deleteMarketingRule();
    }

    @CaseId(523)
    @Test(description = "Удалить правило номинальной стоимости доставки", groups = {"acceptance", "regression"})
    public void testRemoveNominalRule() {
        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToAddNewNominalRule();
        shippingMethod().deleteNominalRule();
    }
}
