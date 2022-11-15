package ru.instamart.reforged.hr_ops_partners.block.header;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface HeaderCheck extends Check, HeaderElement {

    @Step("Проверяем, что отображается тултип автопределения города")
    default void checkRegionTooltipVisible() {
        Kraken.waitAction().shouldBeVisible(headerTooltip);
    }

    @Step("Проверяем, что тултип автоопределения города не отображается")
    default void checkRegionTooltipNotVisible() {
        Assert.assertTrue(headerTooltip.is().invisible());
    }

    @Step("Проверяем, что город в тултипе: '{expectedRegion}'")
    default void checkRegionNameInTooltip(final String expectedRegion) {
        ThreadUtil.simplyAwait(1);
        Assert.assertEquals(suggestedCityInTooltip.getText(), expectedRegion, "Указанный в тултипе город отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается кнопка выбора города")
    default void checkRegionButtonVisible() {
        Kraken.waitAction().shouldBeVisible(selectedCity);
    }

    @Step("Проверяем, что город шапке: '{expectedRegion}'")
    default void checkRegionNameInHeader(final String expectedRegion) {
        Assert.assertEquals(selectedCity.getText(), expectedRegion, "Указанный в шапке город отличается от ожидаемого");
    }
}
