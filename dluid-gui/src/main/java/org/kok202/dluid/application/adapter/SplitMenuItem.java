package org.kok202.dluid.application.adapter;

import javafx.scene.control.MenuItem;
import lombok.Data;

@Data
public class SplitMenuItem<T> {
    private MenuItem menuItem;
    private T settingType;

    public SplitMenuItem(MenuItem menuItem, T settingType) {
        this.menuItem = menuItem;
        this.settingType = settingType;
    }
}
