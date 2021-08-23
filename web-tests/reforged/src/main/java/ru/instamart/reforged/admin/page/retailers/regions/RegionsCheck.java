package ru.instamart.reforged.admin.page.retailers.regions;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.admin.AdminRout.regions;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RegionsCheck extends Check, RegionsElements {

    @Step("Проверяем что отображается алерт успешного создания региона")
    default void checkSuccessCreateRegionAlertVisible() {
        waitAction().shouldBeVisible(successCreateRegionAlert);
    }

    @Step("Проверяем, что добавляемый тестовый регион не отображается в таблице")
    default void checkAutotestRegionInTableNotVisible() {
        waitAction().shouldNotBeVisible(testRegionInRegionsTable);
    }

    @Step("Проверяем, что тестовый регион добавился в таблицу")
    default void checkAutotestRegionInTableVisible() {
        waitAction().shouldBeVisible(testRegionInRegionsTable);
    }

    @Step("Проверяем что открыта страница регионов")
    default void checkUrlExpected(String url) {
        regions().checkPageUrl(regions().pageUrl());
    }

}
