package org.kok202.dluid.content.popup;

import org.kok202.dluid.adapter.file.ExtendedFileFinder;
import org.kok202.dluid.common.AbstractController;

public abstract class FileManagementPopUpController extends AbstractController {
    private ExtendedFileFinder extendedFileFinder;

    public FileManagementPopUpController(ExtendedFileFinder extendedFileFinder) {
        this.extendedFileFinder = extendedFileFinder;
    }

    public ExtendedFileFinder getExtendedFileFinder() {
        return extendedFileFinder;
    }
}
