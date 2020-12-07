package ru.instamart.tests;

import instamart.core.helpers.HelperBase;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Foo bar test from ci")
public class Foo {

    @Test(description = "TEST FROM MAC", groups = "debug")
    @Description("Test method")
    @Story("Check gitlab-cli")
    public void bar() {
        HelperBase.verboseMessage("DEBUG!!!!!");
        Assert.assertTrue(true);
    }
}
