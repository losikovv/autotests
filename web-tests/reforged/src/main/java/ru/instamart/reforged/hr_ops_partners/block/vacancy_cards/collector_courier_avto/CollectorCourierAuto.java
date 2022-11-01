package ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.collector_courier_avto;

import io.qameta.allure.Step;

public final class CollectorCourierAuto implements CollectorCourierAutoCheck {

    @Step("Скроллим к карточке вакансии 'Cборщик-курьер на авто'")
    public void scrollToVacancy() {
        respond.scrollTo();
    }

    @Step("Нажимаем на заголовок вакансии 'Cборщик-курьер на авто'")
    public void clickTitle() {
        vacancyCartTitle.click();
    }

    @Step("Нажимаем 'Откликнуться' в карточке вакансии 'Cборщик-курьер на авто'")
    public void clickRespond() {
        respond.click();
    }

    @Step("Нажимаем 'Подробнее' в карточке вакансии 'Cборщик-курьер на авто'")
    public void clickMoreInfo() {
        moreInfo.click();
    }

}
