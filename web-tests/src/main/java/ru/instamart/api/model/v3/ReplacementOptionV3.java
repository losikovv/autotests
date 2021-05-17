package ru.instamart.api.model.v3;

import ru.instamart.api.model.BaseObject;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ReplacementOptionV3 extends BaseObject {
    private String id;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("description", description).toString();
    }
}
