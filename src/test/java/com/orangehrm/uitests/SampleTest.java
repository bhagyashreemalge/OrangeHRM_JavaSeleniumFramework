package com.orangehrm.uitests;

import com.orangehrm.utils.commonUtils.ScreenshotUtil;
import org.openqa.selenium.bidi.emulation.ScreenArea;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest{

    @Test()
    public void testSample()
    {
       driver.get("https://www.google.com");
        ScreenshotUtil.captureScreenshot(driver,"google");
    }
}
