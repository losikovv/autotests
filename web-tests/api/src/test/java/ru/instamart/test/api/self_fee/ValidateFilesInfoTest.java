package ru.instamart.test.api.self_fee;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.SelfFeeBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.self_fee.SelfFeeV3Request;
import ru.instamart.api.response.self_fee.FileUploadResponse;
import ru.instamart.kraken.data.user.UserManager;

import java.util.HashMap;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.Group.SELF_FEE;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("On Demand")
@Feature("Self-Fee")
public class ValidateFilesInfoTest extends SelfFeeBase {

    @BeforeClass(alwaysRun = true)
    public void auth() {
        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminSelfFee());
    }


    @TmsLink("452")
    @Story("V3 Получение инфо о валидации файлов")
    @Test(groups = {SELF_FEE},
            description = "После загрзуки информация о реестре  =  данным из xlsx + csv")
    public void test452() {
        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/ТЕСТ_выплаты+тестовые+ноябрь-декабрь.csv");
        files.put("file", "src/test/resources/data/self_fee/ТЕСТ_выплаты+тестовые+ноябрь-декабрь.xlsx");
        final var response = SelfFeeV3Request.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);
        final var responseFileInfo = SelfFeeV3Request.GET(fileUploadResponse.getId());
        checkStatusCode200(responseFileInfo);
        //TODO  Дописать проверки после актуализации кейса или заведения бага

    }

    @TmsLink("466")
    @Story("V3 Получение инфо о валидации файлов")
    @Test(groups = {SELF_FEE},
            description = "Ошибки в файле xlsx, когда в xlsx есть период, которого нет в csv")
    public void test466() {
        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/test+дат.csv");
        files.put("file", "src/test/resources/data/self_fee/тест+дат.xlsx");
        final var response = SelfFeeV3Request.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);
        assertNotNull(fileUploadResponse.getId(), "Номер id вернулся null");

        //TODO дописать шаг 3 и проверки после актуализации кейса или заведения бага
    }

    @TmsLink("451")
    @Story("V3 Получение инфо о валидации файлов")
    @Test(groups = {SELF_FEE},
            description = "Инфо после загрузки xlsx + невалидный формат csv")
    public void test451() {
        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/test-78+разделители+запятая.csv");
        files.put("file", "src/test/resources/data/self_fee/test-78+разделители+запятая.xlsx");
        final var response = SelfFeeV3Request.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);
        assertNotNull(fileUploadResponse.getId(), "Номер id вернулся null");
        final var responseFileInfo = SelfFeeV3Request.GET(fileUploadResponse.getId());
        checkStatusCode200(responseFileInfo);
        //TODO дописать шаг 3 и проверки после актуализации кейса или заведения бага
    }

    @TmsLink("460")
    @Story("V3 Получение инфо о валидации файлов")
    @Test(groups = {SELF_FEE},
            description = "Файл с ошибкой без обязательного поля xlsx + валидным csv")
    public void test460() {
        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/пустой+БИК.csv");
        files.put("file", "src/test/resources/data/self_fee/пустой+БИК.xlsx");
        final var response = SelfFeeV3Request.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);
        assertNotNull(fileUploadResponse.getId(), "Номер id вернулся null");
        final var responseFileInfo = SelfFeeV3Request.GET(fileUploadResponse.getId());
        checkStatusCode200(responseFileInfo);
        //TODO дописать шаг 3 и проверки после актуализации кейса или заведения бага
    }
}
