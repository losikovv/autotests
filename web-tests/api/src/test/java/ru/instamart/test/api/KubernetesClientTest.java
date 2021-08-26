package ru.instamart.test.api;

import io.kubernetes.client.PortForward;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1PodList;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.kraken.database.util.ConnectionMySQLManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.k8s.K8sConsumer.*;


@Epic("KUBERNETES")
public class KubernetesClientTest extends RestBase {
    private final String namespace = "s-sb-stfkraken";
    private final String labelSelector = "app=app-sbermarket";

    @Story("Список подов для namespace")
    @Test(groups = {"api-instamart-regress"},
            description = "Список подов для namespace = s-sb-stfkraken")
    public void kubeTest() {
        V1PodList list = getPodList(namespace, labelSelector);
        AtomicReference<String> attach = new AtomicReference<>();
        list.getItems().stream().forEach(item -> attach.set(item.getMetadata().getName()));
        Allure.addAttachment("Pods kraken stage", String.valueOf(attach));
    }

    @Story("Логи пода")
    @Test(groups = {"api-instamart-regress"},
            description = "Список последних 10 строк лога")
    public void kubeLogs() {
        V1PodList list = getPodList(namespace, labelSelector);
        List<String> logs = getLogs(list.getItems().get(0), 10);
        AtomicReference<String> attach = new AtomicReference<>();
        logs.stream().forEach(item -> attach.set(attach + "\r\n" + item));
        Allure.addAttachment("namespace: " + namespace + " logs", String.valueOf(attach));
    }

    @Story("Port Forward")
    @Test(groups = {"api-instamart-regress"},
            description = "Переброс порта с пода на localhost")
    public void kubePortForward() throws IOException, ApiException, SQLException {
        V1PodList list = getPodList(namespace, labelSelector);
        PortForward.PortForwardResult test = getK8sPortForward(namespace, list.getItems().get(0).getMetadata().getName(), 3306, 3306);
        assertNotNull(test, "Not Connected");

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
        result.stream().forEach(System.out::println);

    }
}
