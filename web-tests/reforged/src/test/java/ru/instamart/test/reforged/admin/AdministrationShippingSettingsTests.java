package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v1.ShippingMethodV1;
import ru.instamart.api.request.admin.ShippingMethodsRequest.ShippingMethod;
import ru.instamart.api.request.v1.ShippingMethodsV1Request;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.Calculators;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.Rules;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Настройки")
public final class AdministrationShippingSettingsTests {

    private final ApiHelper helper = new ApiHelper();

    private final Rules firstOrderRule = Rules
            .builder()
            .type(ShippingMethodsV1Request.RulesType.FIRST_ORDER_RULE)
            .build();
    private final Calculators flatCalculator = Calculators
            .builder()
            .type(ShippingMethodsV1Request.CalculatorType.FLAT_CALCULATOR)
            .build();

    private ShippingMethodV1 shippingMethod;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        shippingMethod = helper.getShippingMethod()
                .stream()
                .filter(m -> m.getName().equals("Autotest"))
                .findAny().orElseGet(() -> {
                    var method = ShippingMethod.builder().name("Autotest").kindId(3).build();
                    helper.createShippingMethod(method);
                    return helper
                            .getShippingMethod()
                            .stream()
                            .filter(m -> m.getName().equals("Autotest"))
                            .findAny()
                            .orElse(null);
                });
        Assert.assertNotNull(shippingMethod, "Отсутствует метод доставки Autotest");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        helper.getNominalRule(shippingMethod.getId())
                .forEach(rule -> helper.deleteNominalRule(rule.getId()));
        helper.getMarketingRule(shippingMethod.getId())
                .forEach(rule -> helper.deleteMarketingRule(rule.getId()));

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());
    }

    @CaseId(527)
    @Test(description = "При клике на 'Калькулятор цены' отображается выпадающий список с типами", groups = "regression")
    public void testPriceCalculator() {
        helper.createMarketingRule(shippingMethod.getId());

        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().clickToMarketingSelectCalculator();
        shippingMethod().checkValueInSelector("Фиксированная цена");
        shippingMethod().checkValueInSelector("Цена с учетом сложности");
    }

    @CaseId(528)
    @Test(description = "Выбор калькулятора 'Фиксированная цена'", groups = "regression")
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
    @Test(description = "Валидация полей калькулятора 'Фиксированная цена'", groups = "regression")
    public void testValidateFixPriceCalculator() {
        //Одно и тоже с 528
    }

    @CaseId(530)
    @Test(description = "Выбор калькулятора 'Цена с учётом сложности'", groups = "regression")
    public void testSelectCalculatorPriceWithDifficulty() {
        final var marketingPricer = helper.createMarketingRule(shippingMethod.getId());
        helper.createPricerRules(marketingPricer.getId(), firstOrderRule);
        helper.createPricerCalculator(marketingPricer.getId(), flatCalculator);
        final var nominalPricer = helper.createNominalRule(shippingMethod.getId());
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
    @Test(description = "Валидация полей калькулятора 'Цена с учётом сложности'", groups = "regression")
    public void testValidatePriceWithDifficulty() {
        //Одно и тоже с 529
    }

    //TODO: Сейчас это создает кучу данных в базе, удалять пока что не представляется возможным.
    @CaseId(511)
    @Test(enabled = false, description = "Создать новый способ доставки", groups = "regression")
    public void testCreateNewDelivery() {
        shippingMethod().goToPage();
        shippingMethod().addNewDelivery();

        shippingNewMethod().fillMethodName("Autotest_Вывоз_Алкоголя");
        shippingNewMethod().selectType("Самовывоз");
        shippingNewMethod().addDeliveryCategory("Алкоголь");
        shippingNewMethod().clickToSubmit();
        shippingNewMethod().checkPageContains("admin/spa/shipping_methods");
    }

    @Skip
    @CaseId(525)
    @Test(description = "Редактировать способ доставки", groups = "regression")
    public void testEditDeliveryMethod() {
        //Одно и тоже с 528, 530
    }

    //Нельзя добавить только маркетинговое правило
    @Skip
    @CaseId(512)
    @Test(description = "Добавить правило маркетинговой стоимости доставки", groups = "regression")
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
        shippingMethod().interactFlashAlert().checkSuccessFlashVisible();
    }

    //Нельзя добавить только номинальное правило
    @Skip
    @CaseId(513)
    @Test(description = "Добавить правило номинальной стоимости доставки", groups = "regression")
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
        shippingMethod().interactFlashAlert().checkSuccessFlashVisible();
    }

    @CaseId(522)
    @Test(description = "Удалить правило маркетинговой стоимости доставки", groups = "regression")
    public void testRemoveMarketingRule() {
        helper.createMarketingRule(shippingMethod.getId());
        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().deleteMarketingRule();
    }

    @CaseId(523)
    @Test(description = "Удалить правило номинальной стоимости доставки", groups = "regression")
    public void testRemoveNominalRule() {
        helper.createNominalRule(shippingMethod.getId());
        shippingMethod().goToPage();
        shippingMethod().clickToEditShipmentMethod("Autotest");
        shippingMethod().deleteNominalRule();
    }

    @CaseId(516)
    @Test(description = "При клике на 'Тип правила' отображается выпадающий список с типами", groups = "regression")
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
    @Test(description = "Выбор правила 'Интервал стоимости заказа' и установка интервала", groups = "regression")
    public void testInterval() {
        final var marketingPricer = helper.createMarketingRule(shippingMethod.getId());
        helper.createPricerCalculator(marketingPricer.getId(), flatCalculator);
        final var nominalPricer = helper.createNominalRule(shippingMethod.getId());
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
    @Test(description = "Выбор правила 'Периодический заказ' и заполнение полей с днями и суммой", groups = "regression")
    public void testPeriodic() {
        final var marketingPricer = helper.createMarketingRule(shippingMethod.getId());
        helper.createPricerCalculator(marketingPricer.getId(), flatCalculator);
        final var nominalPricer = helper.createNominalRule(shippingMethod.getId());
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
    @Test(description = "Валидация полей интервала стоимости заказа", groups = "regression")
    public void testValidateInterval() {
        final var marketingPricer = helper.createMarketingRule(shippingMethod.getId());
        helper.createPricerCalculator(marketingPricer.getId(), flatCalculator);
        final var nominalPricer = helper.createNominalRule(shippingMethod.getId());
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
