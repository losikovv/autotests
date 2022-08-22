package ru.instamart.test.api;

import io.kubernetes.client.openapi.models.V1PodList;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static ru.instamart.k8s.K8sConsumer.*;


@Slf4j
@Epic("KUBERNETES")
public final class KubernetesClientTest extends RestBase {

    private final String namespace = EnvironmentProperties.K8S_NAME_STF_SPACE;
    private final String labelSelector = EnvironmentProperties.K8S_LABEL_STF_SELECTOR;

    @Story("Список подов для namespace")
    @Test(groups = {"api-instamart-regress"},
            description = "Список подов для namespace = s-sb-stfkraken")
    public void kubeTest() {
        V1PodList list = getPodList(namespace, labelSelector);
        AtomicReference<String> attach = new AtomicReference<>();
        list.getItems().forEach(item -> attach.set(item.getMetadata().getName()));
        Allure.addAttachment("Pods kraken stage", String.valueOf(attach));
    }

    @Story("Логи пода")
    @Test(groups = {"api-instamart-regress"},
            description = "Список последних 10 строк лога")
    public void kubeLogs() {
        final List<String> logs = getLogs(getPod(namespace, labelSelector), "puma", 10);

        Assert.assertNotNull(logs, "Список логов не вернулся");
        Assert.assertFalse(logs.isEmpty(), "Список логов пуст");
        Assert.assertEquals(logs.size(), 10, "Количество строк логов вернулось иным");

        Allure.addAttachment("namespace: " + namespace + " logs", String.valueOf(logs));
    }
}
