package ru.instamart.api.helper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.enums.RailsConsole.Shadowcat.CREATE_JWT_TOKEN;
import static ru.instamart.k8s.K8sConsumer.execRailsCommandWithPod;

@Slf4j
public class ShadowcatHelper {

    private static volatile ShadowcatHelper INSTANCE;
    @Getter
    private String jwtToken;

    private ShadowcatHelper() {
        this.jwtToken = createJwtTokenService();
    }

    public static ShadowcatHelper getInstance() {
        ShadowcatHelper RESULT = INSTANCE;
        if (RESULT != null) {
            return INSTANCE;
        }
        synchronized (ShadowcatHelper.class) {
            if (INSTANCE == null) {
                INSTANCE = new ShadowcatHelper();
            }
            return INSTANCE;
        }
    }

    private String createJwtTokenService() {
        log.debug("JWT Token create");
        List<String> strings = execRailsCommandWithPod(CREATE_JWT_TOKEN.get());
        log.debug("Логи рельсовой консоли: \n {}", String.join("\n\t", strings));
        assertTrue(strings.size() > 0, "logs is empty");
        return strings.get(0);
    }
}
