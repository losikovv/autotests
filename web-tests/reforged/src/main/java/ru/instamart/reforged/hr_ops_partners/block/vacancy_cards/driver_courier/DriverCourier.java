package ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.driver_courier;

import io.qameta.allure.Step;

public final class DriverCourier implements DriverCourierCheck {

    @Step("Скроллим к карточке вакансии 'Водитель-курьер'")
    public void scrollToVacancy() {
        respond.scrollTo();
    }

    @Step("Нажимаем на заголовок вакансии 'Водитель-курьер'")
    public void clickTitle() {
        vacancyCartTitle.click();
    }

    @Step("Нажимаем 'Откликнуться' в карточке вакансии 'Водитель-курьер'")
    public void clickRespond() {
        respond.click();
    }

    @Step("Нажимаем 'Подробнее' в карточке вакансии 'Водитель-курьер'")
    public void clickMoreInfo() {
        moreInfo.click();
    }

}
