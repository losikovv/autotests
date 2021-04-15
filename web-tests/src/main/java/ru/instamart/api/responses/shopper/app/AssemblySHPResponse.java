package ru.instamart.api.responses.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.app.AssemblyDataSHP;
import ru.instamart.api.objects.shopper.app.AssemblyItemDataSHP;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblySHPResponse extends BaseResponseObject {
    private AssemblyDataSHP data;
    private List<AssemblyItemDataSHP> included = null;
}
