package ru.instamart.api.responses.v2;

import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.Meta;
import ru.instamart.api.objects.v2.Store;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoresResponse extends BaseResponseObject {
    private List<Store> stores = null;
    private Meta meta;
}
