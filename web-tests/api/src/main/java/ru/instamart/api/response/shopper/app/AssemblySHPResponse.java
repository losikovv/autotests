package ru.instamart.api.response.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.AssemblyItemSHP;
import ru.instamart.api.model.shopper.app.AssemblySHP;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblySHPResponse extends BaseResponseObject {
    private AssemblySHP.Data data;
    private List<AssemblyItemSHP.Data> included = null;
}
