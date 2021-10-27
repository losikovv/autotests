package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.kraken.data.BonusPrograms.aeroflot;
import static ru.instamart.kraken.data.BonusPrograms.mnogoru;

import static ru.instamart.reforged.stf.page.StfRouter.*;

public class CheckoutBonusesTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(1702)
    @Story("Тест успешного добавления всех доступных бонусных программ в чекауте")
    @Test(description = "Тест успешного добавления всех доступных бонусных программ в чекауте", groups = {"acceptance", "regression"})
    public void successAddBonusPrograms() {
        final UserData checkoutBonusesUser = UserManager.getQaUser();
        helper.dropAndFillCart(checkoutBonusesUser, EnvironmentProperties.DEFAULT_SID);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutBonusesUser);
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
        helper.dropCart(checkoutBonusesUser);
    }

    @CaseId(1703)
    @Story("Тест выбора добавленных бонусных программ в чекауте")
    @Test(description = "Тест выбора добавленных бонусных программ в чекауте", groups = {"acceptance", "regression"})
    public void successSelectBonusPrograms() {
        final UserData checkoutBonusesUser = UserManager.getQaUser();
        helper.dropAndFillCart(checkoutBonusesUser, EnvironmentProperties.DEFAULT_SID);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutBonusesUser);
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

        helper.dropCart(checkoutBonusesUser);
    }

    @CaseId(1704)
    @Story("Тест удаления всех бонусных программ в чекауте")
    @Test(description = "Тест удаления всех бонусных программ в чекауте", groups = {"acceptance", "regression"})
    public void successDeleteBonusPrograms() {
        final UserData checkoutBonusesUser = UserManager.getQaUser();
        helper.dropAndFillCart(checkoutBonusesUser, EnvironmentProperties.DEFAULT_SID);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaPhone(checkoutBonusesUser);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal()
                  .fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal()
                  .clickToSaveModal();
        checkout().checkLoyaltyCardModalNotVisible();
        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal()
                  .clickToDeleteModal();
        checkout().checkBonusCardNotApplied(mnogoru().getName());

        checkout().clickToAddLoyaltyCard(aeroflot().getName());
        checkout().interactEditLoyaltyCardModal()
                  .fillValue(aeroflot().getCardNumber());
        checkout().interactEditLoyaltyCardModal()
                  .clickToSaveModal();
        checkout().checkLoyaltyCardModalNotVisible();
        checkout().clickToEditLoyaltyCard(aeroflot().getName());
        checkout().interactEditLoyaltyCardModal()
                .clickToDeleteModal();
        checkout().checkBonusCardNotApplied(aeroflot().getName());

        helper.dropCart(checkoutBonusesUser);
    }
}
