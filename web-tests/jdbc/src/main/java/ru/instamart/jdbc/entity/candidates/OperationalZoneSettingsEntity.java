package ru.instamart.jdbc.entity.candidates;

import lombok.Data;

@Data
public class OperationalZoneSettingsEntity {
    private Long id;
    private Integer OperationalZoneId;
    private Integer SurgedShiftThreshold;
    private Integer NormalShiftThreshold;
}
