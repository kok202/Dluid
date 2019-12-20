package org.kok202.dluid.application.singleton;

import lombok.Getter;
import org.kok202.dluid.application.Main;
import org.kok202.dluid.application.entity.AppConfiguration;
import org.kok202.dluid.domain.util.ObjectConverter;

import java.util.prefs.Preferences;

@Getter
public class AppConfigurationSingleton {
    private AppConfiguration data;
    private static final String CONFIGURATION_KEY = "configuration";
    private static final AppConfigurationSingleton INSTANCE = new AppConfigurationSingleton();

    private AppConfigurationSingleton(){
        loadData();
    }

    public static AppConfigurationSingleton getInstance(){
        return INSTANCE;
    }

    public void saveData(){
        Preferences preferences = Preferences.userNodeForPackage(Main.class);
        preferences.put(CONFIGURATION_KEY, ObjectConverter.toJson(data));
    }

    public void loadData(){
        Preferences preferences = Preferences.userNodeForPackage(Main.class);
        String dataJson = preferences.get(CONFIGURATION_KEY, "{}");
        data = ObjectConverter.convert(dataJson, AppConfiguration.class);
        if(data == null) {
            data = new AppConfiguration();
            saveData();
        }
    }
}
