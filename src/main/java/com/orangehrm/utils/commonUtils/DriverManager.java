package com.orangehrm.utils.commonUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Thread-safe DriverManager with support for local and remote (Grid) WebDriver
 */
public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final int TIMEOUT_SECONDS = 10;

    // Configuration from environment or system properties
    private static final String GRID_URL = System.getProperty("gridUrl", "http://localhost:4444");
    private static final boolean USE_GRID = Boolean.parseBoolean(System.getProperty("useGrid", "false"));
    private static final String PLATFORM = System.getProperty("platform", "windows");  // windows, mac, linux

    /**
     * Initialize WebDriver for the current thread (Local or Remote)
     */
    public static WebDriver initializeDriver(String browserName) {
        if (browserName == null || browserName.isBlank()) {
            browserName = "chrome";
        }

        WebDriver driverInstance;

        if (USE_GRID) {
            // Use Selenium Grid (remote)
            driverInstance = initializeRemoteDriver(browserName.trim().toLowerCase());
            System.out.println("Remote WebDriver initialized for: " + browserName +
                    " on platform: " + PLATFORM + " | Grid URL: " + GRID_URL);
        } else {
            // Use Local WebDriver
            driverInstance = initializeLocalDriver(browserName.trim().toLowerCase());
            System.out.println("Local WebDriver initialized for: " + browserName);
        }

        // Common setup
        driverInstance.manage().window().maximize();
        driverInstance.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT_SECONDS));
        driverInstance.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TIMEOUT_SECONDS));

        // Store in ThreadLocal
        driver.set(driverInstance);
        return driverInstance;
    }

    /**
     * Initialize local WebDriver
     */
    private static WebDriver initializeLocalDriver(String browserName) {
        switch (browserName) {
            case "firefox":
                return new FirefoxDriver();
            case "edge":
                return new EdgeDriver();
            case "chrome":
            default:
                return new ChromeDriver();
        }
    }

    /**
     * Initialize remote WebDriver (Selenium Grid)
     */
    private static WebDriver initializeRemoteDriver(String browserName) {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (browserName) {
            case "firefox":
                capabilities.setBrowserName("firefox");
                break;
            case "edge":
                capabilities.setBrowserName("edge");
                break;
            case "chrome":
            default:
                capabilities.setBrowserName("chrome");
        }

        // Set platform/OS
        capabilities.setCapability("platformName", PLATFORM);  // windows, mac, linux

        try {
            URL gridUrl = new URL(GRID_URL);
            return new RemoteWebDriver(gridUrl, capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Grid URL: " + GRID_URL, e);
        }
    }

    /**
     * Get WebDriver for the current thread
     */
    public static WebDriver getDriver() {
        WebDriver driverInstance = driver.get();

        if (driverInstance == null) {
            throw new RuntimeException("WebDriver not initialized for thread: " +
                    Thread.currentThread().getName() + ". Call initializeDriver() first.");
        }
        return driverInstance;
    }

    /**
     * Close/quit WebDriver for the current thread
     */
    public static void closeDriver() {
        WebDriver driverInstance = driver.get();
        if (driverInstance != null) {
            try {
                driverInstance.quit();
                System.out.println("Driver closed for thread: " + Thread.currentThread().getName());
            } catch (Exception e) {
                System.err.println("Error closing driver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }

    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }

    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }
}