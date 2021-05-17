package ru.instamart.ui.module;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.common.pagesdata.ElementData;

@Slf4j
public class Base {

    public static AppManager kraken;

    public Base(final AppManager kraken) {
        Base.kraken = kraken;
    }

    /** Обработать алерт в зависимости от настройки acceptNextAlert */
    public static void handleAlertAcceptByDefault() {
        try {
            log.info("> вызываем алерт");
            AppManager.getWebDriver().switchTo().alert().accept();
        }catch (Exception exception){
            log.info("> аллерт убран");
        }
    }

    @Step("проверяем наличие рекламных банеров на странице")
    public static void catchAndCloseAd(ElementData data, int timer){
        if (kraken.await().fluentlyPossibleAppearance(
                ExpectedConditions.elementToBeClickable(
                        data.getLocator()),
                "\n> поп-ап с рекламой не появился",
                timer)) {
            log.info("> на странице обнаружен рекламный баннер");
//            JavascriptExecutor js;

//            js = (JavascriptExecutor) kraken.getWebDriver();
//            js.executeScript("return (document.evaluate({}, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue)[0].remove();",data);
////            ((JavascriptExecutor)kraken.getWebDriver()).executeScript("document.getElementById('jvlabelWrap').remove();");
            do {
//                WaitingHelper.simply(1);
                if (kraken.detect().isPromoModalOpen(data)) {
                    kraken.perform().click(data);
                }
            } while (kraken.detect().isPromoModalOpen(data));
        }
        log.info("> все хорошо, на странице нет рекламных баннеров");
    }
}
