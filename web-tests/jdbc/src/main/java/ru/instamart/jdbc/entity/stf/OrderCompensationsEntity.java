package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class OrderCompensationsEntity {
    private Long id;
    private String email;
    private String comment;
    private String emailBody;
    private Long reason;
    private Double amount;
    private Long promoType;
    private Integer state;
    private Long customerId;
    private Long creatorId;
    private Long orderId;
    private Long promotionId;
    private Long promotionCodeId;
    private String expireAt;
    private String createdAt;
    private String updatedAt;
}
