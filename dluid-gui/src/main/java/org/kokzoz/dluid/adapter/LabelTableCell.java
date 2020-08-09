package org.kokzoz.dluid.adapter;

import javafx.scene.control.TableCell;

import java.util.ArrayList;

public class LabelTableCell extends TableCell<ArrayList<Double>, Double> {

    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty? null : String.format("%f" ,item));
    }
}