package ru.instamart.jdbc.entity.eta;

import lombok.Data;

@Data
public class DisableEtaIntervalsEntity {
    private Integer id;
    private String storeUuid;
    private String intervalType;
    private String startAt;
    private String endAt;
    private String createdAt;
    private String deletedAt;
}
