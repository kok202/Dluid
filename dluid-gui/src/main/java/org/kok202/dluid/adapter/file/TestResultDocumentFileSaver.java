package org.kok202.dluid.adapter.file;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.domain.stream.NumericRecordSet;
import org.kok202.dluid.domain.stream.StringRecordSet;
import org.kok202.dluid.domain.stream.csv.CsvWriter;
import org.kok202.dluid.domain.stream.excel.ExcelWriter;

import java.io.File;

public class TestResultDocumentFileSaver extends FileSaver{

    public TestResultDocumentFileSaver(Button button) {
        super(button);
        getExtensionFilter().add(new FileChooser.ExtensionFilter("csv", "*.csv"));
        getExtensionFilter().add(new FileChooser.ExtensionFilter("xls", "*.xls"));
        getExtensionFilter().add(new FileChooser.ExtensionFilter("xlsx", "*.xlsx"));
    }

    @Override
    protected void saveContent(File file) {
        String filePath = file.getPath();
        NumericRecordSet numericRecordSet = AIFacade.getTestResultSet().getNumericRecordSet();
        StringRecordSet stringRecordSet = numericRecordSet.toStringRecordSet();

        if(filePath.endsWith(".xls")) {
            ExcelWriter excelWriter = new ExcelWriter();
            excelWriter.writeAsXLS(filePath, stringRecordSet);
        }
        else if(filePath.endsWith(".xlsx")) {
            ExcelWriter excelWriter = new ExcelWriter();
            excelWriter.writeAsXLSX(filePath, stringRecordSet);
        }
        else if(filePath.endsWith(".csv")) {
            CsvWriter csvWriter = new CsvWriter();
            csvWriter.write(filePath, stringRecordSet);
        }
    }
}
