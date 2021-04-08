package ru.instamart.api.responses.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.AssemblyItemDataSHP;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyItemSHPResponse extends BaseResponseObject {
    private AssemblyItemDataSHP data;
}
