package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.shippingcalc.ScriptsDao;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.ShippingcalcGrpc;

import java.util.Objects;

import static ru.instamart.api.helper.ShippingCalcHelper.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class ScriptTest extends ShippingCalcBase {

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

    @CaseId(15)
    @Story("Update Script")
    @Test(description = "Обновление существующего скрипта с валидными данными",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createScriptWithInvalidData")
    public void updateScript() {
        var request = getUpdateScriptRequest(secondScriptId, "autotest-update", FIXED_PRICE_SCRIPT, "autotest-update");
        clientShippingCalc.updateScript(request);
        checkUpdatedScript(secondScriptId, "autotest-update", "TestOk");
    }

    @CaseId(18)
    @Story("Update Script")
    @Test(description = "Обновление существующего скрипта с невалидными данными",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createScript")
    public void updateScriptWithInvalidData() {
        var request = getUpdateScriptRequest(scriptId, "autotest-update", "test", "autotest-update");
        clientShippingCalc.updateScript(request);
        checkUpdatedScript(scriptId, "autotest-update", "TestFailed");
    }

    @CaseId(16)
    @Story("Update Script")
    @Test(description = "Получение ошибки, при обновлении несуществующего скрипта с валидными данными",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update script: entity not found")
    public void updateScriptWithNonExistentScriptId() {
        var request = getUpdateScriptRequest(1243254621, "autotest-update", FIXED_PRICE_SCRIPT, "autotest-update");
        clientShippingCalc.updateScript(request);
    }

    @CaseId(20)
    @Story("Update Script")
    @Test(description = "Получение ошибки, при обновлении существующего скрипта с пустым именем",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: script name cannot be empty",
            dependsOnMethods = "createScript")
    public void updateScriptWithEmptyName() {
        var request = getUpdateScriptRequest(scriptId, "", FIXED_PRICE_SCRIPT, "autotest-update");
        clientShippingCalc.updateScript(request);
    }

    @CaseId(26)
    @Story("Update Script")
    @Test(description = "Получение ошибки, при обновлении существующего скрипта с пустым телом скрипта",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: script body cannot be empty",
            dependsOnMethods = "createScript")
    public void updateScriptWithEmptyScriptBody() {
        var request = getUpdateScriptRequest(scriptId, "autotest-update", "", "autotest-update");
        clientShippingCalc.updateScript(request);
    }

    @CaseId(29)
    @Story("Update Script")
    @Test(description = "Получение ошибки, при обновлении существующего скрипта с пустым автором",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: creator_id cannot be empty",
            dependsOnMethods = "createScript")
    public void updateScriptWithEmptyCreatorId() {
        var request = getUpdateScriptRequest(scriptId, "autotest-update", FIXED_PRICE_SCRIPT, "");
        clientShippingCalc.updateScript(request);
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
