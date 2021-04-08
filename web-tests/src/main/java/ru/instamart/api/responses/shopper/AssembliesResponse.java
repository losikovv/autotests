package ru.instamart.api.responses.shopper;

import ru.instamart.api.objects.shopper.AssemblyData;
import ru.instamart.api.objects.shopper.AssemblyIncluded;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssembliesResponse extends BaseResponseObject {
    private List<AssemblyData> data = null;
    private List<AssemblyIncluded> included = null;
}
