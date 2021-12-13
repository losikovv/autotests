package ru.instamart.k8s;

import com.google.common.io.ByteStreams;
import io.kubernetes.client.Exec;
import io.kubernetes.client.PodLogs;
import io.kubernetes.client.PortForward;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.utils.Mapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.lang.System.console;

public class K8sConsumer {

    private static final Logger log = LoggerFactory.getLogger(K8sConsumer.class);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static ApiClient apiClient;

    /**
     * получение имени пода
     *
     * @param namespace     example: s-sb-stfkraken
     * @param labelSelector example: app=app-stf-sbermarket
     * @return
     */
    private static String getPodName(String namespace, String labelSelector) {
        try {
            V1PodList list = K8sConfig.getInstance().getCoreV1Api().listNamespacedPod(namespace, null, null, null,
                    null, labelSelector, null, null, null, null, null);
            return list.getItems().get(0).getMetadata().getName();
        } catch (ApiException | IOException e) {
            throw new RuntimeException("Не получилось вызвать api k8s");
        }
    }

    /**
     * получение первого пода в списке
     *
     * @param namespace     example: s-sb-stfkraken
     * @param labelSelector example: app=app-stf-sbermarket
     * @return
     */
    private static V1Pod getPod(String namespace, String labelSelector) {
        try {
            V1PodList list = K8sConfig.getInstance().getCoreV1Api().listNamespacedPod(namespace, null, null, null,
                    null, labelSelector, null, null, null, null, null);
            return list.getItems().get(0);
        } catch (ApiException | IOException e) {
            throw new RuntimeException("Не получилось вызвать api k8s");
        }
    }

    public static V1PodList getPodList(String namespace, String labelSelector) throws IOException, ApiException {
        return K8sConfig.getInstance().getCoreV1Api().listNamespacedPod(namespace, null, null, null,
                null, labelSelector, null, null, null, null, null);
    }

    /**
     * Выполнение команды в контейнере
     *
     * @param namespace example: s-sb-stfkraken
     * @param podName   получаем методом getPodName()
     * @param commands
     * @return
     */
    private static Process execCommandWithPod(String namespace, String podName, String[] commands) {
        try {
            K8sConfig.getInstance().getCoreV1Api();

            Exec exec = new Exec();
            boolean tty = console() != null;
            return exec.exec(namespace, podName, commands, true, tty);
        } catch (IOException e) {
            log.error("Error: " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (ApiException ex) {
            log.error("Error k8s api: " + ex.getMessage());
            throw new RuntimeException("Error k8s api: " + ex.getMessage());
        }
    }

    public static List<String> execRailsCommandWithPod(String commands) {
        List<String> result = new CopyOnWriteArrayList<>();

        String nameSpace = EnvironmentProperties.K8S_NAME_STF_SPACE;
        String labelSelector = EnvironmentProperties.K8S_LABEL_STF_SELECTOR;

        final V1Pod pod = getPod(nameSpace, labelSelector);
        try {
            execRailsCommandWithPod(pod, commands, result::add, true).close();
        } catch (IOException e) {
            log.error("Error: {}", e.getMessage());
        }
        return result;
    }

    public static <T> T getClassWithExecRailsCommand(String commands, Class<T> clazz) {
        List<String> result = new CopyOnWriteArrayList<>();
        T getRetailerResponse = null;
        String nameSpace = EnvironmentProperties.K8S_NAME_STF_SPACE;
        String labelSelector = EnvironmentProperties.K8S_LABEL_STF_SELECTOR;

        final V1Pod pod = getPod(nameSpace, labelSelector);
        try {
            execRailsCommandWithPod(pod, commands, result::add, true).close();
            String join = result.stream()
                    .filter(item -> !item.startsWith("{\"host\":\"app-"))
                    .collect(Collectors.joining(""));

            getRetailerResponse = Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(join), clazz);
            if (Objects.isNull(getRetailerResponse)) {
                throw new RuntimeException("FATAL: JSON not valid. Response: " + result);
            }
        } catch (IOException e) {
            log.error("Error: {}", e.getMessage());
        }
        return getRetailerResponse;
    }

    public static List<String> execBashCommandWithPod(String commands) {
        List<String> result = new CopyOnWriteArrayList<>();

        String nameSpace = EnvironmentProperties.K8S_NAME_STF_SPACE;
        String labelSelector = EnvironmentProperties.K8S_LABEL_STF_SELECTOR;

        final V1Pod pod = getPod(nameSpace, labelSelector);
        try {
            execBashCommandWithPod(pod, commands, result::add, true).close();
        } catch (IOException e) {
            log.error("Error: {}", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    private static Closeable execRailsCommandWithPod(V1Pod pod, String commands, Consumer<String> outputFun, boolean waiting) {
        String[] commandExec = new String[]{"/bin/bash", "-c", "/vault/vault-env bundle exec rails runner \"puts " + commands + "\""};
        log.debug("Exec command: {}", String.join("\n", commandExec));
        return execCommandWithPod(pod, commandExec, outputFun, waiting);
    }

    private static Closeable execBashCommandWithPod(V1Pod pod, String commands, Consumer<String> outputFun, boolean waiting) {
        String[] commandExec = new String[]{"/bin/bash", "-c", commands};
        return execCommandWithPod(pod, commandExec, outputFun, waiting);
    }

    /**
     * Выполнение команды в контейнере
     *
     * @param commands
     * @return
     */
    private static Closeable execCommandWithPod(V1Pod pod, String[] commands, Consumer<String> outputFun, boolean waiting) {
        try {
            AtomicBoolean closed = new AtomicBoolean(false);
            CountDownLatch cdl = new CountDownLatch(1);

            boolean tty = console() != null;
            ApiClient apiClient = K8sConfig.getInstance().getApiClient();
            final Process proc = new Exec(apiClient).exec(pod, commands, "puma", true, tty);

            executorService.execute(() -> {
                try {
                    ByteStreams.copy(System.in, proc.getOutputStream());
                } catch (IOException e) {
                    if (!closed.get()) {
                        log.error("Exec error", e);
                    }
                }
            });

            executorService.execute(() -> {
                try {
                    BufferedReader outputReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                    String outputLine;
                    while ((outputLine = outputReader.readLine()) != null) {
                        outputFun.accept(outputLine);
                    }
                } catch (IOException e) {
                    if (!closed.get()) {
                        log.error("Exec error", e);
                    }
                } finally {
                    cdl.countDown();
                }
            });
            if (waiting) {
                cdl.await();
            }
            return () -> {
                closed.set(true);
                proc.destroy();
            };
        } catch (IOException e) {
            log.error("Error: " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (ApiException ex) {
            log.error("Error k8s api: " + ex.getMessage());
            throw new RuntimeException("Error k8s api: " + ex.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Exec error", e);
        }
        return () -> {
        };
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
        log.debug("Connect address: <Current Host> <" + localPort + ">");
        return result;
    }


}
