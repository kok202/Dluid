package org.kok202.deepblock.application.adapter.file;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.kok202.deepblock.ai.interfaces.AIInterface;
import org.kok202.deepblock.domain.stream.NumericRecordSet;
import org.kok202.deepblock.domain.stream.StringRecordSet;
import org.kok202.deepblock.domain.stream.csv.CsvWriter;
import org.kok202.deepblock.domain.stream.excel.ExcelWriter;

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
        NumericRecordSet numericRecordSet = AIInterface.getTestResultSet().getNumericRecordSet();
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
