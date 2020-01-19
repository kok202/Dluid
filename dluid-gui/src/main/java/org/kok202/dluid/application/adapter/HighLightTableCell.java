package org.kok202.dluid.application.adapter;

import javafx.scene.control.TableCell;
import org.kok202.dluid.domain.stream.NumericRecordSet;

import java.util.ArrayList;
import java.util.Collections;

public class HighLightTableCell extends TableCell<ArrayList<Double>, Double> {

    private NumericRecordSet numericRecordSet;
    private static final String HIGHLIGHT_CELL = "customHighLightCell";

    public HighLightTableCell(NumericRecordSet numericRecordSet) {
        this.numericRecordSet = numericRecordSet;
    }

    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if(empty){
            setText(null);
            getStyleClass().removeAll();
        }
        else{
            int rowIndex = getIndex();
            if(rowIndex < 0 || rowIndex >= numericRecordSet.getRecordsSize())
                return;
            double maxValue = Collections.max(numericRecordSet.getRecords().get(rowIndex));

            setText(String.format("%f" ,item));
            getStyleClass().removeAll();
            if(item == maxValue)
                getStyleClass().add(HIGHLIGHT_CELL);
        }
    }
}