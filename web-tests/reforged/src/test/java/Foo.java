import lombok.SneakyThrows;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.instamart.reforged.stf.page.Rout;
import ru.instamart.ui.listener.UiExecutionListener;
import ru.instamart.ui.report.CustomReport;

@Listeners(UiExecutionListener.class)
public class Foo {

    @SneakyThrows
    @Test
    public void bar() {
        Rout.home().goToPage();
        Rout.home().openLoginModal();
        Rout.home().useAuthModal().fillPhone("79999999999");
        Rout.home().useAuthModal().sendSms();
        Rout.home().useAuthModal().fillSMS("111111");
        Rout.shop().useHeader().clickToProfile();
        Rout.home().goToPage();
    }

    @SneakyThrows
    @Test
    public void bar2() {
        Rout.home().goToPage();
        Rout.home().openLoginModal();
        Rout.home().useAuthModal().checkForBusiness();
        Rout.home().useAuthModal().uncheckForBusiness();
        Rout.home().useAuthModal().checkForBusiness();
        Rout.home().useAuthModal().uncheckForBusiness();
        Rout.home().useAuthModal().checkForBusiness();
        Rout.home().useAuthModal().uncheckForBusiness();
    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
            CustomReport.takeScreenshot();
    }
}
