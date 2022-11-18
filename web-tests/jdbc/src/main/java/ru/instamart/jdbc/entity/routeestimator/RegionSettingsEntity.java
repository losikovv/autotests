package ru.instamart.jdbc.entity.routeestimator;

import lombok.Data;

@Data
public class RegionSettingsEntity {
    private Integer regionId;
    private double bicycleCorrectionFactor;
    private long bicycleIncreasingFactorSec;
    private long bicycleMinimumSegmentLengthSec;
    private double autoCorrectionFactor;
    private long autoIncreasingFactorSec;
    private long autoMinimumSegmentLengthSec;
    private double pedestrianCorrectionFactor;
    private long pedestrianIncreasingFactorSec;
    private long pedestrianMinimumSegmentLengthSec;
    private String createdAt;
    private String updatedAt;
}
