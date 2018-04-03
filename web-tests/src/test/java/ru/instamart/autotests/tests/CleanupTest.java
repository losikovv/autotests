package ru.instamart.autotests.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CleanupTest extends TestBase {

    @Test
    public void cleanup() throws Exception {
        app.getSessionHelper().cleanup();
        //TODO изменить проверки
        //Assert.assertTrue(app.getNavigationHelper().isElementPresent(By.xpath("//*[@id='spree_user_66971']/td[1]/div[1]/a")),"!!! CANT SEE DA TABLE !!!");
        //Assert.assertTrue(app.getNavigationHelper().isElementPresent(By.className("delete-resource")),"!!! CANT SEE DA BUTTON !!!");
    }
}
