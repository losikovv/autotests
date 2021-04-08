package ru.instamart.api.objects.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchPhraseV2 extends BaseObject {
    private String name;
    private IconV2 icon;
}
