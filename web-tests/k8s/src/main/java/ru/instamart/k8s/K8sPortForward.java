package ru.instamart.k8s;

import io.kubernetes.client.openapi.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static org.testng.Assert.fail;
import static ru.instamart.k8s.K8sConsumer.getK8sPortForward;
import static ru.instamart.k8s.K8sConsumer.getPodList;

@Slf4j
public enum K8sPortForward {

    INSTANCE;

    public void portForward(final String namespace, final String label, final int internalPort, final int containerPort) {
        log.debug("Forward for - namespace: {}, label: {}, internalPort: {}, containerPort: {}", namespace, label, internalPort, containerPort);
        try {
            final var podList = getPodList(namespace, label);
            final var pod = podList.getItems().stream().findFirst();
            if (pod.isPresent()) {
                getK8sPortForward(pod.get(), internalPort, containerPort);
            } else {
                throw new IOException("Первый под не найден");
            }
        } catch (IOException | ApiException e) {
            log.error("Ошибка проброса порта {}:{} до пода. Error: {}", containerPort, internalPort, e.getMessage());
            fail("Ошибка проброса порта " + containerPort + ":" + internalPort + " до пода. Error: " + e.getMessage());
        }
    }
}
