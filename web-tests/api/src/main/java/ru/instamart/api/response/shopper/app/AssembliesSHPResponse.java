package ru.instamart.api.response.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.AssemblyDataSHP;
import ru.instamart.api.model.shopper.app.AssemblyIncludedSHP;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssembliesSHPResponse extends BaseResponseObject {
    private List<AssemblyDataSHP> data = null;
    private List<AssemblyIncludedSHP> included = null;
}
