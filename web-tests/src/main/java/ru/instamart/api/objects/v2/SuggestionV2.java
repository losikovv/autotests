package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class SuggestionV2 extends BaseObject {
    private List<String> searches = null;
    private List<TaxonV2> taxons = null;
    private List<ProductV2> products = null;
    @JsonProperty(value = "search_phrases")
    private List<SearchPhraseV2> searchPhrases = null;
}
