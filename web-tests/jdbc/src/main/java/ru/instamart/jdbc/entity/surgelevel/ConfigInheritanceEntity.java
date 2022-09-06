package ru.instamart.jdbc.entity.surgelevel;

import lombok.Data;

@Data
public class ConfigInheritanceEntity {
    private String inheritorId;
    private String inheritedId;
    private Integer priority;
}