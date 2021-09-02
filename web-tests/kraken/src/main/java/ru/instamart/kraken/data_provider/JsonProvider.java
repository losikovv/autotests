package ru.instamart.kraken.data_provider;

import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import ru.instamart.kraken.util.FileUtils;
import ru.instamart.utils.Mapper;

import java.lang.reflect.Method;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public final class JsonProvider {

    @SneakyThrows
    @DataProvider(parallel = true, name = "json")
    public static Object[][] parseJson(final Method method) {
        final JsonDataProvider dataProvider = method.getAnnotation(JsonDataProvider.class);
        if (isNull(dataProvider)) {
            throw new RuntimeException("Missing JsonDataProvider annotation");
        }

        final DataList<?> dataList = Mapper.INSTANCE.jsonToObject(FileUtils.getJson(dataProvider.path()), dataProvider.type());
        return nonNull(dataList) ? dataList.getData().stream().map(data -> new Object[]{data}).toArray(Object[][]::new) : new Object[0][];
    }
}
