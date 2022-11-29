package ru.instamart.reforged.core.service.ashot;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Component;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.comparison.PointsMarkupPolicy;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * При дальнейшем расширении необходимо часть констант вытащить в конфиги
 * Работу с файловой системой вынести в отдельный класс
 * Работу компараторов вынести в отдельный класс
 */
@Slf4j
public final class AshotService {

    private static final ImageDiffer imageDiffer = new ImageDiffer().withDiffMarkupPolicy(new PointsMarkupPolicy().withDiffColor(Color.RED));
    private static final String expected_img = "expected.png";
    private static final String actual_img = "actual.png";
    private static final String diff_img = "diff.png";
    private static final int allowableDiffSize = 1;
    private static final String type = "png";

    static {
        try {
            Files.createDirectories(Paths.get("src", "test", "resources", "ashot"));
        } catch (IOException e) {
            log.error("Can't create dir");
        }
    }

    public static Screenshot screenWebElement(final WebElement element, final Component... components) {
        try {
            Files.deleteIfExists(Paths.get(getAbsolutePath(), actual_img));
            Files.deleteIfExists(Paths.get(getAbsolutePath(), diff_img));
        } catch (Exception e) {
            log.error("Can't clean up actual and diff screen for test={}", Reporter.getCurrentTestResult().getTestName());
        }
        return screenWebElement(element, expected_img, false, components);
    }

    public static void compareImage(final Screenshot expected, final WebElement element, final Component... components) {
        final var actual = screenWebElement(element, actual_img, true, components);
        if (getDiffSize(expected, actual) > allowableDiffSize) {
            final var diff = saveDiffImage(expected, actual);
            Allure.label("testType", "screenshotDiff");
            attachImg("expected", expected.getImage());
            attachImg("actual", actual.getImage());
            attachImg("diff", diff.getMarkedImage());
            Assert.fail("Скрины отличаются");
        }
    }

    public static ImageDiff saveDiffImage(final Screenshot expected, final Screenshot actual) {
        final var diffImg = imageDiffer.makeDiff(expected, actual);
        final var diffFile = Paths.get(getAbsolutePath(), diff_img);

        writeImg(diffImg.getMarkedImage(), diffFile.toFile());

        return diffImg;
    }

    private static Screenshot screenWebElement(final WebElement element, final String fileName, final boolean override, final Component... components) {
        final var img = Paths.get(getAbsolutePath(), fileName);
        if (Files.exists(img) && !override) {
            return readImg(img.toFile());
        }
        final var ashot = new AShot()
                .coordsProvider(new WebDriverCoordsProvider())
                .ignoredElements(Arrays.stream(components).map(Component::getBy).collect(Collectors.toSet()));
        final var screen = ashot.takeScreenshot(Kraken.getWebDriver(), element);

        writeImg(fillIgnoredArea(screen), img.toFile());

        return screen;
    }

    private static BufferedImage fillIgnoredArea(final Screenshot screenshot) {
        final var img = screenshot.getImage();
        if (screenshot.getIgnoredAreas().isEmpty()) {
            return img;
        }
        final Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.BLACK);
        screenshot.getIgnoredAreas().forEach(c -> g2d.fill(new Rectangle2D.Double(c.getX(), c.getY(), c.getWidth(), c.getHeight())));
        g2d.dispose();

        return img;
    }

    private static int getDiffSize(Screenshot expected, Screenshot actual) {
        final var diff = new ImageDiffer().makeDiff(expected, actual);
        return diff.getDiffSize();
    }

    private static Screenshot readImg(final File file) {
        try {
            return new Screenshot(ImageIO.read(file));
        } catch (IOException e) {
            log.error("Can't read img from file={}", file);
        }
        return null;
    }

    private static void writeImg(final BufferedImage image, final File file) {
        try {
            ImageIO.write(image, type, file);
        }
        catch(Exception e) {
            log.error("Try to write to file={}", file);
        }
    }

    private static String getAbsolutePath() {
        try {
            final var result = Reporter.getCurrentTestResult();
            final var simpleClassName = result.getTestClass().getRealClass().getSimpleName();
            final var simpleMethodName = result.getMethod().getMethodName();
            return Files.createDirectories(Paths.get("src","test","resources", "ashot", simpleClassName, simpleMethodName)).toAbsolutePath().toString();
        } catch (IOException e) {
            log.error("FATAL: Create dir failed");
            throw new RuntimeException(e);
        }
    }

    private static void attachImg(final String name, final BufferedImage img) {
        try (final var os = new ByteArrayOutputStream()) {
            ImageIO.write(img, type, os);
            try(final var is = new ByteArrayInputStream(os.toByteArray())) {
                Allure.addAttachment(name, is);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
