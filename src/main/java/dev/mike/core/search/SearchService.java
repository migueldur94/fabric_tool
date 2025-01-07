package dev.mike.core.search;

import dev.mike.infrastructure.inMemory.UrlTemenos;
import dev.mike.infrastructure.search.SearchFacade;

public class SearchService {
    private final SearchFacade searchFacade;

    public SearchService(SearchFacade searchFacade) {
        this.searchFacade = searchFacade;
    }

    public Summer search(String url) {
        UrlTemenos.splitDetails(url);
        return searchFacade.search();
    }
}
