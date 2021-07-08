package ru.instamart.ab;

import lombok.Getter;
import ru.instamart.ab.model.Setting;

public final class AbApi {

    @Getter
    private final AbApiClient abApiClient;

    public AbApi(final Setting setting) {
        this.abApiClient = new AbApiClient(setting);
    }
}
