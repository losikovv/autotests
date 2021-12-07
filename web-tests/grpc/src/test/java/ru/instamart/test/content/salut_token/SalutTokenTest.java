package ru.instamart.test.content.salut_token;

import io.qameta.allure.Epic;
import io.qase.api.annotation.CaseId;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import salut_token.SalutToken;
import salut_token.TokenGeneratorGrpc;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

@Deprecated
@Epic("Salut Token Microservice")
@Slf4j
public class SalutTokenTest extends GrpcBase {
    private TokenGeneratorGrpc.TokenGeneratorBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        //channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_SALUT_TOKEN);
        client = TokenGeneratorGrpc.newBlockingStub(channel);
    }

    @CaseId(1)
    @Test(  description = "Get token",
            groups = "grpc-salut-token")
    public void getToken() {
        var request = SalutToken.TokenRequest.newBuilder()
                .setInstallationId("258b347d-b978-4a75-8f02-407d43a932af").build();

        var response = client.getToken(request);

        assertNotNull(response.getToken(), "Не вернулся токен");
        assertFalse(response.getToken().isEmpty(), "Вернулся пустой токен");
    }
}
