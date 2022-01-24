package ru.instamart.test.reforged.business;


import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.business.page.BusinessRouter.business;
import static ru.instamart.reforged.metro.page.MetroRouter.metro;

@Epic("SMBUSINESS UI")
@Feature("Базовые тесты бизнес-тенанта")
public final class BasicBusinessTests extends BaseTest {

    //    @CaseId(1440)
//    @Story("Валидация элементов")
    @Test(description = "Тест перехода на страницу Sbermarket Business", groups = {"smoke"})
    public void successValidateBusinessTenant() {
        business().goToPage();
    }
}
