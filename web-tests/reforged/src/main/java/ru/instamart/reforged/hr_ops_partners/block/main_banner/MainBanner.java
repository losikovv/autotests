package ru.instamart.reforged.hr_ops_partners.block.main_banner;

import io.qameta.allure.Step;

public final class MainBanner implements MainBannerCheck {

    @Step("Нажимаем кнопку '{buttonText}' на главном баннере")
    public void clickActionsButton(final String buttonText){
        vacancyActionsButton.click(buttonText);
    }
}
