package ru.instamart.ab;

import lombok.Getter;
import ru.instamart.kraken.util.Crypt;

public final class AbApi {

    public static final String BASE_AB_SERVICE_URL = "https://bs-ab-admin.k-stage.sbermarket.tech/api/v1";
    private static final String USER = Crypt.INSTANCE.decrypt("xsgWkIGVrwj0lHZaP7QICWE5QMclQo2TQ54YaDTOeCk=");
    private static final String PASS = Crypt.INSTANCE.decrypt("q0ZylSwt4ousASKzV9fcm4xG20UvV1IOGxOOplS0Cvw=");

    @Getter
    private final AbApiClient abApiClient;

    public AbApi() {
        this.abApiClient = new AbApiClient();
    }
}
