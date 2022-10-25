package ru.instamart.test.reforged.hp_ops_partners;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.HR_OPS_PARTNERS;
import static ru.instamart.reforged.hr_ops_partners.page.HRPartnersRouter.home;

@Epic("HR Ops.Partners UI")
@Feature("Лендинг")
public final class HomePageRespondTests {

    @CaseId(13)
    @Story("Главная страница")
    @Test(description = "PM-13. Отклик на вакансию (шаг 1-2)", groups = {HR_OPS_PARTNERS})
    public void testHomePageRespond1_2() {

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCollector().clickRespond();
        home().interactApplyForm().clickPersonalDataConfirmInfo();

        home().switchToNextWindow();
        home().checkPageContains("docs/personal_partners_policy.pdf");
    }

    @CaseId(13)
    @Story("Главная страница")
    @Test(description = "PM-13. Отклик на вакансию (шаг 3-4)", groups = {HR_OPS_PARTNERS})
    public void testHomePageRespond3_4() {

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCollector().clickRespond();
        home().interactApplyForm().checkModalVisible();

        home().interactApplyForm().clickPhone();
        home().interactApplyForm().clickRegion();
        home().interactApplyForm().clickSurname();
        home().interactApplyForm().clickName();
        home().interactApplyForm().checkPersonalDataCheckbox();
        home().interactApplyForm().clickSendSMS();

        home().interactApplyForm().checkModalVisible();
        home().interactConfirmSMS().checkModalNotVisible();
        home().interactApplyForm().checkNameInputErrorVisible();
        home().interactApplyForm().checkNameInputErrorText("Укажите имя");
        home().interactApplyForm().checkSurnameInputErrorVisible();
        home().interactApplyForm().checkSurnameInputErrorText("Укажите фамилию");
        home().interactApplyForm().checkRegionInputErrorVisible();
        home().interactApplyForm().checkRegionInputErrorText("Укажите город");
        home().interactApplyForm().checkPhoneInputErrorVisible();
        home().interactApplyForm().checkNamePhoneErrorText("Номер должен начинаться с \"+7 (9..\"");
    }

    @CaseId(13)
    @Story("Главная страница")
    @Test(description = "PM-13. Отклик на вакансию (шаг 5)", groups = {HR_OPS_PARTNERS})
    public void testHomePageRespond5() {

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCollector().clickRespond();
        home().interactApplyForm().checkModalVisible();

        home().interactApplyForm().fillName(Generate.literalString(10));
        home().interactApplyForm().fillSurname(Generate.literalString(10));
        home().interactApplyForm().clickRegion();
        home().interactApplyForm().checkRegionsListVisible();
        home().interactApplyForm().selectRegionByName("Москва");
        home().interactApplyForm().fillPhone(Generate.phone());
        home().interactApplyForm().checkPersonalDataCheckbox();
        home().interactApplyForm().clickSendSMS();

        home().interactApplyForm().checkModalVisible();

    }
}
