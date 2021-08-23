package ru.instamart.api.k8s;

import io.kubernetes.client.PortForward;
import io.kubernetes.client.openapi.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import static ru.instamart.api.k8s.K8sConfig.getCoreV1Api;

@Slf4j
public class K8sPortForward {

    private static K8sPortForward INSTANCE;
    private static PortForward portForward;
    private static PortForward.PortForwardResult result;

    private K8sPortForward() {
    }

    public static K8sPortForward getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new K8sPortForward();
        }
        return INSTANCE;
    }

    public PortForward getPortForward() {
        if (portForward == null) {
            portForward = new PortForward();
        }
        return portForward;
    }


    public final PortForward.PortForwardResult getK8sPortForward(String namespace, String podName, Integer localPort, Integer targetPort)
            throws IOException, ApiException {

        getCoreV1Api();

        PortForward forward = new PortForward();
        List<Integer> ports = new ArrayList<>();

        ports.add(targetPort);
        final PortForward.PortForwardResult result =
                forward.forward(namespace, podName, ports);
        log.info("Port {}:{} forwarding.", localPort, targetPort);
        ServerSocket ss = new ServerSocket(localPort);
        System.out.println("Connected!");
        ss.close();
        return result;
    }

    public void closePort(PortForward.PortForwardResult result) {

    }
}

