package ru.instamart.api.responses.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;
import ru.instamart.api.objects.v2.AddressV2;

@Data
@EqualsAndHashCode(callSuper=false)
public final class AddressesV2Response extends BaseObject {
    private AddressV2 address;
}
