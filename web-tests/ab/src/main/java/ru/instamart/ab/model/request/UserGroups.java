package ru.instamart.ab.model.request;

import lombok.Data;

/**
 * {
 *   "identityId": "string",
 *   "abTestId": "string",
 *   "abGroupId": "string"
 * }
 */
@Data
public final class UserGroups implements IRequest {

    private String identityId;
    private String abTestId;
    private String abGroupId;

    public static void main(String[] args) {
        UserGroups userGroups = new UserGroups();
        userGroups.setAbGroupId("asfasfaf");

        System.out.println(userGroups.getQuery());
    }
}
