package ru.instamart.jdbc.entity.self_fee;

import lombok.Data;

@Data
public class FlagsEntity {
    private String key;
    private String value;
    private String bool_value;
    private String description;
}
