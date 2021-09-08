package ru.instamart.api.k8s;

import io.kubernetes.client.PodLogs;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;
import lombok.Getter;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static ru.instamart.kraken.config.CoreProperties.BASE64_KUBE_CONFIG;

public class K8sConfig {
    private static K8sConfig INSTANCE;
    private static ApiClient apiClient;
    private static CoreV1Api coreV1Api;
    @Getter
    private PodLogs podLogs;

    private K8sConfig() {
    }

    public static K8sConfig getInstance() {
       if(INSTANCE==null){
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
                                            BASE64_KUBE_CONFIG
                                    ), StandardCharsets.UTF_8)
                            )
                    )
            );
            apiClient.setReadTimeout(0);
            Configuration.setDefaultApiClient(apiClient);
            podLogs = new PodLogs(apiClient);
            coreV1Api = new CoreV1Api(apiClient);
        }
        return coreV1Api;
    }

}
