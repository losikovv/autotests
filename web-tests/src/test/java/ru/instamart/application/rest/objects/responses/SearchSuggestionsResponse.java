package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Suggestion;

public class SearchSuggestionsResponse extends BaseResponseObject {

    private Suggestion suggestion;

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

}
