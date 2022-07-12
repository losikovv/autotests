package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Data;

@Data
public class ScriptsEntity {
    private Integer id;
    private String name;
    private String code;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String state;
    private String requiredParams;
    private String creatorId;
}
