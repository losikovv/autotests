package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.reforged.business.page.BusinessRouter.*;

@Epic("SMBUSINESS UI")
@Feature("Лендинг")
public final class LandingTests extends BaseTest {

    @CaseId(25)
    @Test(description = "Ссылка на лендинг для неавторизованного юзера", groups = {"smoke", "regression"})
    public void fromB2CToB2BNotAuthorized() {
        b2cShop().goToPageFromTenant();
        b2cShop().interactHeader().clickBuyForBusiness();

        business().switchToNextWindow();
        business().refreshWithBasicAuth();

        business().checkLandingVisible();
    }

    @CaseId(26)
    @Test(description = "Переход на страницу лендинга", groups = {"smoke", "regression"})
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

    @CaseId(27)
    @Test(description = "Добавление компании с лендинга", groups = {"smoke", "regression"})
    public void addCompanyFromLanding() {
        var user = UserManager.getQaUser();

        business().goToPage();
        business().checkLandingVisible();

        business().clickAddCompany();
        business().interactAuthModal().checkModalIsVisible();
        business().interactAuthModal().authViaPhone(user);
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(28)
    @Test(description = "Заказать обратный звонок с лендинга", groups = {"smoke", "regression"})
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

    @CaseId(733)
    @Test(description = "Отображение всех доступных ритейлеров в блоке 'до 20% от чека' на лендинге.", groups = {"smoke", "regression"})
    public void retailersOnLanding() {
        var expectedTopThreeRetailers = List.of("METRO", "Лента", "Ашан");

        business().goToPage();
        business().checkLandingVisible();

        business().checkRetailersListOnPage(expectedTopThreeRetailers);
    }
}
