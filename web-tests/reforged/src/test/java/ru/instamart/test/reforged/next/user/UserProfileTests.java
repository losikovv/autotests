package ru.instamart.test.reforged.next.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.StringUtil;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.pages;
import static ru.instamart.reforged.next.page.StfRouter.*;

@Epic("STF UI")
@Feature("Профиль пользователя")
public class UserProfileTests {

    private UserData userData;

    @BeforeMethod(alwaysRun = true, description = "Авторизация")
    public void login() {
        this.userData = UserManager.getQaUser();
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
    }

    @CaseId(1524)
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации меню профиля Sbermarket", groups = "regression")
    public void successValidateSbermarketTenantProfileMenu() {
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().checkProfileNameExists();
        shop().interactHeader().interactAccountMenu().checkProfileButtonExists();
        shop().interactHeader().interactAccountMenu().checkCompaniesButtonExists();
        shop().interactHeader().interactAccountMenu().checkTermsButtonExists();
        shop().interactHeader().interactAccountMenu().checkLogoutButtonExists();
        shop().interactHeader().interactAccountMenu().checkDeliveryLinkExists();
        shop().interactHeader().interactAccountMenu().checkFAQLinkExists();
    }

    @CaseId(1525)
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'Профиль' в меню профиля", groups = "regression")
    public void successValidateUserProfileButton() {
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToProfile();
        pages().checkPageIsAvailable();
    }

    @CaseId(1527)
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'Условия использования' в меню профиля", groups = "regression")
    public void successValidateTermsButton() {
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToTerms();
        pages().checkPageIsAvailable();
    }

    @CaseId(1528)
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'Доставка' в меню профиля", groups = "regression")
    public void successValidateDeliveryButton() {
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToDelivery();
        pages().checkPageIsAvailable();
    }

    @CaseId(1530)
    @Story("Выпадающее меню")
    @Test(description = "Тест валидации кнопки 'FAQ' в меню профиля", groups = "regression")
    public void successValidateFaqButton() {
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToFaq();
        pages().checkPageIsAvailable();
    }

    @CaseId(1531)
    @Story("навигация в меню пользователя")
    @Test(description = "Тест доступности страниц профиля пользователя", groups = "regression")
    public void successCheckProfilePagesAreAvailable() {
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToProfile();
        pages().checkPageIsAvailable();
        userEdit().openFavoritePage();
        pages().checkPageIsAvailable();
        userEdit().openOrders();
        pages().checkPageIsAvailable();
    }

    @CaseId(1532)
    @Story("Заказы")
    @Test(description = "Тест валидации дефолтных страниц истории заказов", groups = "regression")
    public void successValidateDefaultOrderHistory() {
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

    @CaseId(2559)
    @Story("Данные профиля пользователя")
    @Test(description = "Добавление имени и фамилии для новых пользователей", groups = "regression")
    public void addFullName() {
        userEdit().goToPage();
        userEdit().clickToChangeName();
        userEdit().interactFullNameForm().fillFirstName(userData.getFirstName());
        userEdit().interactFullNameForm().fillLastName(userData.getLastName());
        userEdit().interactFullNameForm().submit();
        userEdit().checkSaveAlert();
        userEdit().checkFullName(userData.getName(), userEdit().getName());
    }

    @CaseId(2560)
    @Story("Данные профиля пользователя")
    @Test(description = "Добавление E-mail для новых пользователей", groups = "regression")
    public void addEmail() {
        userEdit().goToPage();
        userEdit().clickToChangeEmail();
        userEdit().interactEmailFrame().fillEmail(userData.getEmail());
        userEdit().interactEmailFrame().submit();
        userEdit().interactEmailFrame().clickToOk();
        //TODO: Переход из почты по ссылке, для подтверждения и смены ящика
    }

    @CaseId(2561)
    @Story("Данные профиля пользователя")
    @Test(description = "Изменение имени и фамилии для существующих пользователей", groups = "regression")
    public void changeFullName() {
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

    @CaseId(2562)
    @Story("Данные профиля пользователя")
    @Test(description = "Изменение E-mail для существующих пользователей", groups = "regression")
    public void changeEmail() {
        userEdit().goToPage();
        userEdit().clickToChangeEmail();
        userEdit().interactEmailFrame().fillEmail(userData.getEmail());
        userEdit().interactEmailFrame().submit();
        userEdit().interactEmailFrame().clickToOk();
        //TODO: Переход из почты по ссылке, для подтверждения и смены ящика
    }

    @CaseId(2563)
    @Story("Данные профиля пользователя")
    @Test(description = "Изменение телефона для существующих пользователей", groups = "regression")
    public void changePhone() {
        final var newUser = UserManager.getUser();
        userEdit().goToPage();
        userEdit().clickToChangePhone();
        userEdit().interactAuthModal().authViaPhone(newUser);
        userEdit().checkSaveAlert();
        userEdit().checkPhone(newUser.getPhone(), StringUtil.getPhone(userEdit().getPhone()));
    }

    @CaseId(2564)
    @Story("Данные профиля пользователя")
    @Test(description = "Изменение E-mail на тот, который уже есть в системе", groups = "regression")
    public void changeToExistingEmail() {
        userEdit().goToPage();
        userEdit().clickToChangeEmail();
        userEdit().interactEmailFrame().fillEmail(UserManager.getDefaultAdmin().getEmail());
        userEdit().interactEmailFrame().submit();
        userEdit().interactEmailFrame().clickToOk();
        //TODO: Проверка почты, что нет письма
    }
}
