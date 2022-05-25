package ru.instamart.jdbc.entity.candidates;

import lombok.Data;

@Data
public class OperationalZoneSettingsEntity {
    private Long id;
    private Integer operationalZoneId;
    private Integer surgedShiftThreshold;
    private Integer normalShiftThreshold;
}
