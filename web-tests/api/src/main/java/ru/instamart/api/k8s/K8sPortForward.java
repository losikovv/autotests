package ru.instamart.api.k8s;

import io.kubernetes.client.PortForward;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1PodList;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.k8s.K8sConsumer.getK8sPortForward;
import static ru.instamart.api.k8s.K8sConsumer.getPodList;

@Slf4j
public class K8sPortForward {

    private static K8sPortForward INSTANCE;
    private K8sPortForward() {
    }
    public static K8sPortForward getInstance() {
        if(INSTANCE==null){
            INSTANCE = new K8sPortForward();
        }
        return INSTANCE;
    }

    @Step("Переброс порта для mysql")
    public PortForward.PortForwardResult portForwardMySQL() {
        final String namespace = EnvironmentProperties.K8S_NAME_SPACE;
        final String labelSelector = EnvironmentProperties.K8S_LABEL_SELECTOR;
        V1PodList list = getPodList(namespace, labelSelector);
        PortForward.PortForwardResult connected = null;
        try {
            connected = getK8sPortForward(namespace, list.getItems().get(0).getMetadata().getName(), 3306, 3306);
        } catch (IOException | ApiException e) {
            log.info("Ошибка проброса порта 3306 до пода pod: {}",  list.getItems().get(0).getMetadata().getName());
            e.printStackTrace();
        }
        assertNotNull(connected, "Not Connected");
        return connected;
    }
}
