package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.request.admin.ShippingMethodsRequest.ShippingMethod;
import ru.instamart.api.request.v1.ShippingMethodsV1Request;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.Calculators;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.Rules;
import ru.instamart.api.response.v1.ShippingMethodsResponse.ShippingMethods;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Настройки")
public final class AdministrationShippingSettingsTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    private final Rules firstOrderRule = Rules
            .builder()
            .type(ShippingMethodsV1Request.RulesType.FIRST_ORDER_RULE)
            .build();
    private final Calculators flatCalculator = Calculators
            .builder()
            .type(ShippingMethodsV1Request.CalculatorType.FLAT_CALCULATOR)
            .build();

    private ShippingMethods shippingMethod;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        shippingMethod = helper.getShippingMethod()
                .getShippingMethods()
                .stream()
                .filter(m -> m.getName().equals("Autotest"))
                .findAny().orElseGet(() -> {
                    var method = ShippingMethod.builder().build();
                    helper.createShippingMethod(method);
                    return null;
                });
        Assert.assertNotNull(shippingMethod, "Отсутствует метод доставки Autotest");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        helper.getNominalRule(shippingMethod.getId()).getPricers()
                .forEach(rule -> helper.deleteNominalRule(rule.getId()));
        helper.getMarketingRule(shippingMethod.getId()).getPricers()
                .forEach(rule -> helper.deleteMarketingRule(rule.getId()));

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());
    }

    @CaseId(527)
    @Test(description = "При клике на 'Калькулятор цены' отображается выпадающий список с типами", groups = {"acceptance", "regression"})
    public void testPriceCalculator() {
        helper.createMarketingRule(shippingMethod.getId());

        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToMarketingSelectCalculator();
        shippingMethod().checkValueInSelector("Фиксированная цена");
        shippingMethod().checkValueInSelector("Цена с учетом сложности");
    }

    @CaseId(528)
    @Test(description = "Выбор калькулятора 'Фиксированная цена'", groups = {"acceptance", "regression"})
    public void testSelectFixPriceCalculator() {
        helper.createNominalRule(shippingMethod.getId());

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
        final var marketingPricer = helper.createMarketingRule(shippingMethod.getId()).getPricer();
        helper.createPricerRules(marketingPricer.getId(), firstOrderRule);
        helper.createPricerCalculator(marketingPricer.getId(), flatCalculator);
        final var nominalPricer = helper.createNominalRule(shippingMethod.getId()).getPricer();
        helper.createPricerRules(nominalPricer.getId(), firstOrderRule);
        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToNominalSelectCalculator();
        shippingMethod().selectValue("Цена с учетом сложности");
        shippingMethod().clickToSubmitChanges();
        shippingMethod().fillBasePrice("10");
        shippingMethod().fillBaseCount("11");
        shippingMethod().fillAssemblySurcharge("12");
        shippingMethod().fillBagSurcharge("13");
        shippingMethod().fillBaseWeight("14");
        shippingMethod().fillAddedWeight("15");
        shippingMethod().fillSurchargeWeight("16");
        shippingMethod().fillAddedCount("17");
        shippingMethod().fillSurchargeCount("18");
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
        helper.createMarketingRule(shippingMethod.getId());
        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().deleteMarketingRule();
    }

    @CaseId(523)
    @Test(description = "Удалить правило номинальной стоимости доставки", groups = {"acceptance", "regression"})
    public void testRemoveNominalRule() {
        helper.createNominalRule(shippingMethod.getId());
        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().deleteNominalRule();
    }

    @CaseId(516)
    @Test(description = "При клике на 'Тип правила' отображается выпадающий список с типами", groups = {"acceptance", "regression"})
    public void testPriceRules() {
        helper.createMarketingRule(shippingMethod.getId());

        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToMarketingSelectRule();
        shippingMethod().checkValueInSelector("Первый заказ");
        shippingMethod().checkValueInSelector("Интервал стоимости заказа");
        shippingMethod().checkValueInSelector("Ритейлер");
        shippingMethod().checkValueInSelector("Периодический заказ");
        shippingMethod().checkValueInSelector("Заказ c 5-20 апреля 2021");
    }

    @CaseId(517)
    @Test(description = "Выбор правила 'Интервал стоимости заказа' и установка интервала", groups = {"acceptance", "regression"})
    public void testInterval() {
        final var marketingPricer = helper.createMarketingRule(shippingMethod.getId()).getPricer();
        helper.createPricerCalculator(marketingPricer.getId(), flatCalculator);
        final var nominalPricer = helper.createNominalRule(shippingMethod.getId()).getPricer();
        helper.createPricerRules(nominalPricer.getId(), firstOrderRule);
        helper.createPricerCalculator(nominalPricer.getId(), flatCalculator);

        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToMarketingSelectRule();
        shippingMethod().selectValue("Интервал стоимости заказа");
        shippingMethod().fillFirstMarketingIntervalRule("10");
        shippingMethod().fillSecondMarketingIntervalRule("500");
        shippingMethod().clickToSubmitChanges();
    }

    @CaseId(520)
    @Test(description = "Выбор правила 'Периодический заказ' и заполнение полей с днями и суммой", groups = {"acceptance", "regression"})
    public void testPeriodic() {
        final var marketingPricer = helper.createMarketingRule(shippingMethod.getId()).getPricer();
        helper.createPricerCalculator(marketingPricer.getId(), flatCalculator);
        final var nominalPricer = helper.createNominalRule(shippingMethod.getId()).getPricer();
        helper.createPricerRules(nominalPricer.getId(), firstOrderRule);
        helper.createPricerCalculator(nominalPricer.getId(), flatCalculator);

        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToMarketingSelectRule();
        shippingMethod().selectValue("Периодический заказ");
        shippingMethod().fillDayMarketingPeriodicRule("6");
        shippingMethod().fillMinSumMarketingPeriodicRule("666");
        shippingMethod().clickToSubmitChanges();
    }

    @CaseId(526)
    @Test(description = "Валидация полей интервала стоимости заказа", groups = {"acceptance", "regression"})
    public void testValidateInterval() {
        final var marketingPricer = helper.createMarketingRule(shippingMethod.getId()).getPricer();
        helper.createPricerCalculator(marketingPricer.getId(), flatCalculator);
        final var nominalPricer = helper.createNominalRule(shippingMethod.getId()).getPricer();
        helper.createPricerRules(nominalPricer.getId(), firstOrderRule);
        helper.createPricerCalculator(nominalPricer.getId(), flatCalculator);

        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToMarketingSelectRule();
        shippingMethod().selectValue("Интервал стоимости заказа");
        shippingMethod().fillFirstMarketingIntervalRule("2000");
        shippingMethod().fillSecondMarketingIntervalRule("1500");
        shippingMethod().checkIntervalError();

        shippingMethod().fillFirstMarketingIntervalRule("500");
        shippingMethod().checkSubmitButtonActive();

        shippingMethod().fillFirstMarketingIntervalRule("0");
        shippingMethod().checkSubmitButtonActive();
    }
}
