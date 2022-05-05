package ru.instamart.api.response.v1.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.AdminStoreLabelsItemV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StoreLabelsAdminV1Response extends BaseResponseObject {

    @JsonProperty("store_labels")
    private List<AdminStoreLabelsItemV1> storeLabels;
}