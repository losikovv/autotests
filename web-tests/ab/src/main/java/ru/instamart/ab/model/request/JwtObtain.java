package ru.instamart.ab.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * domainObtainJwtRequest{
 * code	string
 * callback_url	string
 * provider	string
 * email	string
 * password	string
 * }
 */
@Data
public final class JwtObtain {

    private String code;
    @JsonProperty(value = "callback_url")
    private String callback;
    private String provider;
    private String email;
    private String password;
}
