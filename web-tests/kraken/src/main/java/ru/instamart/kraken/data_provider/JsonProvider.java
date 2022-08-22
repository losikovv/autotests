package ru.instamart.kraken.data_provider;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import ru.instamart.kraken.common.Mapper;
import ru.instamart.kraken.util.FileUtils;

import java.lang.reflect.Method;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public final class JsonProvider {

    @DataProvider(parallel = true, name = "json")
    public static Object[][] parseJson(final Method method) {
        var dataProvider = method.getAnnotation(JsonDataProvider.class);
        if (isNull(dataProvider)) {
            throw new RuntimeException("Missing JsonDataProvider annotation");
        }

        var dataList= Mapper.INSTANCE.jsonToObject(FileUtils.getJson(dataProvider.path(), method.getDeclaringClass()), dataProvider.type());
        return nonNull(dataList) ? dataList.getData().stream().map(data -> new Object[]{data}).toArray(Object[][]::new) : new Object[0][];
    }

    @DataProvider(name = "jsonWithoutParallel")
    public static Object[][] parseJsonWithoutParallel(final Method method) {
        return parseJson(method);
    }
}
