package ru.instamart.core.helpers;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import java.util.Map;

public final class KrakenAssert extends Assertion {

    private final Map<AssertionError, IAssert> assertError = Maps.newLinkedHashMap();

    @Override
    public void executeAssert(IAssert a) {
        try {
            a.doAssert();
        } catch(AssertionError ex) {
            onAssertFailure(a, ex);
            assertError.put(ex, a);
        }
    }

    public void assertAll() {
        if (!assertError.isEmpty()) {
            var sb = new StringBuilder("The following asserts failed:\n");
            boolean first = true;
            for (Map.Entry<AssertionError, IAssert> ae : assertError.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(ae.getValue().getMessage());
            }
            clean();
            throw new AssertionError(sb.toString());
        }
    }

    public void clean() {
        assertError.clear();
    }
}
