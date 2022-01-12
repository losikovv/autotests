package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class PhoneTokensEntity {
    private Long id;
    private Long userId;
    private String value;
    private Integer confirmed;
    private Integer confirmationCode;
    private String createdAt;
    private String updatedAt;
    private String smsRequestedAt;
    private Integer autoMigrated;
}
