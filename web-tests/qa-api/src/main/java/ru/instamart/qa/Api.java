package ru.instamart.qa;

import lombok.Getter;
import ru.instamart.qa.model.Setting;
import ru.instamart.qa.service.SessionService;

public final class Api {

    @Getter
    private final SessionService sessionService;

    public Api(final Setting setting) {
        final ApiClient apiClient = new ApiClient(setting);
        this.sessionService = new SessionService(apiClient);
    }
}
