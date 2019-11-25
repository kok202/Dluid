package org.kok202.dluid.application.adapter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import org.kok202.dluid.domain.stream.NumericRecordSet;

import java.util.ArrayList;

public class NumericTableViewAdapter {
    private TableView tableView;
    private TableColumn<ArrayList, Double>[] tableColumns;

    public NumericTableViewAdapter(TableView tableView) {
        this.tableView = tableView;
    }

    public void setRecordSetAndRefresh(NumericRecordSet numericRecordSet) {
        refreshHeader(numericRecordSet);
        refreshRecord(numericRecordSet);
    }

    private void refreshHeader(NumericRecordSet numericRecordSet){
        tableView.setEditable(true);
        tableView.getItems().clear();
        tableView.getColumns().clear();

        ArrayList<String> header = numericRecordSet.getHeader();
        if(header == null){
            header = new ArrayList<>();
            int columnSize = numericRecordSet.getRecords().get(0).size();
            for(int i = 0; i < columnSize; i++){
                header.add("Column " + i+1);
            }
        }

        tableColumns = null;
        tableColumns = new TableColumn[header.size()];
        for(int i = 0; i < header.size(); i ++){
            String head = header.get(i);
            tableColumns[i] = createTableColumn(i, head);
            tableView.getColumns().add(tableColumns[i]);
        }
    }

    private TableColumn<ArrayList, Double> createTableColumn(int columnIndex, String head){
        TableColumn<ArrayList, Double> tableColumn = new TableColumn<>(head);
        tableColumn.setCellValueFactory(cellDataFeatures -> mappingToColumn(columnIndex, cellDataFeatures));
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tableColumn.setOnEditCommit(cell -> {
            ArrayList<Double> record = (ArrayList<Double>) tableView
                    .getItems()
                    .get(cell.getTablePosition().getRow());
            record.set(columnIndex, cell.getNewValue());
        });
        return tableColumn;
    }

    private ObjectProperty mappingToColumn(int columnIndex, TableColumn.CellDataFeatures<ArrayList, Double> cellDataFeatures){
        // No data for mapped column
        if(columnIndex >= cellDataFeatures.getValue().size()){
            return new SimpleDoubleProperty(0).asObject();
        }
        // Get i'th element from record and set it to i'th column
        double data = (double) cellDataFeatures.getValue().get(columnIndex);
        return new SimpleDoubleProperty(data).asObject();
    }

    private void refreshRecord(NumericRecordSet numericRecordSet){
        numericRecordSet.getRecords().forEach(record ->{
            tableView.getItems().add(record);
        });
    }

    public NumericRecordSet toNumericRecordSet(){
        ArrayList<String> header = new ArrayList<>();
        ArrayList<ArrayList<Double>> records = new ArrayList<>();

        for(TableColumn<ArrayList, Double> tableColumn : tableColumns) {
            header.add(tableColumn.getText());
        }
        for(int row = 0; row < tableView.getItems().size(); row++){
            ArrayList<Double> record = new ArrayList<>();
            for(int col = 0; col < tableColumns.length; col++){
                record.add(getValue(row, col));
            }
            records.add(record);
        }

        NumericRecordSet numericRecordSet = new NumericRecordSet();
        numericRecordSet.setHeader(header);
        numericRecordSet.setRecords(records);
        return numericRecordSet;
    }

    private Double getValue(int row, int col) {
        return tableColumns[col].getCellObservableValue(row).getValue();
    }
}
