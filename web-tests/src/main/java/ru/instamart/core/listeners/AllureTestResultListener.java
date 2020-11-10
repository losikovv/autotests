package instamart.core.listeners;

import instamart.core.helpers.ConsoleOutputCapturerHelper;
import instamart.core.helpers.HelperBase;
import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class AllureTestResultListener implements ITestListener {
    private static ConsoleOutputCapturerHelper capture = new ConsoleOutputCapturerHelper();
    @Override
    public void onTestStart(ITestResult result) {
        capture.start();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        result();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        screenShot();
        result();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        result();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        result();
        screenShot();
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }
    @BeforeMethod
    private void result(){
        String value = capture.stop();
        Allure.addAttachment("Системный лог теста",value);
    }
    @AfterMethod
    private void screenShot(){
        HelperBase.takeScreenshot();
    }
}
