package ru.instamart.test.reforged.hp_ops_partners;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.hr_ops_partners.faq.Faq;
import ru.instamart.kraken.data.hr_ops_partners.vacancy_cards.VacancyCards;
import ru.instamart.kraken.enums.CiPipelineSource;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.kraken.data.TestVariables.CompanyParams.companyTelegramLink;
import static ru.instamart.kraken.data.TestVariables.CompanyParams.companyVkontakteLink;
import static ru.instamart.reforged.Group.HR_OPS_PARTNERS;
import static ru.instamart.reforged.hr_ops_partners.page.HRPartnersRouter.home;
import static ru.instamart.reforged.hr_ops_partners.page.HRPartnersRouter.vacancyPage;

@Epic("HR Ops.Partners UI")
@Feature("Лендинг")
public final class VacanciesPageTests {

    private final String expectedRegionName = CiPipelineSource.isLocal() ? "Москва" : "Санкт-Петербург";

    @CaseId(58)
    @Story("Страница вакансий")
    @Test(description = "PM-58. Шапка (шаг 1-2)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageHeader1_2() {

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl());
        vacancyPage().waitPageLoad();

        vacancyPage().interactHeader().checkRegionTooltipVisible();
        vacancyPage().interactHeader().checkRegionNameInTooltip(expectedRegionName);
        vacancyPage().interactHeader().clickConfirmRegionInTooltip();

        vacancyPage().interactHeader().checkRegionTooltipNotVisible();
        vacancyPage().interactHeader().checkRegionNameInHeader(expectedRegionName);

        vacancyPage().refresh();

        vacancyPage().waitPageLoad();
        vacancyPage().interactHeader().checkRegionTooltipNotVisible();
        vacancyPage().interactHeader().checkRegionNameInHeader(expectedRegionName);

    }

    @CaseId(58)
    @Story("Страница вакансий")
    @Test(description = "PM-58. Шапка (шаг 3-4)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageHeader3_4() {

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl());
        vacancyPage().waitPageLoad();

        vacancyPage().interactHeader().checkRegionTooltipVisible();
        vacancyPage().interactHeader().clickSelectAnotherRegionInTooltip();

        vacancyPage().interactHeader().interactRegionsModal().checkRegionsModalVisible();
        vacancyPage().interactHeader().interactRegionsModal().selectRegionByName("Иваново");
        vacancyPage().interactHeader().interactRegionsModal().checkRegionsListNotVisible();

        vacancyPage().interactHeader().checkRegionNameInHeader("Иваново");
    }

    @CaseId(58)
    @Story("Страница вакансий")
    @Test(description = "PM-58. Шапка (шаг 5)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageHeader5() {

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl());
        vacancyPage().waitPageLoad();

        vacancyPage().interactHeader().checkRegionTooltipVisible();
        vacancyPage().interactHeader().clickSelectAnotherRegionInTooltip();

        vacancyPage().interactHeader().interactRegionsModal().checkRegionsModalVisible();
        vacancyPage().interactHeader().interactRegionsModal().selectRegionByName("Анадырь");
        vacancyPage().interactHeader().interactRegionsModal().checkRegionsListNotVisible();

        home().waitPageLoad();
        home().interactNotAvailableModal().checkModalNotVisible();
        home().interactNotAvailableModal().checkModalContent("В Анадыре данная вакансия не найдена.");

        home().interactHeader().checkRegionNameInHeader("Анадырь");
    }

    //TODO разобраться с подменой геолокации, добавить шаги 6-7

    @CaseId(58)
    @Story("Страница вакансий")
    @Test(description = "PM-58. Шапка (шаг 8)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageHeader8() {

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorMSK().getVacancyPageTitle());
        vacancyPage().interactHeader().clickLogo();
        home().waitPageLoad();
        home().interactCollector().checkVacancyPictureVisible();
        home().interactMainBanner().checkBannerTitle("ВАКАНСИИ В СБЕРМАРКЕТЕ");

        vacancyPage().goToPage(VacancyCards.collectorCourierMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorCourierMSK().getVacancyPageTitle());
        vacancyPage().interactHeader().clickLogo();
        home().waitPageLoad();
        home().interactCollector().checkVacancyPictureVisible();
        home().interactMainBanner().checkBannerTitle("ВАКАНСИИ В СБЕРМАРКЕТЕ");

        vacancyPage().goToPage(VacancyCards.collectorCourierAutoMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorCourierAutoMSK().getVacancyPageTitle());
        vacancyPage().interactHeader().clickLogo();
        home().waitPageLoad();
        home().interactCollector().checkVacancyPictureVisible();
        home().interactMainBanner().checkBannerTitle("ВАКАНСИИ В СБЕРМАРКЕТЕ");

        vacancyPage().goToPage(VacancyCards.driverCourierMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.driverCourierMSK().getVacancyPageTitle());
        vacancyPage().interactHeader().clickLogo();
        home().waitPageLoad();
        home().interactCollector().checkVacancyPictureVisible();
        home().interactMainBanner().checkBannerTitle("ВАКАНСИИ В СБЕРМАРКЕТЕ");

        vacancyPage().goToPage(VacancyCards.callCenterOperatorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.callCenterOperatorMSK().getVacancyPageTitle());
        vacancyPage().interactHeader().clickLogo();
        home().waitPageLoad();
        home().interactCollector().checkVacancyPictureVisible();
        home().interactMainBanner().checkBannerTitle("ВАКАНСИИ В СБЕРМАРКЕТЕ");
    }

    @CaseId(60)
    @Story("Страница вакансий")
    @Test(description = "PM-60. Боди (шаг 1)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageBody1() {

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().interactMainBanner().checkBannerImageVisible();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorMSK().getVacancyPageTitle());
        vacancyPage().interactMainBanner().checkBannerSubtitle(VacancyCards.collectorMSK().getHomePageSalary());
        vacancyPage().interactMainBanner().checkBannerShortDescription(VacancyCards.collectorMSK().getShortDescription());
        vacancyPage().interactMainBanner().checkActionsButtonVisible(VacancyCards.collectorMSK().getActionButtonText());
        vacancyPage().interactMainBanner().checkFullDescriptionTitleVisible();
        vacancyPage().interactMainBanner().checkFullDescriptionText(VacancyCards.collectorMSK().getFullDescription());

        vacancyPage().scrollToAdvantagesBlock();
        vacancyPage().checkAdvantagesBlockTitleVisible();
        vacancyPage().checkVacancyAdvantages(VacancyCards.collectorMSK().getVacancyPageAdvantages());

        vacancyPage().scrollToApplyBlock();
        vacancyPage().checkApplyFormBlockTitleVisible();
        vacancyPage().checkApplyBlockSteps();

        vacancyPage().scrollToAnotherVacanciesBlock();
        vacancyPage().checkAnotherVacanciesBlocTitleVisible();
        vacancyPage().interactCollectorCourier().checkVacancyPictureVisible();
        vacancyPage().interactCollectorCourier().checkVacancyCardTitle(VacancyCards.collectorCourierMSK().getAnotherVacancyCardOnVacancyPageTitle());
        vacancyPage().interactCollectorCourier().checkVacancyCardDescription(VacancyCards.collectorCourierMSK().getAnotherVacancyCardOnVacancyPageDescription());
        vacancyPage().interactCollectorCourier().checkMoreInfoButtonVisible();

        vacancyPage().interactDriverCourier().checkVacancyPictureVisible();
        vacancyPage().interactDriverCourier().checkVacancyCardTitle(VacancyCards.driverCourierMSK().getAnotherVacancyCardOnVacancyPageTitle());
        vacancyPage().interactDriverCourier().checkVacancyCardDescription(VacancyCards.driverCourierMSK().getAnotherVacancyCardOnVacancyPageDescription());
        vacancyPage().interactDriverCourier().checkMoreInfoButtonVisible();

        vacancyPage().interactCollectorCourierAvto().checkVacancyPictureVisible();
        vacancyPage().interactCollectorCourierAvto().checkVacancyCardTitle(VacancyCards.collectorCourierAutoMSK().getAnotherVacancyCardOnVacancyPageTitle());
        vacancyPage().interactCollectorCourierAvto().checkVacancyCardDescription(VacancyCards.collectorCourierAutoMSK().getAnotherVacancyCardOnVacancyPageDescription());
        vacancyPage().interactCollectorCourierAvto().checkMoreInfoButtonVisible();

        vacancyPage().interactCities().scrollToBlock();
        vacancyPage().interactCities().checkTitle("Найдите свой город для работы");
        vacancyPage().interactCities().checkSubtitle("Работаем более, чем в 120 городах России и продолжаем расширять свою географию");
        vacancyPage().interactCities().checkMainCitiesListVisible();
        vacancyPage().interactCities().checkMainCitiesListCount(12);
        vacancyPage().interactCities().checkHiddenCitiesListNotVisible();
        vacancyPage().interactCities().checkShowAllButtonVisible();

        vacancyPage().interactFaq().scrollToBlock();
        vacancyPage().interactFaq().checkTitle("Популярные вопросы");
        vacancyPage().interactFaq().checkMainFAQListVisible();
        vacancyPage().interactFaq().checkMainFAQListCount(3);
        vacancyPage().interactFaq().checkExpandeAnswersNotVisible();
        vacancyPage().interactFaq().checkMoreFAQButtonVisible();
    }

    @CaseId(60)
    @Story("Страница вакансий")
    @Test(description = "PM-60. Боди (шаг 2)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageBody2() {
        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().scrollToAnotherVacanciesBlock();
        vacancyPage().interactCollectorCourier().clickTitle();
        vacancyPage().checkPageContains(VacancyCards.collectorCourierMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorCourierMSK().getVacancyPageTitle());

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().scrollToAnotherVacanciesBlock();
        vacancyPage().interactCollectorCourierAvto().clickTitle();
        vacancyPage().checkPageContains(VacancyCards.collectorCourierAutoMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorCourierAutoMSK().getVacancyPageTitle());

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().scrollToAnotherVacanciesBlock();
        vacancyPage().interactDriverCourier().clickTitle();
        vacancyPage().checkPageContains(VacancyCards.driverCourierMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.driverCourierMSK().getVacancyPageTitle());
    }

    @CaseId(60)
    @Story("Страница вакансий")
    @Test(description = "PM-60. Боди (шаг 3)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageBody3() {
        vacancyPage().goToPage(VacancyCards.collectorCourierAutoMSK().getPageUrl() + "?lead_city=Кострома");
        vacancyPage().waitPageLoad();

        vacancyPage().scrollToAnotherVacanciesBlock();
        vacancyPage().checkAnotherVacancyCardsCount(1);
    }

    @CaseId(60)
    @Story("Страница вакансий")
    @Test(description = "PM-60. Боди (шаг 4)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageBody4() {
        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().scrollToAnotherVacanciesBlock();
        vacancyPage().interactCollectorCourier().clickMoreInfo();
        vacancyPage().checkPageContains(VacancyCards.collectorCourierMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorCourierMSK().getVacancyPageTitle());

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().scrollToAnotherVacanciesBlock();
        vacancyPage().interactCollectorCourierAvto().clickMoreInfo();
        vacancyPage().checkPageContains(VacancyCards.collectorCourierAutoMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.collectorCourierAutoMSK().getVacancyPageTitle());

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().scrollToAnotherVacanciesBlock();
        vacancyPage().interactDriverCourier().clickMoreInfo();
        vacancyPage().checkPageContains(VacancyCards.driverCourierMSK().getPageUrl());
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.driverCourierMSK().getVacancyPageTitle());
    }

    @CaseId(60)
    @Story("Страница вакансий")
    @Test(description = "PM-60. Боди (шаг 5)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageBody5() {

        vacancyPage().goToPage(VacancyCards.collectorCourierAutoMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().interactCities().scrollToBlock();
        vacancyPage().interactCities().clickCityByName("Санкт-Петербург");
        vacancyPage().interactHeader().checkRegionButtonVisible();
        vacancyPage().interactHeader().checkRegionNameInHeader("Санкт-Петербург");
    }

    @CaseId(60)
    @Story("Страница вакансий")
    @Test(description = "PM-60. Боди (шаг 6)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageBody6() {

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().interactCities().scrollToBlock();
        vacancyPage().interactCities().checkHiddenCitiesListNotVisible();

        vacancyPage().interactCities().checkShowAllButtonVisible();
        vacancyPage().interactCities().checkHideButtonNotVisible();

        vacancyPage().interactCities().clickShowAllButton();
        vacancyPage().interactCities().checkHiddenCitiesListVisible();
        vacancyPage().interactCities().checkShowAllButtonNotVisible();

        vacancyPage().interactCities().checkHideButtonVisible();

        vacancyPage().interactCities().clickHideButton();
        vacancyPage().interactCities().checkHiddenCitiesListNotVisible();
        vacancyPage().interactCities().checkHideButtonNotVisible();
        vacancyPage().interactCities().checkShowAllButtonVisible();
    }

    @CaseId(60)
    @Story("Страница вакансий")
    @Test(description = "PM-60. Боди (шаг 7)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageBody7() {

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().interactFaq().scrollToBlock();
        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(0).getQuestion());
        vacancyPage().interactFaq().checkAnswerVisible(Faq.getFaq().get(0).getQuestion());
        vacancyPage().interactFaq().checkExpandedAnswersSize(1);

        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(0).getQuestion());
        vacancyPage().interactFaq().checkAnswerNotVisible(Faq.getFaq().get(0).getQuestion());
        vacancyPage().interactFaq().checkExpandeAnswersNotVisible();

        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(1).getQuestion());
        vacancyPage().interactFaq().checkAnswerVisible(Faq.getFaq().get(1).getQuestion());
        vacancyPage().interactFaq().checkExpandedAnswersSize(1);

        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(1).getQuestion());
        vacancyPage().interactFaq().checkAnswerNotVisible(Faq.getFaq().get(1).getQuestion());
        vacancyPage().interactFaq().checkExpandeAnswersNotVisible();

        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(2).getQuestion());
        vacancyPage().interactFaq().checkAnswerVisible(Faq.getFaq().get(2).getQuestion());
        vacancyPage().interactFaq().checkExpandedAnswersSize(1);

        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(2).getQuestion());
        vacancyPage().interactFaq().checkAnswerNotVisible(Faq.getFaq().get(2).getQuestion());
        vacancyPage().interactFaq().checkExpandeAnswersNotVisible();

        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(0).getQuestion());
        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(1).getQuestion());
        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(2).getQuestion());
        vacancyPage().interactFaq().checkExpandedAnswersSize(3);

        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(0).getQuestion());
        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(1).getQuestion());
        vacancyPage().interactFaq().clickFAQTitleByName(Faq.getFaq().get(2).getQuestion());
        vacancyPage().interactFaq().checkExpandeAnswersNotVisible();
    }

    @CaseId(60)
    @Story("Страница вакансий")
    @Test(description = "PM-60. Боди (шаг 8-9)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageBody89() {

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().interactFaq().scrollToBlock();
        vacancyPage().interactFaq().checkHiddenFAQListNotVisible();

        vacancyPage().interactFaq().clickMoreFAQ();
        vacancyPage().interactFaq().checkHiddenFAQListVisible();
        vacancyPage().interactFaq().checkLessFAQButtonVisible();
        vacancyPage().interactFaq().checkMoreFAQButtonNotVisible();

        vacancyPage().interactFaq().clickLessFAQ();
        vacancyPage().interactFaq().checkHiddenFAQListNotVisible();
        vacancyPage().interactFaq().checkLessFAQButtonNotVisible();
        vacancyPage().interactFaq().checkMoreFAQButtonVisible();
    }

    @CaseId(60)
    @Story("Страница вакансий")
    @Test(description = "PM-60. Боди (шаг 10-11)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageBody1011() {

        vacancyPage().goToPage(VacancyCards.callCenterOperatorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().interactMainBanner().checkBannerImageVisible();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.callCenterOperatorMSK().getVacancyPageTitle());
        vacancyPage().interactMainBanner().checkBannerSubtitle(VacancyCards.callCenterOperatorMSK().getVacancyPageSalary());

        vacancyPage().goToPage(VacancyCards.callCenterOperatorMSK().getPageUrl() + "?lead_city=Иваново");
        vacancyPage().waitPageLoad();

        vacancyPage().interactMainBanner().checkBannerImageVisible();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.callCenterOperatorMSK().getVacancyPageTitle());
        vacancyPage().interactMainBanner().checkBannerSubtitle(VacancyCards.callCenterOperatorMSK().getVacancyPageSalary());

        vacancyPage().goToPage(VacancyCards.callCenterOperatorMSK().getPageUrl() + "?lead_city=Вологда");
        vacancyPage().waitPageLoad();

        vacancyPage().interactMainBanner().checkBannerImageVisible();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.callCenterOperatorMSK().getVacancyPageTitle());
        vacancyPage().interactMainBanner().checkBannerSubtitle(VacancyCards.callCenterOperatorMSK().getVacancyPageSalary());

        vacancyPage().goToPage(VacancyCards.callCenterOperatorMSK().getPageUrl() + "?lead_city=Орел");
        vacancyPage().waitPageLoad();

        vacancyPage().interactMainBanner().checkBannerImageVisible();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.callCenterOperatorOrel().getVacancyPageTitle());
        vacancyPage().interactMainBanner().checkBannerSubtitle(VacancyCards.callCenterOperatorMSK().getVacancyPageSalary());

        vacancyPage().goToPage(VacancyCards.callCenterOperatorMSK().getPageUrl() + "?lead_city=Томск");
        vacancyPage().waitPageLoad();

        vacancyPage().interactMainBanner().checkBannerImageVisible();
        vacancyPage().interactMainBanner().checkBannerTitle(VacancyCards.callCenterOperatorTomsk().getVacancyPageTitle());
        vacancyPage().interactMainBanner().checkBannerSubtitle(VacancyCards.callCenterOperatorMSK().getVacancyPageSalary());
    }

    @CaseId(59)
    @Story("Страница вакансий")
    @Test(description = "PM-59. Подвал (шаг 1)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageFooter1() {

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().interactFooter().scrollToBlock();
        vacancyPage().interactFooter().checkLogoVisible();
        vacancyPage().interactFooter().checkSocialsTitleVisible();
        vacancyPage().interactFooter().checkSocialsTitle("Мы в соцсетях");
        vacancyPage().interactFooter().checkSocialIconsVisible();
        vacancyPage().interactFooter().checkSocialIconsCount(2);
        vacancyPage().interactFooter().checkLeftInfoBlockVisible();
        vacancyPage().interactFooter().checkLeftInfoBlockText(
                "По вопросам сотрудничества\n" +
                        "+7 (800) 500-49-54\n" +
                        "© 2013 — 2022\n" +
                        "ООО «Инстамарт Сервис»");
        vacancyPage().interactFooter().checkRightInfoBlockVisible();
        vacancyPage().interactFooter().checkRightInfoBlockText(
                "Россия, 115035, г. Москва, вн.тер.г.\n" +
                        "мун. округ Замоскворечье,\n" +
                        "\n" +
                        "ул. Садовническая, 9а, этаж 5, помещ. I, ком.1");
        vacancyPage().interactFooter().checkBottomInfoBlockVisible();
        vacancyPage().interactFooter().checkBottomInfoBlockText(
                "* Партнёры, имеющие статус «самозанятого» (плательщика налога на профессиональный доход) и ИП, " +
                        "не являются работниками СберМаркета. На таких партнёров не распространяются положения " +
                        "об обязательном социальном страховании, установленные трудовым законодательством. " +
                        "Партнёры могут воспользоваться предложениями по добровольному страхованию от СберСтрахования: " +
                        "Программа «Защита от травм», Пенсионное страхование, Страхование ответственности перед заказчиком");

    }

    @CaseId(59)
    @Story("Страница вакансий")
    @Test(description = "PM-59. Подвал (шаг 4)", groups = {HR_OPS_PARTNERS})
    public void testVacancyPageFooter4() {

        vacancyPage().goToPage(VacancyCards.collectorMSK().getPageUrl() + "?lead_city=Москва");
        vacancyPage().waitPageLoad();

        vacancyPage().interactFooter().scrollToBlock();
        vacancyPage().interactFooter().checkSocialIconsCount(2);
        vacancyPage().interactFooter().clickSocialsByPosition(1);
        vacancyPage().switchToNextWindow();
        vacancyPage().waitPageLoad();
        vacancyPage().checkPageContains(companyVkontakteLink);

        vacancyPage().closeAndSwitchToPrevWindow();

        vacancyPage().interactFooter().clickSocialsByPosition(2);
        vacancyPage().switchToNextWindow();
        vacancyPage().waitPageLoad();
        vacancyPage().checkPageContains(companyTelegramLink);
    }
}
