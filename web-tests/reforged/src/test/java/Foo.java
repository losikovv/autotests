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
        Rout.home().interactAuthModal().fillPhone("9999919613");
        Rout.home().interactAuthModal().sendSms();
        Rout.home().interactAuthModal().fillSMS("111111");
        Rout.shop().openFirstProductCard();
        Rout.shop().interactProductCard().addToFavorite();
        //Rout.shop().interactProductCard().clickOnBuy();
//        Rout.shop().interactProductCard().close();
//        Rout.shop().interactHeader().clickToCart();
//        Rout.shop().interactCart().clearCart();
//        Rout.shop().interactCart().confirmClear();
//        Rout.shop().interactProductCard().deleteFromFavorite();

//        Rout.shop().interactProductCard().increaseItemCount();
//        Rout.shop().interactProductCard().decreaseItemCount();
        Rout.shop().interactProductCard().hideDetailedInfo();
//        Rout.shop().interactProductCard().showDetailedInfo();
        Rout.shop().interactProductCard().clickOnNextSlide();
        Rout.shop().interactProductCard().clickOnPrevSlide();







    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
            CustomReport.takeScreenshot();
    }
}
