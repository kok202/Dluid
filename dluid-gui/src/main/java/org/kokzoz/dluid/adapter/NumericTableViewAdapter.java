package org.kokzoz.dluid.adapter;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import lombok.Builder;
import org.kokzoz.dluid.domain.stream.NumericRecordSet;

import java.util.*;

public class NumericTableViewAdapter {
    private boolean editable = false;
    private EventHandler classificationEventHandler = (event) -> classificationCellListener();
    private Map<Integer, Double> classificationMaxValueMap;
    private static final String CLASSIFIED_CELL = "customHighlightCell";

    private TableView tableView;
    private TableColumn<ArrayList<Double>, Double>[] tableColumns;
    private NumericRecordSet numericRecordSet;

    @Builder
    public NumericTableViewAdapter(boolean editable, TableView tableView) {
        this.editable = editable;
        this.tableView = tableView;
    }

    public void setRecordSetAndRefresh(NumericRecordSet numericRecordSet) {
        this.numericRecordSet = numericRecordSet;
        ArrayList<String> header = refreshHeader();
        initializeColumn(header);
        refreshRecord();
    }

    private ArrayList<String> refreshHeader(){
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

    private void initializeColumn(ArrayList<String> header) {
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
            else{
                tableColumn.setCellFactory((column) -> new LabelTableCell());
            }

            tableColumns[columnIndex] = tableColumn;
            tableView.getColumns().add(tableColumns[columnIndex]);
        }
    }

    private void refreshRecord(){
        numericRecordSet.getRecords().forEach(record -> tableView.getItems().add(record));
    }

    public NumericRecordSet toNumericRecordSet(){
        if(!editable)
            return numericRecordSet;

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

    public void setClassificationStyle(boolean classification){
        if(classification) {
            tableView.removeEventHandler(MouseEvent.ANY, classificationEventHandler);
            tableView.addEventHandler(MouseEvent.ANY, classificationEventHandler);
            classificationMaxValueMap = new HashMap<>();
            for(int i = 0; i < numericRecordSet.getRecords().size(); i++)
                classificationMaxValueMap.put(i, Collections.max(numericRecordSet.getRecords().get(i)));
            classificationCellListener();
        }
        else{
            tableView.removeEventHandler(MouseEvent.ANY, classificationEventHandler);
            getAllTableCells().stream()
                    .filter(Objects::nonNull)
                    .filter(tableCell -> !tableCell.isEmpty())
                    .forEach(tableCell -> tableCell.getStyleClass().remove(CLASSIFIED_CELL));
        }
    }

    private void classificationCellListener(){
        getAllTableCells().stream()
                .filter(tableCell -> tableCell != null && !tableCell.isEmpty())
                .forEach(tableCell -> {
                    Double tableValue = (Double) tableCell.getItem();
                    int rowIndex = tableCell.getIndex();
                    if(rowIndex < 0 || rowIndex >= numericRecordSet.getRecordsSize())
                        return;
                    double maxValue = classificationMaxValueMap.getOrDefault(rowIndex, Double.MIN_VALUE);

                    tableCell.setText(String.format("%f" ,tableValue));
                    tableCell.getStyleClass().remove(CLASSIFIED_CELL);
                    if(tableValue == maxValue)
                        tableCell.getStyleClass().add(CLASSIFIED_CELL);
                });
    }

    private Set<TableCell> getAllTableCells() {
        Set<Node> lookupAll = tableView.lookupAll("*");
        Set<TableCell> returnTableCellSet = new HashSet<>();
        for (Node node : lookupAll){
            if (node instanceof TableCell){
                TableCell tableCell = (TableCell) node;
                returnTableCellSet.add(tableCell);
            }
        }
        return returnTableCellSet;
    }
}
