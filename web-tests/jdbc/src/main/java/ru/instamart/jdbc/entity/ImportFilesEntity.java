package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class ImportFilesEntity {
    private Long id;
    private Long userId;
    private String status;
    private String type;
    private String payloadFileName;
    private String payloadContentType;
    private Integer payloadFileSize;
    private String payloadUpdatedAt;
    private String createdAt;
    private String updatedAt;
    private String logfileFileName;
    private String logfileContentType;
    private Integer logfileFileSize;
    private String logfileUpdatedAt;
}
