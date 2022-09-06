package ru.instamart.jdbc.entity.surgelevel;

import lombok.Data;

@Data
public class ConfigEntity {
    private String id;
    private Float stepSurgeLevel;
    private Boolean disabled;
    private String formulaId;
    private String formulaVar;
    private Boolean basic;
}