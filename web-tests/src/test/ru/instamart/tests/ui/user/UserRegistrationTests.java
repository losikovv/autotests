package ru.instamart.tests.ui.user;

import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.ShoppingCartCheckpoints;
import instamart.ui.checkpoints.users.UsersAuthorizationCheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qase.api.annotation.CaseId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class UserRegistrationTests extends TestBase {
    public static String modalType;
    private static String phone;
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ShoppingCartCheckpoints shopChecks = new ShoppingCartCheckpoints();
    UsersAuthorizationCheckpoints authChecks = new UsersAuthorizationCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Завершаем сессию, текущего пользователя")
    public void quickLogout() {
        User.Logout.quickly();
    }

    @CaseId(1552)
    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke","testing"
            }
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
            baseChecks.checkIsErrorMessageElementPresentByPhone("Номер должен начинаться с \"+7 (9..\"",
                    "Нет пользовательской ошибки пустого номера телефона");
        }
        kraken.get().page(Config.DEFAULT_RETAILER);
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя с пустыми реквизитами");
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без имени",
            groups = {
                    "metro-regression","metro-acceptance",
                    "sbermarket-regression","testing"
            }
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
                    "sbermarket-regression","testing"
            }
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
                    "sbermarket-regression","testing"
            }
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
                    "sbermarket-regression","testing"
            }
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
                    "sbermarket-regression","testing"
            }
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
                    "sbermarket-regression","testing"
            }
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
                    "sbermarket-acceptance","sbermarket-regression","testing"
            }
    )
    public void noRegWithExistingEmail() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Do.registration("Test User", UserManager.getDefaultUser().getLogin(),
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
                    "sbermarket-regression","testing"
            }
    )
    public void noRegWithLongFields() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);

        User.Do.registration(UserManager.getUser(150));
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
                    "sbermarket-regression","testing"
            }
    )
    public void noRegOnCancel() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.open();
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(
                        Elements.Modals.AuthModal.popup().getLocator()),
                "Модалка авторизации не открыта\n");
        User.Do.regSequence(UserManager.getUser());
        Shop.AuthModal.close();
        baseChecks.checkIsAuthModalClosed();
        kraken.get().baseUrl();
        authChecks.checkIsUserNotAuthorized("Произошла регистрация пользователя " +
                "после заполнения всех полей и закрытия модалки");
    }

    @CaseId(1541)
    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"metro-acceptance","sbermarket-Ui-smoke","MRAutoCheck","testing"}
    )
    public void successRegOnLanding() {
        phone = Generate.phoneNumber();
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

    @CaseId(1543)
    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {
                    "metro-smoke", "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke","testing"
            }
    )
    public void successRegOnMainPage() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        phone = Generate.phoneNumber();
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
                    "sbermarket-regression","testing"
            }
    )
    public void successRegFromAddressModal() throws AssertionError {
        kraken.get().page(Config.DEFAULT_RETAILER);
        phone = Generate.phoneNumber();
        Shop.ShippingAddressModal.open();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());
        baseChecks.checkIsAuthModalOpen("Не работает переход на авторизацию из адресной модалки");
        User.Do.registration(
                "Test User",
                "test@example.com",
                "12345678",
                "12345678",
                phone,
                "1111"
        );
        authChecks.checkIsUserAuthorized("Не работает регистрация из адресной модалки феникса");
    }

    @CaseId(748)
    @Test(

            description = "Тест регистрации при переходе из корзины в чекаут",
            groups = {
                    "metro-regression",
                    "sbermarket-regression","testing","sbermarket-Ui-smoke"
            }
    )
    public void successRegFromCart() {
        phone = Generate.phoneNumber();
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);

        Shop.Cart.collectFirstTime();
        Shop.Cart.proceedToCheckout();
        baseChecks.checkIsAuthModalOpen("Не открывается авторизационная" +
                " модалка при переходе неавторизованным из корзины в чекаут");
        User.Do.registration(
                "Test User",
                "test@example.com",
                "12345678",
                "12345678",
                phone,
                "1111"
        );
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
                    "sbermarket-acceptance","sbermarket-regression","testing"
            }
    )
    public void successRegWithoutMailingCheckbox() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.AuthModal.open();
        User.Do.regSequence(UserManager.getUser(), false ); // todo вынести в Shop.AuthModal.fill()
        Shop.AuthModal.submit();
        authChecks.checkIsUserAuthorized("Не работает регистрация без согласия на получение почтовой рассылки");
    }

    @Test(
            description = "Тест успешной регистрации с заново проставленной галкой согласия на почтовую рассылку",
            groups = {
                    "metro-regression",
                    "sbermarket-regression","testing"
            }
    )
    public void successRegWithMailingCheckbox() {
        if(modalType.equals("модалка с телефоном")){skipTest();}
        kraken.get().page(Config.DEFAULT_RETAILER);

        Shop.AuthModal.open();
        User.Do.regSequence(UserManager.getUser(), false );
        kraken.await().fluently(ExpectedConditions.elementToBeClickable(Elements.Modals.AuthModal.agreementCheckbox().getLocator()));
        kraken.perform().setCheckbox(Elements.Modals.AuthModal.checkBoxes(),2);
        kraken.perform().click(Elements.Modals.AuthModal.submitButton());
        kraken.await().implicitly(5);
        authChecks.checkIsUserAuthorized("Не работает регистрация с согласием на получение почтовой рассылки");
    }

    @Test(
            description = "Тест успешной регистрации через ВКонтакте",

            groups = {"sbermarket-Ui-smoke","testing"},
            enabled = false
    )
    public void successRegWithVkontakte() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withVkontakte(UserManager.getDefaultVkUser());
        authChecks.checkIsUserAuthorized("Не работает регистрация через ВКонтакте");
    }

    @Test(
            description = "Тест успешной регистрации через Facebook",

            groups = {"sbermarket-Ui-smoke","testing"},
            enabled = false
    )
    public void successRegWithFacebook() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withFacebook(UserManager.getDefaultFbUser());
        authChecks.checkIsUserAuthorized("Не работает регистрация через Facebook");
    }

    @Test(  description = "Тест успешной регистрации через MailRu",

            groups = {"sbermarket-Ui-smoke","testing"},
            enabled = false
    )
    public void successRegWithMailRu() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withMailRu(UserManager.getDefaultMailRuUser());
        authChecks.checkIsUserAuthorized("Не работает регистрация через MailRu");
    }


    @Test(
            description = "Тест успешной регистрации через Sber ID",

            groups = {"sbermarket-Ui-smoke","testing"},
            enabled = false
    )
    public void successRegWithSberID() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        User.Auth.withSberID(UserManager.getDefaultSberIdUser());
        authChecks.checkIsUserAuthorized("Не работает регистрация через Sber ID");
    }
}
