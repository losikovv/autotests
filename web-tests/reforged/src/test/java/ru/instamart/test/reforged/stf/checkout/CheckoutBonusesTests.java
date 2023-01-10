package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;

import static ru.instamart.kraken.data.BonusPrograms.aeroflot;
import static ru.instamart.kraken.data.BonusPrograms.mnogoru;
import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.checkout;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Бонусные программы")
public final class CheckoutBonusesTests {

    private static final ThreadLocal<UserData> userData = new ThreadLocal<>();
    private final ApiHelper helper = new ApiHelper();

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        userData.set(UserManager.getQaUser());
        this.helper.dropAndFillCart(userData.get(), UiProperties.DEFAULT_SID);
    }

    @TmsLink("1702")
    @Story("Тест успешного добавления всех доступных бонусных программ в чекауте")
    @Test(description = "Тест успешного добавления всех доступных бонусных программ в чекауте", groups = REGRESSION_STF)
    public void successAddBonusPrograms() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToAddLoyaltyCard(aeroflot().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(aeroflot().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(aeroflot().getName());
    }

    @TmsLink("1703")
    @Story("Тест выбора добавленных бонусных программ в чекауте")
    @Test(description = "Тест выбора добавленных бонусных программ в чекауте", groups = REGRESSION_STF)
    public void successSelectBonusPrograms() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardActive(mnogoru().getName());

        checkout().clickToAddLoyaltyCard(aeroflot().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(aeroflot().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardActive(aeroflot().getName());

        checkout().clickToSelectLoyaltyCard(mnogoru().getName());
        checkout().checkLoyaltyCardLoaderNotVisible();
        checkout().checkBonusCardActive(mnogoru().getName());

        checkout().clickToSelectLoyaltyCard(aeroflot().getName());
        checkout().checkLoyaltyCardLoaderNotVisible();
        checkout().checkBonusCardActive(aeroflot().getName());
    }

    @TmsLink("1704")
    @Story("Тест удаления всех бонусных программ в чекауте")
    @Test(description = "Тест удаления всех бонусных программ в чекауте", groups = REGRESSION_STF)
    public void successDeleteBonusPrograms() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();

        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToDeleteModal();

        checkout().checkBonusCardNotApplied(mnogoru().getName());

        checkout().clickToAddLoyaltyCard(aeroflot().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(aeroflot().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();

        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();

        checkout().clickToEditLoyaltyCard(aeroflot().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToDeleteModal();
        checkout().checkBonusCardNotApplied(aeroflot().getName());
    }

    @TmsLink("1705")
    @Story("Тест не добавлять бонусы при нажатии кнопки отмена")
    @Test(description = "Тест не добавлять бонусы при нажатии кнопки отмена", groups = REGRESSION_STF)
    public void noAddBonusProgramOnCancel() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToCancelModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardNotApplied(mnogoru().getName());
    }

    @TmsLink("1706")
    @Story("Тест на недобавление бонусов при закрытии модалки")
    @Test(description = "Тест на недобавление бонусов при закрытии модалки", groups = REGRESSION_STF)
    public void noAddBonusProgramOnModalClose()  {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToCloseModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardNotApplied(mnogoru().getName());
    }

    @TmsLink("1707")
    @Story("Тест не добавлять бонусы при отсутствии номера карты")
    @Test(description = "Тест не добавлять бонусы при отсутствии номера карты", groups = REGRESSION_STF)
    public void noAddBonusProgramWithEmptyCardNumber() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue("");
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkEmptyCardNumberAlert();
        checkout().interactEditLoyaltyCardModal().clickToCloseModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardNotApplied(mnogoru().getName());
    }

    @TmsLink("1708")
    @Story("Тест не добавлять бонусы с некорректным номером карты")
    @Test(description = "Тест не добавлять бонусы с некорректным номером карты", groups = REGRESSION_STF)
    public void noAddBonusProgramWithWrongCardNumber() {
        final String cardNumber = Generate.literalString(8);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(cardNumber);
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkWrongCardNumberAlert();
        checkout().interactEditLoyaltyCardModal().clickToCloseModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardNotApplied(mnogoru().getName());
    }

    @TmsLink("1736")
    @Story("Тест успешного редактирования карты ритейлера")
    @Test(description = "Тест успешного редактирования карты ритейлера", groups = REGRESSION_STF)
    public void successEditBonusProgram() {
        final String cardNumber = Generate.digitalString(8);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(cardNumber);
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().checkCardNumberInputValue(cardNumber);
    }

    @TmsLink("1710")
    @Story("Тест на невозможность редактировать бонусов при нажатии кнопки отмена")
    @Test(description = "Тест на невозможность редактировать бонусов при нажатии кнопки отмена", groups = REGRESSION_STF)
    public void noEditBonusProgramOnCancel() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToCancelModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().checkCardNumberInputValue(mnogoru().getCardNumber());
    }

    @TmsLink("1711")
    @Story("Тест на отсутствие возможности редактировать бонусы при закрытии модалки")
    @Test(description = "Тест на отсутствие возможности редактировать бонусы при закрытии модалки", groups = REGRESSION_STF)
    public void noEditBonusProgramOnModalClose() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToCloseModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().checkCardNumberInputValue(mnogoru().getCardNumber());
    }

    @TmsLink("1713")
    @Story("Тест на отсутсвие возможности редактировать бонусы с пустым номером карты")
    @Test(description = "Тест на отсутсвие возможности редактировать бонусы с пустым номером карты", groups = REGRESSION_STF)
    public void noEditBonusProgramWithEmptyCardNumber() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue("");
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkEmptyCardNumberAlert();
        checkout().interactEditLoyaltyCardModal().clickToCloseModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().checkCardNumberInputValue(mnogoru().getCardNumber());
    }

    @TmsLink("1714")
    @Story("Тест на не удаление бонусов при нажатии кнопки отмена")
    @Test(description = "Тест на не удаление бонусов при нажатии кнопки отмена", groups = REGRESSION_STF)
    public void noDeleteBonusProgramOnCancel() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();

        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToCancelModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().checkCardNumberInputValue(mnogoru().getCardNumber());
    }

    @TmsLink("1715")
    @Story("Тест на не удаление бонусов при закрытии модалки")
    @Test(description = "Тест на не удаление бонусов при закрытии модалки", groups = REGRESSION_STF)
    public void noDeleteBonusProgramOnModalClose() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().fillValue(mnogoru().getCardNumber());
        checkout().interactEditLoyaltyCardModal().clickToSaveModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardApplied(mnogoru().getName());

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToCloseModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();

        checkout().clickToEditLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().checkCardNumberInputValue(mnogoru().getCardNumber());
    }
}
