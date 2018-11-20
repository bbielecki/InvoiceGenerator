package io.spinme;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ConfigReader {
//    String configPath = "..\\CompanyInfo.properties";
    String configPath = "CompanyInfo.properties";
    InputStream inputStream;

    public Properties GetConfiguration() throws IOException {
        Properties config = new Properties();

        try{
//            File prop = new File(configPath);
//            String root = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
//            String configPath = root + "CompanyInfo.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(configPath);
            if(inputStream != null){
                config.load(inputStream);
            }else {
                throw new FileNotFoundException("Cannot find configuration properties file");
            }
        }catch (Exception e){
            System.out.println("Exception: " + e);
        }
        finally {
            if(inputStream != null)
                inputStream.close();
        }

        return config;
    }
}
