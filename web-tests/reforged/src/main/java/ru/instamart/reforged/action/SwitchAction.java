package ru.instamart.reforged.action;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.ui.manager.AppManager;

import java.util.List;

@Slf4j
public class SwitchAction {

    public void switchToWindowIndex(final int index) {
        try {
            final List<String> windowHandles = List.copyOf(AppManager.getWebDriver().getWindowHandles());
            AppManager.getWebDriver().switchTo().window(windowHandles.get(index));
        } catch (IndexOutOfBoundsException windowWithIndexNotFound) {
            log.error("FATAL: Windows with index {} doesn't exist", index);
        }
    }
}