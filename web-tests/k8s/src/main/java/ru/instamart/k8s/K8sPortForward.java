package ru.instamart.k8s;

import io.kubernetes.client.openapi.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

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
            final var pod = podList.stream().findFirst().orElseThrow(() -> new IOException("Первый под не найден"));
            log.debug("Select pod: {}", Objects.requireNonNull(pod.getSpec()).getNodeName());
            getK8sPortForward(pod, internalPort, containerPort);
        } catch (IOException | ApiException e) {
            final var errorMsg = String.format("Ошибка проброса порта %s:%s до namespace %s. Ошибка: %s", containerPort, internalPort, namespace, e.getMessage());
            log.error(errorMsg);
            fail(errorMsg);
        }
    }
}
