package ru.instamart.qa.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.util.Date;

@JsonTypeName("qa_session")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@Data
public final class QaSessionResponse {

    private String id;
    private User user;
    private Session session;
    @JsonProperty(value = "anonymous_id")
    private Anonymous anonymous;
    @JsonProperty(value = "api_session")
    private ApiSession apiSession;

    @Data
    public static final class User {
        private String email;
        private String phone;
        @JsonProperty(value = "spree_api_key")
        private String spreeApiKey;
    }

    @Data
    public static final class Session {
        @JsonProperty(value = "access_token")
        private String accessToken;
        @JsonProperty(value = "expires_at")
        private Date expiresAt;
    }

    @Data
    public static final class Anonymous {
        private Date expires;
        private String value;
    }

    @Data
    public static final class ApiSession {
        private String value;
        @JsonProperty(value = "expires_at")
        private Date expireAt;
    }
}
