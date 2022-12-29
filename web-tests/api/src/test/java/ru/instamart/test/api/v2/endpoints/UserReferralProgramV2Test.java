package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.request.v2.UsersV2Request;
import ru.instamart.api.response.v2.UserReferralProgramV2Response;
import ru.instamart.jdbc.dao.stf.InstacoinAccountsDao;

import java.util.Objects;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Реферальная программа")
public class UserReferralProgramV2Test extends RestBase {

    private Long userDbId;

    @TmsLinks(value = {@TmsLink("551"), @TmsLink("552")})
    @Story("Реферальная программа пользователя")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2", "api-bff"},
            description = "Существующий пользователь",
            dataProvider = "userDataForReferralProgram",
            dataProviderClass = RestDataProvider.class)
    public void getReferralProgram(String id, String token, String promotionCode, Long userId) {
        userDbId = userId;
        final Response response = UsersV2Request.ReferralProgram.GET(id, token);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, UserReferralProgramV2Response.class);
        UserReferralProgramV2Response userReferralProgramResponse = response.as(UserReferralProgramV2Response.class);
        Assert.assertNull(userReferralProgramResponse.getUserReferralProgram());
        compareTwoObjects(userReferralProgramResponse.getShortTutorial(), "С 31 января баллы не начисляются. Потратить накопленные баллы можно до 28 февраля включительно.");
    }

    @TmsLinks(value = {@TmsLink("553"), @TmsLink("554")})
    @Story("Реферальная программа пользователя")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2", "api-bff"},
            description = "Несуществующий пользователь",
            dataProvider = "invalidUserDataForReferralProgram",
            dataProviderClass = RestDataProvider.class)
    public void getReferralProgramForNonExistingUser(String id, String token, Integer statusCode) {
        final Response response = UsersV2Request.ReferralProgram.GET(id, token);
        checkStatusCode(response, statusCode);
    }

    @AfterClass(alwaysRun = true)
    public void clearData(){
        if(Objects.nonNull(userDbId)) InstacoinAccountsDao.INSTANCE.updatePromotionCode(null, userDbId);
    }
}
