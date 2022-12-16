package ru.instamart.test.api.on_demand.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.EtaBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.eta.ServiceParametersDao;
import ru.instamart.jdbc.entity.eta.ServiceParametersEntity;
import ru.instamart.redis.Redis;
import ru.instamart.redis.RedisManager;
import ru.instamart.redis.RedisService;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.UUID;

import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.api.helper.EtaHelper.checkBasketEta;

@Epic("ETA")
@Feature("ML Timeout")
public class MLTimeoutTest extends EtaBase {

    private PredEtaGrpc.PredEtaBlockingStub clientEta;
    private ServiceParametersEntity serviceParameters;
    private boolean isMLTimeoutUpdated;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientEta = PredEtaGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ETA));
        serviceParameters = ServiceParametersDao.INSTANCE.getServiceParameters();
        isMLTimeoutUpdated = checkMLTimeout(serviceParameters.getWaitMlTimeout());
        updateStoreMLStatus(STORE_UUID_WITH_ML, true);
        RedisService.del(RedisManager.getConnection(Redis.ETA), List.of(
                String.format("store_%s", STORE_UUID_WITH_ML),
                "service_parameters"
        ));
    }

    @CaseId(62)
    @Story("Store ETA")
    @Test(description = "Проверка отсутствия ошибки, в случае, если ML не возвращает ответ по таймауту",
            groups = "ondemand-eta")
    public void getEtaWithFallbackAfterMLTimeout() {
        var request = getStoreUserEtaRequest(STORE_UUID_WITH_ML, 55.7006f, 37.7266f);
        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID_WITH_ML, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(61)
    @Story("Basket ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае, если ML не возвращает ответ по таймауту",
            groups = "ondemand-eta")
    public void getBasketEtaWithMLTimeout() {
        final var orderUuid = UUID.randomUUID().toString();
        final var shipmentUuid = UUID.randomUUID().toString();

        var request = getUserEtaRequest(UUID.randomUUID().toString(), 55.7010f, 37.7280f, STORE_UUID_WITH_ML, 55.7010f, 37.7280f,orderUuid, shipmentUuid);
        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, orderUuid, shipmentUuid, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (isMLTimeoutUpdated) {
            ServiceParametersDao.INSTANCE.updateWaitMlTimeout(serviceParameters.getWaitMlTimeout());
        }
        RedisService.del(RedisManager.getConnection(Redis.ETA), "service_parameters");
    }
}
