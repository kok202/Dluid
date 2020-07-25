package org.kok202.dluid.content.test;

import lombok.Data;
import org.kok202.dluid.common.AbstractController;
import org.kok202.dluid.content.TabModelTestController;

@Data
public abstract class AbstractModelTestController extends AbstractController {
    private TabModelTestController tabModelTestController;

    public AbstractModelTestController(TabModelTestController tabModelTestController) {
        this.tabModelTestController = tabModelTestController;
    }
}
