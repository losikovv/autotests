package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeStatesEntity {

    private Long id;
    private String name;
    private String abbr;
    private Long countryId;
}
