package ru.instamart.autotests.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.ApplicationManager;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.models.EnvironmentData;


// Basic test class


public class TestBase {

    protected static final ApplicationManager kraken = new ApplicationManager(BrowserType.CHROME);


    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        kraken.rise();
    }


    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        kraken.stop();
    }


    /**
     * Проверить возможность перехода на страницу и ее доступность
     */
    void assertPageIsAvailable(Pages page) throws AssertionError {
        assertPageIsAvailable(kraken.grab().fullBaseUrl + Pages.getPagePath());
    }

    /**
     * Проверить возможность перехода на страницу по указанному url и ее доступность
     */
    void assertPageIsAvailable(String URL) throws AssertionError {
        assertTransition(URL);
        assertPageIsAvailable();
    }

    /**
     * Проверить возможность перехода на страницу
     */
    void assertTransition(Pages page) {
        assertTransition(kraken.grab().fullBaseUrl + Pages.getPagePath());
    }

    /**
     * Проверить возможность перехода на страницу по указанному url
     */
    void assertTransition(String URL) {
        kraken.get().url(URL);
        Assert.assertTrue(kraken.grab().currentURL().equalsIgnoreCase(URL),
                "Невозможно перейти на страницу " + URL + " по прямой ссылке\n"
                        + "Вместо нее попадаем на " + kraken.grab().currentURL() + "\n"
        );
        kraken.perform().printMessage("✓ Успешный переход по ссылке " + URL);
    }

    /**
     * Проверить работоспособность перехода по страницам
     */
    void assertTransition(Elements element) {
        String startPage = kraken.grab().currentURL();
        //TODO kraken.go(element);
        kraken.perform().click(element);
        Assert.assertFalse(kraken.grab().currentURL().equalsIgnoreCase(startPage),
                "Не работает преход по элементу " + Elements.locator() + " на странице " + startPage + "\n");
    }

    /**
     * Проверить доступность текущей страницы
     */
    void assertPageIsAvailable() throws AssertionError {
        String page = kraken.grab().currentURL();
        Assert.assertFalse(kraken.detect().is404(), "Ошибка 404 на странице " + page + "\n");
        Assert.assertFalse(kraken.detect().is500(), "Ошибка 500 на странице " + page + "\n");
        kraken.perform().printMessage("✓ Страница доступна (" + page + ")\n");
    }

    /**
     * Проверить что текущая страница недоступна с ошибкой 404
     */
    void assertPageIs404() throws AssertionError {
        Assert.assertTrue(kraken.detect().is404(), "Нет ошибки 404 на странице " + kraken.grab().currentURL() + "\n");
    }

    /**
     * Проверить возможность перехода на страницу и ее недоступность с ошибкой 404
     */
    void assertPageIs404(Pages page) throws AssertionError {
        assertPageIs404(kraken.grab().fullBaseUrl + Pages.getPagePath());
    }

    /**
     * Проверить возможность перехода на страницу по указанному url и ее недоступность с ошибкой 404
     */
    void assertPageIs404(String URL) throws AssertionError {
        assertTransition(URL);
        assertPageIs404();
    }


    /** Проверка недоступности страницы для перехода */
    void assertPageIsUnavailable(Pages page) throws AssertionError {
        assertPageIsUnavailable(kraken.grab().fullBaseUrl + Pages.getPagePath());
    }

    /** Проверка недоступности страницы для перехода по прямой ссылке */
    void assertPageIsUnavailable(String URL) throws AssertionError {
        kraken.perform().printMessage("Проверяем недоступность перехода на страницу " + URL);
        kraken.get().url(URL);
        Assert.assertFalse(kraken.grab().currentURL().equalsIgnoreCase(URL),
                "Можно попасть на страницу " + kraken.grab().currentURL() + " по прямой ссылке\n");
    }

    /** Пропуск теста */
    void skip() throws SkipException{
        kraken.perform().printMessage("Пропускаем тест");
            throw new SkipException("Пропускаем тест");
    }

    /** Пропуск теста на указанном окружении */
    void skipOn(EnvironmentData environment) throws SkipException{
        if (kraken.detect().environment(environment)) {
            kraken.perform().printMessage("Пропускаем тест на " + environment.getName());
            throw new SkipException("Пропускаем тест");
        }
    }
}
