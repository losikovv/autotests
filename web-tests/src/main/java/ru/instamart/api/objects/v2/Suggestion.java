package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Suggestion extends BaseObject {
    private List<String> searches = null;
    private List<Taxon> taxons = null;
    private List<Product> products = null;
    @JsonProperty(value = "search_phrases")
    private List<SearchPhrase> searchPhrases = null;
}
