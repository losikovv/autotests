package instamart.api.objects.responses;

import instamart.api.objects.Suggestion;

public class SearchSuggestionsResponse extends BaseResponseObject {

    private Suggestion suggestion;

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

}
