package org.kok202.dluid.application.content.test;

import lombok.Data;
import org.kok202.dluid.application.common.AbstractController;
import org.kok202.dluid.application.content.TabModelTestController;

@Data
public abstract class AbstractModelTestController extends AbstractController {
    private TabModelTestController tabModelTestController;

    public AbstractModelTestController(TabModelTestController tabModelTestController) {
        this.tabModelTestController = tabModelTestController;
    }
}
