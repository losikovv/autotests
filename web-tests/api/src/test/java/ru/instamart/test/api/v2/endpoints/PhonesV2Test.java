package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.kraken.testdata.UserRoles;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.PhonesV2Request;
import ru.instamart.api.response.v2.PhonesV2Response;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.TestVariables;
import ru.instamart.kraken.testdata.UserData;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Телефоны пользователей")
public class PhonesV2Test extends RestBase {

    @CaseId(433)
    @Story("Получить список всех телефонов пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить список всех телефонов пользователя. У пользователя нет телефонов")
    public void phoneNotFound() {
        final UserData testUser = new UserData(
                UserRoles.USER.getRole(),
                Generate.email(),
                TestVariables.CompanyParams.companyName,
                Generate.testUserName(UserRoles.USER.getRole())
        );
        SessionFactory.createSessionToken(SessionType.API_V2_FB, testUser);
        response = PhonesV2Request.GET();
        checkStatusCode200(response);
        PhonesV2Response phonesV2Response = response.as(PhonesV2Response.class);
        assertEquals(phonesV2Response.getPhones().size(), 0, "phone not empty");
    }

    @CaseId(435)
    @Story("Получить список всех телефонов пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить список всех телефонов пользователя. Один номер телефона")
    public void onePhoneFound() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        response = PhonesV2Request.GET();
        checkStatusCode200(response);
        PhonesV2Response phonesV2Response = response.as(PhonesV2Response.class);
        assertFalse(phonesV2Response.getPhones().get(0).getCode().isEmpty(), "phone code is empty");
        assertFalse(phonesV2Response.getPhones().get(0).getNumber().isEmpty(), "phone number is empty");
        assertFalse(phonesV2Response.getPhones().get(0).getHumanNumber().isEmpty(), "phone human_number is empty");
    }
}