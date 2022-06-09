package ru.instamart.test.api.dispatch.shifts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.DispatchDataProvider;
import ru.instamart.api.enums.shopper.RoleSHP;
import ru.instamart.api.request.shifts.PlanningAreasRequest;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Shifts")
@Feature("Endpoints")
public class ShiftsPlanningPeriodsTest extends RestBase {

    @BeforeClass(alwaysRun = true,
            description = "Авторизация")
    public void auth() {
        UserData user = UserManager.getShp6Shopper2();
        shopperApp.authorisation(user);
    }


    @CaseId(154)
    @Story("Вывод списка плановых периодов")
    @Test(groups = {"api-shifts"},
            dataProvider = "planningPeriodFilters",
            dataProviderClass = DispatchDataProvider.class,
            description = "Фильтрация плановых периодов по временному диапазону")
    public void importPlanningPeriod200(final String startedAt, final String endedAt) {
        final Response response = PlanningAreasRequest.GET(
                EnvironmentProperties.DEFAULT_SHIFTS_ZONE_ID, RoleSHP.UNIVERSAL, startedAt, endedAt);
        checkStatusCode200(response);
    }
}
