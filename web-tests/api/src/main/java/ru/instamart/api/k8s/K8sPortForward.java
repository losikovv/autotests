package ru.instamart.api.k8s;

import io.kubernetes.client.PortForward;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1PodList;
import io.qameta.allure.Step;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.k8s.K8sConsumer.getK8sPortForward;
import static ru.instamart.api.k8s.K8sConsumer.getPodList;

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
    public void portForwardMySQL() {
        final String namespace = EnvironmentProperties.K8S_NAME_SPACE;
        final String labelSelector = EnvironmentProperties.K8S_LABEL_SELECTOR;
        V1PodList list = getPodList(namespace, labelSelector);
        PortForward.PortForwardResult connected = null;
        try {
            connected = getK8sPortForward(namespace, list.getItems().get(0).getMetadata().getName(), 3306, 3306);
        } catch (IOException | ApiException e) {
            e.printStackTrace();
        }
        assertNotNull(connected, "Not Connected");
    }
}
