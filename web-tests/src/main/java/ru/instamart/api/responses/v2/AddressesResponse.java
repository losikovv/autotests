package instamart.api.responses.v2;

import instamart.api.objects.BaseObject;
import instamart.api.objects.v2.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class AddressesResponse extends BaseObject {
    private Address address;
}
