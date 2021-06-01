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
        Rout.home().interactAuthModal().fillPhone("79999999999");
        Rout.home().interactAuthModal().sendSms();
        Rout.home().interactAuthModal().fillSMS("111111");
        Rout.shop().interactHeader().clickToProfile();
        Rout.home().goToPage();
    }

    @SneakyThrows
    @Test
    public void bar2() {
        Rout.home().goToPage();
        Rout.home().openLoginModal();
        Rout.home().interactAuthModal().checkForBusiness();
        Rout.home().interactAuthModal().uncheckForBusiness();
        Rout.home().interactAuthModal().checkForBusiness();
        Rout.home().interactAuthModal().uncheckForBusiness();
        Rout.home().interactAuthModal().checkForBusiness();
        Rout.home().interactAuthModal().uncheckForBusiness();
    }

    @SneakyThrows
    @Test
    public void bar3() {
        Rout.home().goToPage();
        Rout.home().openLoginModal();
        Rout.home().interactAuthModal().fillPhone("9999999999");
        Rout.home().interactAuthModal().sendSms();
        Rout.home().interactAuthModal().fillSMS("111111");
        Rout.shop().interactHeader().clickToCart();
        //ожидание открытия корзины
        Rout.shop().interactCart().increaseCount();
        Rout.shop().interactCart().increaseCount();
        Rout.shop().interactCart().decreaseCount();
        Rout.shop().interactCart().openItemCard();
    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
            CustomReport.takeScreenshot();
    }
}
