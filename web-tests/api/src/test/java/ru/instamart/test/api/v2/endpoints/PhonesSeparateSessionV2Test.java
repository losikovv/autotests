package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.checkpoint.BaseApiCheckpoints;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.model.v2.PhonesItemV2;
import ru.instamart.api.request.v2.PhonesV2Request;
import ru.instamart.api.response.v2.PhoneV2Response;
import ru.instamart.api.response.v2.PhonesV2Response;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.TestVariables;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserRoles;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode403;

@Epic("ApiV2")
@Feature("Телефоны пользователей")
public class PhonesSeparateSessionV2Test extends RestBase {
    private BaseApiCheckpoints check = new BaseApiCheckpoints();

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
        RegistrationHelper.registration(testUser);
        SessionFactory.createSessionToken(SessionType.API_V2_FB, testUser);
        response = PhonesV2Request.GET();
        checkStatusCode200(response);
        PhonesV2Response phonesV2Response = response.as(PhonesV2Response.class);
        assertEquals(phonesV2Response.getPhones().size(), 0, "phone not empty");
    }

    @CaseId(437)
    @Story("Получить телефонный номер по id")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить телефонный номер по id. Существующий id у пользователя нет телефонов")
    public void getPhone403() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        PhonesItemV2 phone = apiV2.getPhoneId().getPhones().get(0);
        SessionFactory.clearSession(SessionType.API_V2_FB);
        //Перелогин другим пользователем у которого нет телефона
        final UserData testUser = new UserData(
                UserRoles.USER.getRole(),
                Generate.email(),
                TestVariables.CompanyParams.companyName,
                Generate.testUserName(UserRoles.USER.getRole())
        );
        RegistrationHelper.registration(testUser);
        SessionFactory.createSessionToken(SessionType.API_V2_FB, testUser);
        response = PhonesV2Request.PhonesById.GET(Integer.toString(phone.getId()));
        checkStatusCode403(response);
        check.errorAssert(response, "Пользователь не может выполнить это действие");
    }

    @CaseId(447)
    @Story("Удалить телефон пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Удалить телефон пользователя с существующим id")
    public void deletePhones200() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        PhonesItemV2 phone = apiV2.getPhoneId().getPhones().get(0);
        response = PhonesV2Request.PhonesById.DELETE(phone.getId().toString());
        checkStatusCode200(response);
        PhoneV2Response phoneV2Response = response.as(PhoneV2Response.class);
        assertEquals(phoneV2Response.getPhone(), phone, "phone mismatch");
    }

    @CaseId(441)
    @Story("Добавить новый телефон")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавить новый телефон с валидным phone[value]")
    public void addPhones200() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        Map<String, String> params = new HashMap<>();
        params.put("phone[value]", Generate.phoneNumber());
        response = PhonesV2Request.POST(params);
        checkStatusCode200(response);
        PhoneV2Response phoneV2Response = response.as(PhoneV2Response.class);
        assertNotNull(phoneV2Response.getPhone(), "response is empty");
        response = PhonesV2Request.GET();
        checkStatusCode200(response);
        assertEquals(response.as(PhonesV2Response.class).getPhones().size(), 2, "Количество телефонов не равно 2");
    }
}