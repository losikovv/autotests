package ru.instamart.api.dataprovider;

import org.testng.annotations.DataProvider;
import ru.instamart.api.enums.shadowcat.SimpleCondition;
import ru.instamart.api.enums.shadowcat.TwoStepCondition;

import java.util.Arrays;

public class ShadowcatDataProvider {
    @DataProvider(name = "SimpleCondition")
    public static Object[][] checkFailSimpleConditionOrder() {
        return Arrays.stream(SimpleCondition.values())
                .map(f -> new Object[]{f})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "TwoStepCondition")
    public static Object[][] checkFailTwoStepConditionOrder() {
        return Arrays.stream(TwoStepCondition.values())
                .map(f -> new Object[]{f})
                .toArray(Object[][]::new);
    }
}
