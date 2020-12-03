package ru.instamart.tests.user;

import instamart.core.settings.Config;
import instamart.core.testdata.Users;
import instamart.core.testdata.ui.Generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.ShoppingCartCheckpoints;
import instamart.ui.checkpoints.users.UsersAuthorizationCheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class UserRegistrationTests extends TestBase {
    public static String modalType;
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ShoppingCartCheckpoints shopChecks = new ShoppingCartCheckpoints();
    UsersAuthorizationCheckpoints authChecks = new UsersAuthorizationCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void quickLogout() {
        User.Logout.quickly();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke","testing"
            },
            priority = 201
    )
    public void noRegWithEmptyRequisites() {
        kraken.get().page(Config.DEFAULT_RETAILER); // Переход на страничку Метро

        modalType = User.Do.registration(
                null,
                null,
                null,
                null,
                "",
                null
        );
        if(modalType.equals("модалка с почтой")){
            baseChecks.checkIsErrorMessageElementPresent("Укажите имя и фамилию",
                    "Нет пользовательской ошибки пустого поля name");
            baseChecks.checkIsErrorMessageElementPresent("Укажите email",
                    "Нет пользовательской ошибки пустого поля email");
            baseChecks.checkIsErrorMessageElementPresent("Укажите пароль",
                    "Нет пользовательской ошибки пустого поля password");
            baseChecks.checkIsErrorMessageElementPresent("Подтвердите пароль",
                    "Нет пользовательской ошибки пустого поля password confirmation");
        }else{
            baseChecks.checkIsErrorMessageElementPresentByPhone("Номер должен начинаться с \"+7 9...\"",
                    "Нет пользовательской ошибки пустого номера телефона");
        }
        kraken.get().page(Config.DEFAULT_RETAILER);
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя с пустыми реквизитами");
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без имени",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 202
    )
    public void noRegWithoutName() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        // для стейджа телефон 799999999999 код 1111
        String phone = Generate.phoneNumber();
        modalType = User.Do.registration(
                null,
                "test@example.com",
                "12345678",
                "12345678",
                phone,
                "1111"
        );
        if(modalType.equals("модалка с почтой")){
            baseChecks.checkIsErrorMessageElementPresent("Укажите имя и фамилию",
                    "Нет пользовательской ошибки пустого поля name");
            kraken.get().baseUrl();
            authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя без имени");
        }
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без кода подтверждения",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 202
    )
    public void noRegWithoutNameMobile() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        // для стейджа телефон 799999999999 код 1111
        User.Do.registration(
                null,
                "test@example.com",
                "12345678",
                "12345678"
        );
        baseChecks.checkIsErrorMessageElementPresent("Укажите имя и фамилию",
                "Нет пользовательской ошибки пустого поля name");
        kraken.get().baseUrl();
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя без имени");
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без email",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 203
    )
    public void noRegWithoutEmail() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);

        User.Do.registration(
                "Test User",
                null,
                "12345678",
                "12345678"
        );
        baseChecks.checkIsErrorMessageElementPresent("Укажите email",
                "Нет пользовательской ошибки пустого поля email");
        kraken.get().baseUrl();
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя без email");
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без пароля",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 204
    )
    public void noRegWithoutPassword() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.registration("Test User", "test@example.com",
                null, "12345678");
        baseChecks.checkIsErrorMessageElementPresent("Укажите пароль",
                "Нет пользовательской ошибки пустого поля password");
        kraken.get().baseUrl();
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя без пароля");
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без подтверждения пароля",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 205
    )
    public void noRegWithoutPasswordConfirmation() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.registration(
                "Test User",
                "test@example.com",
                "12345678",
                null
        );
        baseChecks.checkIsErrorMessageElementPresent("Подтвердите пароль",
                "Нет пользовательской ошибки пустого поля password confirmation");
        kraken.get().baseUrl();
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя без подтверждения пароля");
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с несовпадающими паролями",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 206
    )
    public void noRegWithWrongPasswordConfirmation() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.registration(
                "Test User",
                "test@example.com",
                "12345678",
                "12345679"
        );
        baseChecks.checkIsErrorMessageElementPresent("Введенные пароли должны совпадать",
                "Нет пользовательской ошибки несовпадения пароля");
        kraken.get().baseUrl();
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя с несовпадающим подтверждёнием пароля");
    }

    @Test(
            description = "Негативный тест попытки повторно зарегистрировать существующего пользователя",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 207
    )
    public void noRegWithExistingEmail() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.registration("Test User", Users.superuser().getLogin(),
                "12345678", "12345678");
        baseChecks.checkIsErrorMessageElementPresent("Этот email уже зарегистрирован",
                "Нет пользовательской ошибки регистрации с уже зарегистрированным email");
        kraken.get().baseUrl();
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя с уже используемым email");
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с длинными полями",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 208
    )
    public void noRegWithLongFields() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);

        User.Do.registration(Generate.testCredentials("user", 150));
        baseChecks.checkIsErrorMessageElementPresent("Имя должно содержать не более 128 символов",
                "Нет пользовательской ошибки превышения длины поля name");
        baseChecks.checkIsErrorMessageElementPresent("Email адрес должен содержать не более 128 символов",
                "Нет пользовательской ошибки превышения длины поля email");
        baseChecks.checkIsErrorMessageElementPresent("Пароль должен содержать не более 128 символов",
                "Нет пользовательской ошибки превышения длины поля password");
        kraken.get().baseUrl();
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя" +
                " с ошибками заполнения формы регистрации длинными полями");
    }

    @Test(
            description = "Тест отмены регистрации после заполнения всех полей",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 209
    )
    public void noRegOnCancel() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.open();
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(
                        Elements.Modals.AuthModal.popup().getLocator()),
                "Модалка авторизации не открыта\n");
        User.Do.regSequence(Generate.testCredentials("user"));
        Shop.AuthModal.close();
        baseChecks.checkIsAuthModalClosed();
        kraken.get().baseUrl();
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя " +
                "после заполнения всех полей и закрытия модалки");
    }

    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"metro-acceptance","sbermarket-Ui-smoke"},
            priority = 210
    )
    public void successRegOnLanding() {
        String phone = Generate.phoneNumber();
        modalType = User.Do.registration(
                "Test User",
                "test@example.com",
                "12345678",
                "12345678",
                phone,
                "1111"
        );
        authChecks.checkIsUserAuthorized("Не работает регистрация на лендинге");
    }

    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {
                    "metro-smoke", "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke"
            },
            priority = 211
    )
    public void successRegOnMainPage() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        String phone = Generate.phoneNumber();
        modalType = User.Do.registration(
                "Test User",
                "test@example.com",
                "12345678",
                "12345678",
                phone,
                "1111"
        );
        authChecks.checkIsUserAuthorized("Не работает регистрация на витрине магазина");
    }

    @Test(
            description = "Тест регистрации из адресной модалки феникса",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 212
    )
    public void successRegFromAddressModal() throws AssertionError {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.ShippingAddressModal.open();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());

        baseChecks.checkIsAuthModalOpen("Не работает переход на авторизацию из адресной модалки");
        User.Do.registration();
        authChecks.checkIsUserAuthorized("Не работает регистрация из адресной модалки феникса");
    }

    @Test(
            description = "Тест регистрации при переходе из корзины в чекаут",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 213
    )
    public void successRegFromCart() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        baseChecks.checkIsAuthModalOpen("Не открывается авторизационная" +
                " модалка при переходе неавторизованным из корзины в чекаут");
        User.Do.registration();
        authChecks.checkAutoCheckoutRedirect("Нет автоперехода в чекаут после регистрации из корзины");
        kraken.get().baseUrl();
        authChecks.checkIsUserAuthorized("Не работает регистрация из корзины");
        shopChecks.checkIsCartEmpty("регистрации из корзины",
                "Пропали товары после регистрации из корзины");
    }

    @Test(
            description = "Тест успешной регистрации без проставленной галки согласия на почтовую рассылку",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 214
    )
    public void successRegWithoutMailingCheckbox() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.AuthModal.open();
        User.Do.regSequence(Generate.testCredentials("user"), false ); // todo вынести в Shop.AuthModal.fill()
        Shop.AuthModal.submit();
        authChecks.checkIsUserAuthorized("Не работает регистрация без согласия на получение почтовой рассылки");
    }

    @Test(
            description = "Тест успешной регистрации с заново проставленной галкой согласия на почтовую рассылку",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 215
    )
    public void successRegWithMailingCheckbox() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.AuthModal.open();
        User.Do.regSequence(Generate.testCredentials("user"), false );
        kraken.await().fluently(ExpectedConditions.elementToBeClickable(Elements.Modals.AuthModal.agreementCheckbox().getLocator()));
        kraken.perform().setCheckbox(Elements.Modals.AuthModal.checkBoxes(),2);
        kraken.perform().click(Elements.Modals.AuthModal.submitButton());
        kraken.await().implicitly(5);
        authChecks.checkIsUserAuthorized("Не работает регистрация с согласием на получение почтовой рассылки");
    }

    // todo public void successRegWithVkontakte() {}

    // todo public void successRegWithFacebook() {}

    // todo public void successRegWithMailRu() {}

    // todo public void successRegWithSberId() {}
}
