package ru.instamart.reforged.core.cdp;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.devtools.v103.emulation.Emulation;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.cdp.model.DeviceMetrics;
import ru.instamart.reforged.core.cdp.model.GeoLocation;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Slf4j
public final class CdpEmulation {

    public void changeGeoLocation(final GeoLocation loc) {
        log.debug("Set new geo location to:'{}'", loc);
        Kraken.getDevTools().send(Emulation.setGeolocationOverride(
                of(loc.getLatitude()),
                of(loc.getLongitude()),
                of(loc.getAccuracy())
        ));
    }

    public void clearGeoLocation() {
        log.debug("Очистить указанную геолокацию");
        Kraken.getDevTools().send(Emulation.clearGeolocationOverride());
    }

    public void changeDevice(final DeviceMetrics deviceMetrics) {
        log.debug("Set device metrics to:'{}'", deviceMetrics);
        Kraken.getDevTools().send(Emulation.setDeviceMetricsOverride(deviceMetrics.getWidth(),
                deviceMetrics.getHeight(),
                deviceMetrics.getScaleFactor(),
                deviceMetrics.isMobile(),
                deviceMetrics.getScale() == 0 ? empty() : of(deviceMetrics.getScale()),
                deviceMetrics.getScreenWight() == 0 ? empty() : of(deviceMetrics.getScreenWight()),
                deviceMetrics.getScreenHeight() == 0 ? empty() : of(deviceMetrics.getScreenHeight()),
                deviceMetrics.getPositionX() == 0 ? empty() : of(deviceMetrics.getPositionX()),
                deviceMetrics.getPositionY() == 0 ? empty() : of(deviceMetrics.getPositionY()),
                of(deviceMetrics.isDontSetVisibleSize()),
                isNull(deviceMetrics.getScreenOrientation()) ? empty() : of(deviceMetrics.getScreenOrientation()),
                isNull(deviceMetrics.getViewport()) ? empty() : of(deviceMetrics.getViewport()),
                isNull(deviceMetrics.getDisplayFeature()) ? empty() : of(deviceMetrics.getDisplayFeature())
        ));
    }

    public void clearDevice() {
        log.debug("Очистить указанные параметры устройства");
        Kraken.getDevTools().send(Emulation.clearDeviceMetricsOverride());
    }
}
