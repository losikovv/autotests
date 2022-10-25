package ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.collector;

import io.qameta.allure.Step;

public final class Collector implements CollectorCheck {

    @Step("Нажимаем на заголовок вакансии 'Cборщик заказов'")
    public void clickTitle(){
        vacancyCartTitle.click();
    }

    @Step("Нажимаем 'Подробнее' в карточке вакансии 'Cборщик заказов'")
    public void clickMoreInfo() {
        moreInfo.click();
    }
}
