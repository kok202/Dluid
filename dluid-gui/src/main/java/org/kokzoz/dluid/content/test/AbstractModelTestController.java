package org.kokzoz.dluid.content.test;

import lombok.Data;
import org.kokzoz.dluid.common.AbstractController;
import org.kokzoz.dluid.content.TabModelTestController;

@Data
public abstract class AbstractModelTestController extends AbstractController {
    private TabModelTestController tabModelTestController;

    public AbstractModelTestController(TabModelTestController tabModelTestController) {
        this.tabModelTestController = tabModelTestController;
    }
}
