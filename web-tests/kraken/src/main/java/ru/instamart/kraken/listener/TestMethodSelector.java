package ru.instamart.kraken.listener;

import org.testng.IMethodSelector;
import org.testng.IMethodSelectorContext;
import org.testng.ITestNGMethod;

import java.lang.reflect.Method;
import java.util.List;

public final class TestMethodSelector implements IMethodSelector {

    @Override
    public boolean includeMethod(IMethodSelectorContext context, ITestNGMethod method, boolean isTestMethod) {
        final Method testMethod = method.getConstructorOrMethod().getMethod();

        return !isTestMethod || !testMethod.isAnnotationPresent(Skip.class);
    }

    @Override
    public void setTestMethods(List<ITestNGMethod> testMethods) {

    }
}
