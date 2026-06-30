package com.orangehrm.wrappers;

import com.orangehrm.utils.commonUtils.ExtentManager;
import org.apache.logging.log4j.LogManager;

public class ReportLogger {

    public static void info(String log)
    {
        LogManager.getLogger().info(log);
        ExtentManager.getTest().info(log);

    }
}
