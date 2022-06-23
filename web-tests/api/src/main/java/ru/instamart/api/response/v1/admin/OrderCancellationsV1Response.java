package ru.instamart.api.response.v1.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.AdminOrderCancellationsV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderCancellationsV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private List<AdminOrderCancellationsV1> cancellations;
}