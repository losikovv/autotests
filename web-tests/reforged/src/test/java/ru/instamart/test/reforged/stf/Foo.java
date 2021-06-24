package ru.instamart.test.reforged.stf;

import lombok.SneakyThrows;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.reforged.core.listener.UiExecutionListener;
import ru.instamart.reforged.core.report.CustomReport;
import ru.instamart.reforged.stf.frame.auth.AuthSberId;
import ru.instamart.reforged.stf.page.StfRouter;

@Listeners(UiExecutionListener.class)
public class Foo {

    @SneakyThrows
    @Test
    public void bar() {
        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().fillPhone("79999999999");
        StfRouter.home().interactAuthModal().sendSms();
        StfRouter.home().interactAuthModal().fillSMS("111111");
        StfRouter.shop().interactHeader().clickToProfile();
        StfRouter.home().goToPage();
    }

    @SneakyThrows
    @Test
    public void bar2() {
        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().checkForBusiness();
        StfRouter.home().interactAuthModal().uncheckForBusiness();
        StfRouter.home().interactAuthModal().checkForBusiness();
        StfRouter.home().interactAuthModal().uncheckForBusiness();
        StfRouter.home().interactAuthModal().checkForBusiness();
        StfRouter.home().interactAuthModal().uncheckForBusiness();
    }

    @SneakyThrows
    @Test
    public void bar3() {
        String retailerName = "METRO";
        String callAndReplace = "Позвонить мне. Подобрать замену, если не смогу ответить";
        String callAndRemove = "Позвонить мне. Убрать из заказа, если не смогу ответить";
        String noCallAndReplace = "Не звонить мне. Подобрать замену";
        String noCallAndRemove = "Не звонить мне. Убрать из заказа";
        AuthSberId sberIdPage = StfRouter.home().interactAuthModal().interactAuthSberIdPage();
        UserData user = UserManager.getDefaultSberIdUser();

        //9999919613

        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
//        StfRouter.home().interactAuthModal().fillPhone("9999919613");
//        StfRouter.home().interactAuthModal().sendSms();
//        StfRouter.home().interactAuthModal().fillSMS("111111");
        StfRouter.home().interactAuthModal().authViaSberId();
        sberIdPage.clickToChangeAuthTypeOnLogin();
        sberIdPage.fillLogin(user.getLogin());
        sberIdPage.clickToSubmitLogin();
        sberIdPage.fillPassword(user.getPassword());
        sberIdPage.clickToSubmitPassword();
    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
        CustomReport.takeScreenshot();
    }
}
