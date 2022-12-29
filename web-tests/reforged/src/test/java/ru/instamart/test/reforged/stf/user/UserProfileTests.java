package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.StringUtil;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.Group.SMOKE_STF;
import static ru.instamart.reforged.admin.AdminRout.pages;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Профиль пользователя")
public final class UserProfileTests {

    @TmsLink("1524")
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации меню профиля Sbermarket", groups = {REGRESSION_STF, SMOKE_STF})
    public void successValidateSbermarketTenantProfileMenu() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
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
    @Test(description = "Тест валидации кнопки 'Профиль' в меню профиля", groups = {REGRESSION_STF, SMOKE_STF})
    public void successValidateUserProfileButton() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToProfile();
        pages().checkPageIsAvailable();
    }

    @TmsLink("1527")
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'Условия использования' в меню профиля", groups = {REGRESSION_STF, SMOKE_STF})
    public void successValidateTermsButton() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToTerms();
        pages().checkPageIsAvailable();
    }

    @TmsLink("1528")
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'Доставка' в меню профиля", groups = {REGRESSION_STF, SMOKE_STF})
    public void successValidateDeliveryButton() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToDelivery();
        pages().checkPageIsAvailable();
    }

    @TmsLink("1530")
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'FAQ' в меню профиля", groups = {REGRESSION_STF, SMOKE_STF})
    public void successValidateFaqButton() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToFaq();
        pages().checkPageIsAvailable();
    }

    @TmsLink("1531")
    @Story("навигация в меню пользователя")
    @Test(description = "Тест доступности страниц профиля пользователя", groups = {REGRESSION_STF, SMOKE_STF})
    public void successCheckProfilePagesAreAvailable() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToProfile();
        pages().checkPageIsAvailable();
        userEdit().openOrders();
        pages().checkPageIsAvailable();
    }

    @TmsLink("1532")
    @Story("Заказы")
    @Test(description = "Тест валидации дефолтных страниц истории заказов", groups = {REGRESSION_STF, SMOKE_STF})
    public void successValidateDefaultOrderHistory() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToProfile();
        pages().checkPageIsAvailable();
        userShipments().goToPage();
        userShipments().checkTextOnThePage();
        userShipments().checkAllOrdersButton();
        userShipments().checkActiveOrdersButton();
        userShipments().checkFinishedOrdersButton();
        userShipments().checkGoToShoppingButton();
    }

    @TmsLink("2559")
    @Story("Данные профиля пользователя")
    @Test(description = "Добавление имени и фамилии для новых пользователей", groups = {REGRESSION_STF, SMOKE_STF})
    public void addFullName() {
        final var userData = UserManager.getQaUser();
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userEdit().goToPage();
        userEdit().clickToChangeName();
        userEdit().interactFullNameForm().fillFirstName(userData.getFirstName());
        userEdit().interactFullNameForm().fillLastName(userData.getLastName());
        userEdit().interactFullNameForm().submit();
        userEdit().checkSaveAlert();
        userEdit().checkFullName(userData.getName(), userEdit().getName());
    }

    @TmsLink("2560")
    @Story("Данные профиля пользователя")
    @Test(description = "Добавление E-mail для новых пользователей", groups = REGRESSION_STF)
    public void addEmail() {
        final var userData = UserManager.getQaUser();
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userEdit().goToPage();
        userEdit().clickToChangeEmail();
        userEdit().interactEmailFrame().fillEmail(userData.getEmail());
        userEdit().interactEmailFrame().submit();
        userEdit().interactEmailFrame().clickToOk();
        //TODO: Переход из почты по ссылке, для подтверждения и смены ящика
    }

    @TmsLink("2561")
    @Story("Данные профиля пользователя")
    @Test(description = "Изменение имени и фамилии для существующих пользователей", groups = {REGRESSION_STF, SMOKE_STF})
    public void changeFullName() {
        final var userData = UserManager.getQaUser();
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userEdit().goToPage();
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

    @TmsLink("2562")
    @Story("Данные профиля пользователя")
    @Test(description = "Изменение E-mail для существующих пользователей", groups = REGRESSION_STF)
    public void changeEmail() {
        final var userData = UserManager.getQaUser();
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        userEdit().goToPage();
        userEdit().clickToChangeEmail();
        userEdit().interactEmailFrame().fillEmail(userData.getEmail());
        userEdit().interactEmailFrame().submit();
        userEdit().interactEmailFrame().clickToOk();
        //TODO: Переход из почты по ссылке, для подтверждения и смены ящика
    }

    @TmsLink("2563")
    @Story("Данные профиля пользователя")
    @Test(description = "Изменение телефона для существующих пользователей", groups = {REGRESSION_STF, SMOKE_STF})
    public void changePhone() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        final var newUser = UserManager.getUser();
        userEdit().goToPage();
        userEdit().clickToChangePhone();
        userEdit().interactAuthModal().authViaPhone(newUser);
        userEdit().checkSaveAlert();
        userEdit().checkPhone(newUser.getPhone(), StringUtil.getPhone(userEdit().getPhone()));
    }

    @TmsLink("2564")
    @Story("Данные профиля пользователя")
    @Test(description = "Изменение E-mail на тот, который уже есть в системе", groups = REGRESSION_STF)
    public void changeToExistingEmail() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();

        userEdit().goToPage();
        userEdit().clickToChangeEmail();
        userEdit().interactEmailFrame().fillEmail(UserManager.getDefaultAdmin().getEmail());
        userEdit().interactEmailFrame().submit();
        userEdit().interactEmailFrame().clickToOk();
        //TODO: Проверка почты, что нет письма
    }
}
