package ru.instamart.qa.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@JsonRootName("qa_session")
@Data
public final class QaSessionRequest {

    private User user;

    @Data
    public static final class User{
        private String password;
    }
}
