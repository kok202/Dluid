package org.kok202.dluid.application.content.popup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kok202.dluid.application.adapter.MenuAdapter;
import org.kok202.dluid.application.adapter.file.ExtendedImageSaver;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.application.util.TextFieldUtil;
import org.kok202.dluid.domain.stream.image.ImageColorScale;

public class ImageManagementPopUpController extends AbstractController {
    @FXML private Label labelName;
    @FXML private Label labelWidth;
    @FXML private Label labelHeight;
    @FXML private Label labelColorType;

    @FXML private TextField textFieldName;
    @FXML private TextField textFieldWidth;
    @FXML private TextField textFieldHeight;
    @FXML private MenuButton menuButtonColorType;
    @FXML private Button buttonSaveData;
    @FXML private Button buttonCancel;
    @Getter private ExtendedImageSaver testResultImageSaver;

    public ImageManagementPopUpController(ExtendedImageSaver testResultImageSaver) {
        this.testResultImageSaver = testResultImageSaver;
    }

    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/popup/file_save_setting_image.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        TextFieldUtil.applyPositiveIntegerFilter(textFieldWidth, 1);
        TextFieldUtil.applyPositiveIntegerFilter(textFieldHeight, 1);
        initializeMenuButtonWeightInit();
        //기본 값 세팅
        buttonSaveData.setOnAction(event -> buttonSaveActionHandler());
        buttonCancel.setOnAction(event -> buttonCancelHandler());

        labelName.setText(AppPropertiesSingleton.getInstance().get("frame.file.save.image.name"));
        labelWidth.setText(AppPropertiesSingleton.getInstance().get("frame.file.save.image.width"));
        labelHeight.setText(AppPropertiesSingleton.getInstance().get("frame.file.save.image.height"));
        labelColorType.setText(AppPropertiesSingleton.getInstance().get("frame.file.save.image.type"));
        buttonSaveData.setText(AppPropertiesSingleton.getInstance().get("frame.file.save.save"));
        buttonCancel.setText(AppPropertiesSingleton.getInstance().get("frame.file.save.cancel"));
    }

    private void initializeMenuButtonWeightInit(){
        MenuAdapter<ImageColorScale> menuAdapter = new MenuAdapter<>(menuButtonColorType);
        menuAdapter.setMenuItemChangedListener(imageColorScale -> {
            testResultImageSaver.setImageColorScale(imageColorScale);
        });
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.file.save.image.type.gray"), ImageColorScale.GRAY);
        menuAdapter.addMenuItem(AppPropertiesSingleton.getInstance().get("frame.file.save.image.type.rgb"), ImageColorScale.RGB);
        menuAdapter.setDefaultMenuItem(ImageColorScale.GRAY);
    }

    private void buttonSaveActionHandler(){
        testResultImageSaver.setImageName(textFieldName.getText());
        testResultImageSaver.setImageWidth(Integer.parseInt(textFieldWidth.getText()));
        testResultImageSaver.setImageHeight(Integer.parseInt(textFieldHeight.getText()));
        testResultImageSaver.closePopUpExtensionWithLoad();
    }

    private void buttonCancelHandler() {
        testResultImageSaver.closePopUpExtension();
    }
}
