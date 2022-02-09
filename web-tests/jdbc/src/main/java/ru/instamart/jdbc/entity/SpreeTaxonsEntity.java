package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreeTaxonsEntity {
    private Long id;
    private Long parentId;
    private Integer position;
    private String name;
    private String permalink;
    private Long taxonomyId;
    private Integer lft;
    private Integer rgt;
    private String iconFileName;
    private String iconContentType;
    private Integer iconFileSize;
    private String iconUpdatedAt;
    private String description;
    private String createdAt;
    private String updatedAt;
    private Integer instamartId;
    private Integer published;
    private Integer exportToYandexMarket;
    private Integer depth;
    private Integer publishedOnHomePage;
    private Integer promoted;
    private String keywords;
    private Integer viewAsLeaf;
    private Long shippingCategoryId;
    private Integer hideFromNav;
    private Integer activeForNew;
    private Integer suggested;
    private Integer originalId;
    private Integer duplicateToPromoted;
    private String categoryType;
}
