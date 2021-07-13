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
            log.error("", e);
        }
    }
}
