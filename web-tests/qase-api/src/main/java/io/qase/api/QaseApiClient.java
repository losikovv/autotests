package io.qase.api;

import io.qase.api.exceptions.QaseException;
import io.qase.api.inner.FilterHelper;
import io.qase.api.inner.RouteFilter;
import kong.unirest.*;
import kong.unirest.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

@RequiredArgsConstructor
@Slf4j
public final class QaseApiClient {

    private static final String FAILED_REQUEST_MESSAGE = "The request failed. Error - %s";

    private final UnirestInstance unirestInstance;
    private final String baseUrl;

    public <Response, Filter extends RouteFilter> Response get(
            Class<Response> responseClass,
            String path,
            Map<String, Object> routeParams,
            Filter filter,
            int limit,
            int offset) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        queryParams.put("offset", offset);
        return get(responseClass, path, routeParams, queryParams, filter);
    }

    public <Response, Filter extends RouteFilter> Response get(
            Class<Response> responseClass,
            String path,
            Filter filter,
            int limit,
            int offset) {
        return this.get(responseClass, path, emptyMap(), filter, limit, offset);
    }

    public <Response> Response get(
            Class<Response> responseClass,
            String path,
            int limit,
            int offset) {
        return this.get(responseClass, path, emptyMap(), null, limit, offset);
    }

    public <Response, Filter extends RouteFilter> Response get(
            Class<Response> responseClass,
            String path,
            Map<String, Object> routeParams,
            Filter filter) {
        return this.get(responseClass, path, routeParams, emptyMap(), filter);
    }

    public <Response> Response get(
            Class<Response> responseClass,
            String path,
            Map<String, Object> routeParams) {
        return this.get(responseClass, path, routeParams, emptyMap(), null);
    }

    public <Response> Response get(
            Class<Response> responseClass,
            String path,
            Map<String, Object> routeParams,
            Map<String, Object> queryParams) {
        return this.get(responseClass, path, routeParams, queryParams, null);
    }

    public <Response, Filter extends RouteFilter> Response get(
            Class<Response> responseClass,
            String path,
            Map<String, Object> routeParams,
            Map<String, Object> queryParams,
            Filter filter) {
        String filterPath = FilterHelper.getFilterRouteParam(filter);
        JSONObject jsonObject = asJson(HttpMethod.GET, path + filterPath, routeParams, queryParams);
        if (!jsonObject.getBoolean("status")) {
            throw new QaseException(jsonObject.getString("errorMessage")
                    + (jsonObject.isNull("errorFields") ? "" : System.lineSeparator() + jsonObject.get("errorFields")));
        }
        return getObjectMapper().readValue(jsonObject.get("result").toString(), responseClass);
    }

    public <Response, Request> Response post(Class<Response> responseClass, String path, Map<String, Object> routeParams) {
        return asObject(HttpMethod.POST, responseClass, path, routeParams);
    }

    public <Response, Request> Response post(Class<Response> responseClass, String path, Request request) {
        return this.post(responseClass, path, emptyMap(), request);
    }

    public <Response> Response post(Class<Response> responseClass, String path, Map<String, Object> routeParams, File file) {
        HttpResponse<JsonNode> jsonNodeHttpResponse = unirestInstance.post(baseUrl + path)
                .routeParam(routeParams)
                .field(file.getName(), file)
                .asJson();
        if (jsonNodeHttpResponse.isSuccess()) {
            JsonNode body = jsonNodeHttpResponse
                    .getBody();
            JSONObject jsonObject = getJsonObject(body);
            return getObjectMapper().readValue(jsonObject.get("result").toString(), responseClass);
        }
        throw new QaseException(FAILED_REQUEST_MESSAGE, jsonNodeHttpResponse.getStatus());
    }

    public <Response, Request> Response post(Class<Response> responseClass, String path, Map<String, Object> routeParams, Request request) {
        return asObject(HttpMethod.POST, responseClass, path, routeParams, request);
    }

    public <Response, Request> Response patch(Class<Response> responseClass, String path, Map<String, Object> routeParams, Request request) {
        return asObject(HttpMethod.PATCH, responseClass, path, routeParams, request);
    }

    public JSONObject delete(String path, Map<String, Object> routeParams) {
        return this.asJson(HttpMethod.DELETE, path, routeParams, null);
    }

    public JSONObject patch(String path, Map<String, Object> routeParams) {
        return this.asJson(HttpMethod.PATCH, path, routeParams, null);
    }

    private <Response, Request> Response asObject(HttpMethod method,
                                                  Class<Response> responseClass,
                                                  String path,
                                                  Map<String, Object> routeParams,
                                                  Request request) {

        JSONObject jsonObject = asJson(method, path, routeParams, request);
        return getObjectMapper().readValue(jsonObject.get("result").toString(), responseClass);
    }

    private <Response, Request> Response asObject(HttpMethod method,
                                                  Class<Response> responseClass,
                                                  String path,
                                                  Map<String, Object> routeParams) {

        JSONObject jsonObject = asJson(method, path, routeParams);
        return getObjectMapper().readValue(jsonObject.get("status").toString(), responseClass);
    }

    private <Request> JSONObject asJson(HttpMethod method, String path, Map<String, Object> routeParams, Request request) {
        HttpResponse<JsonNode> jsonNodeHttpResponse = unirestInstance
                .request(method.name(), baseUrl + path)
                .routeParam(routeParams)
                .header("Content-Type", "application/json")
                .body(request)
                .asJson();

        if (jsonNodeHttpResponse.isSuccess()) {
            return getJsonObject(jsonNodeHttpResponse
                    .getBody());
        }

        throw new QaseException(FAILED_REQUEST_MESSAGE, jsonNodeHttpResponse.getStatus());
    }

    private JSONObject asJson(HttpMethod method,
                              String path,
                              Map<String, Object> routeParams,
                              Map<String, Object> queryParams) {
        HttpResponse<JsonNode> jsonNodeHttpResponse = unirestInstance.request(method.name(), baseUrl + path)
                .routeParam(routeParams)
                .header("Content-Type", "application/json")
                .queryString(queryParams)
                .asJson();
        if (jsonNodeHttpResponse.isSuccess()) {
            return getJsonObject(jsonNodeHttpResponse.getBody());
        }
        throw new QaseException(FAILED_REQUEST_MESSAGE, jsonNodeHttpResponse.getStatus());
    }

    private <Request> JSONObject asJson(HttpMethod method, String path, Map<String, Object> routeParams) {
        HttpResponse<JsonNode> jsonNodeHttpResponse = unirestInstance
                .request(method.name(), baseUrl + path)
                .routeParam(routeParams)
                .header("Content-Type", "application/json")
                .asJson();

        if (jsonNodeHttpResponse.isSuccess()) {
            return getJsonObject(jsonNodeHttpResponse
                    .getBody());
        }

        throw new QaseException(FAILED_REQUEST_MESSAGE, jsonNodeHttpResponse.getStatus());
    }

    private JSONObject getJsonObject(JsonNode body) {
        JSONObject jsonObject = Optional.ofNullable(body).orElseThrow(() -> new QaseException("Something went wrong")).getObject();
        if (!jsonObject.getBoolean("status")) {
            throw new QaseException(jsonObject.getString("errorMessage")
                    + (jsonObject.isNull("errorFields") ? "" : System.lineSeparator() + jsonObject.get("errorFields")));
        }
        return jsonObject;
    }

    private ObjectMapper getObjectMapper() {
        return unirestInstance.config().getObjectMapper();
    }
}
