package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Добавление ритейлеров")
public final class AdministrationRetailerAddTests {

    private final ApiHelper apiHelper = new ApiHelper();
    private final String retailerName = "AdministrationRetailerAddTests_" + Generate.literalString(6);
    private final String importKey = Generate.digitalString(6);
    private final String phone = Generate.phoneNumber();
    private final String inn = Generate.generateINN(10);
    private final String categoriesDepth = Generate.digitalString(1);

    @AfterClass(alwaysRun = true)
    public void clearData() {
        apiHelper.deleteRetailerByNameInAdmin(retailerName);
    }

    @CaseIDs
            ({@CaseId(206), @CaseId(207), @CaseId(208)})
    @Test(description = "Проверка обязательности заполнения полей", groups = {"regression"})
    public void checkRequiredFields() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnAddRetailerButton();

        retailerAdd().checkNameInputVisible();
        retailerAdd().clickSubmit();

        retailerAdd().checkNameInputErrorVisible();
        retailerAdd().checkNameInputErrorText("Обязательное поле");

        retailerAdd().checkURLInputErrorVisible();
        retailerAdd().checkURLInputErrorText("Обязательное поле");

        retailerAdd().checkImportKeyInputErrorVisible();
        retailerAdd().checkImportKeyInputErrorText("Обязательное поле");
    }

    @CaseId(206)
    @Test(description = "Поле Название обязательное для заполнения при создании ритейлера", groups = {"regression"})
    public void checkRetailerNameRequiredAfterClear() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnAddRetailerButton();

        retailerAdd().checkNameInputVisible();
        retailerAdd().fillName(retailerName);
        retailerAdd().checkNameInputText(retailerName);
        retailerAdd().clearName();

        retailerAdd().clickSubmit();

        retailerAdd().checkNameInputErrorVisible();
        retailerAdd().checkNameInputErrorText("Обязательное поле");
    }

    @CaseId(207)
    @Test(description = "Поле URL обязательное для заполнения", groups = {"regression"})
    public void checkURLRequiredAfterClear() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnAddRetailerButton();

        retailerAdd().checkNameInputVisible();
        retailerAdd().fillShortURL(retailerName);
        retailerAdd().checkURLInputText(retailerName);
        retailerAdd().clearURL();

        retailerAdd().clickSubmit();

        retailerAdd().checkURLInputErrorVisible();
        retailerAdd().checkURLInputErrorText("Обязательное поле");
    }

    @CaseId(208)
    @Test(description = "Поле Ключ в файле импорта обязательное для заполнения", groups = {"regression"})
    public void checkImportKeyRequiredAfterClear() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnAddRetailerButton();

        retailerAdd().checkNameInputVisible();
        retailerAdd().fillImportKey(importKey);
        retailerAdd().checkImportKeyInputText(importKey);
        retailerAdd().clearImportKey();

        retailerAdd().clickSubmit();

        retailerAdd().checkImportKeyInputErrorVisible();
        retailerAdd().checkImportKeyInputErrorText("Обязательное поле");
    }

    @CaseId(209)
    @Test(description = "Поле Ключ в файле импорта заполнять только числовым значением", groups = {"regression"})
    public void checkImportKeyDigitalOnly() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnAddRetailerButton();

        retailerAdd().checkNameInputVisible();

        retailerAdd().fillImportKey(Generate.literalString(10));
        retailerAdd().checkImportKeyInputErrorVisible();
        retailerAdd().checkImportKeyInputErrorText("Только число");
        retailerAdd().clearImportKey();

        retailerAdd().fillImportKey(Generate.symbolString(10));
        retailerAdd().checkImportKeyInputErrorVisible();
        retailerAdd().clearImportKey();

        retailerAdd().fillImportKey(Generate.literalCyrillicString(10));
        retailerAdd().checkImportKeyInputErrorVisible();
        retailerAdd().checkImportKeyInputText("");
        retailerAdd().clearImportKey();

        retailerAdd().fillImportKey(importKey);
        retailerAdd().checkImportKeyInputErrorNotVisible();
    }

    @CaseIDs({
            @CaseId(211), @CaseId(215), @CaseId(217)})
    @Test(description = "Успешное создание ритейлера со всеми заполненными данными", groups = {"regression"})
    public void successAddNewRetailer() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnAddRetailerButton();

        retailerAdd().checkNameInputVisible();
        retailerAdd().fillName(retailerName);
        retailerAdd().fillShortName(retailerName + "ShortName");
        retailerAdd().fillShortDescription(retailerName + "Description");
        retailerAdd().fillShortURL(retailerName + "ShortUrl");
        retailerAdd().fillJuridicalName(retailerName + "JuridicalName");
        retailerAdd().fillINN(inn);
        retailerAdd().fillPhone(phone);
        retailerAdd().fillJuridicalAddress(retailerName + "JuridicalAddress");
        retailerAdd().fillCategoriesDepth(categoriesDepth);
        retailerAdd().fillImportKey(importKey);
        retailerAdd().uploadLogo("src/test/resources/data/logo.png");
        retailerAdd().uploadIcon("src/test/resources/data/icon.png");

        retailerAdd().clickSubmit();

        retailer().checkPageContains(retailerName + "ShortUrl");
        retailer().checkRetailerName(retailerName);
        retailer().checkRetailerUrlName(retailerName + "ShortUrl");
        retailer().checkRetailerCategoriesDepth(categoriesDepth);
        retailer().checkImportKey(importKey);
        retailer().checkJuridicalName(retailerName + "JuridicalName");
        retailer().checkINN(inn);
        retailer().checkPhone("+7" + phone);
        retailer().checkJuridicalAddress(retailerName + "JuridicalAddress");
        retailer().checkContractType("Агентский договор");

        shipments().openAdminPageWithoutSpa(shipments().pageUrl());
        shipments().checkCustomerName();
        shipments().fillRetailer(retailerName);
        shipments().clickOnFirstResultInDropDown();
        shipments().checkRetailerInFilterContains(retailerName);
    }
}
