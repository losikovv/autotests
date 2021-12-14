package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.kraken.data.BonusPrograms.aeroflot;
import static ru.instamart.kraken.data.BonusPrograms.mnogoru;
import static ru.instamart.reforged.stf.page.StfRouter.checkout;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Бонусные программы")
public final class CheckoutBonusesTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        this.userData = UserManager.getQaUser();
        this.helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }

    @CaseId(1702)
    @Story("Тест успешного добавления всех доступных бонусных программ в чекауте")
    @Test(description = "Тест успешного добавления всех доступных бонусных программ в чекауте", groups = {"acceptance", "regression"})
    public void successAddBonusPrograms() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal()
                  .fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal()
                  .clickToSaveModal();
        checkout().checkLoyaltyCardModalNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToAddLoyaltyCard(aeroflot().getName());
        checkout().interactEditLoyaltyCardModal()
                  .fillValue(aeroflot().getCardNumber());
        checkout().interactEditLoyaltyCardModal()
                  .clickToSaveModal();
        checkout().checkLoyaltyCardModalNotVisible();
        checkout().checkBonusCardApplied(aeroflot().getName());
    }

    @CaseId(1703)
    @Story("Тест выбора добавленных бонусных программ в чекауте")
    @Test(description = "Тест выбора добавленных бонусных программ в чекауте", groups = {"acceptance", "regression"})
    public void successSelectBonusPrograms() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal()
                  .fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal()
                  .clickToSaveModal();
        checkout().checkLoyaltyCardModalNotVisible();
        checkout().checkBonusCardActive(mnogoru().getName());

        checkout().clickToAddLoyaltyCard(aeroflot().getName());
        checkout().interactEditLoyaltyCardModal()
                  .fillValue(aeroflot().getCardNumber());
        checkout().interactEditLoyaltyCardModal()
                  .clickToSaveModal();
        checkout().checkLoyaltyCardModalNotVisible();
        checkout().checkBonusCardActive(aeroflot().getName());

        checkout().clickToSelectLoyaltyCard(mnogoru().getName());
        checkout().checkLoyaltyCardLoaderNotVisible();
        checkout().checkBonusCardActive(mnogoru().getName());

        checkout().clickToSelectLoyaltyCard(aeroflot().getName());
        checkout().checkLoyaltyCardLoaderNotVisible();
        checkout().checkBonusCardActive(aeroflot().getName());
    }

    @CaseId(1704)
    @Story("Тест удаления всех бонусных программ в чекауте")
    @Test(description = "Тест удаления всех бонусных программ в чекауте", groups = {"acceptance", "regression"})
    public void successDeleteBonusPrograms() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();

        checkout().checkLoyaltyCardModalNotVisible();

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToDeleteModal();

        checkout().checkBonusCardNotApplied(mnogoru().getName());

        checkout().clickToAddLoyaltyCard(aeroflot().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(aeroflot().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();

        checkout().checkLoyaltyCardModalNotVisible();

        checkout().clickToEditLoyaltyCard(aeroflot().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToDeleteModal();
        checkout().checkBonusCardNotApplied(aeroflot().getName());
    }
}
