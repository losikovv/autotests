package ru.instamart.api.common;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import ru.instamart.api.factory.ArtifactFactory.ArtifactRequests;

import static ru.instamart.api.factory.ArtifactFactory.addArtifact;

public class CounterFilter implements OrderedFilter {

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Response filter(final FilterableRequestSpecification requestSpec,
                           final FilterableResponseSpecification responseSpec,
                           final FilterContext ctx) {

        final Response response = ctx.next(requestSpec, responseSpec);
        final String method = requestSpec.getMethod();
        final String path = requestSpec.getDerivedPath();
        final String body = requestSpec.getBody();

        if (Status.SUCCESS.matches(response.getStatusCode())) {
            var data = new ArtifactRequests(method, path, body);
            addArtifact(data);
        }
        return response;
    }
}
