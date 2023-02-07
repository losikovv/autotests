package ru.instamart.test.api.self_fee;

import io.qameta.allure.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.SelfFeeBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.self_fee.SelfFeeV1Request;
import ru.instamart.api.request.self_fee.SelfFeeV2Request;
import ru.instamart.api.request.self_fee.SelfFeeV3Request;
import ru.instamart.api.response.self_fee.FileUploadResponse;
import ru.instamart.api.response.self_fee.RegistryV1Response;
import ru.instamart.api.response.self_fee.UploadIdResponse;
import ru.instamart.jdbc.dao.self_fee.FlagsDao;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.XlsUtil;

import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.Group.SELF_FEE;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.SelfFeeHelper.awaitFile;
import static ru.instamart.api.helper.SelfFeeHelper.awaitFiscal;

@Epic("On Demand")
@Feature("Self-Fee")
public class XlsxRegistryErrorsTest extends SelfFeeBase {

    private final String fileFiscal = "Реестр+ТЕСТ+с+ошибочками+фискализации.xlsx";
    private RegistryV1Response registryResponse;

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        FlagsDao.INSTANCE.update("auto", "no");
        FlagsDao.INSTANCE.update("BIL-786", "false");
        FlagsDao.INSTANCE.update("BIL-798", "false");

        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminSelfFee());

        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/Реестр+ТЕСТ+с+ошибочками+фискализации.csv");
        files.put("file", "src/test/resources/data/self_fee/" + fileFiscal);
        final var response = SelfFeeV3Request.Upload.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);

        final var responseFileInfo = awaitFile(fileUploadResponse.getId(), 600);
        checkStatusCode200(responseFileInfo);
        final var responseFile = responseFileInfo.as(UploadIdResponse.class);

        final var resp = SelfFeeV2Request.POST(responseFile.getOther().getId());
        checkStatusCode(resp, 202);

        final var respFiscal = SelfFeeV1Request.Registry.Fiscalize.POST(responseFile.getOther().getId());
        checkStatusCode(respFiscal, 200);

        awaitFiscal(fileFiscal, 600);
        final var respRegistry = SelfFeeV1Request.Registry.GET(0, false);
        checkStatusCode200(respRegistry);
        registryResponse = respRegistry.as(RegistryV1Response.class);
    }

    @TmsLinks({
            @TmsLink("255"),
            @TmsLink("256"),
            @TmsLink("317"),
    })
    @Story("Получение xlsx реестра с ошибками")
    @Test(groups = {SELF_FEE},
            description = "Получить реестр с ошибками. В реестре есть все обязательные поля. Текст ошибки в каждой строке файла и совпадает с ожидаемой")
    public void xlsxRegistryErrorsTest() {
        final var response = SelfFeeV3Request.Registry.GET(registryResponse.getResult().get(0).getId(), true);
        checkStatusCode(response, 200);
        Allure.step("Проверка contentType", () -> assertEquals(response.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "content type не совпадает"));
        Sheet sheet = new XlsUtil(response.asInputStream())
                .getExcel()
                .getSheetAt(0);
        Allure.step("Проверка шапки xlsx файла", () -> {
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(sheet.getRow(0).getCell(0).getStringCellValue(), "Ошибка фискализации", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(1).getStringCellValue(), "Дата ошибки фискализации", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(2).getStringCellValue(), "Фамилия", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(3).getStringCellValue(), "Имя", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(4).getStringCellValue(), "Отчество", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(5).getStringCellValue(), "ИНН", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(6).getStringCellValue(), "Описание задания для чека", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(7).getStringCellValue(), "Сумма включая налог 4%", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(8).getStringCellValue(), "Сумма включая налог 6%", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(9).getStringCellValue(), "Сумма выплаты", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(10).getStringCellValue(), "Период начисления: начало", "Ошибка наименования колонки");
            softAssert.assertEquals(sheet.getRow(0).getCell(11).getStringCellValue(), "Период начисления: окончание", "Ошибка наименования колонки");
            softAssert.assertAll();
        });
        Allure.step("Проверка ошибок по партнерам", () -> {
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(sheet.getRow(1).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 1");
            softAssert.assertEquals(sheet.getRow(2).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 2");
            softAssert.assertEquals(sheet.getRow(3).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 3");
            softAssert.assertEquals(sheet.getRow(4).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 4");
            softAssert.assertEquals(sheet.getRow(5).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 5");
            softAssert.assertEquals(sheet.getRow(6).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 6");
            softAssert.assertEquals(sheet.getRow(7).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 7");
            softAssert.assertEquals(sheet.getRow(8).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 8");
            softAssert.assertEquals(sheet.getRow(9).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 9");
            softAssert.assertEquals(sheet.getRow(10).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 10");
            softAssert.assertEquals(sheet.getRow(11).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 11");
            softAssert.assertEquals(sheet.getRow(12).getCell(0).getStringCellValue(), "Партнер перестал быть самозанятым. ", "Неверный статус ошибки строки 12");
            softAssert.assertAll();
        });
    }
}
