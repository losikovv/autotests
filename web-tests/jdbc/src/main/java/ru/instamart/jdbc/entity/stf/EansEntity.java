package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class EansEntity {
    private Long id;
    private String value;
    private String offerRetailerSku;
    private Long retailerId;
    private String createdAt;
    private String updatedAt;
}
