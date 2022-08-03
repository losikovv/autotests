package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class SuggestionV2 extends BaseObject {
    private List<String> searches = null;

    private List<TaxonV2> taxons = null;

    @NotEmpty
    private List<ProductV2> products = null;

    @JsonProperty(value = "search_phrases")
    private List<SearchPhraseV2> searchPhrases = null;
}
