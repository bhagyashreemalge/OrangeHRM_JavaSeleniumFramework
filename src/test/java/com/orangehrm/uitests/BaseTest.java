package com.orangehrm.uitests;

import com.orangehrm.utils.commonUtils.DriverFactory;
import com.orangehrm.config.ConfigReader;
import com.orangehrm.utils.commonUtils.ScreenshotUtil;
import com.orangehrm.wrappers.ReportLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;
import java.util.Properties;

public class BaseTest{
    WebDriver driver;
    //DriverFactory driverFactory =new DriverFactory();
    String appProperties=System.getProperty("user.dir")+"\\src\\main\\resources\\application.properties";
    ConfigReader configReader=new ConfigReader();
    public static final Logger logger=LogManager.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun=true)
    public void cleanupSetup()
    {
        ScreenshotUtil.deleteScreenshotsFolder();
    }
    @BeforeMethod(alwaysRun = true)
    public void setup(){
        driver= DriverFactory.createDriver();
        logger.info("Initialized driver");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));
        Properties properties=configReader.propertyReader(appProperties);
        driver.get(properties.getProperty("baseurl"));
        logger.info("Browser navigated to : "+properties.getProperty("baseurl"));
        driver.manage().window().maximize();
        logger.info("Browser is in maximized mode");
    }

    @AfterMethod(alwaysRun=true)
    public void tearDown(){
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
