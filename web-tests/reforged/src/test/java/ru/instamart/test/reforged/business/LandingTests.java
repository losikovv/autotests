package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import io.qameta.allure.TmsLink;

import java.util.List;

import static ru.instamart.reforged.Group.REGRESSION_BUSINESS;
import static ru.instamart.reforged.business.page.BusinessRouter.*;

@Epic("SMBUSINESS UI")
@Feature("Лендинг")
public final class LandingTests {

    @TmsLink("25")
    //TODO Переход с STF на Business при текущей схеме невозможен см коммент https://jira.sbmt.io/browse/ATST-2251
    @Skip
    @Test(description = "Ссылка на лендинг для неавторизованного юзера", groups = {"smoke", REGRESSION_BUSINESS})
    public void fromB2CToB2BNotAuthorized() {
        b2cShop().goToPage();
        b2cShop().interactHeader().clickBuyForBusiness();

        business().switchToNextWindow();
        business().refreshWithBasicAuth();

        business().checkLandingVisible();
    }

    @TmsLink("26")
    @Test(description = "Переход на страницу лендинга", groups = {"smoke", REGRESSION_BUSINESS})
    public void basicLandingCheck() {
        business().goToPage();
        business().checkLandingVisible();

        business().checkButtonGetCallBackDisplayed();
        business().checkButtonAddCompanyDisplayed();
        business().checkButtonDeliveryDisplayed();
        business().checkButtonBenefitsDisplayed();
        business().checkButtonHowToOrderDisplayed();
        business().checkButtonCitiesDisplayed();
        business().checkButtonFAQDisplayed();
        business().checkButtonCertificatesDisplayed();
    }

    @TmsLink("27")
    @Test(description = "Добавление компании с лендинга", groups = {"smoke", REGRESSION_BUSINESS})
    public void addCompanyFromLanding() {
        var user = UserManager.getQaUser();
        var company = JuridicalData.juridical();

        business().goToPage();
        business().interactHeader().clickToLogin();
        business().interactAuthModal().authViaPhone(user);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

        companies().goToPage();
        companies().checkCompaniesListIsEmpty();

        companies().clickAddCompany();
        companies().interactAddCompany().checkAddCompanyModalVisible();
        companies().interactAddCompany().fillInn(company.getInn());
        companies().interactAddCompany().clickAddCompany();
        companies().interactAddCompany().fillName(company.getJuridicalName());
        companies().interactAddCompany().clickAddCompany();
        companies().interactAddCompany().checkAddCompanyModalNotVisible();

        companies().goToPage();
        companies().checkCompaniesListIsNotEmpty();
        companies().checkCompaniesCount(1);
        companies().checkCompaniesListContains(company.getJuridicalName());
    }

    @TmsLink("28")
    @Test(description = "Заказать обратный звонок с лендинга", groups = {"smoke", REGRESSION_BUSINESS})
    public void getCallback() {
        var user = UserManager.getQaUser();

        business().goToPage();
        business().checkLandingVisible();

        business().clickGetCallback();
        business().interactGetCallbackModal().checkCallbackModalVisible();
        business().interactGetCallbackModal().fillName(user.getFirstName());
        business().interactGetCallbackModal().fillCompanyName(user.getLastName());
        business().interactGetCallbackModal().fillPhone(user.getPhone());

        business().interactGetCallbackModal().clickConfirm();
        business().interactHeader().checkCallbackConfirmDisplayed();
    }

    @TmsLink("733")
    @Test(description = "Отображение всех доступных ритейлеров в блоке 'до 20% от чека' на лендинге.", groups = {"smoke", REGRESSION_BUSINESS})
    public void retailersOnLanding() {
        business().goToPage();
        business().checkLandingVisible();

        business().checkRetailersListOnPage(List.of("METRO", "Лента", "Ашан"));
    }
}
