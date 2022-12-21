package ru.instamart.kraken.data.chatwoot.user;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.common.Crypt;

import static java.util.Objects.isNull;

@Slf4j
public final class UserManager {

    private static UserData defaultUser;

    public static UserData getOperatorUser() {
        if (isNull(defaultUser)) {
            defaultUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("7xiUwnaxUQYLiK4zTxUk5p53zTRPovGV8Eq+ghTBuKY="))
                    .password(Crypt.INSTANCE.decrypt("5AWeCqqme8bpV4dmDSBiNQ=="))
                    .build();
        }
        return defaultUser;
    }

    public static UserData getSVCCUser() {
        if (isNull(defaultUser)) {
            defaultUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("OYr+/o671sXcGBt3USKQ1OqSRFzZ1Ti9gyXcSrtqnp0="))
                    .password(Crypt.INSTANCE.decrypt("dxsncs6r90niphxhnTTO4w=="))
                    .build();
        }
        return defaultUser;
    }
}
