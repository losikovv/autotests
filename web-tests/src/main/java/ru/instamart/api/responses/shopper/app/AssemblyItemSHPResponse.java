package ru.instamart.api.responses.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.app.AssemblyItemDataSHP;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyItemSHPResponse extends BaseResponseObject {
    private AssemblyItemDataSHP data;
}
