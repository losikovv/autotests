package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.shippingcalc.ScriptsDao;
import ru.instamart.jdbc.entity.shippingcalc.ScriptsEntity;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.*;

import java.util.Objects;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.ShippingCalcHelper.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class ScriptTest extends ShippingCalcBase {

    private Integer scriptId;
    private Integer secondScriptId;
    private Integer strategyId;
    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private final String FIXED_PRICE_SCRIPT = "exportVars = [\n {\n \"type\": \"int\",\n \"name\": \"basicPrice\",\n \"caption\": \"Базовая цена, в рублях\",\n \"fraction\": 100\n },\n {\n \"type\": \"int\",\n \"name\": \"assemblyIncrease\",\n \"caption\": \"Надбавка за сборку, в рублях\",\n \"fraction\": 100\n },\n {\n \"type\": \"int\",\n \"name\": \"bagIncrease\",\n \"caption\": \"Надбавка за пакеты, в рублях\",\n \"fraction\": 100\n  }\n]; \nfunction result() {\n  return Params.basicPrice + Params.assemblyIncrease + Params.bagIncrease;\n}";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
        strategyId = addStrategy(false, 0, DeliveryType.COURIER_DELIVERY.toString());
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
    @Test(description = "Получение ошибки, при создании скрипта с пустым именем",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: script name cannot be empty")
    public void createScriptWithEmptyName() {
        var request = getCreateScriptRequest("", FIXED_PRICE_SCRIPT, "autotest");
        clientShippingCalc.createScript(request);
    }

    @CaseId(10)
    @Story("Create Script")
    @Test(description = "Получение ошибки, при создании скрипта с пустым телом скрипта",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: script body cannot be empty")
    public void createScriptWithEmptyScriptBody() {
        var request = getCreateScriptRequest("autotest", "", "autotest");
        clientShippingCalc.createScript(request);
    }

    @CaseId(13)
    @Story("Create Script")
    @Test(description = "Получение ошибки, при создании скрипта с пустым автором",
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
            dependsOnMethods = "getScript")
    public void updateScriptWithInvalidData() {
        var request = getUpdateScriptRequest(scriptId, "autotest-update-with-rule", "test", "autotest-update");
        clientShippingCalc.updateScript(request);
        checkUpdatedScript(scriptId, "autotest-update-with-rule", "TestFailed");
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
            dependsOnMethods = "getScript")
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
            dependsOnMethods = "getScript")
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
            dependsOnMethods = "getScript")
    public void updateScriptWithEmptyCreatorId() {
        var request = getUpdateScriptRequest(scriptId, "autotest-update", FIXED_PRICE_SCRIPT, "");
        clientShippingCalc.updateScript(request);
    }

    @CaseId(34)
    @Story("Get Script")
    @Test(description = "Получение существующего скрипта",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createScript")
    public void getScript() {
        var request = GetScriptRequest.newBuilder()
                        .setScriptId(scriptId)
                        .build();
        var response = clientShippingCalc.getScript(request);

        Allure.step("Проверка скрипта в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(response.getScript(), "В ответе пустой скрипт");
            compareTwoObjects(response.getScript().getScriptId(), scriptId, softAssert);
            softAssert.assertTrue(response.getScript().getRequiredParamsCount() > 0, "В ответе пустой список обязательных параметров");
            softAssert.assertAll();
        });
    }

    @CaseId(35)
    @Story("Get Script")
    @Test(description = "Получение ошибки при отправке запроса с несуществующим скриптом",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot get script: entity not found")
    public void getScriptNonExistent() {
        var request = GetScriptRequest.newBuilder()
                .setScriptId(1234567890)
                .build();

        clientShippingCalc.getScript(request);
    }

    @CaseId(36)
    @Story("Get Script")
    @Test(description = "Получение ошибки при запросе удаленного скрипта",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot get script: entity not found")
    public void getScriptDeleted() {
        var request = GetScriptRequest.newBuilder()
                .setScriptId(278)
                .build();

        clientShippingCalc.getScript(request);
    }

    @CaseId(135)
    @Story("Delete Script")
    @Test(description = "Удаление существующего скрипта без привязки к правилам",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "updateScript")
    public void deleteScript() {
        var request = getDeleteScriptRequest(secondScriptId);
        var response = clientShippingCalc.deleteScript(request);

        ScriptsEntity scripts = ScriptsDao.INSTANCE.getScriptById(secondScriptId);
        Allure.step("Проверка удаления скрипта", () -> {
            final SoftAssert softAssert = new SoftAssert();
            compareTwoObjects(response.toString(), "", softAssert);
            softAssert.assertNotNull(scripts.getDeletedAt(), "Скрипт не помечен удаленным");
            softAssert.assertAll();
        });
    }

    @CaseId(134)
    @Story("Delete Script")
    @Test(description = "Получение ошибки, при удалении скрипта, привязанного к правилу",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot delete script: can't delete script: script is used in non-deleted rules",
            dependsOnMethods = "updateScriptWithInvalidData")
    public void deleteScriptWithRules() {
        addRule(strategyId, "autotest-update-with-rule", "{}", 0, "delivery_price");
        var request = getDeleteScriptRequest(scriptId);
        clientShippingCalc.deleteScript(request);
    }

    @CaseId(131)
    @Story("Delete Script")
    @Test(description = "Получение ошибки, при удалении несуществующего скрипта",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot delete script: entity not found")
    public void deleteScriptNonExistent() {
        var request = getDeleteScriptRequest(123456789);
        clientShippingCalc.deleteScript(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (Objects.nonNull(scriptId) ) {
            ScriptsDao.INSTANCE.delete(scriptId);
        }
        if (Objects.nonNull(secondScriptId) ) {
            ScriptsDao.INSTANCE.delete(secondScriptId);
        }
        deleteCreatedStrategy(strategyId);
    }
}
