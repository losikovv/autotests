package ru.instamart.kafka.utils;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Objects;

@Slf4j
public class BrokerUtil {
    public static String createClientId() {
        String clientId;
        clientId = EnvironmentProperties.SERVER;

        if (Objects.isNull(clientId)) {
            try {
                NetworkInterface inter = NetUtil.getNetworkInterface();
                if (inter != null)
                    clientId = NetUtil.getMacAddress(inter);
            } catch (SocketException e) {
                log.error("Failed getting MAC address for clientId, defaulting to 'UNKNOWN'");
            }
        }

        if (Objects.isNull(clientId)) {
            clientId = "UNKNOWN";
        }

        clientId = "kraken_" + clientId.replaceAll("[^A-Za-z0-9]", "_");

        return clientId;
    }
}
