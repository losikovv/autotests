package ru.instamart.kraken.helper;

import lombok.Data;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import java.util.*;
import java.util.function.Predicate;

public final class KrakenAssert extends Assertion {

    private static final List<ConcurrentAssert> assertError = Collections.synchronizedList(new LinkedList<>());
    private static final String DEFAULT_KRAKEN_ASSERT_MESSAGE = "The following asserts failed: ";
    private static final Predicate<ConcurrentAssert> ASSERT_PREDICATE = assertError -> assertError.getThreadId() == Thread.currentThread().getId();

    @Override
    public void executeAssert(IAssert a) {
        onBeforeAssert(a);
        try {
            a.doAssert();
            onAssertSuccess(a);
        } catch(AssertionError ex) {
            onAssertFailure(a, ex);
            assertError.add(new ConcurrentAssert(Thread.currentThread().getId(), ex, a));
        } finally {
            onAfterAssert(a);
        }
    }

    public void assertAll() {
        if (isNotEmpty()) {
            var joiner = new StringJoiner(", \n", DEFAULT_KRAKEN_ASSERT_MESSAGE, "");
            assertError.forEach(assertError -> {
                if (assertError.getThreadId() == Thread.currentThread().getId()) {
                    joiner.add(getErrorDetails(assertError.getAssertionError()));
                }
            });
            clean();
            throw new AssertionError(joiner.toString());
        }
    }

    private boolean isNotEmpty() {
        return assertError.stream().anyMatch(ASSERT_PREDICATE);
    }

    private void clean() {
        assertError.removeIf(ASSERT_PREDICATE);
    }

    @Data
    private static final class ConcurrentAssert {
        private final long threadId;
        private final AssertionError assertionError;
        private final IAssert<?> iAssert;
    }
}
