package ru.instamart.api.k8s;

import io.kubernetes.client.Exec;
import io.kubernetes.client.PodLogs;
import io.kubernetes.client.PortForward;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static java.lang.System.console;
import static org.testng.Assert.fail;
import static ru.instamart.api.k8s.K8sConfig.getCoreV1Api;

public class K8sConsumer {

    private static final Logger log = LoggerFactory.getLogger(K8sConsumer.class);

    /**
     * получение имени пода
     *
     * @param namespace     example: s-sb-stfkraken
     * @param labelSelector example: app=app-sbermarket
     * @return
     */
    public static String getPodName(String namespace, String labelSelector) {
        try {
            V1PodList list = getCoreV1Api().listNamespacedPod(namespace, null, null, null,
                    null, labelSelector, null, null, null, null, null);
            return list.getItems().get(0).getMetadata().getName();
        } catch (ApiException | IOException e) {
            throw new SkipException("Не получилось вызвать api k8s");
        }
    }

    /**
     * получение имени пода
     *
     * @param namespace     example: s-sb-stfkraken
     * @param labelSelector example: app=app-sbermarket
     * @return
     */
    public static V1Pod getPod(String namespace, String labelSelector) {
        try {
            V1PodList list = getCoreV1Api().listNamespacedPod(namespace, null, null, null,
                    null, labelSelector, null, null, null, null, null);
            return list.getItems().get(0);
        } catch (ApiException | IOException e) {
            throw new SkipException("Не получилось вызвать api k8s");
        }
    }

    /**
     * Выполнение команды в контейнере
     *
     * @param namespace example: s-sb-stfkraken
     * @param podName   получаем методом getPodName()
     * @param commands
     * @return
     */
    public static Process execCommandWithPod(String namespace, String podName, String[] commands) {
        try {
            getCoreV1Api();

            Exec exec = new Exec();
            boolean tty = console() != null;
            return exec.exec(namespace, podName, commands, true, tty);

        } catch (IOException e) {
            log.error("Error port forwarding: " + e.getMessage());
            fail("Error port forwarding: " + e.getMessage());
        } catch (ApiException ex) {
            log.error("Error k8s api: " + ex.getMessage());
            fail("Error k8s api: " + ex.getMessage());
        }
        return null;
    }

    public static PortForward.PortForwardResult portForward(String namespace, String podName, Integer localPort, Integer targetPort) {
        try {
            return K8sPortForward.getInstance().getK8sPortForward(namespace, podName, localPort, targetPort);
        } catch (IOException e) {
            log.error("Error port forwarding: " + e.getMessage());
            fail("Error port forwarding: " + e.getMessage());
        } catch (ApiException e) {
            log.error("Error k8s api: " + e.getMessage());
            fail("Error k8s api: " + e.getMessage());
        }
        return null;
    }

    public static void getLogs(String namespace, V1Pod pod) {
        OutputStream logPod = null;
        try {
            CoreV1Api coreApi = getCoreV1Api();
            PodLogs logs = new PodLogs();

            InputStream is = logs.streamNamespacedPodLog(pod);
            log.info("Pod logs. Namespace -> {}, pod -> {}");
            Streams.copy(is, logPod);
            log.info("PodLogs: {}", logPod);
        } catch (IOException e) {
            log.error("Error port forwarding: " + e.getMessage());
            fail("Error port forwarding: " + e.getMessage());
        } catch (ApiException e) {
            log.error("Error k8s api: " + e.getMessage());
            fail("Error k8s api: " + e.getMessage());
        }

    }


}
