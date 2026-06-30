package com.orangehrm.utils.commonUtils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class LocalUtils {

    WebDriver driver;

    public LocalUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void enterText(By locator,String text){
        driver.findElement(locator).sendKeys();
    }

    public String getText(By locator){
        return driver.findElement(locator).getText();
    }

    public void click(By locator){
        driver.findElement(locator).click();
    }

}
