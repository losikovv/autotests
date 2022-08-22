package ru.instamart.k8s;

import com.google.common.io.ByteStreams;
import io.kubernetes.client.Exec;
import io.kubernetes.client.PortForward;
import io.kubernetes.client.PortForward.PortForwardResult;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.common.Mapper;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
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
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public final class K8sConsumer {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * @param namespace example: s-sb-stfkraken
     * @param label     example: app=app-stf-sbermarket
     * @return - получение первого пода в списке
     */
    public static V1Pod getPod(final String namespace, final String label) {
        return getPodList(namespace, label).getItems().stream().findFirst().orElse(null);
    }

    public static V1PodList getPodList(final String namespace, final String label) {
        try {
            return K8sConfig.getInstance().getCoreV1Api().listNamespacedPod(namespace, null, null, null,
                    null, label, null, null, null, null, null);
        } catch (ApiException e) {
            throw new RuntimeException("Не получилось вызвать api k8s: " + e.getMessage());
        }
    }

    public static List<String> execRailsCommandWithPod(final String commands) {
        final List<String> result = new CopyOnWriteArrayList<>();
        final var nameSpace = EnvironmentProperties.K8S_NAME_STF_SPACE;
        final var labelSelector = EnvironmentProperties.K8S_LABEL_STF_SELECTOR;
        final var pod = getPod(nameSpace, labelSelector);

        try {
            execRailsCommandWithPod(pod, commands, result::add, true).close();
        } catch (IOException e) {
            log.error("Error: {}", e.getMessage());
        }
        return result;
    }

    public static <T> T getClassWithExecRailsCommand(final String commands, final Class<T> clazz) {
        final List<String> result = new CopyOnWriteArrayList<>();
        final var namespace = EnvironmentProperties.K8S_NAME_STF_SPACE;
        final var label = EnvironmentProperties.K8S_LABEL_STF_SELECTOR;
        final var pod = getPod(namespace, label);

        T getRetailerResponse = null;
        try {
            execRailsCommandWithPod(pod, commands, result::add, true).close();
            final var join = result.stream()
                    .filter(item -> !item.startsWith("{\"host\":\"app-"))
                    .collect(Collectors.joining(""));

            getRetailerResponse = Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(join), clazz);
            if (isNull(getRetailerResponse)) {
                throw new RuntimeException("FATAL: JSON not valid. Response: " + result);
            }
        } catch (IOException e) {
            log.error("Error: {}", e.getMessage());
        }
        return getRetailerResponse;
    }

    public static List<String> execBashCommandWithPod(final String commands) {
        final List<String> result = new CopyOnWriteArrayList<>();
        final var namespace = EnvironmentProperties.K8S_NAME_STF_SPACE;
        final var label = EnvironmentProperties.K8S_LABEL_STF_SELECTOR;
        final var pod = getPod(namespace, label);

        try {
            execBashCommandWithPod(pod, commands, result::add, true).close();
        } catch (IOException e) {
            log.error("Error: {}", e.getMessage());
        }
        return result;
    }

    /**
     * Получение логов в list
     */
    public static List<String> getLogs(@NonNull final V1Pod pod, final String container, final int tailLines) {
        final List<String> logResult = new CopyOnWriteArrayList<>();
        try {
            var call = K8sConfig.getInstance().getCoreV1Api().readNamespacedPodLogCall(
                    pod.getMetadata().getName(),
                    pod.getMetadata().getNamespace(),
                    container,
                    false,
                    null,
                    null,
                    "false",
                    false,
                    null,
                    tailLines == 0 ? null : tailLines,
                    null,
                    null
            );

            try (final var response = call.execute();
                 final var in = response.body().byteStream();
                 final var br = new BufferedReader(new InputStreamReader(in))) {
                String msg;
                while (nonNull(msg = br.readLine())) {
                    logResult.add(msg);
                }
                return logResult;
            }
        } catch (IOException | ApiException e) {
            log.error("FATAL: Can't obtain pod logs for container '{}'", container);
        }
        return Collections.emptyList();
    }

    public static PortForwardResult getK8sPortForward(final V1Pod pod, final int internalPort, final int containerPort) throws IOException, ApiException {
        final var result = new PortForward().forward(pod, List.of(internalPort, containerPort));
        final var ss = new ServerSocket(internalPort);
        final var s = new AtomicReference<Socket>();
        final var isOpen = new AtomicBoolean(true);

        executorService.execute(() -> {
            try {
                while (isOpen.get()) {
                    s.set(ss.accept());
                    ByteStreams.copy(s.get().getInputStream(), result.getOutboundStream(containerPort));
                }
            } catch (IOException e) {
                if (isOpen.get()) {
                    log.error("Froward error", e);
                }
            }
        });
        executorService.execute(() -> {
            try {
                while (isOpen.get()) {
                    if (nonNull(s.get())) {
                        ByteStreams.copy(result.getInputStream(containerPort), s.get().getOutputStream());
                    }
                }
            } catch (IOException e) {
                if (isOpen.get()) {
                    log.error("Forward error", e);
                }
            }
        });
        log.debug("Connect address: <Current Host> <{}>", internalPort);

        return result;
    }

    private static Closeable execRailsCommandWithPod(final V1Pod pod, final String commands, final Consumer<String> outputFun, final boolean waiting) {
        final var commandExec = new String[]{"/bin/bash", "-c", "/vault/vault-env", "bundle", "exec", "rails", "runner", "\"puts " + commands + "\""};
        log.debug("Exec command: {}", String.join("\n", commandExec));
        return execCommandWithPod(pod, commandExec, outputFun, waiting);
    }

    private static Closeable execBashCommandWithPod(final V1Pod pod, final String commands, final Consumer<String> outputFun, final boolean waiting) {
        final var commandExec = new String[]{"/bin/bash", "-c", commands};
        return execCommandWithPod(pod, commandExec, outputFun, waiting);
    }

    /**
     * Выполнение команды в контейнере
     */
    private static Closeable execCommandWithPod(final V1Pod pod, final String[] commands, final Consumer<String> outputFun, final boolean waiting) {
        try {
            final var closed = new AtomicBoolean(false);
            final var cdl = new CountDownLatch(1);

            final boolean tty = nonNull(console());
            final var apiClient = K8sConfig.getInstance().getApiClient();
            final var proc = new Exec(apiClient).exec(pod, commands, "puma", true, tty);

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
                try (final var outputReader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                    outputReader.lines().forEach(outputFun);
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
     * @param namespace example: s-sb-stfkraken
     * @param podName   получаем методом getPodName()
     * @param commands  - команда к поду
     * @return - Выполнение команды в контейнере
     */
    private static Process execCommandWithPod(final String namespace, final String podName, final String[] commands) {
        try {
            final var exec = new Exec();
            boolean tty = nonNull(console());
            return exec.exec(namespace, podName, commands, true, tty);
        } catch (IOException e) {
            log.error("Error: " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (ApiException ex) {
            log.error("Error k8s api: " + ex.getMessage());
            throw new RuntimeException("Error k8s api: " + ex.getMessage());
        }
    }

    /**
     * @param namespace example: s-sb-stfkraken
     * @param label     example: app=app-stf-sbermarket
     * @return - получение имени пода
     */
    private static String getPodName(final String namespace, final String label) {
        return Objects.requireNonNull(getPod(namespace, label).getMetadata()).getName();
    }
}
