package ru.instamart.api.dataprovider;

import org.testng.annotations.DataProvider;

public class ShippingCalcDataProvider {

    @DataProvider(name = "conditionValidation")
    public static Object[][] getInvalidConditions() {
        return new Object[][]{
                {0, "", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure"},
                {1, "{\"test\": 1}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Count field is absent"},
                {2, "{\"test\": 1, \"Max\": 1}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Min field is absent"},
                {2, "{\"Min\": 1, \"test\": 1}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Max field is absent"},
                {2, "{\"Min\": 1, \"Max\": 1}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: value range parameters cannot be equal"},
                {3, "{\"not-Test\": \"shippingcalc_test\", \"Group\": \"control\"}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Test field is absent or empty"},
                {3, "{\"Test\": \"shippingcalc_test\", \"test\": \"control\"}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Group field is absent or empty"},
                {4, "{\"test\": 1, \"Max\": 1}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Min field is absent"},
                {4, "{\"Min\": 1, \"test\": 1}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Max field is absent"},
                {4, "{\"Min\": 1, \"Max\": 1}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: order distance range parameters cannot be equal"},
                {4, "{\"Min\": 1, \"Max\": 0}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: min order distance range parameter cannot be greater then max"},
                {5, "{\"platforms\": [{\"test\": \"SbermarketIOS\", \"version\": \"6.28.0\"}]}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Name field is absent or empty"},
                {5, "{\"platforms\": [{\"name\": \"SbermarketIOS\", \"test\": \"6.28.0\"}]}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Version field is absent"},
                {6, "{\"test\": \"2022-06-20T12:00:00Z\"}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, registered_after field is absent"},
                {6, "{\"registered_after\": \"test\"}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: parsing time"},
                {7, "{\"test\": true}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, on_demand field is absent"},
                {8, "{\"test\": [\"test_id\"]}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, clients field is absent or empty"},
                {8, "{\"clients\": []}", "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, clients field is absent or empty"}
        };
    }
}
