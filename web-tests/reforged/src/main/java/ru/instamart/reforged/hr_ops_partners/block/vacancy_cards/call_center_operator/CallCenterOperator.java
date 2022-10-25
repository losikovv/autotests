package ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.call_center_operator;

import io.qameta.allure.Step;

public final class CallCenterOperator implements CallCenterOperatorCheck {

    @Step("Скроллим к карточке вакансии 'Оператор контактного центра'")
    public void scrollToVacancy() {
        respond.scrollTo();
    }

    @Step("Нажимаем на заголовок вакансии 'Оператор контактного центра'")
    public void clickTitle() {
        vacancyCartTitle.click();
    }

    @Step("Нажимаем 'Откликнуться' в карточке вакансии 'Оператор контактного центра'")
    public void clickRespond() {
        respond.click();
    }

    @Step("Нажимаем 'Подробнее' в карточке вакансии 'Оператор контактного центра'")
    public void clickMoreInfo() {
        moreInfo.click();
    }

}
