package com.sap.micro.service.ui5.scenario;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class SystemConfig {

    private static Properties props ;

    public SystemConfig(){
        try {
            Resource resource = new ClassPathResource("/application.properties");//
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param key
     * @return
     */
    public static String getProperty(String key){

        return props == null ? null :  props.getProperty(key);

    }

    /**
     * @return
     */
    public static String getProperty(String key,String defaultValue){

         return props == null ? null : props.getProperty(key, defaultValue);

    }

    /**
     * @return
     */
    public static Properties getProperties(){
        return props;
    }
}