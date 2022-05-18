package ru.instamart.reforged.next.page.notfound;

import io.qameta.allure.Step;
import ru.instamart.reforged.next.block.helpdesk.HelpDesk;
import ru.instamart.reforged.next.page.StfPage;

public final class Page404 implements StfPage, Page404Check {

    public HelpDesk interactHelpDesk() {
        return helpDesk;
    }

    @Step("Нажать по кнопке 'Вернуться на главную'")
    public void clickToGoToMainPage() {
        goToMain.click();
    }

    @Override
    public String pageUrl() {
        return "nowhere";
    }
}
