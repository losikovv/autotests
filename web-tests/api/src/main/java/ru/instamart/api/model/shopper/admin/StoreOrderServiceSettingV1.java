package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class StoreOrderServiceSettingV1 {
    @JsonSchema(required = true)
    private int maxOrderAssignRetryCount;

    @JsonSchema(required = true)
    private int periodForTimeToThrowMin;

    @JsonSchema(required = true)
    private String storeUuid;

}
