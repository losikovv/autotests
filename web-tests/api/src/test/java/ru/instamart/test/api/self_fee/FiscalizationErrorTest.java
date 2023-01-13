package ru.instamart.test.api.self_fee;

import io.qameta.allure.*;
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
import ru.instamart.api.response.self_fee.RegistryV3Response;
import ru.instamart.api.response.self_fee.UploadIdResponse;
import ru.instamart.jdbc.dao.self_fee.FlagsDao;
import ru.instamart.kraken.data.user.UserManager;

import java.util.HashMap;

import static ru.instamart.api.Group.SELF_FEE;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.SelfFeeHelper.awaitFile;

@Epic("On Demand")
@Feature("Self-Fee")
public class FiscalizationErrorTest extends SelfFeeBase {

    private RegistryV1Response registryResponse;

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        FlagsDao.INSTANCE.update("auto", "no");
        FlagsDao.INSTANCE.update("BIL-786", "false");
        FlagsDao.INSTANCE.update("BIL-798", "false");

        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminSelfFee());

        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/Реестр+ТЕСТ+с+ошибочками+фискализации.csv");
        files.put("file", "src/test/resources/data/self_fee/Реестр+ТЕСТ+с+ошибочками+фискализации.xlsx");
        final var response = SelfFeeV3Request.Upload.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);

        awaitFile(fileUploadResponse.getId(), 600, 200);

        final var responseFileInfo = SelfFeeV3Request.Upload.GET(fileUploadResponse.getId());
        checkStatusCode200(responseFileInfo);
        final var responseFile = responseFileInfo.as(UploadIdResponse.class);

        final var resp = SelfFeeV2Request.POST(responseFile.getOther().getId());
        checkStatusCode(resp, 202);

        final var respFiscal = SelfFeeV1Request.Registry.Fiscalize.POST(responseFile.getOther().getId());
        checkStatusCode(respFiscal, 202);

        final var respRegistry = SelfFeeV1Request.Registry.GET(0, false);
        checkStatusCode200(respRegistry);
        registryResponse = respRegistry.as(RegistryV1Response.class);
    }

    @TmsLinks({
            @TmsLink("328"),
            @TmsLink("332"),
    })
    @Story("Получение инфо об ошибочках фискализации")
    @Test(groups = {SELF_FEE},
            description = "Сумма ошибочных совпадает с суммой в загруженном реестре, Кол-во чеков с ошибками фискализации совпадает с кол-вом в реестре")
    public void fiscalizationTest() {
        final var response = SelfFeeV3Request.GET(0, false);
        checkStatusCode200(response);
        final var responseRegistry = response.as(RegistryV3Response.class);
        Allure.step("", ()->{
            final var softAssert = new SoftAssert();
            softAssert.assertEquals(responseRegistry.getTotalSum(), "213.90", "Total sum не совпадает");
            softAssert.assertEquals(responseRegistry.getSummary().getFailed().getReceiptCount(), 36, "Кол-во чеков с ошибками фискализации не совпадает");
            softAssert.assertAll();
        });
    }
}
