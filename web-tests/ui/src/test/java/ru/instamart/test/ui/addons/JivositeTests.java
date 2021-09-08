package ru.instamart.test.ui.addons;

import org.testng.annotations.Test;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.testdata.TestVariables;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.test.ui.TestBase;
import ru.instamart.ui.module.Shop;
import ru.instamart.ui.module.User;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class JivositeTests extends TestBase {

    @Test(  description = "Тест отсутствия виджета Jivosite на лендинге",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void noJivositeWidgetOnLanding() {
        User.Logout.quickly();

        assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite доступен на лендинге");
    }

    @Test(  description = "Тест отсутствия виджета Jivosite в чекауте",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void noJivositeWidgetOnCheckout() {
        User.Do.loginAs(UserManager.getDefaultAdmin());
        kraken.reach().checkout();

        assertFalse(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite доступен в чекауте");
    }

    @Test(  description = "Тест работы с виджетом Jivosite на витрине ритейлера",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void successOperateJivositeWidgetOnRetailerPage() {
        kraken.get().page(CoreProperties.DEFAULT_RETAILER);
        assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен на витрине ритейлера\n");

        Shop.Jivosite.open();
        assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite на витрине ритейлера\n");

        Shop.Jivosite.close();
        assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite на витрине ритейлера\n");
    }

    @Test(  description = "Тест работы с виджетом Jivosite в каталоге",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void successOperateJivositeWidgetInCatalog() {
        Shop.Search.searchItem(TestVariables.TestParams.ItemSearch.testQuery);
        assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен в каталоге\n");

        Shop.Jivosite.open();
        assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite в каталоге\n");

        Shop.Jivosite.close();
        assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite в каталоге\n");
    }

    @Test(  description = "Тест работы с виджетом Jivosite на странице 404",
            groups = {"sbermarket-acceptance","sbermarket-regression",}
    )
    public void successOperateJivositeWidgetOnPage404() {
        kraken.get().page(Pages.page404());
        assertTrue(kraken.detect().isJivositeWidgetAvailable(),
                "Виджет Jivosite недоступен на странице 404\n");

        Shop.Jivosite.open();
        assertTrue(kraken.detect().isJivositeOpen(),
                "Не разворачивается виджет Jivosite на странице 404\n");

        Shop.Jivosite.close();
        assertFalse(kraken.detect().isJivositeOpen(),
                "Не сворачивается виджет Jivosite на странице 404\n");
    }

    @Test(  description = "Тест успешной отправки сообщения в Jivosite",
            groups = {"sbermarket-regression",}
    )
    public void successSendMessageToJivositeFromRetailerPage() {
        String testMessage = "тест";
        kraken.get().page(CoreProperties.DEFAULT_RETAILER);

        Shop.Jivosite.sendMessage(testMessage);
        assertTrue(kraken.detect().isJivositeMessageSent(testMessage),
                "Не отправляется сообщение в Jivosite\n");
    }
}
