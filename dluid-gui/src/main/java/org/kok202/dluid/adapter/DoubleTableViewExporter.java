package org.kok202.dluid.adapter;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.kok202.dluid.domain.stream.NumericRecordSet;

import java.util.ArrayList;

public class DoubleTableViewExporter {
    private TableView tableView;
    private TableColumn<ArrayList, Double>[] tableColumns;
    private NumericRecordSet numericRecordSet;

    public DoubleTableViewExporter(TableView tableView) {
        this.tableView = tableView;
    }
}
