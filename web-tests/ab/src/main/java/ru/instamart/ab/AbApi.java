package ru.instamart.ab;

import lombok.Getter;
import ru.instamart.ab.model.Setting;
import ru.instamart.ab.service.AbTestsService;
import ru.instamart.ab.service.UserGroupsService;

public final class AbApi {

    @Getter
    private final UserGroupsService userGroupsService;
    @Getter
    private final AbTestsService abTestsService;

    public AbApi(final Setting setting) {
        final AbApiClient abApiClient = new AbApiClient(setting);
        this.userGroupsService = new UserGroupsService(abApiClient);
        this.abTestsService = new AbTestsService(abApiClient);
    }
}
