package org.kokzoz.dluid.content.popup;

import org.kokzoz.dluid.adapter.file.ExtendedFileFinder;
import org.kokzoz.dluid.common.AbstractController;

public abstract class FileManagementPopUpController extends AbstractController {
    private ExtendedFileFinder extendedFileFinder;

    public FileManagementPopUpController(ExtendedFileFinder extendedFileFinder) {
        this.extendedFileFinder = extendedFileFinder;
    }

    public ExtendedFileFinder getExtendedFileFinder() {
        return extendedFileFinder;
    }
}
