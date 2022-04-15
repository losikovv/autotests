package ru.instamart.k8s;

import io.kubernetes.client.PortForward;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.V1PodList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.io.IOException;
import java.util.Objects;

import static org.testng.Assert.fail;
import static ru.instamart.k8s.K8sConsumer.getK8sPortForward;
import static ru.instamart.k8s.K8sConsumer.getPodList;

@Slf4j
public final class K8sPortForward {

    private static volatile K8sPortForward INSTANCE;
    private static PortForward.PortForwardResult k8sConnectMySql;
    private static PortForward.PortForwardResult k8sConnectPgSql;
    private static PortForward.PortForwardResult k8sConnectPgSqlStage;

    private K8sPortForward() {
    }

    public static K8sPortForward getInstance() {
        K8sPortForward RESULT = INSTANCE;
        if (RESULT != null) {
            return INSTANCE;
        }
        synchronized (K8sPortForward.class) {
            if (INSTANCE == null) {
                INSTANCE = new K8sPortForward();
            }
            return INSTANCE;
        }
    }


    public void portForwardMySQL() {
        if (Objects.isNull(k8sConnectMySql)) {
            final String namespace = EnvironmentProperties.K8S_NAME_STF_SPACE;
            final String labelSelector = EnvironmentProperties.K8S_LABEL_STF_SELECTOR;

            try {
                V1PodList podList = getPodList(namespace, labelSelector);
                k8sConnectMySql = getK8sPortForward(namespace, podList.getItems().get(0).getMetadata().getName(),
                        EnvironmentProperties.DB_PORT, EnvironmentProperties.DB_PORT);
            } catch (IOException | ApiException e) {
                fail("Ошибка проброса порта 3306 до пода. Error: " + e.getMessage());
            }
        }
    }

    public void portForwardPgSQL() {
        if (Objects.isNull(k8sConnectPgSql)) {
            final String namespace = EnvironmentProperties.K8S_NAME_SHP_SPACE;
            final String labelSelector = EnvironmentProperties.K8S_LABEL_SHP_SELECTOR;

            try {
                V1PodList podList = getPodList(namespace, labelSelector);
                k8sConnectPgSql = getK8sPortForward(namespace, podList.getItems().get(0).getMetadata().getName(),
                        EnvironmentProperties.DB_PG_PORT, EnvironmentProperties.DB_PG_PORT);
            } catch (IOException | ApiException e) {
                fail("Ошибка проброса порта " + EnvironmentProperties.DB_PG_PORT + " до пода. Error: " + e.getMessage());
            }
        }
    }

    public PortForward.PortForwardResult portForwardPgSQLService() {
        if (Objects.isNull(k8sConnectPgSqlStage)) {
            String label = "statefulset.kubernetes.io/pod-name=postgresql-0";
            int targetPort = 5432;
            int localPort = EnvironmentProperties.SERVICE.contains("workflow") ? 35432 : 5432; //ServiceHelper.INSTANCE.getFreePort();
            try {
                V1PodList podList = getPodList(EnvironmentProperties.SERVICE, label);
                k8sConnectPgSqlStage = getK8sPortForward(EnvironmentProperties.SERVICE, podList.getItems().get(0).getMetadata().getName(),
                        localPort, targetPort);
                return k8sConnectPgSqlStage;
            } catch (IOException | ApiException e) {
                fail("Ошибка проброса порта " + localPort + ":" + targetPort + " до пода. Error: " + e.getMessage());
            }
        }
        return null;
    }

}
