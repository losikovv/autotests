package ru.instamart.jdbc.entity.surgelevel;

import lombok.Data;

@Data
public class ResultEntity {
    private String id;
    private Integer surgeLevel;
    private String createdAt;
    private String startedAt;
    private String expiredAt;
    private Integer method;
}
