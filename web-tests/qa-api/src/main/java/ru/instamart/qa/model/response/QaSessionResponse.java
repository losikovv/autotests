package ru.instamart.qa.model.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.util.Date;

@JsonTypeName("qa_session")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
@Data
public final class QaSessionResponse {

    private User user;
    private Session session;

    @Data
    private static final class User{
        public String email;
        public String phone;
    }

    @Data
    private static final class Session{
        public String access_token;
        public Date expires_at;
    }
}
