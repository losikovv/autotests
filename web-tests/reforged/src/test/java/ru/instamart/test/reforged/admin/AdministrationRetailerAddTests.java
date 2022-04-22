package ru.instamart.test.reforged.admin;

import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Story("Новый ритейлер /retailers/new")
public class AdministrationRetailerAddTests extends BaseTest {

    private final String retailerName = Generate.literalString(6) + "_retailer";
    private final String importKey = Generate.digitalString(6);

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
        retailerAdd().checkImportKeyInputText("");
        retailerAdd().clearImportKey();

        retailerAdd().fillImportKey(Generate.literalCyrillicString(10));
        retailerAdd().checkImportKeyInputErrorVisible();
        retailerAdd().checkImportKeyInputText("");
        retailerAdd().clearImportKey();

        retailerAdd().fillImportKey(importKey);
        retailerAdd().checkImportKeyInputErrorNotVisible();
    }
}
