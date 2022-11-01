package ru.instamart.reforged.hr_ops_partners.block.faq;

import io.qameta.allure.Step;

public final class Faq implements FaqCheck {


    @Step("Скроллим к блоку 'Популярный вопросы'")
    public void scrollToBlock() {
        title.scrollTo();
    }

    @Step("Нажимаем кнопку 'Больше вопросов>'")
    public void clickMoreFAQ() {
        moreFaq.click();
    }

    @Step("Нажимаем кнопку 'Меньше вопросов>'")
    public void clickLessFAQ() {
        lessFaq.click();
    }

    @Step("Нажимаем на заголовок вопроса: '{faqTitle}'")
    public void clickFAQTitleByName(final String faqTitle) {
        allFaqsList.clickOnElementWithText(faqTitle);
    }
}
