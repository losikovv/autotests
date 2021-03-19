package instamart.api.responses.v2;

import com.google.common.base.Objects;
import instamart.api.objects.BaseObject;
import instamart.api.objects.v2.Address;

public final class AddressesResponse extends BaseObject {

    private Address address;

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressesResponse that = (AddressesResponse) o;
        return Objects.equal(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }

    @Override
    public String toString() {
        return "AddressesResponse{" +
                "addresses=" + address +
                '}';
    }
}
