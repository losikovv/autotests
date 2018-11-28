package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

public class DetectionHelper extends HelperBase {

    private ApplicationManager kraken;

    DetectionHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /** Определить находимся на сайте или нет */
    public boolean isOnSite() {
        return isElementPresent(Elements.Site.footer());
    }

    /** Определить находимся на лендинге или нет */
    public boolean isOnLanding() {
        return isElementPresent(Elements.Site.landing());
    }

    /** Определить находимся в админке или нет */
    public boolean isInAdmin() {
        return isElementPresent(Elements.Admin.container());
    }

    /** Определить 404 ошибку */
    public boolean is404() {
        return isElementDetected(Elements.Page404.title());
    }

    /** Определить 500 ошибку */
    public boolean is500() {
        return isElementDetected(Elements.Page500.placeholder());
    }

    /** Определить открыта ли модалка "Модалка" */
    public boolean isDeliveryModalOpen() {
        return isElementDisplayed(Elements.Site.DeliveryModal.popup())
                && isElementDetected(Elements.Site.DeliveryModal.title());
    }

    /** Определить открыта ли модалка "Оплата" */
    public boolean isPaymentModalOpen() {
        return isElementDisplayed(Elements.Site.PaymentModal.popup())
                && isElementDetected(Elements.Site.PaymentModal.title());
    }

    /** Определить открыт ли модалка "Партнеры" */
    public boolean isPartnersModalOpen() {
        return isElementDisplayed(Elements.Site.PartnersModal.popup())
                && isElementDetected(Elements.Site.PartnersModal.title());
    }

    /** Определить открыто ли меню аккаунта */
    public boolean isAccountMenuOpen() {
        return isElementDisplayed(Elements.Site.AccountMenu.popup());
    }

    /** Определить пустой результат поиска */
    public boolean isSearchResultsEmpty() {
        if(isElementPresent(Elements.Site.Catalog.emptySearchPlaceholder())){
            printMessage("Empty search results");
            return true;
        } else return false;
    }

    /** Определить открыта ли модалка авторизации/регистрации */
    public boolean isAuthModalOpen() {
        return isElementDisplayed(Elements.Site.AuthModal.popup());
    }

    /** Определить авторизован ли пользователь */
    public boolean isUserAuthorised() {
        printMessage("Checking authorisation...");
        if (isElementDetected(Elements.Site.Header.profileButton())) {
            printMessage("✓ Authorised\n");
            return true;
        } else {
            printMessage("No auth!\n");
            return false;
        }
    }

    /** Определить отправлена ли форма восстановления пароля */
    public boolean isRecoverySent(){
        if (!isElementDisplayed(Elements.Site.AuthModal.popup())
                || isElementPresent(Elements.Site.AuthModal.emailField())) {
            printMessage("Recovery not sent!");
            return false;
        } else {
            printMessage("✓ Recovery requested");
            return true;
        }
    }

    /** Распознавание документов к заказу */
    public String orderDocument(int position) {
        Elements.Site.OrderDetailsPage.documentation(position);
        String docName = fetchText(Elements.getLocator());
        if (docName != null) {
            printMessage("Скачиваем: " + docName);
            return docName;
        } else {
            printMessage("Документ отсутствует\n");
            return null;
        }
    }
}
