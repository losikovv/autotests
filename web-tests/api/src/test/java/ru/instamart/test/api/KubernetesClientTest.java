package ru.instamart.test.api;

import io.kubernetes.client.PortForward;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1PodList;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.jdbc.util.ConnectionMySQLManager;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.k8s.K8sConsumer.*;


@Slf4j
@Epic("KUBERNETES")
public class KubernetesClientTest extends RestBase {
    private final String namespace =  EnvironmentProperties.K8S_NAME_SPACE;
    private final String labelSelector =  EnvironmentProperties.K8S_LABEL_SELECTOR;

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
        V1PodList list = getPodList(namespace, labelSelector);
        List<String> logs = getLogs(list.getItems().get(0), 10);
        AtomicReference<String> attach = new AtomicReference<>();
        logs.forEach(item -> attach.set(attach + "\r\n" + item));
        Allure.addAttachment("namespace: " + namespace + " logs", String.valueOf(attach));
    }

    @Story("Port Forward")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Переброс порта с пода на localhost")
    public void kubePortForward() throws SQLException {

        String tenant = "metro";
        List<String> result = new ArrayList<>();
        String sql = "SELECT * FROM tenants WHERE id=?";
        try (var connect = ConnectionMySQLManager.get();
             var preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setString(1, tenant);

            var resultQuery = preparedStatement.executeQuery();
            while (resultQuery.next()) {
                result.add("id: " + resultQuery.getString("id") +
                        ", name: " + resultQuery.getString("name") +
                        ", hostname: " + resultQuery.getString("hostname"));
            }
        }
        result.forEach(log::debug);
    }
}
