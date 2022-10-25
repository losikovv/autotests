package ru.instamart.reforged.hr_ops_partners.frame.regions_modal;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.ThreadUtil;

public class RegionsModal implements RegionsModalCheck {

    @Step("Нажимаем 'Х' (закрыть)")
    public void close() {
        close.click();
    }

    @Step("Вводим в поле 'Найти' текст: '{inputText}'")
    public void fillInput(final String inputText) {
        regionInput.fill(inputText);
    }

    @Step("Выбираем из списка: {regionName}")
    public void selectRegionByName(final String regionName) {
        ThreadUtil.simplyAwait(1);
        regionsList.clickOnElementWithText(regionName);
    }
}
