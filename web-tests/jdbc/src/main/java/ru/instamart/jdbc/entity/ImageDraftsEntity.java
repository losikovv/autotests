package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class ImageDraftsEntity {
    private Long id;
    private String imageFileName;
    private String imageContentType;
    private Integer imageFileSize;
    private String imageUpdatedAt;
    private Integer position;
    private Long productId;
    private Integer importId;
    private String createdAt;
    private String updatedAt;
}
