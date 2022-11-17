package ru.instamart.reforged.core.wait;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.internal.Require;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.Wait;
import ru.instamart.reforged.core.config.WaitProperties;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public final class KrakenWait<T> implements Wait<T> {

    private static final Duration DEFAULT_WAIT_DURATION = Duration.ofSeconds(WaitProperties.WAITING_TIMEOUT);
    private static final Duration DEFAULT_INTERVAL_DURATION = Duration.ofMillis(WaitProperties.POLLING_INTERVAL);

    private final T input;
    private final java.time.Clock clock;
    private final Sleeper sleeper;

    private final List<Class<? extends Throwable>> ignoredExceptions = new ArrayList<>();
    private Duration timeout = DEFAULT_WAIT_DURATION;
    private Duration interval = DEFAULT_INTERVAL_DURATION;
    private Supplier<String> messageSupplier = () -> null;

    public KrakenWait(final T input) {
        this(input, Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER);
    }

    public KrakenWait(final T input, final Clock clock, final Sleeper sleeper) {
        this.input = Require.nonNull("Input", input);
        this.clock = Require.nonNull("Clock", clock);
        this.sleeper = Require.nonNull("Sleeper", sleeper);
    }

    public KrakenWait<T> withTimeout(final Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    public KrakenWait<T> withMessage(final String message) {
        this.messageSupplier = () -> message;
        return this;
    }

    public KrakenWait<T> withMessage(final Supplier<String> messageSupplier) {
        this.messageSupplier = messageSupplier;
        return this;
    }

    public KrakenWait<T> pollingEvery(final Duration interval) {
        this.interval = interval;
        return this;
    }

    public <K extends Throwable> KrakenWait<T> ignoreAll(final Collection<Class<? extends K>> types) {
        ignoredExceptions.addAll(types);
        return this;
    }

    public KrakenWait<T> ignoring(final Class<? extends Throwable> exceptionType) {
        return this.ignoreAll(ImmutableList.<Class<? extends Throwable>>of(exceptionType));
    }

    @Override
    public <V> V until(Function<? super T, V> isTrue) {
        final var end = clock.instant().plus(timeout);

        Throwable lastException;
        V value = null;
        while (true) {
            try {
                value = isTrue.apply(input);
                if (nonNull(value) && (Boolean.class != value.getClass() || Boolean.TRUE.equals(value))) {
                    return value;
                }
                lastException = null;
            } catch (Throwable e) {
                lastException = propagateIfNotIgnored(e);
            }

            if (end.isBefore(clock.instant())) {
                final var message = nonNull(messageSupplier) ? messageSupplier.get() : null;
                if (nonNull(value) && value instanceof Boolean) {
                    log.debug("Condition timeout: {}", message);
                    return value;
                } else {
                    final var timeoutMessage = String.format(
                            "Expected condition failed: %s (tried for %d second(s) with %d milliseconds interval)",
                            isNull(message) ? "waiting for " + isTrue : message,
                            timeout.getSeconds(), interval.toMillis());
                    throw new TimeoutException(timeoutMessage, lastException);
                }
            }

            try {
                sleeper.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new WebDriverException(e);
            }
        }
    }

    private Throwable propagateIfNotIgnored(final Throwable e) {
        for (final Class<? extends Throwable> ignoredException : ignoredExceptions) {
            if (ignoredException.isInstance(e)) {
                return e;
            }
        }
        Throwables.throwIfUnchecked(e);
        throw new RuntimeException(e);
    }
}
