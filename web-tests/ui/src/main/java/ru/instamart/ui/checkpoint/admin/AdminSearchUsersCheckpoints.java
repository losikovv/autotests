package ru.instamart.ui.checkpoint.admin;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.ui.Elements;
import ru.instamart.ui.checkpoint.BaseUICheckpoints;

import static ru.instamart.ui.module.Base.kraken;

public class AdminSearchUsersCheckpoints extends BaseUICheckpoints {

    private static final Logger log = LoggerFactory.getLogger(AdminSearchUsersCheckpoints.class);

    @Step("Проверяем, что поиск пользователя работает корректно")
    public void checkSearchWorks() {
        //TODO если в дальнейшем будет переиспользование данного метода, то строки для поиска нужно будет вынести в аргументы
        log.debug("> проверяем, что искомый пользователь найден в системе");
        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.UsersSection.userEmail()), UserManager.getDefaultAdmin().getEmail(),
                "Не работает поиск пользователя в админке");
        log.debug("✓ Успешно");
    }
}
