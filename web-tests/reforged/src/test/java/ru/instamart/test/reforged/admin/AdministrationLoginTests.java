package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.RequiredArgsConstructor;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import static ru.instamart.reforged.Group.PROD_ADMIN_SMOKE;
import static ru.instamart.reforged.Group.REGRESSION_ADMIN;
import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.main;

@Epic("Админка STF")
@Feature("Страница логина")
public final class AdministrationLoginTests {

    @Test
    public void foo() throws ExecutionException, InterruptedException, IOException {
        //dscacheutil -q host -a name admin.sbmt.io
        Process process = Runtime.getRuntime().exec("dscacheutil -q host -a name admin.sbmt.io");
        StreamGobbler streamGobbler =
                new StreamGobbler(process.getInputStream(), System.out::println);
        Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);

        int exitCode = process.waitFor();
        assert exitCode == 0;

        future.get();
    }

    @RequiredArgsConstructor
    private static class StreamGobbler implements Runnable {
        private final InputStream inputStream;
        private final Consumer<String> consumer;

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }

    @CaseId(440)
    @Story("Тест неуспешной авторизации с пустыми полями")
    @Test(description = "Тест неуспешной авторизации с пустыми полями", groups = {REGRESSION_ADMIN, PROD_ADMIN_SMOKE})
    public void noAuthWithEmptyFields() {
        login().goToPage();
        login().setUsername("");
        login().setPassword("");
        login().submit();
        login().checkErrorEmptyEmail();
        login().checkErrorEmptyPassword();
    }

    @CaseId(441)
    @Story("Тест неуспешной авторизации с некорректным логином")
    @Test(description = "Тест неуспешной авторизации с некорректным логином", groups = {REGRESSION_ADMIN, PROD_ADMIN_SMOKE})
    public void noAuthWithIncorrectUsername() {
        login().goToPage();
        login().setUsername("wrongUsername");
        login().setPassword("123456");
        login().submit();
        login().checkErrorInvalidEmail();
    }

    @CaseId(442)
    @Story("Тест неуспешной авторизации с несуществующим логином")
    @Test(description = "Тест неуспешной авторизации с несуществующим логином", groups = {REGRESSION_ADMIN, PROD_ADMIN_SMOKE})
    public void noAuthWithNonExistingUser() {
        login().goToPage();
        login().setUsername("nonexistinguser@instamart.ru");
        login().setPassword("123456");
        login().submit();
        login().checkErrorInvalidEmailOrPassword();
    }

    @CaseId(443)
    @Story("Тест неуспешной авторизации с коротким паролем")
    @Test(description = "Тест неуспешной авторизации с коротким паролем", groups = {REGRESSION_ADMIN, PROD_ADMIN_SMOKE})
    public void noAuthWithShortPassword() {
        login().goToPage();
        login().setUsername(UserManager.getDefaultAdmin().getEmail());
        login().setPassword("123");
        login().submit();
        login().checkErrorShortPassword();
    }

    @CaseId(444)
    @Story("Тест неуспешной авторизации с неверным паролем")
    @Test(description = "Тест неуспешной авторизации с неверным паролем", groups = {REGRESSION_ADMIN, PROD_ADMIN_SMOKE})
    public void noAuthWithWrongPassword() {
        login().goToPage();
        login().setUsername(UserManager.getDefaultAdmin().getEmail());
        login().setPassword("wrongpassword");
        login().submit();
        login().checkErrorInvalidEmailOrPassword();
    }

    @CaseId(415)
    @Story("Тест успешной авторизации")
    @Test(description = "Тест успешной авторизации", groups = {REGRESSION_ADMIN, "smoke", PROD_ADMIN_SMOKE})
    public void successAuthOnAdminLoginPage() {
        login().goToPage();
        login().setUsername(UserManager.getDefaultAdmin().getEmail());
        login().setPassword(UserManager.getDefaultAdmin().getPassword());
        login().submit();

        main().interactAuthoredHeader().checkAdminAuth();
    }

    @CaseId(2)
    @Story("Тест логаута из админки")
    @Test(description = "Тест логаута из админки", groups = {REGRESSION_ADMIN, "smoke", PROD_ADMIN_SMOKE})
    public void successLogoutFromAdminPage() {
        final var userData = UserManager.getDefaultAdmin();
        login().goToPage();
        login().setUsername(userData.getEmail());
        login().setPassword(userData.getPassword());
        login().submit();

        main().interactAuthoredHeader().clickToDropDown();
        main().interactAuthoredHeader().clickToLogout();

        login().checkTitle();
    }

    @CaseId(417)
    @Story("Тест недоступности админки пользователю без админ. прав")
    @Test(description = "Тест недоступности админки пользователю без админ. прав", groups = {REGRESSION_ADMIN, "smoke", PROD_ADMIN_SMOKE, "test_prod"})
    public void loginWithoutAdminPermission() {
        login().goToPage();
        login().setUsername(UserManager.userWithoutAdminPermission().getEmail());
        login().setPassword(UserManager.userWithoutAdminPermission().getPassword());
        login().submit();
        main().interactAuthoredHeader().checkIsNotAuth();
    }
}
