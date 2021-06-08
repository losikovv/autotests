package ru.instamart.api.response.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.AddressV2;

@Data
@EqualsAndHashCode(callSuper=false)
public final class AddressV2Response extends BaseObject {
    private AddressV2 address;
}
