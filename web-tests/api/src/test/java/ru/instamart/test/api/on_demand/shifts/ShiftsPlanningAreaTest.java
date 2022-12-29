package ru.instamart.test.api.on_demand.shifts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.DispatchDataProvider;
import ru.instamart.api.request.shifts.RegionsRequest;
import ru.instamart.api.response.shifts.PlanningAreaShiftsItemSHPResponse;
import ru.instamart.api.response.shopper.app.ShopperSHPResponse;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Shifts")
@Feature("Endpoints")
public class ShiftsPlanningAreaTest extends RestBase {

    private int currentRegion;

    @BeforeClass(alwaysRun = true,
            description = "Авторизация")
    public void preconditions() {
        UserData user = UserManager.getShp6Universal1();
        shopperApp.authorisation(user);

        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
        //
        ShopperSHPResponse shopperInfo = shiftsApi.getShopperInfo();
        List<ShopperSHPResponse.Included> stores = shopperInfo.getIncluded().stream()
                .filter(item -> item.getType().equals("stores"))
                .collect(Collectors.toList());
        currentRegion = stores.get(0).getAttributes().getOperationalZoneId();
    }

    @AfterClass(alwaysRun = true)
    public void after(){
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @TmsLinks({@TmsLink("100"),@TmsLink("102")})
    @Story("Зоны планирования")
    @Test(groups = {"api-shifts"},
            description = "Получение списка территорий доставки без ограничений")
    public void getPlanningArea200() {
        final Response response = RegionsRequest.GET(currentRegion);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PlanningAreaShiftsItemSHPResponse[].class);
    }

    @TmsLinks({@TmsLink("160"), @TmsLink("159")})
    @Story("Зоны планирования")
    @Test(groups = {"api-shifts"},
            dataProvider = "shopperRole",
            dataProviderClass = DispatchDataProvider.class,
            description = "Получение списка территорий доставки с ограничением allowed_planning_areas_ids (несколько регионов)")
    @Parameters("Роль")
    public void getPlanningAreaWithRole200(String role) {
        final Response response = RegionsRequest.GET(currentRegion, role);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PlanningAreaShiftsItemSHPResponse[].class);
    }
}
