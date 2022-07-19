package ru.instamart.test.api.on_demand.shifts;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.checkpoint.StatusCodeCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.shifts.TariffDescriptionRequest;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

@Epic("Shifts")
@Feature("Endpoints")
public class ShiftTariffDescriptionTest extends RestBase {

    @BeforeClass(alwaysRun = true,
            description = "Авторизация в шопере")
    public void preconditions() {
        UserData user = UserManager.getShp6Shopper1();
        shopperApp.authorisation(user);
    }

    @Test(groups = {"api-shifts"},
            description = "Получение описания тарифа смены")
    public void getTariffDescription() {
        String description = "Гарантированный доход является минимальной суммой, которую вы можете получить за смену без учета штрафов при соблюдении стандартов сервиса, а именно:\n" +
                "• Необходимо выполнять заказы: условием для получения минималки является выполнение не менее 1 заказа за каждые 2 часа работы\n" +
                "• Необходимо принимать все назначенные на вас заказы: если вы на смене – вы обязаны выполнять заказы. Даже за 5 минут до завершения смены\n" +
                "• Необходимо быть онлайн и передавать достоверные координаты\n" +
                "• Необходимо находиться в пешей доступности от магазинов, из которых осуществляется доставка\n" +
                "Во время работы вы будете выполнять заказы различной стоимости.\n" +
                "Если по завершению смены оплата за выполненные заказы окажется меньше гарантированного дохода, то ваша оплата за эту смену будет равна гарантированному доходу.";
        Response response = TariffDescriptionRequest.GET();
        StatusCodeCheckpoints.checkStatusCode(response, 200, "text/plain; charset=utf-8");
        Allure.step("Проверка описания тарифов", ()-> {
            Assert.assertEquals(response.getBody().asString(), description, "Описание тарифа не совпадает");
        });
    }

}
