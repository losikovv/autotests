package instamart.ui.modules;

import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.ElementData;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base {

    private static final Logger log = LoggerFactory.getLogger(Base.class);

    public static AppManager kraken;

    Base(final AppManager kraken) {
        Base.kraken = kraken;
    }

    /** Обработать алерт в зависимости от настройки acceptNextAlert */
    public static void handleAlert() {
        Alert alert = kraken.getWebDriver().switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        log.info("> handling alert [{}]", alertText);
    }

    @Step("проверяем наличие рекламных банеров на странице")
    public static void catchAndCloseAd(ElementData data, int timer){
        if (kraken.await().fluentlyPossibleAppearance(
                ExpectedConditions.elementToBeClickable(
                        data.getLocator()),
                "\n> поп-ап с рекламой не появился",timer)) {
            JavascriptExecutor js;
            log.info("> на странице обнаружен рекламный баннер");
            js = (JavascriptExecutor) kraken.getWebDriver();
            js.executeScript("return (document.evaluate({}, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue)[0].remove();",data);
//            do {
//
//                WaitingHelper.simply(1);
//                if (kraken.detect().isPromoModalOpen(data)) {
//                    kraken.perform().click(data);
//                }
//            } while (kraken.detect().isPromoModalOpen(data));
        }
        log.info("> все хорошо, на странице нет рекламных баннеров");
    }
}
