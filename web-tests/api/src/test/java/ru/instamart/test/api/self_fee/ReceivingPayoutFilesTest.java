package ru.instamart.test.api.self_fee;

import io.qameta.allure.*;
import lombok.SneakyThrows;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.bean.self_fee.CheckBean;
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
import ru.instamart.kraken.util.CsvUtil;
import shelf.ShelfOuterClass;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.Group.SELF_FEE;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.SelfFeeHelper.awaitFile;
import static ru.instamart.api.helper.SelfFeeHelper.awaitFiscal;
import static ru.instamart.kraken.util.FileUtil.*;

@Epic("On Demand")
@Feature("Self-Fee")
public class ReceivingPayoutFilesTest extends SelfFeeTest {

    private final String fileFiscal = "Реестр ТЕСТ выплаты сбер и не сбер.xlsx";
    private RegistryV1Response registryResponse;
    private Path sberZipFile;

    @BeforeClass(alwaysRun = true)
    public void precondition() {
        FlagsDao.INSTANCE.update("auto", "no");
        FlagsDao.INSTANCE.update("BIL-786", "false");
        FlagsDao.INSTANCE.update("BIL-798", "false");

        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminSelfFee());

        final var files = new HashMap<String, String>();
        files.put("b2c", "src/test/resources/data/self_fee/Реестр ТЕСТ выплаты сбер и не сбер.csv");
        files.put("file", "src/test/resources/data/self_fee/Реестр ТЕСТ выплаты сбер и не сбер.xlsx");
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
        checkStatusCode(respFiscal, 200);

        awaitFiscal(fileFiscal, 600);
        final var respRegistry = SelfFeeV1Request.Registry.GET(0, false);
        checkStatusCode200(respRegistry);
        registryResponse = respRegistry.as(RegistryV1Response.class);
    }

    @TmsLink("217")
    @Story("Получение файлов на выплату")
    @Test(groups = {SELF_FEE},
            description = "Получение архива на выплату в сбер")
    public void test217() {
        final var reestrId = registryResponse.getResult().get(0).getId();
        final var sberResponse = SelfFeeV1Request.Registry.Payroll.GET(reestrId);
        sberZipFile = saveFile(sberResponse, "testZipFile.zip");
        final var zipFilesList = readZipFile(sberZipFile);
        assertTrue(zipFilesList.size()>0, "Файл не прочитан, архивов нет");
    }


    @SneakyThrows
    @TmsLinks({
            @TmsLink("318"),
            @TmsLink("320"),
            @TmsLink("548"),
            @TmsLink("218"),
            //@TmsLink("319"), Дописать проверки billing_qa
            @TmsLink("327"),
            //@TmsLink("549"), Дописать проверки billing_qa
    })
    @Story("Получение файлов на выплату")
    @Test(enabled = false,
            groups = {SELF_FEE},
            dependsOnMethods = "test217",
            description = "Получение архива на выплату в сбер")
    public void receivingPayoutTest() {
        final var patchSberFiles = Paths.get("build", "sberZipFiles");
        unzipFolder(sberZipFile, patchSberFiles);
        final var allFiles = getAllFileDirectory(patchSberFiles);
        //TODO дописать проверки

        final var csv = new CsvUtil<>(CheckBean.class);
        final var lines = csv.readByPosition(allFiles.get(0).toFile(), StandardCharsets.UTF_8);
        //TODO дописать проверки
    }
}
