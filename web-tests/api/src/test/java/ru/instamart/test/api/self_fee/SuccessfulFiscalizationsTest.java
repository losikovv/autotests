package ru.instamart.test.api.self_fee;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
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
import ru.instamart.kraken.data.user.UserManager;

import java.util.HashMap;

import static ru.instamart.api.Group.SELF_FEE;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.SelfFeeHelper.awaitFile;

@Epic("On Demand")
@Feature("Self-Fee")
public class SuccessfulFiscalizationsTest extends SelfFeeBase {

    @BeforeClass(alwaysRun = true)
    public void auth() {
        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminSelfFee());
    }

    @TmsLink("452")
    @Story("Получение инфо об успешных фискализациях")
    @Test(groups = {SELF_FEE},
            description = "Получение ненулевой суммы успешных фискализации")
    public void test452() {
        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/Успешная+фискализация.csv");
        files.put("file", "src/test/resources/data/self_fee/Успешная+фискализация.xlsx");
        final var response = SelfFeeV3Request.Upload.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);
        awaitFile(fileUploadResponse.getId(), 600, 200);
        final var responseFileInfo = SelfFeeV3Request.Upload.GET(fileUploadResponse.getId());
        checkStatusCode200(responseFileInfo);
        final var responseFile = responseFileInfo.as(UploadIdResponse.class);

        final var softAssert = new SoftAssert();
        softAssert.assertEquals(responseFile.getFile().getFilename(), "Успешная+фискализация.xlsx", "Наименование загруженного файла не совпадает");
        softAssert.assertEquals(responseFile.getB2c().getFilename(), "Успешная+фискализация.csv", "Наименование b2c файла не совпадает");
        softAssert.assertEquals(responseFile.getSber().getReceiptCount(), 0, "sber.receipt_count не совпадает с файлом");
        softAssert.assertEquals(responseFile.getSber().getPartnerCount(), 0, "sber.partnerCount не совпадает с файлом");
        softAssert.assertEquals(responseFile.getSber().getTotalSum(), "", "sber.totalSum не совпадает с файлом");
        softAssert.assertNotNull(responseFile.getOther().getId(), "id пришел пустым");
        softAssert.assertEquals(responseFile.getOther().getPartnerCount(), 4, "other.partnerCount не совпадает с файлом");
        softAssert.assertEquals(responseFile.getOther().getReceiptCount(), 30, "other.receiptCount не совпадает с файлом");
        softAssert.assertEquals(responseFile.getOther().getTotalSum(), "1305.79", "other.totalSum не совпадает с файлом");
        softAssert.assertAll();

        final var resp = SelfFeeV2Request.POST(responseFile.getOther().getId());
        checkStatusCode(resp, 202);
        final var respFiscal = SelfFeeV1Request.Registry.Fiscalize.POST(responseFile.getOther().getId());
        checkStatusCode(respFiscal, 202);

        final var respRegistry = SelfFeeV1Request.Registry.GET();
        checkStatusCode200(respRegistry);
        checkResponseJsonSchema(respRegistry, RegistryV1Response.class);
    }
}
