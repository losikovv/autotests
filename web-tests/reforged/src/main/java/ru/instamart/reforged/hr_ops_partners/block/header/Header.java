package ru.instamart.reforged.hr_ops_partners.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.hr_ops_partners.frame.regions_modal.RegionsModal;

public final class Header implements HeaderCheck {

    public RegionsModal interactRegionsModal() {
        return regionsModal;
    }

    @Step("Нажимаем на логотип Сбермаркета")
    public void clickLogo(){
        logo.click();
    }

    @Step("Нажимаем 'Да' в тултипе автоопределенного города")
    public void clickConfirmRegionInTooltip(){
        confirmRegion.click();
    }

    @Step("Нажимаем 'Выбрать другой' в тултипе автоопределенного города")
    public void clickSelectAnotherRegionInTooltip(){
        selectAnotherRegion.click();
    }

    @Step("Нажимаем кнопку выбранного города")
    public void clickRegionSelectButton(){
        selectedCity.click();
    }

}
