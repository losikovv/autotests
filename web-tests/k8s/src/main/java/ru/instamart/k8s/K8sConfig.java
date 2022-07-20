package ru.instamart.k8s;

import io.kubernetes.client.PodLogs;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;
import lombok.Getter;
import ru.instamart.kraken.config.CoreProperties;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class K8sConfig {
    private static K8sConfig INSTANCE;
    private static CoreV1Api coreV1Api;
    @Getter
    private ApiClient apiClient;
    @Getter
    private PodLogs podLogs;

    private K8sConfig() {
    }

    public static K8sConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new K8sConfig();
        }
        return INSTANCE;
    }

    public CoreV1Api getCoreV1Api() throws IOException {
        if (apiClient == null) {
            apiClient = Config.fromConfig(
                    KubeConfig.loadKubeConfig(
                            new StringReader(
                                    new String(Base64.getDecoder().decode(
                                            CoreProperties.BASE64_KUBE_CONFIG
                                    ), StandardCharsets.UTF_8)
                            )
                    )
            );
            apiClient.setReadTimeout(10000);
            apiClient.setWriteTimeout(10000);
            Configuration.setDefaultApiClient(apiClient);
            podLogs = new PodLogs(apiClient);
            coreV1Api = new CoreV1Api(apiClient);
        }
        return coreV1Api;
    }

}
