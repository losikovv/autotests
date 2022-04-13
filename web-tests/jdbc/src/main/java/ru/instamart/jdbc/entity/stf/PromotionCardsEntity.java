package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class PromotionCardsEntity {
    private Long id;
    private String title;
    private String shortDescription;
    private String fullDescription;
    private String backgroundColor;
    private String messageColor;
    private Integer published;
    private String type;
    private Long promotionId;
    private String mobileAppLink;
    private String webAppLink;
    private String createdAt;
    private String updatedAt;
    private Long categoryId;
    private Integer position;
}
