package ru.instamart.api.k8s;

import com.google.common.io.ByteStreams;
import io.kubernetes.client.Exec;
import io.kubernetes.client.PodLogs;
import io.kubernetes.client.PortForward;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static java.lang.System.console;
import static org.testng.Assert.fail;
import static ru.instamart.api.enums.RailsConsole.Order.*;

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
    private static String getPodName(String namespace, String labelSelector) {
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
    private static V1Pod getPod(String namespace, String labelSelector) {
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
    private static Process execCommandWithPod(String namespace, String podName, String[] commands) {
        try {
            K8sConfig.getInstance().getCoreV1Api();

            Exec exec = new Exec();
            boolean tty = console() != null;
            return exec.exec(namespace, podName, commands, true, tty);
        } catch (IOException e) {
            log.error("Error: " + e.getMessage());
            fail("Error: " + e.getMessage());
        } catch (ApiException ex) {
            log.error("Error k8s api: " + ex.getMessage());
            fail("Error k8s api: " + ex.getMessage());
        }
        return null;
    }

    private static List<String> execRailsCommandWithPod(String commands) {
        List<String> result = new CopyOnWriteArrayList<>();

        String nameSpace = EnvironmentProperties.K8S_NAME_SPACE;
        String labelSelector = EnvironmentProperties.K8S_LABEL_SELECTOR;

        final V1Pod pod = getPod(nameSpace, labelSelector);
        try {
            execRailsCommandWithPod(pod, commands, result::add, true).close();
        } catch (IOException e) {
            log.info("Error: {}", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Выполнение команды в контейнере
     *
     * @param commands
     * @return
     */
    @Step("Выполнение команды: {commands} в pod: {pod.spec.nodeName} ")
    private static Closeable execRailsCommandWithPod(V1Pod pod, String commands, Consumer<String> outputFun, boolean waiting) {
        try {
            AtomicBoolean closed = new AtomicBoolean(false);
            CountDownLatch cdl = new CountDownLatch(1);

            String[] rails = new String[]{"sh", "-c"};
            String[] commandExec = ArrayUtils.addAll(rails, "rails runner \"" + commands + "\"");

            boolean tty = console() != null;
            final Process proc = new Exec().exec(pod, commandExec, true, tty);

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
            fail("Error: " + e.getMessage());
        } catch (ApiException ex) {
            log.error("Error k8s api: " + ex.getMessage());
            fail("Error k8s api: " + ex.getMessage());
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

    @Step("Перевод через консоль заказ {shipmentNumber} в статус \"Доставлено\"")
    public static void changeToShip(String shipmentNumber) {
        List<String> strings = execRailsCommandWithPod(SHIP.get(shipmentNumber));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Перевод через консоль позиции заказа в статус \"Собрано\"")
    public static void changeToAssembled(String shipmentNumber, String itemNumber) {
        List<String> strings = execRailsCommandWithPod(ASSEMBLY_ITEMS_ORDER.get(shipmentNumber, itemNumber));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }

    @Step("Перевод через консоль позиции заказа в статус \"Отменено\"")
    public static void changeToCancel(String shipmentNumber, String itemNumber) {
        List<String> strings = execRailsCommandWithPod(CANCEL_ITEMS_ORDER.get(shipmentNumber, shipmentNumber, itemNumber));
        Allure.addAttachment("Логи рельсовой консоли", String.join("\n", strings));
    }
}
