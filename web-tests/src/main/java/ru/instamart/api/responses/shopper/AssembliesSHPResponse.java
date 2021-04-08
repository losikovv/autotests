package ru.instamart.api.responses.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.AssemblyDataSHP;
import ru.instamart.api.objects.shopper.AssemblyIncludedSHP;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssembliesSHPResponse extends BaseResponseObject {
    private List<AssemblyDataSHP> data = null;
    private List<AssemblyIncludedSHP> included = null;
}
