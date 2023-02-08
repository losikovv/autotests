package ru.instamart.test.reforged.hp_ops_partners;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.hr_ops_partners.vacancy_cards.VacancyCards;
import ru.instamart.kraken.enums.CiPipelineSource;
import io.qameta.allure.TmsLink;

import static ru.instamart.kraken.data.TestVariables.CompanyParams.companyTelegramLink;
import static ru.instamart.kraken.data.TestVariables.CompanyParams.companyVkontakteLink;
import static ru.instamart.reforged.Group.HR_OPS_PARTNERS;
import static ru.instamart.reforged.hr_ops_partners.page.HRPartnersRouter.home;
import static ru.instamart.reforged.hr_ops_partners.page.HRPartnersRouter.vacancyPage;

@Epic("HR Ops.Partners UI")
@Feature("Лендинг")
public final class HomePageTests {

    private final String expectedRegionName = CiPipelineSource.isLocal() ? "Москва" : "Санкт-Петербург";

    @TmsLink("51")
    @Story("Главная страница")
    @Test(description = "PM-51. Шапка (шаг 1-2)", groups = {HR_OPS_PARTNERS})
    public void testHomePageHeader1_2() {

        home().goToPage();
        home().waitPageLoad();

        home().interactHeader().checkRegionTooltipVisible();
        home().interactHeader().checkRegionNameInTooltip(expectedRegionName);
        home().interactHeader().clickConfirmRegionInTooltip();

        home().interactHeader().checkRegionTooltipNotVisible();
        home().interactHeader().checkRegionNameInHeader(expectedRegionName);

        home().refresh();

        home().checkPageLoaded();
        home().interactHeader().checkRegionTooltipNotVisible();
        home().interactHeader().checkRegionNameInHeader(expectedRegionName);

    }

    @TmsLink("51")
    @Story("Главная страница")
    @Test(description = "PM-51. Шапка (шаг 3-4)", groups = {HR_OPS_PARTNERS})
    public void testHomePageHeader3_4() {

        home().goToPage();
        home().waitPageLoad();

        home().interactHeader().checkRegionTooltipVisible();
        home().interactHeader().clickSelectAnotherRegionInTooltip();

        home().interactHeader().interactRegionsModal().checkRegionsModalVisible();
        home().interactHeader().interactRegionsModal().selectRegionByName("Анадырь");
        home().interactHeader().interactRegionsModal().checkRegionsListNotVisible();

        home().interactHeader().checkRegionNameInHeader("Анадырь");
    }

    //TODO разобраться с подменой геолокации, добавить шаги 5-6

    @TmsLink("51")
    @Story("Главная страница")
    @Test(description = "PM-51. Шапка (шаг 7-8)", groups = {HR_OPS_PARTNERS})
    public void testHomePageHeader7_8() {

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();
        home().interactHeader().checkRegionTooltipNotVisible();
        home().interactHeader().checkRegionNameInHeader("Москва");
        home().interactCollector().clickMoreInfo();
        vacancyPage().waitPageLoad();
        vacancyPage().interactHeader().checkRegionTooltipNotVisible();
        vacancyPage().interactHeader().checkRegionNameInHeader("Москва");

        home().goToPage("?lead_city=Казань");
        home().checkPageLoaded();
        home().interactHeader().checkRegionTooltipNotVisible();
        home().interactHeader().checkRegionNameInHeader("Казань");
        home().interactCollector().clickMoreInfo();
        vacancyPage().waitPageLoad();
        vacancyPage().interactHeader().checkRegionTooltipNotVisible();
        vacancyPage().interactHeader().checkRegionNameInHeader("Казань");

        home().goToPage("?lead_city=Ростов-на-Дону");
        home().checkPageLoaded();
        home().interactHeader().checkRegionTooltipNotVisible();
        home().interactHeader().checkRegionNameInHeader("Ростов-на-Дону");
        home().interactCollector().checkRespondButtonDisabled();
        home().interactCollector().clickMoreInfo();
        home().interactNotAvailableModal().checkModalVisible();
        home().interactNotAvailableModal().checkModalContent("Ростове-на-Дону ");
    }

    @TmsLink("51")
    @Story("Главная страница")
    @Test(description = "PM-51. Шапка (шаг 9-10)", groups = {HR_OPS_PARTNERS})
    public void testHomePageHeader9_10() {

        vacancyPage().goToPage(VacancyCards.callCenterOperatorMSK().getPageUrl());
        vacancyPage().waitPageLoad();
        vacancyPage().interactHeader().checkRegionTooltipVisible();
        vacancyPage().interactHeader().clickSelectAnotherRegionInTooltip();
        vacancyPage().interactHeader().interactRegionsModal().checkRegionsModalVisible();
        vacancyPage().interactHeader().interactRegionsModal().selectRegionByName("Москва");
        vacancyPage().interactHeader().checkRegionNameInHeader("Москва");
        vacancyPage().refreshPageWithAdditional("?lead_city=Казань");

        vacancyPage().waitPageLoad();
        vacancyPage().interactHeader().checkRegionNameInHeader("Казань");
    }

    @TmsLink("51")
    @Story("Главная страница")
    @Test(description = "PM-51. Шапка (шаг 11)", groups = {HR_OPS_PARTNERS})
    public void testHomePageHeader11() {

        vacancyPage().goToPageCallCenterOperator();
        vacancyPage().waitPageLoad();
        vacancyPage().interactHeader().checkRegionTooltipVisible();
        vacancyPage().interactHeader().clickSelectAnotherRegionInTooltip();
        vacancyPage().interactHeader().interactRegionsModal().checkRegionsModalVisible();
        vacancyPage().interactHeader().interactRegionsModal().selectRegionByName("Москва");
        vacancyPage().interactHeader().checkRegionNameInHeader("Москва");

        vacancyPage().refreshPageWithAdditional("?lead_city=Вологда&utm_source=avito_chatbot");

        vacancyPage().waitPageLoad();
        vacancyPage().checkPageContains("&utm_source=avito_chatbot");
        vacancyPage().interactHeader().checkRegionNameInHeader("Вологда");
    }

    @TmsLink("55")
    @Story("Главная страница")
    @Test(description = "PM-55. Боди (шаг 1)", groups = {HR_OPS_PARTNERS})
    public void testHomePageBody1() {

        home().goToPage("?lead_city=Санкт-Петербург");
        home().waitPageLoad();

        home().interactMainBanner().checkBannerImageVisible();
        home().interactMainBanner().checkBannerTitle("ВАКАНСИИ В СБЕРМАРКЕТЕ");
        home().interactMainBanner().checkBannerSubtitle("Работа в быстрорастущей компании из экосистемы Сбера");
        home().interactMainBanner().checkBannerSalary("Доход до 125,000 рублей в месяц и бонусы");
        home().interactMainBanner().checkBannerShortDescription("Присоединяйтесь к команде и участвуйте в конкурсе с главным призом 1 000 000 ₽");

        home().checkVacanciesCount(5);

        home().interactCollector().checkVacancyPictureVisible();
        home().interactCollector().checkVacancyCardTitle(VacancyCards.collectorSPB().getHomePageTitle());
        home().interactCollector().checkVacancyCardDescription(VacancyCards.collectorSPB().getShortDescription());
        home().interactCollector().checkVacancyCardSalary(VacancyCards.collectorSPB().getHomePageSalary());
        home().interactCollector().checkVacancyCardAdvantages(VacancyCards.collectorSPB().getHomePageAdvantages());
        home().interactCollector().checkRespondButtonVisible();
        home().interactCollector().checkMoreInfoButtonVisible();

        home().interactCollectorCourier().scrollToVacancy();
        home().interactCollectorCourier().checkVacancyPictureVisible();
        home().interactCollectorCourier().checkVacancyCardTitle(VacancyCards.collectorCourierSPB().getHomePageTitle());
        home().interactCollectorCourier().checkVacancyCardDescription(VacancyCards.collectorCourierSPB().getShortDescription());
        home().interactCollectorCourier().checkVacancyCardSalary(VacancyCards.collectorCourierSPB().getHomePageSalary());
        home().interactCollectorCourier().checkVacancyCardAdvantages(VacancyCards.collectorCourierSPB().getHomePageAdvantages());
        home().interactCollectorCourier().checkRespondButtonVisible();
        home().interactCollectorCourier().checkMoreInfoButtonVisible();

        home().interactCollectorCourierWithAuto().scrollToVacancy();
        home().interactCollectorCourierWithAuto().checkVacancyPictureVisible();
        home().interactCollectorCourierWithAuto().checkVacancyCardTitle(VacancyCards.collectorCourierAutoSPB().getHomePageTitle());
        home().interactCollectorCourierWithAuto().checkVacancyCardDescription(VacancyCards.collectorCourierAutoSPB().getShortDescription());
        home().interactCollectorCourierWithAuto().checkVacancyCardSalary(VacancyCards.collectorCourierAutoSPB().getHomePageSalary());
        home().interactCollectorCourierWithAuto().checkVacancyCardAdvantages(VacancyCards.collectorCourierAutoSPB().getHomePageAdvantages());
        home().interactCollectorCourierWithAuto().checkRespondButtonVisible();
        home().interactCollectorCourierWithAuto().checkMoreInfoButtonVisible();

        home().interactDriverCourier().scrollToVacancy();
        home().interactDriverCourier().checkVacancyPictureVisible();
        home().interactDriverCourier().checkVacancyCardTitle(VacancyCards.driverCourierSPB().getHomePageTitle());
        home().interactDriverCourier().checkVacancyCardDescription(VacancyCards.driverCourierSPB().getShortDescription());
        home().interactDriverCourier().checkVacancyCardSalary(VacancyCards.driverCourierSPB().getHomePageSalary());
        home().interactDriverCourier().checkVacancyCardAdvantages(VacancyCards.driverCourierSPB().getHomePageAdvantages());
        home().interactDriverCourier().checkRespondButtonVisible();
        home().interactDriverCourier().checkMoreInfoButtonVisible();

        home().interactCallCenterOperator().scrollToVacancy();
        home().interactCallCenterOperator().checkVacancyPictureVisible();
        home().interactCallCenterOperator().checkVacancyCardTitle(VacancyCards.callCenterOperatorSPB().getHomePageTitle());
        home().interactCallCenterOperator().checkVacancyCardDescription(VacancyCards.callCenterOperatorSPB().getShortDescription());
        home().interactCallCenterOperator().checkVacancyCardSalary(VacancyCards.callCenterOperatorSPB().getHomePageSalary());
        home().interactCallCenterOperator().checkVacancyCardAdvantages(VacancyCards.callCenterOperatorSPB().getHomePageAdvantages());
        home().interactCallCenterOperator().checkRespondButtonVisible();
        home().interactCallCenterOperator().checkMoreInfoButtonVisible();

        home().interactCities().scrollToBlock();
        home().interactCities().checkTitle("Найдите свой город для работы");
        home().interactCities().checkSubtitle("Работаем более, чем в 120 городах России и продолжаем расширять свою географию");
        home().interactCities().checkMainCitiesListVisible();
        home().interactCities().checkMainCitiesListCount(12);
        home().interactCities().checkHiddenCitiesListNotVisible();
        home().interactCities().checkShowAllButtonVisible();

        home().interactFaq().scrollToBlock();
        home().interactFaq().checkTitle("Популярные вопросы");
        home().interactFaq().checkMainFAQListVisible();
        home().interactFaq().checkMainFAQListCount(3);
        home().interactFaq().checkExpandeAnswersNotVisible();
        home().interactFaq().checkMoreFAQButtonVisible();
    }

    @TmsLink("55")
    @Story("Главная страница")
    @Test(description = "PM-55. Боди (шаг 4)", groups = {HR_OPS_PARTNERS})
    public void testHomePageBody4() {

        home().goToPage("?lead_city=Санкт-Петербург");
        home().waitPageLoad();

        home().interactMainBanner().clickActionsButton("Смотреть вакансии");
        home().interactCollector().checkMoreInfoButtonVisible();
        home().interactCollectorCourier().checkMoreInfoButtonVisible();
    }

    @TmsLink("55")
    @Story("Главная страница")
    @Test(description = "PM-55. Боди (шаг 5).", groups = {HR_OPS_PARTNERS})
    public void testHomePageBody5() {

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCollector().clickTitle();
        vacancyPage().checkPageContains(VacancyCards.collectorMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorMSK().getVacancyPageTitle());

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCollectorCourier().clickTitle();
        vacancyPage().checkPageContains(VacancyCards.collectorCourierMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorCourierMSK().getVacancyPageTitle());

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCollectorCourierWithAuto().clickTitle();
        vacancyPage().checkPageContains(VacancyCards.collectorCourierAutoMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorCourierAutoMSK().getVacancyPageTitle());

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactDriverCourier().clickTitle();
        vacancyPage().checkPageContains(VacancyCards.driverCourierMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.driverCourierMSK().getVacancyPageTitle());

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCallCenterOperator().clickTitle();
        vacancyPage().checkPageContains(VacancyCards.callCenterOperatorMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.callCenterOperatorMSK().getVacancyPageTitle());
    }

    @TmsLink("55")
    @Story("Главная страница")
    @Test(description = "PM-55. Боди (шаг 6)", groups = {HR_OPS_PARTNERS})
    public void testHomePageBody6() {

        home().goToPage("?lead_city=Абакан");
        home().waitPageLoad();

        home().interactCollector().clickTitle();
        home().interactNotAvailableModal().checkModalVisible();
        home().interactNotAvailableModal().checkModalContent("В Абакане данная вакансия не найдена.");
        home().interactNotAvailableModal().close();
        home().interactNotAvailableModal().checkModalNotVisible();
    }

    @TmsLink("55")
    @Story("Главная страница")
    @Test(description = "PM-55. Боди (шаг 7)", groups = {HR_OPS_PARTNERS})
    public void testHomePageBody7() {

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCollector().clickMoreInfo();
        vacancyPage().checkPageContains(VacancyCards.collectorMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorMSK().getVacancyPageTitle());

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCollectorCourier().clickMoreInfo();
        vacancyPage().checkPageContains(VacancyCards.collectorCourierMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorCourierMSK().getVacancyPageTitle());

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCollectorCourierWithAuto().clickMoreInfo();
        vacancyPage().checkPageContains(VacancyCards.collectorCourierAutoMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorCourierAutoMSK().getVacancyPageTitle());

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactDriverCourier().clickMoreInfo();
        vacancyPage().checkPageContains(VacancyCards.driverCourierMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.driverCourierMSK().getVacancyPageTitle());

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCallCenterOperator().clickMoreInfo();
        vacancyPage().checkPageContains(VacancyCards.callCenterOperatorMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.callCenterOperatorMSK().getVacancyPageTitle());
    }

    @TmsLink("55")
    @Story("Главная страница")
    @Test(description = "PM-55. Боди (шаг 6)", groups = {HR_OPS_PARTNERS})
    public void testHomePageBody8() {

        home().goToPage("?lead_city=Абакан");
        home().waitPageLoad();

        home().interactCollector().clickMoreInfo();
        home().interactNotAvailableModal().checkModalVisible();
        home().interactNotAvailableModal().checkModalContent("В Абакане данная вакансия не найдена.");
        home().interactNotAvailableModal().close();
        home().interactNotAvailableModal().checkModalNotVisible();
    }

    @TmsLink("55")
    @Story("Главная страница")
    @Test(description = "PM-55. Боди (шаг 9)", groups = {HR_OPS_PARTNERS})
    public void testHomePageBody9() {

        home().goToPage("?lead_city=Абакан");
        home().waitPageLoad();

        home().interactCities().scrollToBlock();
        home().interactCities().clickCityByName("Москва");
        home().interactHeader().checkRegionButtonVisible();
        home().interactHeader().checkRegionNameInHeader("Москва");
    }

    @TmsLink("55")
    @Story("Главная страница")
    @Test(description = "PM-55. Боди (шаг 10)", groups = {HR_OPS_PARTNERS})
    public void testHomePageBody10() {

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactCities().scrollToBlock();
        home().interactCities().checkHiddenCitiesListNotVisible();

        home().interactCities().checkShowAllButtonVisible();
        home().interactCities().checkHideButtonNotVisible();

        home().interactCities().clickShowAllButton();
        home().interactCities().checkHiddenCitiesListVisible();
        home().interactCities().checkShowAllButtonNotVisible();

        home().interactCities().checkHideButtonVisible();

        home().interactCities().clickHideButton();
        home().interactCities().checkHiddenCitiesListNotVisible();
        home().interactCities().checkHideButtonNotVisible();
        home().interactCities().checkShowAllButtonVisible();
    }

    @TmsLink("53")
    @Story("Главная страница")
    @Test(description = "PM-53. Подвал (шаг 1)", groups = {HR_OPS_PARTNERS})
    public void testHomePageFooter1() {

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactFooter().scrollToBlock();
        home().interactFooter().checkLogoVisible();
        home().interactFooter().checkSocialsTitleVisible();
        home().interactFooter().checkSocialsTitle("Мы в соцсетях");
        home().interactFooter().checkSocialIconsVisible();
        home().interactFooter().checkSocialIconsCount(2);
        home().interactFooter().checkLeftInfoBlockVisible();
        home().interactFooter().checkLeftInfoBlockText(
                "По вопросам сотрудничества\n" +
                        "+7 (800) 500-49-54\n" +
                        "© 2013 — 2023\n" +
                        "ООО «Инстамарт Сервис»");
        home().interactFooter().checkRightInfoBlockVisible();
        home().interactFooter().checkRightInfoBlockText(
                "Россия, 115035, г. Москва, вн.тер.г.\n" +
                        "мун. округ Замоскворечье,\n" +
                        "\n" +
                        "ул. Садовническая, 9а, этаж 5, помещ. I, ком.1");
        home().interactFooter().checkBottomInfoBlockVisible();
        home().interactFooter().checkBottomInfoBlockText(
                "* Партнёры, имеющие статус «самозанятого» (плательщика налога на профессиональный доход) и ИП, " +
                        "не являются работниками СберМаркета. На таких партнёров не распространяются положения " +
                        "об обязательном социальном страховании, установленные трудовым законодательством. " +
                        "Партнёры могут воспользоваться предложениями по добровольному страхованию от СберСтрахования: " +
                        "Программа «Защита от травм», Пенсионное страхование, Страхование ответственности перед заказчиком");

    }

    @TmsLink("53")
    @Story("Главная страница")
    @Test(description = "PM-53. Подвал (шаг 4)", groups = {HR_OPS_PARTNERS})
    public void testHomePageFooter4() {

        home().goToPage("?lead_city=Москва");
        home().waitPageLoad();

        home().interactFooter().scrollToBlock();
        home().interactFooter().checkSocialIconsCount(2);
        home().interactFooter().clickSocialsByPosition(1);
        home().switchToNextWindow();
        home().waitPageLoad();
        home().checkPageContains(companyVkontakteLink);

        home().closeAndSwitchToPrevWindow();

        home().interactFooter().clickSocialsByPosition(2);
        home().switchToNextWindow();
        home().waitPageLoad();
        home().checkPageContains(companyTelegramLink);

    }
}


