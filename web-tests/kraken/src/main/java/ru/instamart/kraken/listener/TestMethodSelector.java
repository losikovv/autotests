package ru.instamart.kraken.listener;

import org.testng.IMethodSelector;
import org.testng.IMethodSelectorContext;
import org.testng.ITestNGMethod;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.enums.Tenant;

import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Селектор методов для запуска или пропуска тестов. Соответственно запускает тесты с аннотацией {@link Run}
 * или пропускает тесты с аннотацией {@link Skip}
 * Для применения данного селектора нужно разместить сниппет
 * <method-selectors>
 *   <method-selector>
 *     <selector-class name="ru.instamart.kraken.listener.TestMethodSelector"/>
 *   </method-selector>
 * </method-selectors>
 * в нужных suite, данный блок можно размещать как для всего suite так и для конкретного набора тестов
 * путем указания внутри блока <suite></suite> или внутри блока <test></test>
 */
public final class TestMethodSelector implements IMethodSelector {

    @Override
    public boolean includeMethod(IMethodSelectorContext context, ITestNGMethod method, boolean isTestMethod) {
        final Method testMethod = method.getConstructorOrMethod().getMethod();

        if (isTestMethod && testMethod.isAnnotationPresent(Skip.class)) {
            final Skip skip = testMethod.getAnnotation(Skip.class);
            final Set<Server> onServer = Set.of(skip.onServer());
            final Set<Tenant> onTenant = Set.of(skip.onTenant());

            if (onServer.size() == 0 && onTenant.size() == 0) {
                context.setStopped(true);
                return false;
            }
            if (onServer.contains(Server.valueOf(EnvironmentProperties.SERVER.toUpperCase()))) {
                context.setStopped(true);
                return false;
            }
            if (onTenant.contains(Tenant.valueOf(EnvironmentProperties.TENANT.toUpperCase()))) {
                context.setStopped(true);
                return false;
            }
        }
        if (isTestMethod && testMethod.isAnnotationPresent(Run.class)) {
            final Run run = testMethod.getAnnotation(Run.class);
            final Set<Server> onServer = Set.of(run.onServer());
            final Set<Tenant> onTenant = Set.of(run.onTenant());

            if (onServer.size() > 0 && !onServer.contains(Server.valueOf(EnvironmentProperties.SERVER.toUpperCase()))) {
                context.setStopped(true);
                return false;
            }
            if (onTenant.size() > 0 && !onTenant.contains(Tenant.valueOf(EnvironmentProperties.TENANT.toUpperCase()))) {
                context.setStopped(true);
                return false;
            }
        }
        return true;
    }

    @Override
    public void setTestMethods(List<ITestNGMethod> testMethods) {

    }
}
