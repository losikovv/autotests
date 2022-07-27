package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class ApiClientHooksEntity {
    private Long id;
    private Long apiClientId;
    private Long kind;
    private String url;
    private String createdAt;
    private String updatedAt;
}
