package ru.instamart.jdbc.entity.order_service.candidatesScheme;

import lombok.Data;

@Data
public class CandidatesEntity {

    private String uuid;
    private String fullName;
    private String phone;
    private String transport;
    private Boolean active;
    private Integer employmentType;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
