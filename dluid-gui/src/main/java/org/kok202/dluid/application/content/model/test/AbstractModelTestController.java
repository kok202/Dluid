package org.kok202.dluid.application.content.model.test;

import lombok.Data;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.application.content.model.TabModelTestController;

@Data
public abstract class AbstractModelTestController extends AbstractController {
    private TabModelTestController tabModelTestController;

    public AbstractModelTestController(TabModelTestController tabModelTestController) {
        this.tabModelTestController = tabModelTestController;
    }
}
