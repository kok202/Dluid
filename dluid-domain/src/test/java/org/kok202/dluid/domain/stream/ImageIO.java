package org.kok202.dluid.domain.stream;

import org.kok202.dluid.domain.stream.image.ImageColorScale;
import org.kok202.dluid.domain.stream.image.ImageWriter;

public class ImageIO {
    public static void main(String[] args) throws  Exception{
        writeGrayImage();
        writeRGBImage();
    }

    public static void writeGrayImage(){
        StringRecordSet stringRecordSet = new StringRecordSet();
        stringRecordSet.setHeader(
                "(0,0)", "(1,0)", "(2,0)",
                "(0,1)", "(1,1)", "(2,1)",
                "(0,2)", "(1,2)", "(2,2)");
        stringRecordSet.addRecord(
                "1", "0.9", "0.8",
                "0.7", "0.6", "0.5",
                "0.4", "0.3", "0.2");
        NumericRecordSet numericRecordSet = NumericRecordSet.convertFrom(stringRecordSet);

        ImageWriter imageWriter = new ImageWriter();
        imageWriter.setWidth(3);
        imageWriter.setHeight(3);
        imageWriter.setImageColorScale(ImageColorScale.GRAY);
        imageWriter.setPixel(numericRecordSet.getRecordAsArray(0));
        imageWriter.write("C:\\Users\\user\\Downloads\\test_gray.png");
    }

    public static void writeRGBImage(){
        StringRecordSet stringRecordSet = new StringRecordSet();
        stringRecordSet.setHeader(
                "(0,0)r", "(0,0)g", "(0,0)b", "(1,0)r", "(1,0)g", "(1,0)b", "(2,0)r", "(2,0)g", "(2,0)b",
                "(0,1)r", "(0,1)g", "(0,1)b", "(1,1)r", "(1,1)g", "(1,1)b", "(2,1)r", "(2,1)g", "(2,1)b",
                "(0,2)r", "(0,2)g", "(0,2)b", "(1,2)r", "(1,2)g", "(1,2)b", "(2,2)r", "(2,2)g", "(2,2)b");
        stringRecordSet.addRecord(
                "1", "0", "0", "0", "1", "0", "0", "0", "1",
                "0.5", "0", "0", "0", "0.5", "0", "0", "0", "0.5",
                "0.1", "0", "0", "0", "0.1", "0", "0", "0", "0.1");
        NumericRecordSet numericRecordSet = NumericRecordSet.convertFrom(stringRecordSet);

        ImageWriter imageWriter = new ImageWriter();
        imageWriter.setWidth(3);
        imageWriter.setHeight(3);
        imageWriter.setImageColorScale(ImageColorScale.RGB);
        imageWriter.setPixel(numericRecordSet.getRecordAsArray(0));
        imageWriter.write("C:\\Users\\user\\Downloads\\test_rgb.png");
    }
}