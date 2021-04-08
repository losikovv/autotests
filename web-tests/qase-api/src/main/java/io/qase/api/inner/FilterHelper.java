package io.qase.api.inner;

import io.qase.api.enums.Filters;

import java.util.Map;

public final class FilterHelper {
    private FilterHelper() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static <T extends RouteFilter> String getFilterRouteParam(T filter) {
        if (filter == null) {
            return "";
        }
        Map<Filters, String> filters = filter.getFilters();
        if (filters.isEmpty()) {
            return "";
        }
        StringBuilder filterPath = new StringBuilder("?");
        for (Map.Entry<Filters, String> entry : filters.entrySet()) {
            filterPath.append("filters[").append(entry.getKey().name()).append("]=").append(entry.getValue()).append("&");
        }
        filterPath.deleteCharAt(filterPath.lastIndexOf("&"));
        return filterPath.toString();
    }
}
