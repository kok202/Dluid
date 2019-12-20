package org.kok202.dluid.application.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import lombok.Getter;
import org.kok202.dluid.application.common.AbstractMenuController;
import org.kok202.dluid.application.entity.Language;
import org.kok202.dluid.application.singleton.AppConfigurationSingleton;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;

@Getter
public class MenuSettingController extends AbstractMenuController {
    @FXML
    private Menu menuSettingLanguage;
    @FXML
    private RadioMenuItem menuItemSettingLanguageEnglish;
    @FXML
    private RadioMenuItem menuItemSettingLanguageKorean;

    public Menu createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/menu/menu_setting.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        menuItemSettingLanguageEnglish.selectedProperty().addListener(event -> {
            AppConfigurationSingleton.getInstance().getData().setLanguage(Language.ENGLISH);
            AppConfigurationSingleton.getInstance().saveData();
        });
        menuItemSettingLanguageKorean.selectedProperty().addListener(event -> {
            AppConfigurationSingleton.getInstance().getData().setLanguage(Language.KOREAN);
            AppConfigurationSingleton.getInstance().saveData();
        });

        itself.setText(AppPropertiesSingleton.getInstance().get("frame.menu.setting"));
        menuSettingLanguage.setText(AppPropertiesSingleton.getInstance().get("frame.menu.setting.language"));
        menuItemSettingLanguageEnglish.setText(AppPropertiesSingleton.getInstance().get("frame.menu.setting.language.english"));
        menuItemSettingLanguageKorean.setText(AppPropertiesSingleton.getInstance().get("frame.menu.setting.language.korean"));
    }
}
