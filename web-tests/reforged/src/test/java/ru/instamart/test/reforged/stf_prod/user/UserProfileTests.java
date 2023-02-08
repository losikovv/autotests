package ru.instamart.test.reforged.stf_prod.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.StringUtil;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Профиль пользователя")
public final class UserProfileTests {

    @TmsLink("1524")
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации меню профиля Sbermarket", groups = {STF_PROD_S})
    public void successValidateSbermarketTenantProfileMenu() {
        shop().goToPageWithAuth(DEFAULT_SID, UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().checkProfileNameExists();
        shop().interactHeader().interactAccountMenu().checkProfileButtonExists();
        shop().interactHeader().interactAccountMenu().checkCompaniesButtonExists();
        shop().interactHeader().interactAccountMenu().checkTermsButtonExists();
        shop().interactHeader().interactAccountMenu().checkLogoutButtonExists();
        shop().interactHeader().interactAccountMenu().checkDeliveryLinkExists();
        shop().interactHeader().interactAccountMenu().checkFAQLinkExists();
    }

    @TmsLink("1525")
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'Профиль' в меню профиля", groups = {STF_PROD_S})
    public void successValidateUserProfileButton() {
        shop().goToPageWithAuth(DEFAULT_SID, UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToProfile();
        userEdit().checkPageIsAvailable();
    }

    @TmsLink("1527")
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'Условия использования' в меню профиля", groups = {STF_PROD_S})
    public void successValidateTermsButton() {
        shop().goToPageWithAuth(DEFAULT_SID, UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToTerms();
        terms().checkPageIsAvailable();
    }

    @TmsLink("1528")
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'Доставка' в меню профиля", groups = {STF_PROD_S})
    public void successValidateDeliveryButton() {
        shop().goToPageWithAuth(DEFAULT_SID, UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToDelivery();
        delivery().checkPageIsAvailable();
    }

    @TmsLink("1530")
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'FAQ' в меню профиля", groups = {STF_PROD_S})
    public void successValidateFaqButton() {
        shop().goToPageWithAuth(DEFAULT_SID, UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToFaq();
        faq().checkPageIsAvailable();
    }

    @TmsLink("1531")
    @Story("навигация в меню пользователя")
    @Test(description = "Тест доступности страниц профиля пользователя", groups = {STF_PROD_S})
    public void successCheckProfilePagesAreAvailable() {
        shop().goToPageWithAuth(DEFAULT_SID, UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToProfile();

        userEdit().checkPageIsAvailable();
        userEdit().openOrders();

        userShipments().checkPageIsAvailable();
    }

    @TmsLink("1532")
    @Story("Заказы")
    @Test(description = "Тест валидации дефолтных страниц истории заказов", groups = {STF_PROD_S})
    public void successValidateDefaultOrderHistory() {
        shop().goToPageWithAuth(DEFAULT_SID, UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToProfile();

        userEdit().checkPageIsAvailable();

        userShipments().goToPage();
        userShipments().checkTextOnThePage();
        userShipments().checkAllOrdersButton();
        userShipments().checkActiveOrdersButton();
        userShipments().checkFinishedOrdersButton();
        userShipments().checkGoToShoppingButton();
    }

    @TmsLink("2559")
    @Story("Данные профиля пользователя")
    @Test(description = "Добавление имени и фамилии для новых пользователей", groups = {STF_PROD_S})
    public void addFullName() {
        final var userData = UserManager.getQaUser();
        userEdit().goToPageWithAuth(userData);
        userEdit().clickToChangeName();
        userEdit().interactFullNameForm().fillFirstName(userData.getFirstName());
        userEdit().interactFullNameForm().fillLastName(userData.getLastName());
        userEdit().interactFullNameForm().submit();
        userEdit().checkSaveAlert();
        userEdit().checkFullName(userData.getName(), userEdit().getName());
    }

    @TmsLink("2561")
    @Story("Данные профиля пользователя")
    @Test(description = "Изменение имени и фамилии для существующих пользователей", groups = {STF_PROD_S})
    public void changeFullName() {
        final var userData = UserManager.getQaUser();

        userEdit().goToPageWithAuth(userData);
        userEdit().clickToChangeName();
        userEdit().interactFullNameForm().fillFirstName(userData.getFirstName());
        userEdit().interactFullNameForm().fillLastName(userData.getLastName());
        userEdit().interactFullNameForm().submit();
        userEdit().checkSaveAlert();
        userEdit().checkFullName(userData.getName(), userEdit().getName());

        userEdit().checkSaveAlertHide();

        final String firstName = "test";
        final String lastName = "tset";
        final String fullName = firstName + " " + lastName;

        userEdit().clickToChangeName();
        userEdit().interactFullNameForm().fillFirstName(firstName);
        userEdit().interactFullNameForm().fillLastName(lastName);
        userEdit().interactFullNameForm().submit();
        userEdit().checkSaveAlert();
        userEdit().checkFullName(fullName, userEdit().getName());
    }

    @TmsLink("2563")
    @Story("Данные профиля пользователя")
    @Test(description = "Изменение телефона для существующих пользователей", groups = {STF_PROD_S})
    public void changePhone() {
        userEdit().goToPageWithAuth(UserManager.getQaUser());
        userEdit().clickToChangePhone();
        userEdit().interactAuthModal().checkModalIsVisible();

        final var newUser = UserManager.getQaUser();

        userEdit().interactAuthModal().authViaPhone(newUser);
        userEdit().checkSaveAlert();
        userEdit().checkPhone(newUser.getPhone(), StringUtil.getPhone(userEdit().getPhone()));
    }
}
