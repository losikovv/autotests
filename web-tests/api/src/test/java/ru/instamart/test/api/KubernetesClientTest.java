package ru.instamart.test.api;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1PodList;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static ru.instamart.k8s.K8sConsumer.getLogs;
import static ru.instamart.k8s.K8sConsumer.getPodList;


@Slf4j
@Epic("KUBERNETES")
public class KubernetesClientTest extends RestBase {
    private final String namespace = EnvironmentProperties.K8S_NAME_STF_SPACE;
    private final String labelSelector = EnvironmentProperties.K8S_LABEL_STF_SELECTOR;

    @Story("Список подов для namespace")
    @Test(groups = {"api-instamart-regress"},
            description = "Список подов для namespace = s-sb-stfkraken")
    public void kubeTest() throws IOException, ApiException {
        V1PodList list = getPodList(namespace, labelSelector);
        AtomicReference<String> attach = new AtomicReference<>();
        list.getItems().forEach(item -> attach.set(item.getMetadata().getName()));
        Allure.addAttachment("Pods kraken stage", String.valueOf(attach));
    }

    @Story("Логи пода")
    @Test(groups = {"api-instamart-regress"},
            description = "Список последних 10 строк лога")
    public void kubeLogs() throws IOException, ApiException {
        V1PodList list = getPodList(namespace, labelSelector);
        List<String> logs = getLogs(list.getItems().get(0), "puma", 10);
        AtomicReference<String> attach = new AtomicReference<>();
        logs.forEach(item -> attach.set(attach + "\r\n" + item));
        Allure.addAttachment("namespace: " + namespace + " logs", String.valueOf(attach));
    }
}
