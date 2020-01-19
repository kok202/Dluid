package org.kok202.dluid.application.adapter;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Builder;
import lombok.Setter;
import org.kok202.dluid.domain.stream.NumericRecordSet;

import java.util.ArrayList;

public class NumericTableViewAdapter {
    @Setter
    private boolean editable = false;
    @Setter
    private boolean highlight = false;
    private TableView tableView;
    private TableColumn<ArrayList<Double>, Double>[] tableColumns;

    @Builder
    public NumericTableViewAdapter(boolean editable, boolean highlight, TableView tableView) {
        this.editable = editable;
        this.highlight = highlight;
        this.tableView = tableView;
    }

    public void setRecordSetAndRefresh(NumericRecordSet numericRecordSet) {
        ArrayList<String> header = refreshHeader(numericRecordSet);
        initializeColumn(numericRecordSet, header);
        refreshRecord(numericRecordSet);
    }

    private ArrayList<String> refreshHeader(NumericRecordSet numericRecordSet){
        tableView.setEditable(true);
        tableView.getItems().clear();
        tableView.getColumns().clear();

        ArrayList<String> header = numericRecordSet.getHeader();
        if(header == null){
            header = new ArrayList<>();
            int columnSize = numericRecordSet.getColumnSize();
            for(int i = 0; i < columnSize; i++){
                header.add("Column " + i+1);
            }
        }
        return header;
    }

    private void initializeColumn(NumericRecordSet numericRecordSet, ArrayList<String> header) {
        tableColumns = new TableColumn[numericRecordSet.getColumnSize()];
        for(int i = 0; i < numericRecordSet.getColumnSize(); i ++){
            final int columnIndex = i;
            TableColumn<ArrayList<Double>, Double> tableColumn = new TableColumn<>(header.get(columnIndex));
            /*************************************************************************************************
             /* Initialize cell mapping
             *************************************************************************************************/
            tableColumn.setCellValueFactory(cellDataFeatures -> {
                // No data for mapped column
                if(columnIndex >= cellDataFeatures.getValue().size()){
                    return new SimpleDoubleProperty(0).asObject();
                }
                // Get i'th element from record and set it to i'th column
                double data = cellDataFeatures.getValue().get(columnIndex);
                return new SimpleDoubleProperty(data).asObject();
            });

            /*************************************************************************************************
             /* Initialize cell property
             *************************************************************************************************/
            if(editable){
                tableColumn.setCellFactory((column) -> new EditableTableCell());
                tableColumn.setOnEditCommit(cell -> {
                    ArrayList<Double> record = (ArrayList<Double>) tableView.getItems().get(cell.getTablePosition().getRow());
                    record.set(columnIndex, cell.getNewValue());
                });
            }
            else if(highlight){
                tableColumn.setCellFactory((column) -> new HighLightTableCell(numericRecordSet));
            }
            else{
                tableColumn.setCellFactory((column) -> new TableCell<>());
            }

            tableColumns[columnIndex] = tableColumn;
            tableView.getColumns().add(tableColumns[columnIndex]);
        }
    }

    private void refreshRecord(NumericRecordSet numericRecordSet){
        numericRecordSet.getRecords().forEach(record -> tableView.getItems().add(record));
    }

    public NumericRecordSet toNumericRecordSet(){
        ArrayList<String> header = new ArrayList<>();
        ArrayList<ArrayList<Double>> records = new ArrayList<>();

        for(TableColumn<ArrayList<Double>, Double> tableColumn : tableColumns) {
            header.add(tableColumn.getText());
        }
        for(int row = 0; row < tableView.getItems().size(); row++){
            ArrayList<Double> record = new ArrayList<>();
            for(int col = 0; col < tableColumns.length; col++){
                record.add(tableColumns[col].getCellObservableValue(row).getValue());
            }
            records.add(record);
        }

        NumericRecordSet numericRecordSet = new NumericRecordSet();
        numericRecordSet.setHeader(header);
        numericRecordSet.setRecords(records);
        return numericRecordSet;
    }
}
