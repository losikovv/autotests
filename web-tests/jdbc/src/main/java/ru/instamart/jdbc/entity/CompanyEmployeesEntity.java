package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class CompanyEmployeesEntity {
    private Integer id;
    private Integer userId;
    private Integer companyId;
    private Integer approved;
    private String createdAt;
    private String updatedAt;
}
