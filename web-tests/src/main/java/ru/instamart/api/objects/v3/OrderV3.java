package ru.instamart.api.objects.v3;

import ru.instamart.api.responses.BaseResponseObject;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class OrderV3 extends BaseResponseObject {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).toString();
    }

}
