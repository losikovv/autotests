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
        String retailerName = "METRO";

        Rout.home().goToPage();
        Rout.home().openLoginModal();
        Rout.home().interactAuthModal().fillPhone("9999919613");
        Rout.home().interactAuthModal().sendSms();
        Rout.home().interactAuthModal().fillSMS("111111");
        Rout.shop().openAddressFrame();
//        Rout.shop().interactAddress().clear();
//        Rout.shop().interactAddress().setAddress("Москва проспект Мира, 211к1");
//        Rout.shop().interactAddress().selectFirstAddress();
//        Rout.shop().interactAddress().clickOnSave();
        Rout.shop().interactAddress().selectSelfDelivery();
        Rout.shop().interactAddress().selectCity("Воронеж");
        Rout.shop().interactAddress().selectCity("Самара");
//        Rout.shop().interactAddress().changeStore();
//        Rout.shop().interactAddress().selectFirstStore();
//        Rout.shop().interactAddress().clickViewOtherRetailers();
//        Rout.shop().interactAddress().selectRetailerByName(retailerName);
//        Rout.shop().interactAddress().selectFirstStoreByRetailerName(retailerName);



    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
            CustomReport.takeScreenshot();
    }
}
