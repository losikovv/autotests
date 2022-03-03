package ru.instamart.reforged.business.page.user.companies.companyInfo;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.block.header.B2BHeader;
import ru.instamart.reforged.business.page.BusinessPage;

public final class B2BCompanyInfoPage implements BusinessPage, B2BCompanyInfoCheck {

    public B2BHeader interactHeader() {
        return header;
    }

    @Step("Наводим курсор мыши на кнопку обновления информации о балансе на счёте")
    public void hoverAccountAmountRefreshButton() {
        refreshAccountInfo.getActions().mouseOver();
    }

    @Step("Наводим курсор мыши на иконку дополнительной информации о состоянии счёта")
    public void hoverAccountAmountAdditionalInfo() {
        accountAbout.getActions().mouseOver();
    }

    @Step("Нажимаем на кнопку обновления информации о состоянии счёта")
    public void clickAccountAmountRefreshButton() {
        refreshAccountInfo.click();
    }

    @Step("Нажимаем на кнопку 'Вперёд' списка пользователей компании")
    public void clickGoForwardButton() {
        goForward.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
