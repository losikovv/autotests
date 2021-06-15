package ru.instamart.reforged.core.page;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

@Slf4j
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

    @SuppressWarnings("unchecked")
    private static <T> T initPage(final Class<T> pageClass) {
        try {
            return (T) Class.forName(pageClass.getName()).getConstructor().newInstance();
        } catch (Exception e) {
            log.error("FATAL: Create PageClass {} failed", pageClass.getName());
        }
        return null;
    }
}
