package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.shippingcalc.ScriptsDao;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.ShippingcalcGrpc;

import java.util.Objects;

import static ru.instamart.api.helper.ShippingCalcHelper.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class ScriptTest extends RestBase {

    private Integer scriptId;
    private Integer secondScriptId;
    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private final String FIXED_PRICE_SCRIPT = "exportVars = [\n {\n \"type\": \"int\",\n \"name\": \"basicPrice\",\n \"caption\": \"Базовая цена, в рублях\",\n \"fraction\": 100\n },\n {\n \"type\": \"int\",\n \"name\": \"assemblyIncrease\",\n \"caption\": \"Надбавка за сборку, в рублях\",\n \"fraction\": 100\n },\n {\n \"type\": \"int\",\n \"name\": \"bagIncrease\",\n \"caption\": \"Надбавка за пакеты, в рублях\",\n \"fraction\": 100\n  }\n]; \nfunction result() {\n  return Params.basicPrice + Params.assemblyIncrease + Params.bagIncrease;\n}";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
    }

    @CaseId(38)
    @Story("Create Script")
    @Test(description = "Создание скрипта с валидными данными",
            groups = "dispatch-shippingcalc-smoke")
    public void createScript() {
        var request = getCreateScriptRequest("autotest", FIXED_PRICE_SCRIPT, "autotest");
        var response = clientShippingCalc.createScript(request);
        scriptId = response.getScriptId();
        checkScript(scriptId, "autotest", "TestOk");
    }

    @CaseId(1)
    @Story("Create Script")
    @Test(description = "Создание скрипта с невалидными данными",
            groups = "dispatch-shippingcalc-smoke")
    public void createScriptWithInvalidData() {
        var request = getCreateScriptRequest("autotest", "test", "autotest");
        var response = clientShippingCalc.createScript(request);
        secondScriptId = response.getScriptId();
        checkScript(secondScriptId, "autotest", "TestFailed");
    }

    @CaseId(4)
    @Story("Create Script")
    @Test(description = "Получение ошибки, при создани скрипта с пустым именем",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: script name cannot be empty")
    public void createScriptWithEmptyName() {
        var request = getCreateScriptRequest("", FIXED_PRICE_SCRIPT, "autotest");
        clientShippingCalc.createScript(request);
    }

    @CaseId(10)
    @Story("Create Script")
    @Test(description = "Получение ошибки, при создани скрипта с пустым телом скрипта",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: script body cannot be empty")
    public void createScriptWithEmptyScriptBody() {
        var request = getCreateScriptRequest("autotest", "", "autotest");
        clientShippingCalc.createScript(request);
    }

    @CaseId(13)
    @Story("Create Script")
    @Test(description = "Получение ошибки, при создани скрипта с пустым автором",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: creator_id cannot be empty")
    public void createScriptWithEmptyCreatorId() {
        var request = getCreateScriptRequest("autotest", FIXED_PRICE_SCRIPT, "");
        clientShippingCalc.createScript(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (Objects.nonNull(scriptId) ) {
            ScriptsDao.INSTANCE.delete(scriptId);
        }
        if (Objects.nonNull(secondScriptId) ) {
            ScriptsDao.INSTANCE.delete(secondScriptId);
        }
    }
}
