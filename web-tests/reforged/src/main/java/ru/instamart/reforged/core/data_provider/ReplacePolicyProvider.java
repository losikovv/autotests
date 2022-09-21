package ru.instamart.reforged.core.data_provider;

import org.testng.annotations.DataProvider;

import static ru.instamart.reforged.stf.enums.ReplacementPolicies.*;

public class ReplacePolicyProvider {

    @DataProvider(name = "replacementPolicy")
    public static Object[][] getReplacementPolicyName() {
        return new Object[][]{
                {CALL_AND_REPLACE.getName()},
                {CALL_AND_REMOVE.getName()},
                {DONT_CALL_AND_REPLACE.getName()},
                {DONT_CALL_AND_REMOVE.getName()}
        };
    }
}
