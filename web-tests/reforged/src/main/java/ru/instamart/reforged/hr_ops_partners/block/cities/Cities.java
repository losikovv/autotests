package ru.instamart.reforged.hr_ops_partners.block.cities;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.ThreadUtil;

public final class Cities implements CitiesCheck {


    @Step("Скроллим к блоку 'Найдите свой город для работы'")
    public void scrollToBlock() {
        title.scrollTo();
    }

    @Step("Нажимаем кнопку 'Показать все'")
    public void clickShowAllButton(){
        showAllButton.click();
    }

    @Step("Нажимаем кнопку 'Скрыть'")
    public void clickHideButton(){
        ThreadUtil.simplyAwait(1);
        hideButton.click();
    }

    @Step("Нажимаем на город: '{cityName}'")
    public void clickCityByName(final String cityName){
        allCities.clickOnElementWithText(cityName);
    }
}
