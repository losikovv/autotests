package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    // Тесты чекаута



public class Checkout extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void getAuth() throws Exception {
        app.getNavigationHelper().getRetailerPage("metro");
        if(!app.getSessionHelper().isUserAuthorised()) {
            app.getSessionHelper().doLoginAsAdmin();
        }
        if(!app.getCheckoutHelper().isOnCheckout()){
            app.getShoppingHelper().openCart();
            app.getShoppingHelper().proceedToCheckout();
        }
    }

    // TODO Тесты на изменение телефона и контактов

    // TODO Тесты на добавление и изменение карт оплаты

    // TODO Тесты на добавление и изменение юрлиц


    @Test(
            description = "Тест добавления промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 400
    )
    public void addPromocode(){
        app.getCheckoutHelper().addPromocode("unicorn");

        // Assert promocode is applied
        Assert.assertTrue(app.getCheckoutHelper().isPromocodeApplied(),
                "Can't assert promocode is applied\n");
    }


    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 401
    )
    public void clearPromocode(){
        app.getCheckoutHelper().clearPromocode();

        // Assert promocode is applied
        Assert.assertFalse(app.getCheckoutHelper().isPromocodeApplied(),
                "Can't assert promocode is cleared\n");
    }


    @Test(
            description = "Тест добавления программ лояльности в чекауте",
            groups = {"acceptance","regression"},
            priority = 402
    )
    public void addLoyaltyPrograms(){

        // TODO вынести номера, названия и позиции лояльностей в models.LoyaltyData

        // Много.ру
        if(app.getCheckoutHelper().isLoyaltyProgramApplied(1)){
            app.getCheckoutHelper().clearLoyaltyProgram(1);
        }
        app.getCheckoutHelper().addLoyaltyProgram(1, "11600350");
        Assert.assertTrue(app.getCheckoutHelper().isLoyaltyProgramApplied(1),
                "Can't assert loyalty program #1 is applied");

        // Аэрофлот
        if(app.getCheckoutHelper().isLoyaltyProgramApplied(2)){
            app.getCheckoutHelper().clearLoyaltyProgram(2);
        }
        app.getCheckoutHelper().addLoyaltyProgram(2, "71891831");
        Assert.assertTrue(app.getCheckoutHelper().isLoyaltyProgramApplied(2),
                "Can't assert loyalty program #2 is applied");

        // Семейная команда
        if(app.getCheckoutHelper().isLoyaltyProgramApplied(3)){
            app.getCheckoutHelper().clearLoyaltyProgram(3);
        }
        app.getCheckoutHelper().addLoyaltyProgram(3, "7005992006136053");
        Assert.assertTrue(app.getCheckoutHelper().isLoyaltyProgramApplied(3),
                "Can't assert loyalty program #3 is applied");

        // Связной клуб
        if(app.getCheckoutHelper().isLoyaltyProgramApplied(4)){
            app.getCheckoutHelper().clearLoyaltyProgram(4);
        }
        app.getCheckoutHelper().addLoyaltyProgram(4, "2981796259309");
        Assert.assertTrue(app.getCheckoutHelper().isLoyaltyProgramApplied(4),
                "Can't assert loyalty program #4 is applied");
    }


    @Test(
            description = "Тест выбора программы лояльности в чекауте",
            groups = {"acceptance","regression"},
            priority = 403
    )
    public void selectLoyaltyProgram(){
        // TODO брать позиции лояльностей из models.LoyaltyData
        app.getCheckoutHelper().selectLoyaltyProgram(1);
        app.getCheckoutHelper().selectLoyaltyProgram(2);
        app.getCheckoutHelper().selectLoyaltyProgram(3);
        app.getCheckoutHelper().selectLoyaltyProgram(4);
        // TODO добавить проверки на наличие модалок после выбора
    }


    @Test(
            description = "Тест удаления программ лояльности в чекауте",
            groups = {"acceptance","regression"},
            priority = 404
    )
    public void deleteLoyaltyPrograms(){

        // TODO брать позиции лояльностей из models.LoyaltyData

        // Много.ру
        app.getCheckoutHelper().clearLoyaltyProgram(1);
        Assert.assertFalse(app.getCheckoutHelper().isLoyaltyProgramApplied(1),
                "Can't assert loyalty program #1 is cleared");

        // Аэрофлот
        app.getCheckoutHelper().clearLoyaltyProgram(2);
        Assert.assertFalse(app.getCheckoutHelper().isLoyaltyProgramApplied(2),
                "Can't assert loyalty program #2 is cleared");

        // Семейная команда
        app.getCheckoutHelper().clearLoyaltyProgram(3);
        Assert.assertFalse(app.getCheckoutHelper().isLoyaltyProgramApplied(3),
                "Can't assert loyalty program #3 is cleared");

        // Связной клуб
        app.getCheckoutHelper().clearLoyaltyProgram(4);
        Assert.assertFalse(app.getCheckoutHelper().isLoyaltyProgramApplied(4),
                "Can't assert loyalty program #4 is cleared");
    }


    // TODO Тесты на оформление заказов со всеми способы оплаты

}
