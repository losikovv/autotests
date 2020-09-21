package ru.instamart.tests.user;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import instamart.core.settings.Config;
import instamart.core.testdata.Users;
import instamart.ui.modules.User;
import instamart.ui.common.lib.Addresses;
import instamart.ui.objectsmap.Elements;
import instamart.ui.modules.Shop;
import instamart.core.testdata.ui.generate;
import ru.instamart.tests.TestBase;

public class UserRegistrationTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        User.Logout.quickly();
    }
    public static String modalType;
    String env = System.getProperty("env", Config.CoreSettings.defaultEnvironment);

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с пустыми реквизитами",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","testing","sbermarket-regression"
            },
            priority = 201
    )
    public void noRegWithEmptyRequisites() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro"); // Переход на страничку Метро

        modalType = User.Do.registration(
                null,
                null,
                null,
                null,
                null,
                null
        );
        if(modalType.equals("модалка с почтой")){
            //todo отладить тест для прода
            softAssert.assertTrue(
                    kraken.detect().isElementPresent(
                            Elements.Modals.AuthModal.errorMessage("Укажите имя и фамилию")),
                    "Нет пользовательской ошибки пустого поля name\n");

            softAssert.assertTrue(
                    kraken.detect().isElementPresent(
                            Elements.Modals.AuthModal.errorMessage("Укажите пароль")),
                    "Нет пользовательской ошибки пустого поля email\n");

            softAssert.assertTrue(
                    kraken.detect().isElementPresent(
                            Elements.Modals.AuthModal.errorMessage("Укажите пароль")),
                    "Нет пользовательской ошибки пустого поля password\n");

            softAssert.assertTrue(
                    kraken.detect().isElementPresent(
                            Elements.Modals.AuthModal.errorMessage("Подтвердите пароль")),
                    "Нет пользовательской ошибки пустого поля password confirmation\n");
        }

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя с пустыми реквизитами\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без имени",
            groups = {
                    "metro-regression","testing","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 202
    )
    public void noRegWithoutName() {
        if(env.contains("staging")){skipTest();}//todo проверить как работает если несколько тестов, закрывается ли браузер
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        // для стейджа телефон 799999999999 код 1111
        User.Do.registration(
                null,
                "test@example.com",
                "12345678",
                "12345678"
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите имя и фамилию")),
                        "Нет пользовательской ошибки пустого поля name\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя без имени\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без кода подтверждения",
            groups = {
                    "metro-regression","testing","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 202
    )
    public void noRegWithoutNameMobile() {
        if(env.contains("production")){skipTest();}//todo проверить как работает если несколько тестов, закрывается ли браузер
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        // для стейджа телефон 799999999999 код 1111
        User.Do.registration(
                null,
                "test@example.com",
                "12345678",
                "12345678"
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Modals.AuthModal.errorMessage("Укажите имя и фамилию")),
                "Нет пользовательской ошибки пустого поля name\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                "Произошла регистрация пользователя без имени\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без email",
            groups = {
                    "metro-regression","testing","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 203
    )
    public void noRegWithoutEmail() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        User.Do.registration(
                "Test User",
                null,
                "12345678",
                "12345678"
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите email")),
                        "Нет пользовательской ошибки пустого поля email\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя без email\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без пароля",
            groups = {
                    "metro-regression","testing","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 204
    )
    public void noRegWithoutPassword() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        User.Do.registration("Test User", "test@example.com", null, "12345678");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Укажите пароль")),
                        "Нет пользовательской ошибки пустого поля password\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя без пароля\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя без подтверждения пароля",
            groups = {
                    "metro-regression","testing","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 205
    )
    public void noRegWithoutPasswordConfirmation() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        User.Do.registration(
                "Test User",
                "test@example.com",
                "12345678",
                null
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Подтвердите пароль")),
                        "Нет пользовательской ошибки пустого поля password confirmation\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя без подтверждения пароля\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с несовпадающими паролями",
            groups = {
                    "metro-regression","testing","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 206
    )
    public void noRegWithWrongPasswordConfirmation() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        User.Do.registration(
                "Test User",
                "test@example.com",
                "12345678",
                "12345679"
        );

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Введенные пароли должны совпадать")),
                        "Нет пользовательской ошибки несовпадения пароля\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя с несовпадающим подтверждёнием пароля\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки повторно зарегистрировать существующего пользователя",
            groups = {
                    "metro-acceptance", "metro-regression","testing",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 207
    )
    public void noRegWithExistingEmail() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        User.Do.registration("Test User", Users.superuser().getLogin(),
                "12345678", "12345678");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Этот email уже зарегистрирован")),
                        "Нет пользовательской ошибки регистрации с уже зарегистрированным email\n");

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя с уже используемым email\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Негативный тест попытки зарегистрировать пользователя с длинными полями",
            groups = {
                    "metro-regression","testing","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 208
    )
    public void noRegWithLongFields() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        User.Do.registration(generate.testCredentials("user", 150));

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Имя должно содержать не более 128 символов")),
                        "Нет пользовательской ошибки превышения длины поля name\n");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Email адрес должен содержать не более 128 символов")),
                        "Нет пользовательской ошибки превышения длины поля email\n");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Пароль должен содержать не более 128 символов")),
                        "Нет пользовательской ошибки превышения длины поля password\n");

        kraken.get().baseUrl();
        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    "Произошла регистрация пользователя с ошибками заполнения формы регистрации длинными полями\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест отмены регистрации после заполнения всех полей",
            groups = {
                    "metro-regression","testing","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 209
    )
    public void noRegOnCancel() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        Shop.AuthModal.open();
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(
                        Elements.Modals.AuthModal.popup().getLocator()),
                "Модалка авторизации не открыта\n");
        User.Do.regSequence(generate.testCredentials("user"));
        Shop.AuthModal.close();

        softAssert.assertFalse(
                kraken.detect().isAuthModalOpen(),
                    failMessage("Не закрывается заполненная регистрационная модалка")
        );

        kraken.get().baseUrl();

        softAssert.assertFalse(
                kraken.detect().isUserAuthorised(),
                    failMessage("Произошла регистрация пользователя после заполнения всех полей и закрытия модалки")
        );

        softAssert.assertAll();
    }

    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"sbermarket-acceptance","sbermarket-regression","metro-acceptance","testing"},
            priority = 210
    )
    public void successRegOnLanding() {
        User.Do.registration();

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает регистрация на лендинге")
        );
    }

    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {
                    "metro-smoke", "metro-acceptance", "metro-regression","testing",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 211
    )
    public void successRegOnMainPage() {
        kraken.get().page("metro");

        User.Do.registration();

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает регистрация на витрине магазина\n");
    }

    @Test(
            description = "Тест регистрации из адресной модалки феникса",
            groups = {
                    "metro-regression","testing","metro-acceptance",
                    "sbermarket-regression"
            },
            priority = 212
    )
    public void successRegFromAddressModal() throws AssertionError {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        Shop.ShippingAddressModal.open();
        kraken.perform().click(Elements.Modals.AddressModal.authButton());

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе работает переход на авторизацию из адресной модалки");

        User.Do.registration();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация из адресной модалки феникса");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест регистрации при переходе из корзины в чекаут",
            groups = {
                    "metro-regression","testing",
                    "sbermarket-regression"
            },
            priority = 213
    )
    public void successRegFromCart() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");

        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        softAssert.assertTrue(
                kraken.detect().isAuthModalOpen(),
                    "\nНе открывается авторизационная модалка при переходе неавторизованным из корзины в чекаут");

        User.Do.registration();

        softAssert.assertTrue(
                kraken.detect().isOnCheckout(),
                    "\nНет автоперехода в чекаут после регистрации из корзины");

        kraken.get().baseUrl();

        softAssert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "\nНе работает регистрация из корзины");

        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                    "\nПропали товары после регистрации из корзины");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест успешной регистрации без проставленной галки согласия на почтовую рассылку",
            groups = {
                    "metro-acceptance", "metro-regression", "testing",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 214
    )
    public void successRegWithoutMailingCheckbox() {
        kraken.get().page("metro");

        Shop.AuthModal.open();
        User.Do.regSequence(generate.testCredentials("user"), false ); // todo вынести в Shop.AuthModal.fill()
        Shop.AuthModal.submit();

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    failMessage("Не работает регистрация без согласия на получение почтовой рассылки"));
    }

    @Test(
            description = "Тест успешной регистрации с заново проставленной галкой согласия на почтовую рассылку",
            groups = {
                    "metro-regression","testing",
                    "sbermarket-regression"
            },
            priority = 215
    )
    public void successRegWithMailingCheckbox() {
        kraken.get().page("metro");

        Shop.AuthModal.open();
        User.Do.regSequence(generate.testCredentials("user"), false );
        kraken.await().fluently(ExpectedConditions.elementToBeClickable(Elements.Modals.AuthModal.agreementCheckbox().getLocator()));
        kraken.perform().setCheckbox(Elements.Modals.AuthModal.checkBoxes(),2);
        kraken.perform().click(Elements.Modals.AuthModal.submitButton());
        kraken.await().implicitly(5);
        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не работает регистрация с согласием на получение почтовой рассылки\n");
    }

    // todo public void successRegWithVkontakte() {}

    // todo public void successRegWithFacebook() {}

    // todo public void successRegWithMailRu() {}

    // todo public void successRegWithSberId() {}
}
