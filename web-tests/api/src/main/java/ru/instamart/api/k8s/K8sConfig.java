package ru.instamart.api.k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;

import java.io.IOException;
import java.util.Optional;

public class K8sConfig {
    private static K8sConfig INSTANCE;
    private static ApiClient apiClient;
    private static CoreV1Api coreV1Api;


    private K8sConfig() {
    }

    public static K8sConfig getInstance() {
        return Optional.ofNullable(INSTANCE).orElse(new K8sConfig());
    }

    public static CoreV1Api getCoreV1Api() throws IOException {
        if (apiClient == null) {
            apiClient = Config.defaultClient();
            Configuration.setDefaultApiClient(apiClient);
            return new CoreV1Api();
        }

        return coreV1Api;
    }

}
