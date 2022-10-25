package ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.collector_courier_avto;

import io.qameta.allure.Step;

public final class CollectorCourierAvto implements CollectorCourierAvtoCheck {

    @Step("Нажимаем на заголовок вакансии 'Сборщик-курьер на авто'")
    public void clickTitle(){
        vacancyCartTitle.click();
    }

    @Step("Нажимаем 'Подробнее' в карточке вакансии 'Сборщик-курьер на авто'")
    public void clickMoreInfo() {
        moreInfo.click();
    }
}
