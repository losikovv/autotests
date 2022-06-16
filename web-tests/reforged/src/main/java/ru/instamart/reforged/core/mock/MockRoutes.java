package ru.instamart.reforged.core.mock;

import com.google.common.net.MediaType;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import ru.instamart.reforged.core.cdp.model.GeoLocation;

import java.util.HashSet;

import static org.openqa.selenium.remote.http.Contents.asJson;
import static org.openqa.selenium.remote.http.Contents.utf8String;

public final class MockRoutes {

    //Пример мока для всех роутов
    @MockRout(name = "allRoute")
    public static final Route allRoutes = Route.matching(httpRequest -> true).to(() -> req -> new HttpResponse()
            .setStatus(200)
            .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
            .setContent(utf8String("Потрачено!")));

    //Пример мока для конкретного роута
    @MockRout(name = "metroRout")
    public static final Route metroRout = Route.matching(httpRequest -> httpRequest.getUri().contains("/metro"))
            .to(() -> req -> new HttpResponse()
                    .setStatus(200)
                    .addHeader("Content-Type", MediaType.JSON_UTF_8.toString())
                    .setContent(asJson(GeoLocation.builder().withAccuracy(1).withLatitude(2.3f).withLongitude(4.5f).build())));

    //Пример мока для группы роутов
    @MockRout(name = "multipleRout")
    public static final Route multipleRout = Route.combine(new HashSet<>(){{add(metroRout); add(allRoutes);}});
}
