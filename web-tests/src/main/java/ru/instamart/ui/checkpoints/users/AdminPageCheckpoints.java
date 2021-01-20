package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static instamart.ui.modules.Base.kraken;

public class AdminPageCheckpoints extends BaseUICheckpoints {
    private static final Logger log = LoggerFactory.getLogger(AccountMenuCheckpoints.class);

    SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем авторизацию в Админке")
    public void checkIsAdminPageOpen(){
        log.info("> проверяем, что юзер успешно авторизован в Админке");
        Assert.assertTrue(
                kraken.detect().isInAdmin(),
                "Нет доступна админка пользователю c админ. правми");
        log.info("✓ Успешно");
    }

}
