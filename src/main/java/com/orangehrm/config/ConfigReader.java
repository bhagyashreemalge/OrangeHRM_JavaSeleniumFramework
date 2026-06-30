package com.orangehrm.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public Properties propertyReader(String propertiesFilePath)  {
        Properties properties=new Properties();
        try {
            FileInputStream fis = new FileInputStream(propertiesFilePath);
            properties.load(fis);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        return properties;

    }
}
