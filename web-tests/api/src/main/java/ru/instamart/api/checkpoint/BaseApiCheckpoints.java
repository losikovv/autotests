package ru.instamart.api.checkpoint;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.kraken.helper.KrakenAssert;

@Slf4j
public class BaseApiCheckpoints {

    private final KrakenAssert krakenAssert = new KrakenAssert();

    @Step("Проверка правильного error сообщения")
    public void errorAssert(Response response, String textError) {
        log.info("Check error message: {} with response", textError);
        ErrorResponse error = response.as(ErrorResponse.class);
        krakenAssert.assertEquals(error.getErrors().getBase(), textError);
        krakenAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        krakenAssert.assertEquals(error.getErrorMessages().get(0).getMessage(), textError);
        krakenAssert.assertEquals(error.getErrorMessages().get(0).getHumanMessage(), textError);
        krakenAssert.assertAll();
        log.info("Success");
    }

    @Step("Проверка на существования сообщения об ошибке")
    public void errorTextIsNotEmpty(Response response){
        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(error.getErrors().getBase().isEmpty());
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base");
        softAssert.assertFalse(error.getErrorMessages().get(0).getMessage().isEmpty());
        softAssert.assertFalse(error.getErrorMessages().get(0).getHumanMessage().isEmpty());
        softAssert.assertAll();
    }
}
