package ru.instamart.jdbc.entity.surgelevel;

import lombok.Data;

@Data
public class FormulaEntity {
    private String id;
    private String name;
    private String script;
    private String createdAt;
    private String updatedAt;
}
