package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeActivatorsEntity {
    private Long id;
    private String description;
    private String expiresAt;
    private String startsAt;
    private String name;
    private String eventName;
    private String type;
    private String matchPolicy;
    private Integer advertise;
    private String createdAt;
    private String updatedAt;
    private Integer promotionCodesCount;
    private Integer promotionCodesUsageCount;
    private String serviceComment;
    private String prefix;
    private String landingUrl;
    private Integer exportToYandexMarket;
    private String deletedAt;
    private Integer externalId;
}
