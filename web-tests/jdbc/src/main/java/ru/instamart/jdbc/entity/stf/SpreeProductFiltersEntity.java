package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeProductFiltersEntity {
    private Long id;
    private String name;
    private Long groupId;
    private Long instamartId;
    private Integer position;
    private String permalink;
    private String keywords;
}
