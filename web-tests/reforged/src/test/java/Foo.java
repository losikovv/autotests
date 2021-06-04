import lombok.SneakyThrows;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.instamart.reforged.stf.page.StfRouter;
import ru.instamart.ui.listener.UiExecutionListener;
import ru.instamart.ui.report.CustomReport;

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

        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().fillPhone("9999919613");
        StfRouter.home().interactAuthModal().sendSms();
        StfRouter.home().interactAuthModal().fillSMS("111111");
        StfRouter.shop().openFirstProductCard();
        StfRouter.shop().interactProductCard().addToFavorite();
        //Rout.shop().interactProductCard().clickOnBuy();
//        Rout.shop().interactProductCard().close();
//        Rout.shop().interactHeader().clickToCart();
//        Rout.shop().interactCart().clearCart();
//        Rout.shop().interactCart().confirmClear();
//        Rout.shop().interactProductCard().deleteFromFavorite();

//        Rout.shop().interactProductCard().increaseItemCount();
//        Rout.shop().interactProductCard().decreaseItemCount();
        StfRouter.shop().interactProductCard().hideDetailedInfo();
//        Rout.shop().interactProductCard().showDetailedInfo();
        StfRouter.shop().interactProductCard().clickOnNextSlide();
        StfRouter.shop().interactProductCard().clickOnPrevSlide();







    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
            CustomReport.takeScreenshot();
    }
}
