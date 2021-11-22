package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v2.UserReferralProgramV2;
import ru.instamart.api.request.v2.UsersV2Request;
import ru.instamart.api.response.v2.UserReferralProgramV2Response;
import ru.instamart.jdbc.dao.InstacoinAccountsDao;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Реферальная программа")
public class UserReferralProgramV2Test extends RestBase {

    private Long userDbId;

    @CaseIDs(value = {@CaseId(551), @CaseId(552)})
    @Story("Реферальная программа пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Существующий пользователь",
            dataProvider = "userDataForReferralProgram",
            dataProviderClass = RestDataProvider.class)
    public void getReferralProgram(String id, String token, String promotionCode, Long userId) {
        userDbId = userId;
        Response response = UsersV2Request.ReferralProgram.GET(id, token);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, UserReferralProgramV2Response.class);
        UserReferralProgramV2 userReferralProgram = response.as(UserReferralProgramV2Response.class).getUserReferralProgram();
        compareTwoObjects(userReferralProgram.getCode(), promotionCode.toUpperCase());
    }

    @CaseIDs(value = {@CaseId(553), @CaseId(554)})
    @Story("Реферальная программа пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Несуществующий пользователь",
            dataProvider = "invalidUserDataForReferralProgram",
            dataProviderClass = RestDataProvider.class)
    public void getReferralProgramForNonExistingUser(String id, String token, Integer statusCode) {
        Response response = UsersV2Request.ReferralProgram.GET(id, token);
        checkStatusCode(response, statusCode);
    }

    @AfterClass(alwaysRun = true)
    public void clearData(){
        InstacoinAccountsDao.INSTANCE.updatePromotionCode(null, userDbId);
    }
}
