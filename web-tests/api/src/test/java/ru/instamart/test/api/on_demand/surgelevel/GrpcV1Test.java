package ru.instamart.test.api.on_demand.surgelevel;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import io.qameta.allure.TmsLink;
import surgelevel.ServiceGrpc;
import surgelevel.Surgelevel;

@Epic("Surgelevel")
@Feature("gRPC V1")
public class GrpcV1Test extends GrpcBase {

    private ServiceGrpc.ServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SURGELEVEL);
        client = ServiceGrpc.newBlockingStub(channel);
    }

    @TmsLink("50")
    @Story("Get Config")
    @Test(description = "Получить дефолтную конфигурацию",
            groups = "ondemand-surgelevel")
    public void getConfig() {
        var request = Surgelevel.GetConfigRequest.newBuilder().build();
        var response = client.getConfig(request);

        check.notNull(response.getConfig());
        check.value(response.getConfig().getStepSurgeLevel());
        check.notNull(response.getConfig().getFormula());
        check.notNull(response.getConfig().getFormula().getId());
    }
}
