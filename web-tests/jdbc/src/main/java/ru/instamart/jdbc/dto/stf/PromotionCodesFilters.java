package ru.instamart.jdbc.dto.stf;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public final class PromotionCodesFilters {
    private int limit;
    private int offset;
    private String value;
    private Integer promotionId;
    private Integer id;
    private String activatedAt;
    private String createdAt;
    private String updatedAt;
    private Integer usageLimit;
}
