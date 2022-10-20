package ru.instamart.test.api.on_demand.surgelevel;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.sbermarket.qase.annotation.CaseId;
import surgelevel.v2.SurgelevelV2;

@Epic("On Demand")
@Feature("gRPC V2")
public class GrpcV2Test extends GrpcBase {

    private surgelevel.v2.ServiceGrpc.ServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SURGELEVEL);
        client = surgelevel.v2.ServiceGrpc.newBlockingStub(channel);
    }

    @CaseId(50)
    @Story("Get Config")
    @Test(description = "Получить дефолтную конфигурацию",
            groups = "ondemand-surgelevel-smoke")
    public void getConfig() {
        var request = SurgelevelV2.GetConfigRequest.newBuilder().build();
        var response = client.getConfig(request);

        check.notNull(response.getConfig());
        check.notNull(response.getConfig().getId());
        check.value(response.getConfig().getStep());
        check.notNull(response.getConfig().getFormula());
        check.notNull(response.getConfig().getFormula().getId());
        check.notNull(response.getConfig().getFormula().getName());
        check.notNull(response.getConfig().getFormula().getScript());
    }
}
