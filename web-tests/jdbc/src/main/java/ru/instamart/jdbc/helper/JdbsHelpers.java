package ru.instamart.jdbc.helper;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import static org.testng.Assert.fail;

public class JdbsHelpers {

    public static void filtersMap(Object bean, Consumer<Object> parameters, Consumer<String> whereSql) {
        try {
            Arrays.asList(Introspector.getBeanInfo(bean.getClass(), Object.class)
                            .getPropertyDescriptors())
                    .stream()
                    .filter(pd -> Objects.nonNull(pd.getReadMethod()))
                    .forEach(pd -> {
                        try {
                            Object value = pd.getReadMethod().invoke(bean);
                            if (value != null) {
                                parameters.accept(value);
                                whereSql.accept(pd.getName() + " = ?");
                            }
                        } catch (Exception e) {
                            fail("Errors: " + e.getMessage());
                        }
                    });
        } catch (IntrospectionException e) {
        }
    }
}
