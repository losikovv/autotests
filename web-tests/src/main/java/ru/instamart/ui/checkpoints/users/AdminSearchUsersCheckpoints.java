package instamart.ui.checkpoints.users;

import instamart.core.testdata.UserManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import static instamart.ui.modules.Base.kraken;

public class AdminSearchUsersCheckpoints extends BaseUICheckpoints {

    private static final Logger log = LoggerFactory.getLogger(AdminSearchUsersCheckpoints.class);

    @Step("Проверяем, что поиск пользователя работает корректно")
    public void checkSearchWorks(){
        //TODO если в дальнейшем будет переиспользование данного метода, то строки для поиска нужно будет вынести в аргументы
        log.info("> проверяем, что искомый пользователь найден в системе");
        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.UsersSection.userEmail()), UserManager.getDefaultAdmin().getLogin(),
                "Не работает поиск пользователя в админке");
        log.info("✓ Успешно");
    }
}
