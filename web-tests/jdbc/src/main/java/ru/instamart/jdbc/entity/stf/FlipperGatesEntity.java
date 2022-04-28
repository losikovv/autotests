package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class FlipperGatesEntity {
    private Long id;
    private String featureKey;
    private String key;
    private String value;
    private String createdAt;
    private String updatedAt;
}
