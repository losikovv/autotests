package ru.instamart.test.api.self_fee;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.SelfFeeBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.self_fee.SelfFeeV1Request;
import ru.instamart.api.request.self_fee.SelfFeeV1Request.Registry.Cancellation;
import ru.instamart.api.request.self_fee.SelfFeeV2Request;
import ru.instamart.api.request.self_fee.SelfFeeV3Request;
import ru.instamart.api.response.self_fee.*;
import ru.instamart.jdbc.dao.self_fee.FlagsDao;
import ru.instamart.kraken.data.user.UserManager;

import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.Group.SELF_FEE;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.SelfFeeHelper.awaitFile;
import static ru.instamart.api.helper.SelfFeeHelper.awaitFiscal;

@Epic("On Demand")
@Feature("Self-Fee")
public class CancellationsTest extends SelfFeeBase {

    private final String fileFiscal = "Реестр+ТЕСТ+ПРОД+мелкий+файл.xlsx";

    @TmsLinks({
            @TmsLink("550"),
            @TmsLink("551"),
            @TmsLink("553"),
    })
    @Story("Отмены")
    @Test(enabled = false,
            groups = {SELF_FEE},
            description = "Отмена b2b чеков")
    public void cancelB2bCheckTest() {
        FlagsDao.INSTANCE.update("auto", "no");
        FlagsDao.INSTANCE.update("BIL-786", "false");
        FlagsDao.INSTANCE.update("BIL-798", "false");

        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminSelfFee());

        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/Реестр+ТЕСТ+ПРОД+мелкий+файл.csv");
        files.put("file", "src/test/resources/data/self_fee/Реестр+ТЕСТ+ПРОД+мелкий+файл.xlsx");
        final var response = SelfFeeV3Request.Upload.POST(files);
        checkStatusCode(response, 202);
        final var fileUploadResponse = response.as(FileUploadResponse.class);

        final var responseFileInfo = awaitFile(fileUploadResponse.getId(), 600);
        checkStatusCode200(responseFileInfo);
        final var responseFile = responseFileInfo.as(UploadIdResponse.class);

        final var resp = SelfFeeV2Request.POST(responseFile.getOther().getId());
        checkStatusCode(resp, 202);

        final var respFiscal = SelfFeeV1Request.Registry.Fiscalize.POST(responseFile.getOther().getId());
        checkStatusCode(respFiscal, 202);

        awaitFiscal(fileFiscal, 600);
        final var respRegistry = SelfFeeV1Request.Registry.GET(0, false);
        checkStatusCode200(respRegistry);
        final var registryResponse = respRegistry.as(RegistryV1Response.class);

        final var respRegistryFile = SelfFeeV1Request.Registry.Receipt.GET(responseFile.getOther().getId(), true);
        checkStatusCode200(respRegistryFile);
        assertEquals(respRegistryFile.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "content type не совпадает");

        final var cancelFileResponse = SelfFeeV1Request.File.POST("src/test/resources/data/self_fee/CancellationsFile.xlsx");
        checkStatusCode201(cancelFileResponse);
        final var fileCancelResponse = cancelFileResponse.as(FileCancelResponse.class);

        //TODO нет данных по id
        final var cancellationResponse = Cancellation.POST(1, fileCancelResponse.getId());
        final var cancellation = cancellationResponse.as(RegistryV3Response.class);

        final var resp2Registry = SelfFeeV1Request.Registry.GET(0, false);
        checkStatusCode200(resp2Registry);
        final var registry2Response = resp2Registry.as(RegistryV1Response.class);
        //TODO дописать проверки
    }
}
