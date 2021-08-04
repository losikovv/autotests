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
        log.info("Check error message: {} with response", textError);
        ErrorResponse error = response.as(ErrorResponse.class);
        softAssert.assertEquals(error.getErrors().getBase(), textError);
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), textError);
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), textError);
        softAssert.assertAll();
        log.info("Success");
    }


    @Step("Проверка правильного error сообщения. Type = value")
    public static void errorValueAssert(Response response, String textError) {
        final SoftAssert softAssert = new SoftAssert();
        log.info("Check error message: {} with response", textError);
        ErrorResponse error = response.as(ErrorResponse.class);
        softAssert.assertEquals(error.getErrors().getValue(), textError);
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "value");
        softAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), textError);
        softAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), textError);
        softAssert.assertAll();
        log.info("Success");
    }


    @Step("Проверка на существования сообщения об ошибке")
    public static void errorTextIsNotEmpty(Response response) {
        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(error.getErrors().getBase().isEmpty());
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertFalse(error.getErrorMessages().get(0).getMessage().isEmpty());
        softAssert.assertFalse(error.getErrorMessages().get(0).getHumanMessage().isEmpty());
        softAssert.assertAll();
    }
}
