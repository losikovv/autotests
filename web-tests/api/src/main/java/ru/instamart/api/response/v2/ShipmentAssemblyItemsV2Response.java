
package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.AssemblyItemV2;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentAssemblyItemsV2Response extends BaseResponseObject {

    @JsonProperty("assembly_items")
    private List<AssemblyItemV2> assemblyItems;

    private MetaV2 meta;
}
