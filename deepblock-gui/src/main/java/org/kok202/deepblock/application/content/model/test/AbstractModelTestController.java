package org.kok202.deepblock.application.content.model.test;

import lombok.Data;
import org.kok202.deepblock.application.common.AbstractController;
import org.kok202.deepblock.application.content.model.TabModelTestController;

@Data
public abstract class AbstractModelTestController extends AbstractController {
    private TabModelTestController tabModelTestController;

    public AbstractModelTestController(TabModelTestController tabModelTestController) {
        this.tabModelTestController = tabModelTestController;
    }
}
