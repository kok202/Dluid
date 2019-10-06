package org.kok202.deepblock.application.content.popup;

import org.kok202.deepblock.application.adapter.file.ExtendedFileFinder;
import org.kok202.deepblock.application.common.AbstractController;

public abstract class FileManagementPopUpController extends AbstractController {
    private ExtendedFileFinder extendedFileFinder;

    public FileManagementPopUpController(ExtendedFileFinder extendedFileFinder) {
        this.extendedFileFinder = extendedFileFinder;
    }

    public ExtendedFileFinder getExtendedFileFinder() {
        return extendedFileFinder;
    }
}
