package ru.instamart.api.checkpoint;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.response.ErrorResponse;

@Slf4j
public class BaseApiCheckpoints {

    @Step("Проверка правильного error сообщения. Type = base")
    public static void errorAssert(Response response, String textError) {
        final SoftAssert softAssert = new SoftAssert();
        log.debug("Check error message: {} with response", textError);
        ErrorResponse error = response.as(ErrorResponse.class);
        softAssert.assertEquals(error.getErrors().getBase(), textError, "Невалидная ошибка");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base", "Невалидный тип ошибки");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), textError, "Невалидная ошибка");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), textError, "Невалидная ошибка");
        softAssert.assertAll();
        log.debug("Success");
    }


    @Step("Проверка правильного error сообщения. Type = value")
    public static void errorValueAssert(Response response, String textError, String value) {
        final SoftAssert softAssert = new SoftAssert();
        log.debug("Check error message: {} with response", textError);
        ErrorResponse error = response.as(ErrorResponse.class);
        softAssert.assertEquals(error.getErrors().getValue(), textError, "Невалидная ошибка");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), value, "Невалидный тип ошибки");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), textError, "Невалидная ошибка");
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), textError, "Невалидная ошибка");
        softAssert.assertAll();
        log.debug("Success");
    }


    @Step("Проверка на существования сообщения об ошибке")
    public static void errorTextIsNotEmpty(Response response) {
        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(error.getErrors().getBase().isEmpty(), "Невалидная ошибка");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base", "Невалидный тип ошибки");
        softAssert.assertFalse(error.getErrorMessages().get(0).getMessage().isEmpty(), "Невалидная ошибка");
        softAssert.assertFalse(error.getErrorMessages().get(0).getHumanMessage().isEmpty(), "Невалидная ошибка");
        softAssert.assertAll();
    }
}
