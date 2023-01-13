package ru.instamart.test.api.self_fee;

import io.qameta.allure.Allure;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.qameta.allure.TmsLinks;
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
import ru.instamart.api.response.self_fee.UploadIdResponse;
import ru.instamart.jdbc.dao.self_fee.FlagsDao;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.XlsUtil;

import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.Group.SELF_FEE;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.SelfFeeHelper.awaitFile;

public class GetFiscalizationsTest extends SelfFeeBase {

    @BeforeClass(alwaysRun = true)
    public void auth() {
        FlagsDao.INSTANCE.update("auto", "no");
        FlagsDao.INSTANCE.update("BIL-786", "false");
        FlagsDao.INSTANCE.update("BIL-798", "false");

        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminSelfFee());
    }


    @TmsLinks({
            @TmsLink("263"),
            @TmsLink("264"),
            @TmsLink("265"),
            @TmsLink("267"),
    })
    @Story("Получение xlsx реестра с фискализациями")
    @Test(groups = {SELF_FEE},
            description = "Получить реестр с фискализациями")
    public void test263() {
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

        Allure.step("Проверка json загруженного файла", ()->{
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(responseFile.getFile().getFilename(), "ТЕСТ_выплаты+тестовые+ноябрь-декабрь.xlsx", "Наименование загруженного файла не совпадает");
            softAssert.assertEquals(responseFile.getB2c().getFilename(), "ТЕСТ_выплаты+тестовые+ноябрь-декабрь.csv", "Наименование b2c файла не совпадает");
            softAssert.assertEquals(responseFile.getSber().getReceiptCount(), 0, "sber.receipt_count не совпадает с файлом");
            softAssert.assertEquals(responseFile.getSber().getPartnerCount(), 0, "sber.partnerCount не совпадает с файлом");
            softAssert.assertEquals(responseFile.getSber().getTotalSum(), "", "sber.totalSum не совпадает с файлом");
            softAssert.assertNotNull(responseFile.getOther().getId(), "id пришел пустым");
            softAssert.assertEquals(responseFile.getOther().getPartnerCount(), 3, "other.partnerCount не совпадает с файлом");
            softAssert.assertEquals(responseFile.getOther().getReceiptCount(), 9, "other.receiptCount не совпадает с файлом");
            softAssert.assertEquals(responseFile.getOther().getTotalSum(), "1527.80", "other.totalSum не совпадает с файлом");
            softAssert.assertAll();
        });

        final var resp = SelfFeeV2Request.POST(responseFile.getOther().getId());
        checkStatusCode(resp, 202);
        final var respFiscal = SelfFeeV1Request.Registry.GET(0, false);
        checkStatusCode200(respFiscal);

        //TODO пока возвращается 500
        final var respRegistry = SelfFeeV1Request.Registry.Receipt.GET(responseFile.getOther().getId(), true);
        checkStatusCode200(respRegistry);
        assertEquals(respRegistry.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "content type не совпадает");

        Sheet sheet = new XlsUtil(respRegistry.asInputStream()).getExcel().getSheetAt(0);

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
