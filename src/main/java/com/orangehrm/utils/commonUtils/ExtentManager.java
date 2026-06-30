package com.orangehrm.utils.commonUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;
    public static ThreadLocal<ExtentTest> test=new ThreadLocal<>();

    public static void setTest(ExtentTest extentTest)
    {
        test.set(extentTest);
    }

    public static ExtentTest getTest()
    {
        return test.get();
    }

    public static ExtentReports getExtentReports() {

        if(extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter("reports/ExtentReport.html");

            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("OrangeHRM Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Tester", "Bhagyashree");
            extent.setSystemInfo("OS", "Windows");
        }

        return extent;
    }
}