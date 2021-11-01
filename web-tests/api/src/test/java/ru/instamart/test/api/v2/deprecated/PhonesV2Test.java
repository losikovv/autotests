package ru.instamart.test.api.v2.deprecated;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.PhonesItemV2;
import ru.instamart.api.request.v2.PhonesV2Request;
import ru.instamart.api.response.v2.PhoneV2Response;
import ru.instamart.api.response.v2.PhonesV2Response;
import ru.instamart.kraken.data.Generate;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorAssert;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorValueAssert;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.helper.PhoneNumberHelper.getHumanPhoneNumber;

@Epic("ApiV2")
@Feature("Телефоны пользователей")
@Deprecated
public class PhonesV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @Deprecated
    @Story("Получить список всех телефонов пользователя")
    @Test(groups = {},
            description = "Получить список всех телефонов пользователя. Один номер телефона")
    public void onePhoneFound() {
        response = PhonesV2Request.GET();
        checkStatusCode200(response);
        PhonesV2Response phonesV2Response = response.as(PhonesV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(phonesV2Response.getPhones().get(0).getCode().isEmpty(), "phone code is empty");
        softAssert.assertFalse(phonesV2Response.getPhones().get(0).getNumber().isEmpty(), "phone number is empty");
        softAssert.assertFalse(phonesV2Response.getPhones().get(0).getHumanNumber().isEmpty(), "phone human_number is empty");
        softAssert.assertAll();
    }

    @Deprecated
    @Story("Получить телефонный номер по id")
    @Test(groups = {},
            description = "Получить телефонный номер по id. Существующий id")
    public void getPhone200() {
        PhonesItemV2 phone = apiV2.getPhoneId().getPhones().get(0);
        response = PhonesV2Request.PhonesById.GET(Integer.toString(phone.getId()));
        checkStatusCode200(response);
        PhoneV2Response phoneV2Response = response.as(PhoneV2Response.class);
        assertEquals(phoneV2Response.getPhone(), phone, "Phone data not equals");
    }

    @Deprecated
    @Story("Получить телефонный номер по id")
    @Test(groups = {},
            description = "Получить телефонный номер по несуществующему id")
    public void getPhone404() {
        response = PhonesV2Request.PhonesById.GET("failedPhoneId");
        checkStatusCode404(response);
        errorAssert(response, "translation missing: ru.activerecord.models.spree/phone не существует");
    }


    @Deprecated
    @Story("Обновить телефон пользователя")
    @Test(groups = {},
            description = "Обновить телефон пользователя по существующему id")
    public void updatePhone400() {
        Integer phoneId = apiV2.getPhoneId().getPhones().get(0).getId();
        Map<String, String> params = new HashMap<>();
        response = PhonesV2Request.PhonesById.PUT(phoneId.toString(), params);
        checkStatusCode400(response);
        errorAssert(response, "Отсутствует обязательный параметр 'phone'");
    }

    @Deprecated
    @Story("Обновить телефон пользователя")
    @Test(groups = {},
            description = "Обновить телефон пользователя по несуществующему id")
    public void updatePhone404() {
        Map<String, String> params = new HashMap<>();
        params.put("phone[value]", "failedPhone");
        response = PhonesV2Request.PhonesById.PUT("failedPhoneId", params);
        checkStatusCode404(response);
        errorAssert(response, "translation missing: ru.activerecord.models.spree/phone не существует");
    }

    @Deprecated
    @Story("Обновить телефон пользователя")
    @Test(groups = {},
            description = "Обновить телефон пользователя по существующему id")
    public void updatePhone200() {
        PhonesItemV2 phone = apiV2.getPhoneId().getPhones().get(0);
        Map<String, String> params = new HashMap<>();
        String newPhone = Generate.phoneNumber();
        params.put("phone[value]", newPhone);
        response = PhonesV2Request.PhonesById.PUT(phone.getId().toString(), params);
        checkStatusCode200(response);
        PhoneV2Response phoneV2Response = response.as(PhoneV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(phoneV2Response.getPhone().getId(), phone.getId(), "Id phone not equals");
        softAssert.assertTrue(newPhone.startsWith(phoneV2Response.getPhone().getCode()), "Code phone mismatch");
        softAssert.assertTrue(newPhone.endsWith(phoneV2Response.getPhone().getNumber()), "Number phone mismatch");
        softAssert.assertEquals(phoneV2Response.getPhone().getHumanNumber(), getHumanPhoneNumber(newPhone), "Human phone number mismatch");
        softAssert.assertAll();
    }

    @Deprecated
    @Story("Обновить телефон пользователя")
    @Test(groups = {},
            description = "Обновить телефон пользователя по существующему id")
    public void updatePhone20() {
        PhonesItemV2 phone = apiV2.getPhoneId().getPhones().get(0);
        Map<String, String> params = new HashMap<>();
        params.put("phone[value]", "invalidPhoneNumber");
        response = PhonesV2Request.PhonesById.PUT(phone.getId().toString(), params);
        checkStatusCode422(response);
        errorValueAssert(response, "является недействительным номером", "value");
    }

    @Deprecated
    @Story("Удалить телефон пользователя")
    @Test(groups = {},
            description = "Удалить телефон пользователя с несуществующим id")
    public void deletePhones404() {
        response = PhonesV2Request.PhonesById.DELETE("invalidPhoneId");
        checkStatusCode404(response);
        errorAssert(response,  "translation missing: ru.activerecord.models.spree/phone не существует");
    }

    @Deprecated
    @Story("Добавить новый телефон")
    @Test(groups = {},
            description = "Добавить новый телефон с невалидным phone[value]")
    public void addPhones404() {
        Map<String, String> params = new HashMap<>();
        params.put("phone[value]", "invalidPhoneNumber");
        response = PhonesV2Request.POST(params);
        checkStatusCode422(response);
        errorValueAssert(response, "является недействительным номером", "value");
    }
}