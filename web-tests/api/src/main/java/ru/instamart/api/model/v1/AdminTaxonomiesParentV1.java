package ru.instamart.api.model.v1;

import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

public class AdminTaxonomiesParentV1 extends BaseObject {

    @Null
    private Integer id;

    @Null
    private String name;
}
