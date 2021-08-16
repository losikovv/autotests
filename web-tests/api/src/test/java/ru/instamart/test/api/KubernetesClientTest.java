package ru.instamart.test.api;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


@Epic("KUBERNETES")
public class KubernetesClientTest extends RestBase {

    @Story("Список подов для namespace")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Список подов для namespace = s-sb-stfkraken")
    public void kubeTest() throws IOException, ApiException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();

        V1PodList list2 = api.listNamespacedPod("s-sb-stfkraken", null, null, null,
                null, "app", null, null, null, null, null);
        AtomicReference<String> attach = new AtomicReference<>();
        list2.getItems().stream().forEach(item -> attach.set(attach + "\r\n" + item.getMetadata().getName()));
        Allure.addAttachment("Pods kraken stage", String.valueOf(attach));

    }
}
