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
import ru.instamart.api.request.self_fee.SelfFeeV3Request;
import ru.instamart.api.response.self_fee.FileUploadResponse;
import ru.instamart.api.response.self_fee.UploadErrorResponse;
import ru.instamart.api.response.self_fee.UploadIdResponse;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.XlsUtil;

import java.util.HashMap;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.Group.SELF_FEE;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.SelfFeeHelper.awaitFile;

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
            description = "После загрузки информация о реестре = данным из xlsx + csv")
    public void test452() {
        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/ТЕСТ_выплаты+тестовые+ноябрь-декабрь.csv");
        files.put("file", "src/test/resources/data/self_fee/ТЕСТ_выплаты+тестовые+ноябрь-декабрь.xlsx");
        final var response = SelfFeeV3Request.Upload.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);
        awaitFile(fileUploadResponse.getId(), 600, 200);
        final var responseFileInfo = SelfFeeV3Request.Upload.GET(fileUploadResponse.getId());
        checkStatusCode200(responseFileInfo);
        final var responseFile = responseFileInfo.as(UploadIdResponse.class);

        Allure.step("Проверка информации о статусе валидации", () -> {
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(responseFile.getFile().getFilename(), "ТЕСТ_выплаты+тестовые+ноябрь-декабрь.xlsx", "Наименование загруженного файла не совпадает");
            softAssert.assertEquals(responseFile.getB2c().getFilename(), "ТЕСТ_выплаты+тестовые+ноябрь-декабрь.csv", "Наименование b2c файла не совпадает");
            softAssert.assertEquals(responseFile.getSber().getId(), 0, "sber.id не совпадает с файлом");
            softAssert.assertEquals(responseFile.getSber().getPartnerCount(), 0, "sber.partnerCount не совпадает с файлом");
            softAssert.assertEquals(responseFile.getSber().getTotalSum(), "", "sber.totalSum не совпадает с файлом");
            softAssert.assertNotNull(responseFile.getOther().getId(), "id пришел пустым");
            softAssert.assertEquals(responseFile.getOther().getPartnerCount(), 3, "other.partnerCount не совпадает с файлом");
            softAssert.assertEquals(responseFile.getOther().getReceiptCount(), 9, "other.receiptCount не совпадает с файлом");
            softAssert.assertEquals(responseFile.getOther().getTotalSum(), "1527.80", "other.totalSum не совпадает с файлом");
            softAssert.assertAll();
        });

    }

    @TmsLink("466")
    @Story("V3 Получение инфо о валидации файлов")
    @Test(groups = {SELF_FEE},
            description = "Ошибки в файле xlsx, когда в xlsx есть период, которого нет в csv")
    public void test466() {
        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/test+дат.csv");
        files.put("file", "src/test/resources/data/self_fee/тест+дат.xlsx");
        final var response = SelfFeeV3Request.Upload.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);
        assertNotNull(fileUploadResponse.getId(), "Номер id вернулся null");

        awaitFile(fileUploadResponse.getId(), 600, 422);
        final var responseFileInfo = SelfFeeV3Request.Upload.GET(fileUploadResponse.getId());
        checkStatusCode422(responseFileInfo);
        final var responseError = responseFileInfo.as(UploadErrorResponse.class);

        Allure.step("Проверка ответа json обработанного файла", () -> {
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(responseError.getFile().getFilename(), "тест+дат.xlsx", "Наименование загруженного файла не совпадает");
            softAssert.assertEquals(responseError.getB2c().getFilename(), "test+дат.csv", "Наименование b2c файла не совпадает");
            softAssert.assertEquals(responseError.getFile().getError(), "В реестре есть ошибки. Скачайте файл и поправьте информацию согласно комментариям. Затем прикрепите повторно", "Текст ошибки не совпадает");
            softAssert.assertAll();
        });
        final var responseXlsFile = SelfFeeV1Request.File.GET(responseError.getFile().getId());
        Sheet sheet = new XlsUtil(responseXlsFile.asInputStream()).getExcel().getSheetAt(0);

        Allure.step("Проверка файла: ", () -> {
            assertNotNull(sheet, "XLSX list is null");
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(sheet.getRow(1).getCell(13).getStringCellValue(), "Сумма в реестре и суммы чеков не совпадают. В реестре 88.87. По чекам 0.00", "Ошибка в файле отличается от ожидаемой");
            softAssert.assertEquals(sheet.getRow(2).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(3).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(4).getCell(13).getStringCellValue(), "Сумма в реестре и суммы чеков не совпадают. В реестре 272.14. По чекам 0.00", "Ошибка в файле отличается от ожидаемой");
            softAssert.assertEquals(sheet.getRow(5).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(6).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(7).getCell(13).getStringCellValue(), "Сумма в реестре и суммы чеков не совпадают. В реестре 691.92. По чекам 0.00", "Ошибка в файле отличается от ожидаемой");
            softAssert.assertEquals(sheet.getRow(8).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(9).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(10).getCell(13).getStringCellValue(), "Сумма в реестре и суммы чеков не совпадают. В реестре 885.87. По чекам 0.00", "Ошибка в файле отличается от ожидаемой");
            softAssert.assertEquals(sheet.getRow(11).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(12).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(13).getCell(13).getStringCellValue(), "Сумма в реестре и суммы чеков не совпадают. В реестре 456.74. По чекам 0.00", "Ошибка в файле отличается от ожидаемой");
            softAssert.assertEquals(sheet.getRow(14).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(15).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(16).getCell(13).getStringCellValue(), "Сумма в реестре и суммы чеков не совпадают. В реестре 365.03. По чекам 0.00", "Ошибка в файле отличается от ожидаемой");
            softAssert.assertAll();
        });
    }

    @TmsLink("451")
    @Story("V3 Получение инфо о валидации файлов")
    @Test(groups = {SELF_FEE},
            description = "Инфо после загрузки xlsx + невалидный формат csv")
    public void test451() {
        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/test-78+разделители+запятая.csv");
        files.put("file", "src/test/resources/data/self_fee/test-78+разделители+запятая.xlsx");
        final var response = SelfFeeV3Request.Upload.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);
        assertNotNull(fileUploadResponse.getId(), "Номер id вернулся null");

        awaitFile(fileUploadResponse.getId(), 600, 422);
        final var responseFileInfo = SelfFeeV3Request.Upload.GET(fileUploadResponse.getId());

        checkStatusCode422(responseFileInfo);
        final var responseError = responseFileInfo.as(UploadErrorResponse.class);

        Allure.step("Проверка ответа json обработанного файла", () -> {
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(responseError.getFile().getFilename(), "test-78+разделители+запятая.xlsx", "Наименование загруженного файла не совпадает");
            softAssert.assertEquals(responseError.getB2c().getFilename(), "test-78+разделители+запятая.csv", "Наименование b2c файла не совпадает");
            softAssert.assertEquals(responseError.getB2c().getError(), "csv bad header", "Текст ошибки не совпадает");
            softAssert.assertAll();
        });
    }

    @TmsLink("460")
    @Story("V3 Получение инфо о валидации файлов")
    @Test(groups = {SELF_FEE},
            description = "Файл с ошибкой без обязательного поля xlsx + валидным csv")
    public void test460() {
        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/пустой+БИК.csv");
        files.put("file", "src/test/resources/data/self_fee/пустой+БИК.xlsx");
        final var response = SelfFeeV3Request.Upload.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);
        assertNotNull(fileUploadResponse.getId(), "Номер id вернулся null");
        awaitFile(fileUploadResponse.getId(), 600, 422);

        final var responseFileInfo = SelfFeeV3Request.Upload.GET(fileUploadResponse.getId());
        checkStatusCode422(responseFileInfo);
        final var responseError = responseFileInfo.as(UploadErrorResponse.class);

        Allure.step("Проверка ответа json обработанного файла", () -> {
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(responseError.getFile().getFilename(), "пустой+БИК.xlsx", "Наименование загруженного файла не совпадает");
            softAssert.assertEquals(responseError.getB2c().getFilename(), "пустой+БИК.csv", "Наименование b2c файла не совпадает");
            softAssert.assertEquals(responseError.getFile().getError(), "В реестре есть ошибки. Скачайте файл и поправьте информацию согласно комментариям. Затем прикрепите повторно", "Текст ошибки не совпадает");
            softAssert.assertAll();
        });

        final var responseXlsFile = SelfFeeV1Request.File.GET(responseError.getFile().getId());
        Sheet sheet = new XlsUtil(responseXlsFile.asInputStream()).getExcel().getSheetAt(0);

        Allure.step("Проверка файла: ", () -> {
            assertNotNull(sheet, "XLSX list is null");
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(sheet.getRow(1).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertEquals(sheet.getRow(2).getCell(13).getStringCellValue(), "Некорректный формат БИК", "Ошибка в файле отличается от ожидаемой");
            softAssert.assertEquals(sheet.getRow(3).getLastCellNum(), 13, "Ячейка ошибки не пустая");
            softAssert.assertAll();
        });
    }
}
