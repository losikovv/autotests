package ru.instamart.api.response.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.AssemblyDataSHP;
import ru.instamart.api.model.shopper.app.AssemblyItemDataSHP;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblySHPResponse extends BaseResponseObject {
    private AssemblyDataSHP data;
    private List<AssemblyItemDataSHP> included = null;
}
