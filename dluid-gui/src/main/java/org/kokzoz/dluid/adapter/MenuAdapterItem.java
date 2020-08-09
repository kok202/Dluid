package org.kokzoz.dluid.adapter;

import javafx.scene.control.MenuItem;
import lombok.Data;

@Data
public class MenuAdapterItem<T> {
    private MenuItem menuItem;
    private T settingType;

    public MenuAdapterItem(MenuItem menuItem, T settingType) {
        this.menuItem = menuItem;
        this.settingType = settingType;
    }
}
