package ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.collector_courier;

import io.qameta.allure.Step;

public final class CollectorCourier implements CollectorCourierCheck {

    @Step("Скроллим к карточке вакансии 'Cборщик-курьер'")
    public void scrollToVacancy() {
        respond.scrollTo();
    }

    @Step("Нажимаем на заголовок вакансии 'Cборщик-курьер'")
    public void clickTitle() {
        vacancyCartTitle.click();
    }

    @Step("Нажимаем 'Откликнуться' в карточке вакансии 'Cборщик-курьер'")
    public void clickRespond() {
        respond.click();
    }

    @Step("Нажимаем 'Подробнее' в карточке вакансии 'Cборщик-курьер'")
    public void clickMoreInfo() {
        moreInfo.click();
    }

}
