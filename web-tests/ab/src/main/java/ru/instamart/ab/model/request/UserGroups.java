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
public final class UserGroups {

    private String identityId;
    private String abTestId;
    private String abGroupId;
}
