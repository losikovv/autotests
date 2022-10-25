package ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.driver_courier;

import io.qameta.allure.Step;

public final class DriverCourier implements DriverCourierCheck {

    @Step("Нажимаем на заголовок вакансии 'Водитель-курьер'")
    public void clickTitle(){
        vacancyCartTitle.click();
    }

    @Step("Нажимаем 'Подробнее' в карточке вакансии 'Водитель-курьер'")
    public void clickMoreInfo() {
        moreInfo.click();
    }
}
