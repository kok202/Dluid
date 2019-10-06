package org.kok202.deepblock.application.content.material.list;

import javafx.scene.layout.AnchorPane;
import org.kok202.deepblock.application.common.AbstractController;

public abstract class AbstractMaterialList extends AbstractController {
    abstract public AnchorPane createView() throws Exception;
    abstract void addBaseBlockToContentList();
    abstract void addBaseBlockToContentBoxWithEvent() throws Exception ;
}
