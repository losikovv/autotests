package ru.instamart.api.factory;

import io.restassured.http.Method;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public final class ArtifactFactory {

    @Getter
    private static final CopyOnWriteArrayList<ArtifactRequests> artifactList = new CopyOnWriteArrayList<>();
    @Getter
    private static final CopyOnWriteArrayList<String> orderList = new CopyOnWriteArrayList<>();
    @Getter
    private static final CopyOnWriteArrayList<String> shippingList = new CopyOnWriteArrayList<>();

    public static void addArtifact(final ArtifactRequests data) {
        artifactList.add(data);
    }

    public static void getOrderMatcher(final String str) {
        final String patternOrder = "R\\d{9}";
        Pattern r = Pattern.compile(patternOrder);
        Matcher matcher = r.matcher(str);

        while (matcher.find()) {
            orderList.add(matcher.group());
        }
    }

    public static void getShippingMatcher(final String str) {
        final String patternOrder = "H\\d{11}";
        Pattern r = Pattern.compile(patternOrder);
        Matcher matcher = r.matcher(str);

        while (matcher.find()) {
            shippingList.add(matcher.group());
        }
    }

    public static void filterCollect(final CopyOnWriteArrayList<ArtifactRequests> list, final Method method) {
        var collect = list.stream()
                .filter(item -> method.toString().equalsIgnoreCase(item.getMethod()))
                .collect(Collectors.toList());
        collect
                .forEach(item -> {
                    getOrderMatcher(item.getRoute());
                    getShippingMatcher(item.getRoute());
                    if (Objects.nonNull(item.getBody())) {
                        getOrderMatcher(item.getBody());
                        getShippingMatcher(item.getBody());
                    }
                });
        log.debug("We a send {} request {}", method, collect.size());
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    @ToString
    @Getter
    public static final class ArtifactRequests {
        private final String method;
        private final String route;
        private final String body;
    }
}
