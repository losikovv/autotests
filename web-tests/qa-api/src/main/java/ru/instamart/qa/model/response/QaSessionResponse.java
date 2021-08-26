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

    private User user;
    private Session session;
    @JsonProperty(value = "anonymous_id")
    private Anonymous anonymous;

    @Data
    public static final class User {
        private String email;
        private String phone;
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
}
