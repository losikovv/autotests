package ru.instamart.api.k8s;

import com.google.common.io.ByteStreams;
import io.kubernetes.client.Exec;
import io.kubernetes.client.PodLogs;
import io.kubernetes.client.PortForward;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static java.lang.System.console;
import static org.testng.Assert.fail;

public class K8sConsumer {

    private static final Logger log = LoggerFactory.getLogger(K8sConsumer.class);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * получение имени пода
     *
     * @param namespace     example: s-sb-stfkraken
     * @param labelSelector example: app=app-sbermarket
     * @return
     */
    public static String getPodName(String namespace, String labelSelector) {
        try {
            V1PodList list = K8sConfig.getInstance().getCoreV1Api().listNamespacedPod(namespace, null, null, null,
                    null, labelSelector, null, null, null, null, null);
            return list.getItems().get(0).getMetadata().getName();
        } catch (ApiException | IOException e) {
            throw new SkipException("Не получилось вызвать api k8s");
        }
    }

    /**
     * получение первого пода в списке
     *
     * @param namespace     example: s-sb-stfkraken
     * @param labelSelector example: app=app-sbermarket
     * @return
     */
    public static V1Pod getPod(String namespace, String labelSelector) {
        try {
            V1PodList list = K8sConfig.getInstance().getCoreV1Api().listNamespacedPod(namespace, null, null, null,
                    null, labelSelector, null, null, null, null, null);
            return list.getItems().get(0);
        } catch (ApiException | IOException e) {
            throw new SkipException("Не получилось вызвать api k8s");
        }
    }

    public static V1PodList getPodList(String namespace, String labelSelector) {
        try {
            return K8sConfig.getInstance().getCoreV1Api().listNamespacedPod(namespace, null, null, null,
                    null, labelSelector, null, null, null, null, null);
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
            K8sConfig.getInstance().getCoreV1Api();

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

    /**
     * Получение логов в list
     *
     * @param pod
     * @param tailLines
     * @return
     */
    public static List<String> getLogs(V1Pod pod, int tailLines) {
        List<String> logResult = new CopyOnWriteArrayList<>();
        Closeable closeable = getLogs(pod, logResult::add, tailLines);//(name, container, namespace, logResult::add, tailLines);

        try {
            // Ожидание сбора журнала
            // TODO оптимизировать получение журнала
            Thread.sleep(2000);
            int length = 0;
            while (true) {
                if (logResult.size() == length) {
                    // TODO не придумал более удобного варианта
                    closeable.close();
                    return logResult;
                } else {
                    length = logResult.size();
                }
                Thread.sleep(1);
            }
        } catch (InterruptedException | IOException ex) {
            Thread.currentThread().interrupt();
            return logResult;
        }

    }

    /**
     * приватный closable метод для получения логав
     *
     * @param pod           под
     * @param tailFollowFun логгер
     * @param tailLines
     */
    private static Closeable getLogs(V1Pod pod, Consumer<String> tailFollowFun, int tailLines) {
        try {
            K8sConfig.getInstance().getCoreV1Api();
            PodLogs logs = K8sConfig.getInstance().getPodLogs();

            AtomicBoolean closed = new AtomicBoolean(false);
            InputStream is = logs.streamNamespacedPodLog(pod.getMetadata().getNamespace(),
                    pod.getMetadata().getName(),
                    pod.getSpec().getContainers().get(0).getName(),
                    null,
                    tailLines == 0 ? null : tailLines,
                    false
            );
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            executorService.execute(() -> {
                try {
                    String msg;
                    while ((msg = r.readLine()) != null) {
                        tailFollowFun.accept(msg);
                    }
                } catch (IOException e) {
                    if (!closed.get()) {
                        log.error("Output log error", e);
                    }
                } finally {
                    try {
                        closed.set(true);
                        is.close();
                        r.close();
                    } catch (IOException e) {
                        log.error("Close log stream error", e);
                    }
                }
            });
            return () -> {
                closed.set(true);
                is.close();
                r.close();
            };
        } catch (IOException | ApiException e) {
            log.error("Output log error", e);
        }
        return () -> {
        };
    }


    public static PortForward.PortForwardResult getK8sPortForward(String namespace, String name, Integer localPort, Integer targetPort) throws IOException, ApiException {
        AtomicBoolean closed = new AtomicBoolean(false);
        PortForward.PortForwardResult result = new PortForward().forward(namespace, name, new ArrayList<>() {
            {
                add(localPort);
                add(targetPort);
            }
        });
        ServerSocket ss = new ServerSocket(localPort);
        AtomicReference<Socket> s = new AtomicReference<>();
        executorService.execute(() -> {
            try {
                while (!closed.get()) {
                    s.set(ss.accept());
                    ByteStreams.copy(s.get().getInputStream(), result.getOutboundStream(targetPort));
                }
            } catch (IOException e) {
                if (!closed.get()) {
                    log.error("Froward error", e);
                }
            }
        });
        executorService.execute(() -> {
            try {
                while (!closed.get()) {
                    if (s.get() != null) {
                        ByteStreams.copy(result.getInputStream(targetPort), s.get().getOutputStream());
                    }
                }
            } catch (IOException e) {
                if (!closed.get()) {
                    log.error("Froward error", e);
                }
            }
        });
        log.info("Connect address: <Current Host> <" + localPort + ">");
        return result;
    }
}
