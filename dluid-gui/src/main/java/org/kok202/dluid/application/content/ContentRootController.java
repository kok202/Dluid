package org.kok202.dluid.application.content;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.kok202.dluid.application.common.AbstractController;

@Getter
public class ContentRootController extends AbstractController {
    private TabsController tabsController;


    public AnchorPane createView() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/content_root.fxml"));
        fxmlLoader.setController(this);
        return fxmlLoader.load();
    }

    @Override
    protected void initialize() throws Exception {
        AnchorPane rootPane = initializeRootContent();
        initializeContent(rootPane);
    }

    private AnchorPane initializeRootContent(){
        return (AnchorPane) itself;
    }

    private void initializeContent(AnchorPane rootPane) throws Exception {
        tabsController = new TabsController();
        TabPane tabContent = tabsController.createView();
        AnchorPane anchorPane = (AnchorPane) itself;
        anchorPane.getChildren().add(tabContent);
    }


}
