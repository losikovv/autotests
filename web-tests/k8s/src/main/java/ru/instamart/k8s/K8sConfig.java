package ru.instamart.k8s;

import io.kubernetes.client.PodLogs;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.CoreProperties;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.util.Objects.isNull;

@Slf4j
public final class K8sConfig {

    @Getter
    private final ApiClient apiClient;
    @Getter
    private final CoreV1Api coreV1Api;

    private K8sConfig() {
        try {
            this.apiClient = Config.fromConfig(
                    KubeConfig.loadKubeConfig(
                            new StringReader(
                                    new String(Base64.getDecoder().decode(
                                            CoreProperties.BASE64_KUBE_CONFIG
                                    ), StandardCharsets.UTF_8)
                            )
                    )
            );
            this.apiClient.setConnectTimeout(10000);
            this.apiClient.setReadTimeout(10000);
            this.apiClient.setWriteTimeout(10000);
            Configuration.setDefaultApiClient(apiClient);
        } catch (IOException e) {
            log.error("FATAL: Can't init k8s api client");
            throw new RuntimeException(e);
        }
        this.coreV1Api = new CoreV1Api(apiClient);
    }

    public static K8sConfig getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final K8sConfig INSTANCE = new K8sConfig();
    }
}
