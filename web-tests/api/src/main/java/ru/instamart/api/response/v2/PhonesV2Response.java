package ru.instamart.api.response.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.model.v2.PhonesItemV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class PhonesV2Response extends BaseResponseObject {
    private MetaV2 meta;
    private List<PhonesItemV2> phones;
}