package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.reforged.stf.page.shop.ShopPage;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Адрес доставки")
public class UserShippingAddressTests extends BaseTest {

    @CaseId(1558)
    @Story("Дефолтные настройки адреса доставки")
    @Test(
            description = "Тест на то что по дефолту на витрине ритейлера не выбран адрес",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke","MRAutoCheck","ui-smoke-production"
            }
    )
    public void noShippingAddressByDefault() {

        shop().goToPage(ShopPage.ShopUrl.METRO);
        shop().interactHeader().checkIsShippingAddressNotSet();
    }
}