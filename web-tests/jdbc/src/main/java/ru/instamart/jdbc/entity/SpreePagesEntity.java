package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreePagesEntity {
    private Long id;
    private String title;
    private String body;
    private String slug;
    private String createdAt;
    private String updatedAt;
    private Integer showInHeader;
    private Integer showInFooter;
    private String foreignLink;
    private Integer position;
    private Integer visible;
    private String metaKeywords;
    private String metaDescription;
    private String layout;
    private Integer showInSidebar;
    private String metaTitle;
    private Integer renderLayoutAsPartial;
}
