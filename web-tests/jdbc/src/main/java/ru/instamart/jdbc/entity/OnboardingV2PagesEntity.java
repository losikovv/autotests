package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class OnboardingV2PagesEntity {
    private Long id;
    private String title;
    private String description;
    private String condition;
    private Integer active;
    private Integer position;
    private String appVersion;
    private Integer isBlack;
    private String buttonText;
    private String buttonUrl;
    private String startAt;
    private String endsAt;
    private String androidFrom;
    private String androidTo;
    private String iosFrom;
    private String iosTo;
}
