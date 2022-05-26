package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.kraken.data.BonusPrograms.aeroflot;
import static ru.instamart.kraken.data.BonusPrograms.mnogoru;
import static ru.instamart.reforged.stf.page.StfRouter.checkout;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Бонусные программы")
public final class CheckoutBonusesTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        this.userData = UserManager.getQaUser();
        this.helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }

    @CaseId(1702)
    @Story("Тест успешного добавления всех доступных бонусных программ в чекауте")
    @Test(description = "Тест успешного добавления всех доступных бонусных программ в чекауте", groups = "regression")
    public void successAddBonusPrograms() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

    @CaseId(1703)
    @Story("Тест выбора добавленных бонусных программ в чекауте")
    @Test(description = "Тест выбора добавленных бонусных программ в чекауте", groups = "regression")
    public void successSelectBonusPrograms() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

    @CaseId(1704)
    @Story("Тест удаления всех бонусных программ в чекауте")
    @Test(description = "Тест удаления всех бонусных программ в чекауте", groups = "regression")
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

    @CaseId(1705)
    @Story("Тест не добавлять бонусы при нажатии кнопки отмена")
    @Test(description = "Тест не добавлять бонусы при нажатии кнопки отмена", groups = "regression")
    public void noAddBonusProgramOnCancel() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToCancelModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardNotApplied(mnogoru().getName());
    }

    @CaseId(1706)
    @Story("Тест на недобавление бонусов при закрытии модалки")
    @Test(description = "Тест на недобавление бонусов при закрытии модалки", groups = "regression")
    public void noAddBonusProgramOnModalClose()  {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().clickToAddLoyaltyCard(mnogoru().getName());
        checkout().interactEditLoyaltyCardModal().checkModalWindow();
        checkout().interactEditLoyaltyCardModal().clickToCloseModal();
        checkout().interactEditLoyaltyCardModal().checkModalWindowNotVisible();
        checkout().checkBonusCardNotApplied(mnogoru().getName());
    }

    @CaseId(1707)
    @Story("Тест не добавлять бонусы при отсутствии номера карты")
    @Test(description = "Тест не добавлять бонусы при отсутствии номера карты", groups = "regression")
    public void noAddBonusProgramWithEmptyCardNumber() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

    @CaseId(1708)
    @Story("Тест не добавлять бонусы с некорректным номером карты")
    @Test(description = "Тест не добавлять бонусы с некорректным номером карты", groups = "regression")
    public void noAddBonusProgramWithWrongCardNumber() {
        final String cardNumber = Generate.literalString(8);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

    @CaseId(1736)
    @Story("Тест успешного редактирования карты ритейлера")
    @Test(description = "Тест успешного редактирования карты ритейлера", groups = "regression")
    public void successEditBonusProgram() {
        final String cardNumber = Generate.digitalString(8);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

    @CaseId(1710)
    @Story("Тест на невозможность редактировать бонусов при нажатии кнопки отмена")
    @Test(description = "Тест на невозможность редактировать бонусов при нажатии кнопки отмена", groups = "regression")
    public void noEditBonusProgramOnCancel() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

    @CaseId(1711)
    @Story("Тест на отсутствие возможности редактировать бонусы при закрытии модалки")
    @Test(description = "Тест на отсутствие возможности редактировать бонусы при закрытии модалки", groups = "regression")
    public void noEditBonusProgramOnModalClose() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

    @CaseId(1713)
    @Story("Тест на отсутсвие возможности редактировать бонусы с пустым номером карты")
    @Test(description = "Тест на отсутсвие возможности редактировать бонусы с пустым номером карты", groups = "regression")
    public void noEditBonusProgramWithEmptyCardNumber() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

    @CaseId(1714)
    @Story("Тест на не удаление бонусов при нажатии кнопки отмена")
    @Test(description = "Тест на не удаление бонусов при нажатии кнопки отмена", groups = "regression")
    public void noDeleteBonusProgramOnCancel() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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

    @CaseId(1715)
    @Story("Тест на не удаление бонусов при закрытии модалки")
    @Test(description = "Тест на не удаление бонусов при закрытии модалки", groups = "regression")
    public void noDeleteBonusProgramOnModalClose() {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
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
