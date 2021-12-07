package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Профиль пользователя")
public class UserCompaniesTests extends BaseTest {

    private UserData userData;
    private final ApiHelper helper = new ApiHelper();

    @BeforeMethod(alwaysRun = true,
            description = "Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void login() {
        userData = UserManager.getQaUser();
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
    }

    //@CaseId(1524)
    //@Story("Выпадающее меню")
    @Test(description = "Тест добавления компании в профиле", groups = "regression")
    public void addCompanyForUser() {
        var company = JuridicalData.juridical();
        userCompanies().goToPage();
        userCompanies().clickAddCompany();

        userCompanies().interactAddCompanyModal().fillCompany(company);

        home().checkPageIsAvailable();
    }

    @Test(description = "Тест добавления компании в профиле", groups = "regression")
    public void linkToCompanyPage() {
        var company = JuridicalData.juridical();
        userCompanies().goToPage();
        userCompanies().clickAddCompany();

        userCompanies().interactAddCompanyModal().fillCompany(company);

        home().checkPageIsAvailable();
    }

}
