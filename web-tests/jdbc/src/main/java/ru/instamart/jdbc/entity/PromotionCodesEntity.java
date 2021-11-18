package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class PromotionCodesEntity {
    private Long id;
    private String value;
    private Integer promotion_id;
    private String activated_at;
    private String created_at;
    private String updated_at;
    private Integer usage_limit;

}
