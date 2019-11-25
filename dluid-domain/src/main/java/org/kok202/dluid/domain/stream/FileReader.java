package org.kok202.dluid.domain.stream;

import java.io.BufferedReader;
import java.io.File;

public class FileReader {
    public static String readStringFromFile(File file){
        StringBuffer stringBuffer = new StringBuffer();
        try(BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(file))) {
            String stringBufferLine;
            while ((stringBufferLine = bufferedReader.readLine()) != null) {
                stringBuffer.append(stringBufferLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
