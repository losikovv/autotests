package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeFaqGroupsEntity {
    private Long id;
    private String name;
    private Integer position;
    private String createdAt;
    private String updatedAt;
}
