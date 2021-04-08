package ru.instamart.api.responses.v2;

import ru.instamart.api.objects.BaseObject;
import ru.instamart.api.objects.v2.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class AddressesResponse extends BaseObject {
    private Address address;
}
