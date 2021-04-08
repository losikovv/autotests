package ru.instamart.api.objects.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblySHP extends BaseObject {
    private AssemblyDataSHP data;
}
