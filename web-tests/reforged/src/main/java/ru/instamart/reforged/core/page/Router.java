package ru.instamart.reforged.core.page;

import org.openqa.selenium.support.PageFactory;
import ru.instamart.reforged.stf.page.StfPage;
import ru.instamart.ui.manager.AppManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

public class Router {

    private static final Map<Long, Page> pageThread = new ConcurrentHashMap<>();

    protected static Page getPage(final Class<? extends Page> pageClass) {
        final long currentThreadId = Thread.currentThread().getId();
        final Page pageFromThread = pageThread.get(currentThreadId);
        if (isNull(pageFromThread)) {
            return pageThread.computeIfAbsent(currentThreadId, v -> initPage(pageClass));
        } else if (pageFromThread.getClass().getSimpleName().equals(pageClass.getSimpleName())) {
            return pageFromThread;
        } else {
            return pageThread.computeIfPresent(currentThreadId, (k,v) -> initPage(pageClass));
        }
    }

    private static <T> T initPage(final Class<T> pageClass) {
        return PageFactory.initElements(AppManager.getWebDriver(), pageClass);
    }
}
