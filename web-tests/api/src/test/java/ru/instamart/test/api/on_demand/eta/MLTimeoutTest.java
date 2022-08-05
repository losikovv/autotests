package ru.instamart.test.api.on_demand.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.eta.ServiceParametersDao;
import ru.instamart.jdbc.dao.eta.StoreParametersDao;
import ru.instamart.jdbc.entity.eta.ServiceParametersEntity;
import ru.instamart.redis.Redis;
import ru.instamart.redis.RedisManager;
import ru.instamart.redis.RedisService;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.UUID;

import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.api.helper.EtaHelper.checkBasketEta;

@Epic("On Demand")
@Feature("ETA")
public class MLTimeoutTest extends RestBase {

    private PredEtaGrpc.PredEtaBlockingStub clientEta;
    //ML работает не со всеми магазинами на стейдже, с STORE_UUID_WITH_ML должно работать
    private final String STORE_UUID_WITH_ML = "684609ad-6360-4bae-9556-03918c1e41c1";
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
            groups = "dispatch-eta-smoke")
    public void getEtaWithFallbackAfterMLTimeout() {
        var request = getStoreUserEtaRequest(STORE_UUID_WITH_ML, 55.7006f, 37.7266f);
        var response = clientEta.getStoreEta(request);
        checkStoreEta(response, STORE_UUID_WITH_ML, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(61)
    @Story("Basket ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае, если ML не возвращает ответ по таймауту",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithMLTimeout() {
        String orderUuid = UUID.randomUUID().toString();
        String shipmentUuid = UUID.randomUUID().toString();

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
