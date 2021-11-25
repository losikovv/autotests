package ru.instamart.api.response.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.SuggestionV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchSuggestionsV2Response extends BaseResponseObject {
    @JsonSchema(required = true)
    private SuggestionV2 suggestion;
}
