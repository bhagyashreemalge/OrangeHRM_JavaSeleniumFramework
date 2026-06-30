package com.orangehrm.utils.commonUtils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver,
                                           String testName)
    {
        String path = "screenshots/" + testName + ".png";
        File src = ((TakesScreenshot)driver)
                .getScreenshotAs(OutputType.FILE);
        try {
        FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    public static void deleteScreenshotsFolder() {
        File scrrenshotsDir=new File("screenshots");
        if(scrrenshotsDir.exists()){
            try {
                FileUtils.deleteDirectory(scrrenshotsDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        scrrenshotsDir.mkdirs();
    }
}
