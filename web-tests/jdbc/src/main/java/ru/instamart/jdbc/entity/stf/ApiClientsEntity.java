package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class ApiClientsEntity {
    private Long id;
    private String clientId;
    private String secret;
    private Integer verifiable;
    private Integer customPrices;
    private String createdAt;
    private String updatedAt;
    private String basicAuth;
}
