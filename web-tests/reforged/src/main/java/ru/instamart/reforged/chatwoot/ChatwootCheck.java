package ru.instamart.reforged.chatwoot;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface ChatwootCheck extends Check, ChatwootElement {

    @Step("Проверяем, что проявилось всплывающее сообщение")
    default void checkSnackbarVisible() {
        snackbar.should().visible();
    }

    @Step("Проверяем, что текст всплывающего сообщения: '{text}'")
    default void checkSnackbarText(final String text) {
        Assert.assertEquals(snackbar.getText(), text, "Текст всплывающего сообщения отличается от ожидаемого");
    }
}
