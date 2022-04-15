package ru.instamart.reforged.admin.page.retailers.rank_list_sidebar;

import io.qameta.allure.Step;

public class RankListSidebar implements RankListSidebarCheck {

    @Step("Нажимаем 'Применить'")
    public void clickSubmit() {
        submit.click();
    }

    @Step("Нажимаем 'Отменить'")
    public void clickCancel() {
        cancel.click();
    }
}
