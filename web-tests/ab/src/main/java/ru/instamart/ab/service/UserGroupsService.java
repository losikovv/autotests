package ru.instamart.ab.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.ab.AbApiClient;
import ru.instamart.ab.Endpoint;
import ru.instamart.ab.model.request.UserGroups;
import ru.instamart.ab.model.response.UserGroupsResponse;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public final class UserGroupsService {

    private final AbApiClient client;

    public void update(final UserGroups userGroup) {
        try {
            client.post(UserGroupsResponse.class, Endpoint.USER_GROUPS, userGroup);
        } catch (IOException e) {
            log.error("FATAL: User can't be update for some reason", e);
        }
    }

    //http://bs-ab-admin.k-stage.sbermarket.tech/api/v1/users-groups?identityId={anonymous_id}&abTestId={test_id}
    public void delete(final UserGroups userGroups) {
        try {
            client.delete(UserGroupsResponse.class, Endpoint.USER_GROUPS, userGroups);
        } catch (IOException e) {
            log.error("FATAL: User can't be deleted from some reason", e);
        }
    }
}
