package com.orangehrm.utils.commonUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class DriverFactory {
    private static final String remote=System.getProperty("remote","false");
    static BrowserType browserType= BrowserType.valueOf(System.getProperty("browser","chrome").toUpperCase());


    public static WebDriver createDriver()
    {
        WebDriver driver;
        if(remote.equalsIgnoreCase("true")){
            driver=createRemoteDriver(browserType);
        }
        else{
            driver=createLocalDriver(browserType);
        }
        return driver;
    }

    public static WebDriver createDriver(BrowserType browserType)
    {
        WebDriver driver;
        if(remote.equalsIgnoreCase("true")){
            driver=createRemoteDriver(browserType);
        }
        else{
            driver=createLocalDriver(browserType);
        }
        return driver;
    }

    private static WebDriver createRemoteDriver(BrowserType browserType) {
        WebDriver driver;
        DesiredCapabilities capabilities=new DesiredCapabilities();
        capabilities.setBrowserName(browserType.getBrowserName());
        capabilities.setAcceptInsecureCerts(true);
        try {
            driver = new RemoteWebDriver(new URI("http://localhost:4444/wd/hub").toURL(), capabilities);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return driver;

    }

    public static WebDriver createLocalDriver(BrowserType browserType)
    {
        WebDriver driver;
        switch(browserType)
        {
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case EDGE:
                driver=new EdgeDriver();
                break;
            case CHROME:
                driver=new ChromeDriver();
                break;
            default:
                throw new RuntimeException("Invalid Browser");

        }
        return driver;
    }

}