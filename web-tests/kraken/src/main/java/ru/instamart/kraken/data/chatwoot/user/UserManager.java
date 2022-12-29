package ru.instamart.kraken.data.chatwoot.user;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.common.Crypt;

@Slf4j
public final class UserManager {

    public static UserData getOperatorUser() {
        return UserData.builder()
                .email(Crypt.INSTANCE.decrypt("7xiUwnaxUQYLiK4zTxUk5p53zTRPovGV8Eq+ghTBuKY="))
                .password(Crypt.INSTANCE.decrypt("5AWeCqqme8bpV4dmDSBiNQ=="))
                .build();

    }

    public static UserData getSVCCUser() {
        return UserData.builder()
                .email(Crypt.INSTANCE.decrypt("OYr+/o671sXcGBt3USKQ1OqSRFzZ1Ti9gyXcSrtqnp0="))
                .password(Crypt.INSTANCE.decrypt("dxsncs6r90niphxhnTTO4w=="))
                .build();
    }
}
