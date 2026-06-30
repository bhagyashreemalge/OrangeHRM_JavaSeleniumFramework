package listeners;

import com.orangehrm.uitests.BaseTest;
import com.orangehrm.utils.commonUtils.ExtentManager;
import com.orangehrm.utils.commonUtils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.io.File;

public class ExtentListener implements ITestListener {

    ExtentReports extent = ExtentManager.getExtentReports();

    ExtentTest extentTest;
    //ExtentTest test;
    Logger logger= LogManager.getLogger(ExtentListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        //test = extent.createTest(result.getName());
        //test.info("Test Started");
        logger.info("Executing Test : {}", result.getMethod().getMethodName());
        extentTest=extent.createTest(result.getName());
        ExtentManager.setTest(extentTest);
        ExtentManager.getTest().info("Test Started");

    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentManager.getTest().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object currentClass=result.getInstance();
        WebDriver driver=((BaseTest)currentClass).getDriver();
        String path=ScreenshotUtil.captureScreenshot(driver,result.getName());
        ExtentManager.getTest().addScreenCaptureFromPath(new File(path).getAbsolutePath());
        ExtentManager.getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentManager.getTest().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();
    }
}