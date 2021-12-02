package ru.instamart.api.checkpoint;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.response.ErrorResponse;

import java.util.Collection;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.*;
import static ru.instamart.api.helper.JsonSchemaHelper.getJsonSchema;

@Slf4j
public class BaseApiCheckpoints {

    @Step("Проверка правильного error сообщения. Type = base")
    public static void checkError(Response response, String textError) {
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
    public static void checkErrorValue(Response response, String textError, String value) {
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
    public static void checkErrorTextIsNotEmpty(Response response) {
        ErrorResponse error = response.as(ErrorResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(error.getErrors().getBase().isEmpty(), "Невалидная ошибка");
        softAssert.assertEquals(error.getErrorMessages().get(0).getField(), "base", "Невалидный тип ошибки");
        softAssert.assertFalse(error.getErrorMessages().get(0).getMessage().isEmpty(), "Невалидная ошибка");
        softAssert.assertFalse(error.getErrorMessages().get(0).getHumanMessage().isEmpty(), "Невалидная ошибка");
        softAssert.assertAll();
    }

    /**
     * Проверяет любые объекты на null, также реалзиована дополнительная проверка на наличие содержимого для String и List
     */
    @Step("Проверяем, что поле {fieldName} не пустое")
    public static <T> void checkFieldIsNotEmpty(T field, String fieldName) {
        assertNotNull(field, String.format("Поле %s равно null", fieldName));
        if (field instanceof String) {
            assertFalse(field.toString().isBlank(), String.format("Поле %s пришло пустым", fieldName));
        } else if (field instanceof Collection) {
            Collection<?> collection = (Collection<?>) field;
            assertFalse(collection.isEmpty(), String.format("Коллекция %s пришла пустой", fieldName));
        }
    }

    /**
     * Проверяет, что два объекта совпадают, используя soft assertion
     */
    @Step("Проверяем, что два объекта совпадают")
    public static <T> void compareTwoObjects(T firstObject, T secondObject, SoftAssert softAssert) {
        softAssert.assertEquals(firstObject, secondObject, String.format("Объекты %s и %s не совпадают",
                firstObject.getClass().getSimpleName(), secondObject.getClass().getSimpleName()));
    }

    /**
     * Проверяет, что два объекта совпадают, используя hard assertion
     */
    @Step("Проверяем, что два объекта совпадают")
    public static <T> void compareTwoObjects(T firstObject, T secondObject) {
        assertEquals(firstObject, secondObject, String.format("Объекты %s и %s не совпадают",
                firstObject.getClass().getSimpleName(), secondObject.getClass().getSimpleName()));
    }

    @Step("Проверяем json-схему")
    public static void checkResponseJsonSchema(Response response, Class<?> clazz) {
        String expectedSchema = getJsonSchema(clazz);
        response.then().assertThat().body(matchesJsonSchema(expectedSchema));
    }
}
