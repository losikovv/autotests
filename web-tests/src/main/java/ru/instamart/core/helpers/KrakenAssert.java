package ru.instamart.core.helpers;

import lombok.Data;
import org.apache.groovy.util.concurrent.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class KrakenAssert extends Assertion {

    private final List<ConcurrentAssert> assertError = Collections.synchronizedList(new LinkedList<>());

    @Override
    public void executeAssert(IAssert a) {
        try {
            a.doAssert();
        } catch(AssertionError ex) {
            onAssertFailure(a, ex);
            assertError.add(new ConcurrentAssert(Thread.currentThread().getId(), ex, a));
        }
    }

    public void assertAll() {
        if (!assertError.isEmpty()) {
            var joiner = new StringJoiner(",");
            assertError.forEach(assertError -> {
                if (assertError.getThreadId() == Thread.currentThread().getId()) {
                    joiner.add(assertError.getAssertionError().getMessage());
                }
            });
            clean();
            throw new AssertionError(joiner.toString());
        }
    }

    public void clean() {
        assertError.removeIf(assertError -> assertError.getThreadId() == Thread.currentThread().getId());
    }

    @Data
    private static final class ConcurrentAssert {
        private final long threadId;
        private final AssertionError assertionError;
        private final IAssert iAssert;
    }
}
