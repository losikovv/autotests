package ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.collector_courier;

import io.qameta.allure.Step;

public final class CollectorCourier implements CollectorCourierCheck {

    @Step("Нажимаем на заголовок вакансии 'Сборщик-курьер'")
    public void clickTitle(){
        vacancyCartTitle.click();
    }

    @Step("Нажимаем 'Подробнее' в карточке вакансии 'Сборщик-курьер'")
    public void clickMoreInfo() {
        moreInfo.click();
    }
}
