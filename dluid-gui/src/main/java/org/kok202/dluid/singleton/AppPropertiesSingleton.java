package org.kok202.dluid.singleton;

import org.kok202.dluid.Main;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class AppPropertiesSingleton {
    private static final AppPropertiesSingleton INSTANCE = new AppPropertiesSingleton();
    private Properties commonProperties;
    private Properties languageProperties;
    private AppPropertiesSingleton(){
        try {
            commonProperties = new Properties();
            InputStream commonInputStream = Main.class.getClassLoader().getResourceAsStream("application.properties");
            InputStreamReader commonInputStreamReader = new InputStreamReader(commonInputStream, StandardCharsets.UTF_8);
            commonProperties.load(commonInputStreamReader);

            languageProperties = new Properties();
            InputStream languageInputStream = Main.class.getClassLoader().getResourceAsStream(getLanguagePropertiesPath());
            InputStreamReader languageInputStreamReader = new InputStreamReader(languageInputStream, StandardCharsets.UTF_8);
            languageProperties.load(languageInputStreamReader);

        }catch (Exception e){
            commonProperties = null;
            languageProperties = null;
        }
    }

    private String getLanguagePropertiesPath(){
        switch (AppConfigurationSingleton.getInstance().getData().getLanguage()){
            case KOREAN:
                return "application-kor.properties";
            default:
                return "application-en.properties";
        }
    }

    public static AppPropertiesSingleton getInstance(){
        return INSTANCE;
    }

    public String get(String key) {
        String retValue = "";
        if(languageProperties != null)
            retValue = languageProperties.getProperty(key);
        if(retValue == null || retValue.isEmpty())
            retValue = commonProperties.getProperty(key);
        return retValue;
    }

    public int getInt(String key) {
        int retValue = Integer.MAX_VALUE;
        if(languageProperties != null && languageProperties.getProperty(key) != null)
            retValue = Integer.parseInt(languageProperties.getProperty(key));
        if(retValue == Integer.MAX_VALUE && commonProperties.getProperty(key) !=null)
            retValue = Integer.parseInt(commonProperties.getProperty(key));
        return retValue;
    }
}
