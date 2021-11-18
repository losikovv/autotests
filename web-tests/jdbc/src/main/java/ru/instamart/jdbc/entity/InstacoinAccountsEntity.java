package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class InstacoinAccountsEntity {
    private Long id;
    private Long userId;
    private Long promotionCodeId;
    private Integer amount;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
