package ru.instamart.reforged.core.data_provider;

import org.testng.annotations.DataProvider;
import static ru.instamart.kraken.testdata.ReplacementPolicy.*;

public final class ReplacementPolicyProvider {

    @DataProvider(name = "replacementPolicy")
    public static Object[][] getReplacementPolicyName() {
        return new Object[][]{
                {callAndReplace()},
                {callAndRemove()},
                {noCallAndReplace()},
                {noCallAndRemove()}
        };
    }
}

